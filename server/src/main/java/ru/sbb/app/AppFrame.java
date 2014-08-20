package ru.sbb.app;

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
        final JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.setFont(font1);
        JPanel jPanel6 = new JPanel();
        JPanel jPanel7 = new JPanel();
        JPanel jPanel8 = new JPanel();
        JPanel jPanel9 = new JPanel();

        tabbedPane2.addTab("добавить поезд", jPanel6);
        tabbedPane2.addTab("добавить станцию", jPanel7);
        tabbedPane2.addTab("добавить расписание", jPanel8);
        tabbedPane2.addTab("просмотр поездов", jPanel9);
        tabbedPane2.addTab("просмотр пассажиров",getPassengersByTrainPanel() );
        content2.add(tabbedPane2, BorderLayout.CENTER);

        content2.add(tabbedPane2);


        tabbedPane.addTab("Сотрудник", content2);


        content.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(content);

        setPreferredSize(new Dimension(700, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    JPanel getStationScheduleRequestPanel(){
        JButton button1 = new JButton("поиск поездов");
        JPanel jPanel4 = new JPanel();
        Label R1 = new Label("название станции:");
        final TextField n1 = new TextField(20);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    GetStationScheduleRequest request = new GetStationScheduleRequest(n1.getText());
                    System.out.println(request);
                    client.send(request);
                    client.receive();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }
        });
        jPanel4.add(button1);
        jPanel4.add(R1);
        jPanel4.add(n1);
        return jPanel4;
    }

    JPanel getPassengersByTrainPanel(){
        JButton button1 = new JButton("вывести пассадиров");
        JPanel jPanel4 = new JPanel();
        Label R1 = new Label("номер поезда:");
        final TextField n1 = new TextField(20);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    GetTrainPassengersRequest request = new GetTrainPassengersRequest(Integer.parseInt(n1.getText()));
                    System.out.println(request);
                    client.send(request);
                    client.receive();


                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                catch (ClassNotFoundException e1) {
//                    e1.printStackTrace();
//                }


            }
        });
        jPanel4.add(button1);
        jPanel4.add(R1);
        jPanel4.add(n1);
        return jPanel4;
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
