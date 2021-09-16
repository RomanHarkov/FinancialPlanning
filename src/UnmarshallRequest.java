import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Роман on 15.11.2020.
 */
public class UnmarshallRequest {

    public UnmarshallRequest (){

    }

    //Парсинг XML
    /*public ArrayList<RowRequest> getUnmarshall(StringBuilder request) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringReader stringReader = new StringReader(String.valueOf(request));
        InputSource is = new InputSource(stringReader);

        Document document = builder.parse(is);
        document.getDocumentElement().normalize();

        NodeList columnNodeList = document.getElementsByTagName("column");
        NodeList rowNodeList    = document.getElementsByTagName("row");

        ArrayList<String> columns = new ArrayList<String>();

        for (int y=0; y < columnNodeList.getLength(); y++){

            Node columnNode = columnNodeList.item(y);
            String columnValue = ((DeferredElementImpl) columnNode).getElementsByTagName("Name").item(0).getTextContent();

            columns.add(columnValue);

        }

        ArrayList<RowRequest> table = new ArrayList();

        for (int i = 0; i < rowNodeList.getLength(); i++){

            RowRequest newRow = new RowRequest();

            Node rowNode = rowNodeList.item(i);

            for (int col = 0; col < columns.size(); col++){

                String RowValue = ((DeferredElementImpl) rowNode).getElementsByTagName("Value").item(col).getTextContent();

                newRow.addRow(columns.get(col), RowValue);

            }

            table.add(newRow);
        }

        return  table;
    }*/


    //Парсинг JSON
    public ArrayList<RowRequest> getUnmarshallJSON(StringBuilder request) throws ParseException {

        ArrayList<RowRequest> table = new ArrayList();

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(String.valueOf(request));
        JSONObject value = (JSONObject) ((JSONObject) obj).get("#value");

        JSONArray columns = (JSONArray) value.get("column");
        JSONArray rows = (JSONArray) value.get("row");

        if (rows==null){}else {

            for (int row = 0; row < rows.size(); row++) {

                RowRequest newRow = new RowRequest();

                JSONArray arrRow = (JSONArray) rows.get(row);

                    for (int col = 0; col < columns.size(); col++) {

                    JSONObject strRow = (JSONObject) arrRow.get(col);
                    String RowValue = (String) strRow.get("#value");

                    JSONObject nameColumn = (JSONObject) columns.get(col);
                    String colValue = (String) nameColumn.get("Name");

                    newRow.addRow(colValue, RowValue);

                }

                table.add(newRow);
            }
        }

        return table;

    }

    }
