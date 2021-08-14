import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Роман on 12.01.2021.
 */
public class SaveSettingsProgram {

    public SaveSettingsProgram(){}

    public final void SaveFormElements(final String nameFrame, final ArrayList<RowRequest> FormElements) throws InterruptedException {

        Thread myThread = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                try{
                    FileOutputStream fos = new FileOutputStream(Open.pathSave + "/SettingsForms/"+nameFrame+".txt");
                    ObjectOutputStream outStream = new ObjectOutputStream(fos);
                    outStream.writeObject(FormElements);
                    outStream.flush();
                    outStream.close();

                }catch(Exception efos)
                {
                    System.out.println("Error: " + nameFrame + " " + efos.getMessage());
                    efos.printStackTrace();
                }
            }});

        myThread.start();
        myThread.sleep(1000);

    }

    public final ArrayList<RowRequest> RestoreFormElements(String nameFrame){

        ArrayList<RowRequest> fm = null;

        try{
            FileInputStream fis = new FileInputStream(Open.pathSave + "/SettingsForms/" + nameFrame + ".txt");
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            fm = (ArrayList) inputStream.readObject();
            inputStream.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        return fm;
    }

    public final void SaveArrList(final String id, final ArrayList arrayList) throws InterruptedException {

        Thread myThread = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                try{
                    FileOutputStream fos = new FileOutputStream(Open.pathSave + "/"+id+".txt");
                    ObjectOutputStream outStream = new ObjectOutputStream(fos);
                    outStream.writeObject(arrayList);
                    outStream.flush();
                    outStream.close();

                }catch(Exception efos)
                {
                    System.out.println("Error: " + id + " " + efos.getMessage());
                    efos.printStackTrace();
                }
            }});

        myThread.start();
        myThread.sleep(1000);

    }

    public final ArrayList RestoreArrList(String id){

        ArrayList arrayList = null;

        try{
            FileInputStream fis = new FileInputStream(Open.pathSave + "/" + id + ".txt");
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            arrayList = (ArrayList) inputStream.readObject();
            inputStream.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        return arrayList;
    }

}
