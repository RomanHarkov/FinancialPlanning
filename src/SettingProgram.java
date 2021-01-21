import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Роман on 19.10.2020.
 */
@XmlRootElement(name = "Settings")
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingProgram {

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNameForForm() {
        return NameForForm;
    }

    public void setNameForForm(String nameForForm) {
        NameForForm = nameForForm;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Boolean getUpdate() {
        return Update;
    }

    public void setUpdate(Boolean update) {
        Update = update;
    }

    private String Mode;
    private String Section;
    private String Name;
    private String NameForForm;
    private String Type;
    private String Value;
    private Boolean Update;

    public SettingProgram(){



    }
}
