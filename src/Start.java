
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
//import Images.*;

/**
 * Created by Роман on 14.10.2020.
 */
public class Start extends JFrame{

    private JButton ok = new JButton("Ок");
    private JButton cancel = new JButton("Отмена");
    private JButton registration = new JButton("Регистрация");


    private JTextField loginField;
    private JPasswordField passwordField;
    private String StartMessage;
    private JTextPane  tfStartMessage;

    public Start(String message) {

        super("Вход в систему");
        this.StartMessage = message;
        //**********************************************************************
        //JWindow window = new JWindow();

        //window.getContentPane().add( new JLabel("", new ImageIcon(getClass().getResource("/Images/fin-plan-cropped.jpg")), SwingConstants.CENTER));
       // window.setBounds(400, 400, 400, 400);
        //window.setLocationRelativeTo(null);
        //window.setVisible(true);
        //try {
        //    Thread.sleep(3000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}

        //window.setVisible(false);


        //*********************************************************************
        setSize(400,400);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel BgPanel = new BgPanel();
        //BgPanel.setLayout(new FlowLayout());

        setContentPane(BgPanel);

        Box box1 = Box.createVerticalBox();
        JLabel loginLabel = new JLabel("Логин:");
        loginField = new JTextField(15);
        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(loginField);

        JLabel loginLabel2 = new JLabel("Пароль:");
        passwordField = new JPasswordField(15);
        box1.add(loginLabel2);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(passwordField);

        JLabel loginLabel3 = new JLabel();
        tfStartMessage = new JTextPane();
        tfStartMessage.setText(StartMessage);
        tfStartMessage.setEditable(false);
        box1.add(loginLabel3);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(tfStartMessage);


        Box box2 = Box.createHorizontalBox();
        box2.add(Box.createHorizontalGlue());
        box2.add(ok);
        box2.add(Box.createHorizontalStrut(12));
        box2.add(cancel);
        box2.add(Box.createHorizontalStrut(12));
        box2.add(registration);

        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(Box.createVerticalStrut(17));
        mainBox.add(box2);

        setLayout(new FlowLayout());

        getContentPane().add(mainBox);

        //setContentPane(new BgPanel());


        pack();

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    MainWindowNew mw = new MainWindowNew();
                    mw.setLocationRelativeTo(null);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                setVisible(false);

            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(1);

            }
        });

        registration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FormRegistration rg = new FormRegistration();
                rg.setLocationRelativeTo(null);
                setVisible(false);

            }
        });
    }

    class BgPanel extends JPanel{
        Image im;

        BgPanel(){
            try {
                im = ImageIO.read(new File("src/Images/StartBackgroundImage.jpg"));

            } catch (IOException e) {}					// делаю это в конструкторе что бы не грузить PainComponent
        }
        public void paintComponent(Graphics g){
            g.drawImage(im, 0, 0,300,200, null); 			// непосредственно рисую сам фон на Jpanel
        }
    }
}