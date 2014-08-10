/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 10.08.14
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class  Main extends JFrame {
    static TestClient client = new TestClient();

    public Main() {


        super("Тестовое окно");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Verdana", Font.PLAIN, 10);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);

//        JPanel buttons = new JPanel();
        //content.add(buttons, BorderLayout.NORTH);

        JButton button1 = new JButton("поиск поездов");
        JButton button2 = new JButton("расписание поездов");
        JButton button3 = new JButton("купить билет");
        button1.setFont(font);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    client.send();
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });

        JPanel jPanel1 = new JPanel();
        //jPanel1.setLayout(new BorderLayout());
        jPanel1.add(button1);
        jPanel1.add(button2);
        jPanel1.add(button3);

        tabbedPane.addTab("Клиент", jPanel1);
        tabbedPane.addTab("Сотрудник", new JPanel());


        content.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(content);

        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new Main();
            }
        });
    }
}
