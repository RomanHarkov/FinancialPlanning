import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Роман on 14.10.2020.
 */
public class FormSettings extends JFrame {

    private JTable tableSettings;

    public FormSettings() throws Exception {

        super("Настройки");

        //HttpClient obj = new HttpClient();
        //obj.setText(text);
        //obj.setXml_parameters(xml_parameters);

        //StringBuilder response = obj.sendGet();

/*        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            // получаем узлы с именем Mode
            // теперь XML полностью загружен в память в виде объекта Document
            NodeList nodeList = document.getElementsByTagName("Mode");

            for (int i = 0; i < nodeList.getLength(); i++) {

                String Mode = nodeList.item(i).getAttributes().getNamedItem("NameMode").getNodeValue();

                if (Mode.equals("Базовый")){

                    //NodeList nnSection = (NodeList) nodeList.item(i).getAttributes().getNamedItem("Entities.Section");
                    NodeList nnSection = (NodeList) nodeList.item(i).getAttributes().getNamedItem("Section");


                   // for (int y = 0; y < nnSection.getLength(); y++) {

                        //String sectionName = nnSection.item(y).getAttributes().getNamedItem("NameSection").getNodeValue();

                        //NodeList settList = (NodeList) nodeList.item(i).getAttributes().getNamedItem("NameSection");

                    //for (int r = 0; r < settList.getLength(); r++) {



                    //}
                 //   }
                }
            }

         } catch (Exception exc) {
            exc.printStackTrace();
        }


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableSettings = new JTable() {
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(1000, 400);
            }
        };

        TableColumn columnName = new TableColumn();
        columnName.setWidth(35);
        columnName.setHeaderValue("Настройка");

        TableColumn columnValue = new TableColumn();
        columnValue.setWidth(15);
        columnValue.setHeaderValue("Значение");

        tableSettings.addColumn(columnName);
        tableSettings.addColumn(columnValue);

        tableSettings.setVisible(true);
        JScrollPane scrollSettings = new JScrollPane(tableSettings);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());


        panel1.add(scrollSettings,BorderLayout.WEST);

        Font font = new Font("Verdana", Font.PLAIN, 10);
        final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabbedPane.setFont(font);

        tabbedPane.add("раздел1", panel1);


        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        content.add(buttons, BorderLayout.NORTH);

        JButton save = new JButton("Сохранить");
        save.setFont(font);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainWindowNew mw = null;
                try {
                    mw = new MainWindowNew();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                mw.setLocationRelativeTo(null);
                setVisible(false);
                //mw.setVisible(true);
            }
        });
        buttons.add(save);

        content.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(content);

        setPreferredSize(new Dimension(1000, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
*/
    }
}
