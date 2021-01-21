import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by ����� on 19.10.2020.
 */
public class JaxbWorker {

    // ��������������� ������ �� XML �����
    public static SettingProgram fromXmlToObject(String filePath) {
        try {
            // ������� ������ JAXBContext - ����� ����� ��� JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(SettingProgram.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (SettingProgram) un.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ��������� ������ � XML ����
    public static void convertObjectToXml(SettingProgram settingProgram, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(SettingProgram.class);
            Marshaller marshaller = context.createMarshaller();
            // ������������� ���� ��� ������������ ������ XML � JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // ���������� ������� � ����
            marshaller.marshal(settingProgram, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
