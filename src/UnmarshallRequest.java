import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
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

    public ArrayList<RowRequest> getUnmarshall(StringBuilder request) throws ParserConfigurationException, IOException, SAXException {

         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();

        StringReader stringReader = new StringReader(String.valueOf(request));
        InputSource is = new InputSource(stringReader);

        //Document document = builder.parse(new InputSource(new StringReader(String.valueOf(request))));
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

        ArrayList<RowRequest> table = new ArrayList<RowRequest>();


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
    }

}
