
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by ����� on 27.10.2020.
 */
public class HttpClient {

    private String text;
    private String id;
    private String xml_parameters;

    private HttpURLConnection httpClient;

    public HttpClient() {
    }

    public StringBuilder sendGet() throws Exception {

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Open.appProps.getProperty("login"), Open.appProps.getProperty("password").toCharArray());
            }
        });

        //HttpURLConnection httpClient = (HttpURLConnection) new URL(url+"/hs/Query"+"?Text="+text+"&XML_parameters="+xml_parameters).openConnection();
        httpClient = (HttpURLConnection) new URL(Open.appProps.getProperty("url")+"/hs/Query").openConnection();
        httpClient.setDoOutput(true);
        httpClient.setDoInput(true);
        httpClient.setUseCaches(false);
        httpClient.setInstanceFollowRedirects(false);
        httpClient.setRequestMethod("GET");
        httpClient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        //Разобраться, почему в параметрах не передаётся
        //httpClient.setRequestProperty("Text", text);
        httpClient.setRequestProperty("ID", id);
        httpClient.setRequestProperty("XML_parameters", xml_parameters);

        httpClient.connect();


        if (httpClient.getResponseCode() == 200){

            try (BufferedReader in = new BufferedReader( new InputStreamReader(httpClient.getInputStream(), StandardCharsets.UTF_8))) {

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                in.close();

                httpClient.disconnect();
                return response;

            }catch(IOException e){

                httpClient.disconnect();
                return null;
            }
        }
        httpClient.disconnect();
        return null;
    }

    public boolean getConnect() throws IOException {

        HttpURLConnection httpClient = (HttpURLConnection) new URL(Open.appProps.getProperty("url")).openConnection();

        if (httpClient.getResponseCode() == 200){
            httpClient.disconnect();
            return true;
        }

        httpClient.disconnect();
        return false;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setXml_parameters(String xml_parameters){
       this.xml_parameters =  xml_parameters;
    }

    public void  setID(String id){
        this.id = id;
    }
}
