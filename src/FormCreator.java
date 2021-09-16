
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by ����� on 13.10.2020.
 */
public final class FormCreator {

    private char dm = (char) 34;
    //Проверим, есть ли подключение
    private Boolean isConnect = Open.isConnect;
    //Boolean isConnect = false;

    public FormCreator(){ }

    public JInternalFrame createJInternalFrame(final String name) throws Exception {

        ArrayList<RowRequest> FormElements = new ArrayList();

        JInternalFrame jInternalFrame = new JInternalFrame(name, true, true, true);
        jInternalFrame.setLocation(100 + Open.windowOffset, 70 + Open.windowOffset);
        jInternalFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));


        //Если нет подключения - загрузим сохраненные ранее настройки формы,если они есть
        if (!isConnect){

            File fileFormElements = new File(Open.pathSave + "/SettingForms/" + name + ".txt");
            if (fileFormElements.exists()) {
                FormElements = Open.saveSettingsProgram.RestoreFormElements(name);
            }

        }else {

            String xml_parameters = "<?xml version="+dm+"1.0"+dm+" encoding="+dm+"UTF-8"+dm+"?><XML_Parameters><Parameter>ParameterName=Section ParameterValue=" + name + "</Parameter></XML_Parameters>";

            FormElements = Open.LoadValue("FormSettings", xml_parameters);
            final ArrayList<RowRequest> finalFormElements = FormElements;
            Thread myThread = new Thread(new Runnable() {
                   public void run() //Этот метод будет выполняться в побочном потоке
                   {

                       try {
                           Open.saveSettingsProgram.SaveFormElements(name, finalFormElements);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                });
                myThread.start();
        }

        if (FormElements.size() > 0){

            String [][] listElements = new String [FormElements.size()][3];
            ArrayList <String> Groups = new ArrayList<>();

            for (int ElementIndex=0; ElementIndex < FormElements.size(); ElementIndex++){

                HashMap<String,String> row = FormElements.get(ElementIndex).getRow();

                String typeElement = row.get("Type");
                String nameElement = row.get("Name");

                listElements[ElementIndex][0] = nameElement;
                listElements[ElementIndex][1] = typeElement;
                listElements[ElementIndex][2] = row.get("Parent");

                if (typeElement.equals("GroupHorizontal")){

                    Groups.add(nameElement);
                }
            }

            for (int GroupIndex = 0; GroupIndex < Groups.size(); GroupIndex++){

                JPanel newPanel = new JPanel();
                newPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


                for (int i = 0; i < listElements.length; i++){

                    if (listElements[i][2].equals(Groups.get(GroupIndex))){

                        if (!listElements[i][1].equals("GroupHorizontal")) {

                            Component newElement = addFormElement(listElements[i][1], listElements[i][0],name);

                            if (!newElement.equals(null)){
                                newPanel.add(newElement, BorderLayout.WEST);
                            }
                        }
                    }
                }
                mainPanel.add(newPanel);
            }
        }

        jInternalFrame.add(mainPanel);
        jInternalFrame.pack();
        jInternalFrame.setVisible(true);

        Open.windowOffset = Open.windowOffset + Open.indexOffset;

        return jInternalFrame;
    }

    private Component addFormElement(String typeElement, final String nameElement, String nameSection) throws Exception {

        if (typeElement.equals("Table")){

            JPanel panelTable = new JPanel();
            panelTable.setLayout(new BoxLayout(panelTable,BoxLayout.Y_AXIS));

            DefaultTableModel tableModel = new DefaultTableModel();
            JTable table = new JTable(tableModel);

            //String xml_parameters = "<?xml version="+dm+"1.0"+dm+" encoding="+dm+"UTF-8"+dm+"?><XML_Parameters><Parameter>ParameterName=Entities.Section ParameterValue=" + nameSection + "</Parameter><Parameter>ParameterName=TableName ParameterValue=" + nameElement + "</Parameter></XML_Parameters>";
            String xml_parameters = "<?xml version="+dm+"1.0"+dm+" encoding="+dm+"UTF-8"+dm+"?><XML_Parameters><Parameter>ParameterName=Section ParameterValue=" + nameSection + "</Parameter><Parameter>ParameterName=TableName ParameterValue=" + nameElement + "</Parameter></XML_Parameters>";

            ArrayList<RowRequest> TableColumns = Open.LoadValue("TableColumns", xml_parameters);

            ArrayList<String> listCol = new ArrayList<>();

            for (int i = 0; i<TableColumns.size(); i++){

                HashMap<String, String> row_tc = TableColumns.get(i).getRow();

                String columnName = row_tc.get("ИмяКолонки");

                tableModel.addColumn(columnName);

                if (row_tc.get("ТипКолонки").equals("List")){

                    ArrayList<RowRequest> Indicators = Open.listValues.get("IndicatorsOfSection");

                    for (int y=0; y < Indicators.size(); y++){

                        HashMap<String,String> Row = Indicators.get(y).getRow();

                        if (Row.get("Section").equals(nameSection) & Row.get("Table").equals(nameElement) & Row.get("Column").equals(columnName)){
                            listCol.add(Row.get("Name"));
                        }
                    }
                }
            }

            TableColumn column = table.getColumnModel().getColumn(0);
            JComboBox comboBox = new JComboBox();

            for(String col:listCol){comboBox.addItem(col);}

            column.setCellEditor(new DefaultCellEditor(comboBox));

            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelButton.setBackground(Color.DARK_GRAY);
            panelButton.setUI(new BasicPanelUI());

            String addPath = "src/Images/add1.png";
            String delPath = "src/Images/del1.png";

            panelButton.add(addButtonTable("Add", table, tableModel,addPath));
            panelButton.add(addButtonTable("Delete", table, tableModel,delPath));

            Font font = new Font("Verdana", Font.PLAIN, 11);
            table.setFont(font);

            table.setName(nameElement);
            table.setBackground(Color.WHITE);
            table.setSize(250, 100);

            panelTable.add(panelButton);
            panelTable.add(new JScrollPane(table));

            return panelTable;

        } else if (typeElement.equals("Field")){

            JLabel textLabel = new JLabel(nameElement);
            JTextField jTextField = new JTextField(15);

            Box boxField = Box.createHorizontalBox();
            //boxField.setLayout(new BoxLayout(boxField,BoxLayout.LINE_AXIS));
            boxField.add(textLabel);
            boxField.add(Box.createHorizontalStrut(6));
            boxField.add(jTextField);

            return boxField;

        }else if (typeElement.equals("Button")){

            JButton button = new JButton(nameElement);
            button.setUI(new BasicButtonUI());
            button.setVerticalTextPosition(AbstractButton.CENTER);
            button.setHorizontalTextPosition(AbstractButton.LEADING);
            /*button.setBackground(new Color(0,51,204));//import java.awt.Color;
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);*/

            final ActionListener actionListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("" + nameElement);
                }
            };
            button.addActionListener(actionListener);

            return button;
        }

        return null;
    }

    private JButton addButtonTable(String action, JTable table, DefaultTableModel tableModel, String addPath) throws IOException {

        JButton button = new JButton(new ImageIcon(addPath));

        button.setPreferredSize(new Dimension(15,15));

        button.setUI(new BasicButtonUI());
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.LEADING);

        button.addActionListener(addActionButton(table, tableModel, action));
        return button;
    }

    final private ActionListener addActionButton(final JTable table, final DefaultTableModel tableModel, final String action){

        final ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (action.equals("Delete")){

                    int sel = table.getSelectedRow();//получаем номер выделенной строки

                    if (sel!=-1){

                        tableModel.removeRow(sel);//удаляем данную позицию из ArrayList в TableModel
                        //tableModel.fireTableDataChanged();

                    }
                }else if (action.equals("Add")){

                    tableModel.addRow(new Vector());

                }
                table.changeSelection(table.getRowCount() - 1,0,false,false);
            }
        };

        return actionListener;
    }

}
