
/**
 * Created by Роман on 30.10.2020.
 */
public class MainRequest {

    private final String text = "ВЫБРАТЬ Запросы.Текст КАК Текст ИЗ Справочник.Запросы КАК Запросы ГДЕ Запросы.ID =  &ID";
    private String xml_parameters;
    private final char dm = (char) 34;

    public MainRequest(){

    }

    public StringBuilder sendGet(String id) throws Exception {

        xml_parameters = "<?xml version="+dm+"1.0"+dm+" encoding="+dm+"UTF-8"+dm+"?><XML_Parameters><Parameter>ParameterName=ID ParameterValue=" + id + "</Parameter></XML_Parameters>";

        HttpClient hc = Open.gethttpConnect();
        hc.setText(text);
        hc.setXml_parameters(xml_parameters);
        StringBuilder response = hc.sendGet();

        return response;
    }
}
