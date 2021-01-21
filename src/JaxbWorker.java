import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Роман on 19.10.2020.
 */
public class JaxbWorker {

    // восстанавливаем объект из XML файла
    public static SettingProgram fromXmlToObject(String filePath) {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(SettingProgram.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (SettingProgram) un.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // сохраняем объект в XML файл
    public static void convertObjectToXml(SettingProgram settingProgram, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(SettingProgram.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(settingProgram, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
