import Entities.ListValue;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Роман on 14.10.2020.
 */
public class Open {

    public static HttpClient httpConnect = new HttpClient();
    public static HashMap<Integer,Color> ColorButtons = new HashMap<Integer,Color>();
    public static HashMap<String,ArrayList> listValues = new HashMap<String,ArrayList>();
    public static int windowOffset = 0;
    public static final int indexOffset = 30;
    public static String pathSave = "C:/FinancialPlanning";
    //public static ArrayList<RowRequest> buttonsMainWindow = new ArrayList();
    public static SaveSettingsProgram saveSettingsProgram = new SaveSettingsProgram();
    public static final String actualVersion = "1.0";
    public static Boolean isConnect;
    public static Properties appProps = new Properties();
    public static ArrayList<String> listSettings = getListSettings();



    public static void main(String args[]) throws Exception {

        /*char dm = (char) 34;
        String xml_parameters = "<?xml version="+dm+"1.0"+dm+" encoding="+dm+"UTF-8"+dm+"?><XML_Parameters><Parameter>ParameterName=Entities.Section ParameterValue=Активы и пассивы</Parameter><Parameter>ParameterName=TableName ParameterValue=Активы</Parameter></XML_Parameters>";

        ArrayList<RowRequest> TableColumns = LoadValue("TableColumns", xml_parameters);

        for (int i = 0;i<TableColumns.size();i++){

            //jTable.addColumn("");

        }*/

        appProps.load(new FileInputStream("src/Settings/connection parameters.properties"));

        /*Проверяем, есть ли соединение с базой настроек
          Инициализируем переменную для будущего использования*/
        isConnect = httpConnect.getConnect();


        //Инициализируем
        LoadInitialValues(isConnect);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        ColorButtons.put(0, Color.ORANGE);
        ColorButtons.put(1, Color.PINK);
        ColorButtons.put(2, Color.GREEN);
        ColorButtons.put(3, Color.DARK_GRAY);
        ColorButtons.put(4, Color.BLUE);
        ColorButtons.put(5, Color.YELLOW);

        File folder = new File(pathSave);
        if (!folder.exists()) {
            folder.mkdir();

        }
        File folderSettingForms = new File(pathSave+"/SettingsForms");
        if (!folderSettingForms.exists()) {
            folderSettingForms.mkdir();

        }

        String mess = getMessage(isConnect);

        Start start = new Start(mess);
        //start.setLocationRelativeTo(null);
        start.setVisible(true);


        /* выполнение кода из строки
        String code="System.out.println(\"Hello, World!\")";
        Interpreter i = new Interpreter();
        try {
            i.eval(code);
        } catch (EvalError ex) {
        System.out.println(ex.getMessage());
        }
        */

    }

    private static String getMessage(Boolean isConnect) throws IOException {

        String mess = "";

        if (!isConnect){return mess = "Нет связи с сервером...";}

        return mess;

    }


    public static HttpClient gethttpConnect(){

        return httpConnect;
    }

    public static void LoadInitialValues(Boolean isConnect) throws Exception {

        //Получить по списку данные из 1С, записать их куда-то и сохранить в файл

        if (!isConnect){

        }else {
        for (int i = 0; i<listSettings.size(); i++){

            final String nameList = listSettings.get(i);
            final ArrayList<RowRequest> value = LoadValue(nameList);

            listValues.put(nameList, value);

            Thread myThread = new Thread(new Runnable() {
                public void run() //Этот метод будет выполняться в побочном потоке
                {

                    try {
                        Open.saveSettingsProgram.SaveArrList(nameList, value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            myThread.start();

            }
        }

    }

    public static ArrayList LoadValue (String value) throws Exception {

        return arrayListValue(value, "");
    }

    public static ArrayList LoadValue (String value, String XmlParameters) throws Exception {

        return arrayListValue(value, XmlParameters);
    }

    private static ArrayList arrayListValue(String id, String XmlParameters) throws Exception {

        HttpClient hc = gethttpConnect();
        hc.setXml_parameters(XmlParameters);
        hc.setID(id);
        StringBuilder responseActive = hc.sendGet();

        //return (ArrayList) new UnmarshallRequest().getUnmarshall(responseActive);
        return (ArrayList) new UnmarshallRequest().getUnmarshallJSON(responseActive);

    }

    //Список ID настроек для загрузки
    private static ArrayList<String> getListSettings(){

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("ListOfSections");
        arrayList.add("IndicatorsOfSection");


        return arrayList;
    }

}
