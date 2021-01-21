import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Роман on 14.10.2020.
 */
public class FormRegistration extends JFrame{

    private JButton ok = new JButton("Ок");
    private JButton cancel = new JButton("Отмена");

    public FormRegistration(){
        super("Регистрация");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel1 = new JPanel();
        panel1.add(ok);
        panel1.add(cancel);

        getContentPane().add(panel1).setBackground(Color.WHITE);

        setSize(new Dimension(300,300));
        setVisible(true);
        setResizable(false);


        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MainWindowNew mw = null;
                try {
                    mw = new MainWindowNew();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                mw.setLocationRelativeTo(null);
                setVisible(false);

            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(1);

            }
        });
    }

}
