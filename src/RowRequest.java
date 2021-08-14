import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Роман on 20.11.2020.
 */
public class RowRequest implements Serializable{

    private HashMap<String,String> row = new HashMap<String,String>();

    public RowRequest(){

    }

    public void addRow(String key,String value) {
        this.row.put(key,value);
    }

    public HashMap<String, String> getRow() {
        return row;
    }
}
