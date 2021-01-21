
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by ����� on 13.10.2020.
 */
public class FormCreator {

    private char dm = (char) 34;

    public FormCreator(){ }

    public JInternalFrame createJInternalFrame(String name) throws Exception {

        ArrayList<RowRequest> FormElements = new ArrayList();

        JInternalFrame jInternalFrame = new JInternalFrame(name, true, true, true);
        jInternalFrame.setLocation(100 + Open.windowOffset, 70 + Open.windowOffset);
        jInternalFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        //Проверим, есть ли подключение
        Boolean isConnect = Open.httpConnect.getConnect();
        //Boolean isConnect = false;

        //Если его нет - загрузим сохраненные ранее настройки формы,если они есть
        if (!isConnect){

            File fileFormElements = new File(Open.pathSave + "/SettingForms/" + name + ".txt");
            if (fileFormElements.exists()) {
                FormElements = Open.saveSettingsProgram.RestoreFormElements(name);
            }

        }else {

            String xml_parameters = "<?xml version="+dm+"1.0"+dm+" encoding="+dm+"UTF-8"+dm+"?><XML_Parameters><Parameter>ParameterName=Section ParameterValue=" + name + "</Parameter></XML_Parameters>";

            FormElements = Open.LoadValue("FormSettings", xml_parameters);
            Open.saveSettingsProgram.SaveFormElements(name, FormElements);

        }

        if (FormElements.size() > 0){

            String [][] listElements = new String [FormElements.size()][3];
            ArrayList <String> Groups = new ArrayList<>();

            for (int ElementIndex=0; ElementIndex < FormElements.size(); ElementIndex++){

                String typeElement = FormElements.get(ElementIndex).getRow().get("Type");
                String nameElement = FormElements.get(ElementIndex).getRow().get("Name");
                String parentElement = FormElements.get(ElementIndex).getRow().get("Parent");

                listElements[ElementIndex][0] = nameElement;
                listElements[ElementIndex][1] = typeElement;
                listElements[ElementIndex][2] = parentElement;

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

            String xml_parameters = "<?xml version="+dm+"1.0"+dm+" encoding="+dm+"UTF-8"+dm+"?><XML_Parameters><Parameter>ParameterName=Section ParameterValue=" + nameSection + "</Parameter><Parameter>ParameterName=TableName ParameterValue=" + nameElement + "</Parameter></XML_Parameters>";

            ArrayList<RowRequest> TableColumns = Open.LoadValue("TableColumns", xml_parameters);

            ArrayList<String> listCol = new ArrayList<>();

            for (int i = 0; i<TableColumns.size(); i++){

                String columnName = TableColumns.get(i).getRow().get("ИмяКолонки");

                tableModel.addColumn(columnName);

                if (TableColumns.get(i).getRow().get("ТипКолонки").equals("List")){

                    String IDList = TableColumns.get(i).getRow().get("ИдентификаторСвязиСписка");

                    ArrayList<RowRequest> list = Open.listValues.get(IDList);

                    for (int y=0;y<list.size();y++){
                        listCol.add(list.get(y).getRow().get("Наименование"));
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

    private DefaultTableModel addTableColumns(DefaultTableModel TableModel,String name,String nameElement, JTable table) throws Exception {

        String xml_parameters = "<?xml version="+dm+"1.0"+dm+" encoding="+dm+"UTF-8"+dm+"?><XML_Parameters><Parameter>ParameterName=Section ParameterValue=" + name + "</Parameter><Parameter>ParameterName=TableName ParameterValue=" + nameElement + "</Parameter></XML_Parameters>";

        ArrayList<RowRequest> TableColumns = Open.LoadValue("TableColumns", xml_parameters);

        for (int i = 0; i<TableColumns.size(); i++){

            String columnName = TableColumns.get(i).getRow().get("ИмяКолонки");

            TableModel.addColumn(columnName);

            if (TableColumns.get(i).getRow().get("ТипКолонки").equals("List")){

                String IDList = TableColumns.get(i).getRow().get("ИдентификаторСвязиСписка");

                JComboBox<String> comboBox = new JComboBox<>();

                TableColumn column = table.getColumnModel().getColumn(i);

                ArrayList<RowRequest> list = Open.listValues.get(IDList);

                for (int y=0;y<list.size();y++){

                    comboBox.addItem(list.get(y).getRow().get("Наименование"));

                }

                column.setCellEditor(new DefaultCellEditor(comboBox));
                //table.setColumnSelectionAllowed(true);
            }

        }

        return TableModel;
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
