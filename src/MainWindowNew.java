
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Роман on 02.11.2020.
 */
public class MainWindowNew extends JFrame {

    private JDesktopPane desktopPane = new JDesktopPane(){

        private final ImageIcon image = new ImageIcon("src/Images/MainWindowBackground.jpg");

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = (desktopPane.getWidth() - image.getIconWidth()) / 2;
            int y = (desktopPane.getHeight() - image.getIconHeight()) / 2;
            g.drawImage(image.getImage(), x, y, this);
        }

    };
    private JMenuBar bar = new JMenuBar();

    public MainWindowNew() throws Exception {

        setTitle("Финансовое планирование" + " ver. " + Open.actualVersion);
        //setLocationRelativeTo(null);
        setPanel(desktopPane, "Panel", 0, 0);
        setDesktopPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenu fileMenu = new JMenu("Файл");

        JMenuItem menuItem1 = new JMenuItem("Настройки");
        fileMenu.add(menuItem1);

        bar.add(fileMenu);
        setJMenuBar(bar);
        setSize(1500, 800);
        setVisible(true);

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FormSettings st = null;
                try {
                    st = new FormSettings();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                st.setLocationRelativeTo(null);
                setVisible(false);

            }
        });

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

                /*try{
                    FileOutputStream fos = new FileOutputStream(Open.pathSave + "/SettingsForm.txt");
                    ObjectOutputStream outStream = new ObjectOutputStream(fos);
                    outStream.writeObject(Open.settingsForm);
                    outStream.flush();
                    outStream.close();
                }catch(Exception efos)
                {
                    System.out.println("Error" + efos.getMessage());
                }

                System.exit(0);*/
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void setDesktopPane(){

        desktopPane.setBackground(Color.WHITE);
        add(desktopPane, BorderLayout.CENTER);
    }

    private void setPanel(JDesktopPane jd, String name, int loc1, int loc2) throws Exception {

        StringBuilder resp = null;

        JPanel panel = new JPanel();
        panel.setName(name);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setSize(1500, 40);

        ArrayList<RowRequest> buttons = Open.listValues.get("ListOfSections");

        if (buttons.size() > 0) {
            for (int buttIndex = 0; buttIndex < buttons.size(); buttIndex++) {

                //String nameEl = Open.buttonsMainWindow.get(buttIndex);
                String nameEl = buttons.get(buttIndex).getRow().get("Наименование");

                addButton(panel, nameEl, Open.ColorButtons.get(buttIndex));

                final String finalName = nameEl;
                //Thread myThread = new Thread(new Runnable() {
                 //       public void run() //Этот метод будет выполняться в побочном потоке
                //        {
                            FormCreator formCreator = new FormCreator();
                            JInternalFrame NewFrame = null;
                            try {
                                NewFrame = formCreator.createJInternalFrame(finalName);
                                NewFrame.setVisible(false);

                                desktopPane.add(NewFrame);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    //});
                    //myThread.start();
                    //myThread.sleep(1000);
                //}
            }

        panel.setLocation(loc1, loc2);
        panel.setVisible(true);
        jd.add(panel);

    }

    private void addButton(JPanel panel, String name, Color color) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        JButton button = new JButton(name);
        button.setUI(new BasicButtonUI());

        button.setBackground(color);
        ActionListener actionListener = new TestActionListener(name);
        button.addActionListener(actionListener);

        panel.add(button);
    }

    public class TestActionListener implements ActionListener {

        public String name;
        private JInternalFrame[] AllFrames;

        public TestActionListener(String name) {

            this.name = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Boolean isOpen = false;
            int index=0;

            AllFrames = desktopPane.getAllFrames();

            if (AllFrames.length > 0 ){

                for (int i=0; i < AllFrames.length; i++){

                    if (AllFrames[i].getTitle().equals(name)){

                        if (AllFrames[i].isVisible()){
                            isOpen = true;
                        }
                        index = i;
                        break;
                    }
                }

                if (isOpen){

                    AllFrames[index].setVisible(false);
                    Open.windowOffset = Open.windowOffset - Open.indexOffset;

                }else {

                        for (JInternalFrame fr: AllFrames) {
                            if (fr.getTitle().equals(name)){
                                fr.setVisible(true);
                            }
                        }
                }

            } else {

                    for (JInternalFrame fr: AllFrames) {
                        if (fr.getTitle().equals(name)){
                            fr.setVisible(true);
                        }
                    }

            }
        }
    }

}
