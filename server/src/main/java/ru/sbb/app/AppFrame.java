package ru.sbb.app;

import ru.sbb.request.AddTrainRequest;
import ru.sbb.request.GetAllTrainsRequest;
import ru.sbb.request.GetStationScheduleRequest;
import ru.sbb.request.GetTrainPassengersRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 10.08.14
 */
public class AppFrame extends JFrame {
    Client client;



    public AppFrame() {

        super("sbb");
        try {
            client  = new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            Client client = new Client();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Verdana", Font.PLAIN, 10);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);

        Font font1 = new Font("Jokerman", Font.PLAIN, 20);
        JPanel content1 = new JPanel();
        content1.setLayout(new BorderLayout());
        final JTabbedPane tabbedPane1 = new JTabbedPane();
        tabbedPane1.setFont(font1);
        JPanel jPanel3 = new JPanel();

       JPanel jPanel5 = new JPanel();

        tabbedPane1.addTab("поиск поезда", jPanel3);
        tabbedPane1.addTab("расписание поездов", getStationScheduleRequestPanel() );
        tabbedPane1.addTab("купить билет", jPanel5);
        content1.add(tabbedPane1, BorderLayout.CENTER);
        content1.add(tabbedPane1);

        tabbedPane.addTab("Клиент", content1);

        JPanel content2 = new JPanel();
        content2.setLayout(new BorderLayout());
//        Label R2 = new Label("пароль:");
//        final TextField n1 = new TextField(20);
//        content2.add(R2);
//        content2.add(n1);
        final JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.setFont(font1);

        JPanel jPanel7 = new JPanel();
        JPanel jPanel8 = new JPanel();


        tabbedPane2.addTab("добавить поезд", getAddTrainRequestPanel());
        tabbedPane2.addTab("добавить станцию", jPanel7);
        tabbedPane2.addTab("добавить расписание", jPanel8);
        tabbedPane2.addTab("просмотр поездов", getAllTrainsPanel());
        tabbedPane2.addTab("просмотр пассажиров",getPassengersByTrainPanel() );
        //content2.add(tabbedPane2, BorderLayout.CENTER);

        content2.add(tabbedPane2);


        tabbedPane.addTab("Сотрудник", content2);


        content.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(content);

        setPreferredSize(new Dimension(700, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JPanel getAddTrainRequestPanel(){
        JPanel jPanel = new JPanel();
        JPanel jPanel4 = new JPanel();
        //jPanel4.setLayout(new BoxLayout(jPanel4, BoxLayout.X_AXIS));
        jPanel4.setLayout(new GridLayout(4, 2));

        Label R1 = new Label("номер поезда:");
        final TextField n1 = new TextField(20);
        Label R2 = new Label("сместимость поезда:");
        final TextField n2 = new TextField(20);
        Label R3 = new Label("пароль:");
        final TextField n3 = new TextField(20);
        final JTextArea ta = new JTextArea();

        JButton button1 = new JButton("добавить поезд");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AddTrainRequest request = new AddTrainRequest(Integer.parseInt(n1.getText()),Integer.parseInt(n2.getText()),n3.getText());
                    System.out.println(request);
                    client.send(request);
                    ta.setText("");
                    ta.append(client.receive());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }
        });

        jPanel4.add(R1);
        jPanel4.add(n1);
        jPanel4.add(R2);
        jPanel4.add(n2);
        jPanel4.add(R3);
        jPanel4.add(n3);
        jPanel4.add(button1);
        jPanel4.add(ta);

        jPanel.add(jPanel4);
        return jPanel;
    }

    JPanel getStationScheduleRequestPanel(){
        JButton button1 = new JButton("поиск поездов");
        JPanel jPanel4 = new JPanel();
        Label R1 = new Label("название станции:");
        final TextField n1 = new TextField(20);
        final JTextArea ta = new JTextArea();
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    GetStationScheduleRequest request = new GetStationScheduleRequest(n1.getText());
                    System.out.println(request);
                    client.send(request);
                    ta.setText("");
                    ta.append(client.receive());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }
        });
        jPanel4.add(button1);
        jPanel4.add(R1);
        jPanel4.add(n1);
        jPanel4.add(ta,BorderLayout.EAST);
        return jPanel4;
    }

    JPanel getPassengersByTrainPanel(){
        setLayout(new BorderLayout());
        JButton button1 = new JButton("вывести данные пассажиров");
        JPanel jPanel4 = new JPanel();
        Label R1 = new Label("номер поезда:");
        Label R2 = new Label("пароль:");
        final TextField n1 = new TextField(20);
        final TextField n2 = new TextField(20);
        final JTextArea ta = new JTextArea();
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    GetTrainPassengersRequest request = new GetTrainPassengersRequest(Integer.parseInt(n1.getText()),n2.getText());
                    System.out.println(request);
                    client.send(request);
                    ta.setText("");
                    ta.append(client.receive());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                catch (ClassNotFoundException e1) {
//                    e1.printStackTrace();
//                }


            }
        });

        jPanel4.add(R1);
        jPanel4.add(n1);
        jPanel4.add(R2);
        jPanel4.add(n2);
        jPanel4.add(button1);
        jPanel4.add(ta);

        //jPanel4.setLayout(new GridLayout(3, 2));
        return jPanel4;
    }

    JPanel getAllTrainsPanel(){
        JPanel jPanel = new JPanel();
        JButton button1 = new JButton("вывести номера поездов");
        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new GridLayout(3, 2));
       // FlowLayout experimentLayout = new FlowLayout();
       // jPanel4.setLayout(null);
        //jPanel4.setSize(100,300);
        Label R1 = new Label("пароль:");
        final TextField n1 = new TextField(20);
        final JTextArea ta = new JTextArea();

  //      ta.setSize(300,100);
//        ta.setLocation(40,40);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    GetAllTrainsRequest request = new GetAllTrainsRequest(n1.getText());
                    System.out.println(request);
                    client.send(request);
                    ta.setText("");
                    ta.append(client.receive());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                catch (ClassNotFoundException e1) {
//                    e1.printStackTrace();
//                }


            }
        });
       // jPanel4.SetLayout(new BorderLayout(100,100));
        jPanel4.add(R1);
        jPanel4.add(n1);
        jPanel4.add(button1);
        jPanel4.add(ta);
       // jPanel4.add(ta,BorderLayout.EAST);
        jPanel.add(jPanel4);
        return jPanel;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new AppFrame();
            }
        });
    }
}
