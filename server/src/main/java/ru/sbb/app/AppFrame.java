package ru.sbb.app;

import ru.sbb.DateBuilder;
import ru.sbb.request.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

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
               e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


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


        tabbedPane1.addTab("train search", getTrainByRouteRequestPanel());
        tabbedPane1.addTab("station schedule", getStationScheduleRequestPanel() );
        tabbedPane1.addTab("buy ticket", getBuyTicketRequestPanel());
        content1.add(tabbedPane1, BorderLayout.CENTER);
        content1.add(tabbedPane1);

        tabbedPane.addTab("Client", content1);

        JPanel content2 = new JPanel();
        content2.setLayout(new BorderLayout());

        final JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.setFont(font1);


        tabbedPane2.addTab("add train", getAddTrainRequestPanel());
        tabbedPane2.addTab("add station", getAddStationRequestPanel());
        tabbedPane2.addTab("add schedule", getAddSchedulRecordRequestPanel());
        tabbedPane2.addTab("train review", getAllTrainsPanel());
        tabbedPane2.addTab("passenger review",getPassengersByTrainPanel() );

        content2.add(tabbedPane2);


        tabbedPane.addTab("Мanager", content2);


        content.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(content);

        setPreferredSize(new Dimension(700, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JPanel getTrainByRouteRequestPanel(){
        JPanel jPanel = new JPanel();
        JPanel jPanel4 = new JPanel();

        jPanel4.setLayout(new GridLayout(5, 2));

        Label R1 = new Label("station A name:");
        final TextField n1 = new TextField(20);

        Label R2 = new Label("station B name:");
        final TextField n2 = new TextField(20);

        Label R3 = new Label("lowerBound (yyyy/MM/dd HH:mm:ss):");
        final TextField n3 = new TextField(20);

        Label R4 = new Label("upperBound (yyyy/MM/dd HH:mm:ss):");
        final TextField n4 = new TextField(20);


        final JTextArea ta = new JTextArea();

        JButton button1 = new JButton("view trains");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        Date time1  = DateBuilder.createDateTime(n3.getText());
                        Date time2  = DateBuilder.createDateTime(n4.getText());
                        GetTrainsByRouteRequest request = new GetTrainsByRouteRequest(n1.getText(),n2.getText(),time1,time2);
                        System.out.println(request);
                        client.send(request);
                        ta.setText("");
                        ta.append(client.receive());
                    } catch (ParseException e1) {
                        ta.setText("");
                        ta.append("incorrect date format");
                    }

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
        jPanel4.add(R4);
        jPanel4.add(n4);
        jPanel4.add(button1);
        jPanel4.add(ta);

        jPanel.add(jPanel4);
        return jPanel;
    }

    JPanel getBuyTicketRequestPanel(){
        JPanel jPanel = new JPanel();
        JPanel jPanel4 = new JPanel();

        jPanel4.setLayout(new GridLayout(8, 2));

        Label R1 = new Label("name:");
        final TextField n1 = new TextField(20);
        Label R2 = new Label("surname:");
        final TextField n2 = new TextField(20);
        Label R3 = new Label("birthday (yyyy/MM/dd):");
        final TextField n3 = new TextField(20);
        Label R4 = new Label("ticket date (yyyy/MM/dd)");
        final TextField n4 = new TextField(20);

        Label R6 = new Label("station:");
        final TextField n6 = new TextField(20);
        Label R7 = new Label("train num:");
        final TextField n7 = new TextField(20);
        final JTextArea ta = new JTextArea();

        JButton button1 = new JButton("buy ticket");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    BuyTicketRequest request = new BuyTicketRequest(n1.getText(),n2.getText(),n3.getText(),Integer.parseInt(n7.getText()),n6.getText(), n4.getText());
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
        jPanel4.add(R4);
        jPanel4.add(n4);

        jPanel4.add(R6);
        jPanel4.add(n6);
        jPanel4.add(R7);
        jPanel4.add(n7);
        jPanel4.add(button1);
        jPanel4.add(ta);

        jPanel.add(jPanel4);
        return jPanel;
    }

    JPanel getAddTrainRequestPanel(){
        JPanel jPanel = new JPanel();
        JPanel jPanel4 = new JPanel();

        jPanel4.setLayout(new GridLayout(4, 2));

        Label R1 = new Label("train num:");
        final TextField n1 = new TextField(20);
        Label R2 = new Label("capacity:");
        final TextField n2 = new TextField(20);
        Label R3 = new Label("password:");
        final TextField n3 = new TextField(20);
        final JTextArea ta = new JTextArea();

        JButton button1 = new JButton("add train");
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

    JPanel getAddSchedulRecordRequestPanel(){
        JPanel jPanel = new JPanel();
        JPanel jPanel4 = new JPanel();

        jPanel4.setLayout(new GridLayout(6, 2));

        Label R1 = new Label("station name:");
        final TextField n1 = new TextField(20);

        Label R2 = new Label("train num:");
        final TextField n2 = new TextField(20);

        Label R3 = new Label("time (yyyy/MM/dd HH:mm:ss):");
        final TextField n3 = new TextField(20);

        Label R4 = new Label("offset:");
        final TextField n4 = new TextField(20);

        Label R5 = new Label("password:");
        final TextField n5 = new TextField(20);

        final JTextArea ta = new JTextArea();

        JButton button1 = new JButton("add schedule record");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        Date time  = DateBuilder.createDateTime(n3.getText());
                        AddScheduleRecordRequest request = new AddScheduleRecordRequest(n1.getText(),Integer.parseInt(n2.getText()),time,Integer.parseInt(n4.getText()),n5.getText());
                        System.out.println(request);
                        client.send(request);
                        ta.setText("");
                        ta.append(client.receive());
                    } catch (ParseException e1) {
                        ta.setText("");
                        ta.append("incorrect date format");
                    }

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
        jPanel4.add(R4);
        jPanel4.add(n4);
        jPanel4.add(R5);
        jPanel4.add(n5);
        jPanel4.add(button1);
        jPanel4.add(ta);

        jPanel.add(jPanel4);
        return jPanel;
    }

    JPanel getAddStationRequestPanel(){
        JPanel jPanel = new JPanel();
        JPanel jPanel4 = new JPanel();

        jPanel4.setLayout(new GridLayout(4, 2));

        Label R1 = new Label("station name:");
        final TextField n1 = new TextField(20);

        Label R3 = new Label("password:");
        final TextField n3 = new TextField(20);
        final JTextArea ta = new JTextArea();

        JButton button1 = new JButton("add station");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AddStationRequest request = new AddStationRequest(n1.getText(),n3.getText());
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
        jPanel4.add(R3);
        jPanel4.add(n3);
        jPanel4.add(button1);
        jPanel4.add(ta);

        jPanel.add(jPanel4);
        return jPanel;
    }

    JPanel getStationScheduleRequestPanel(){
        JPanel jPanel11 = new JPanel();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 1));
        JButton button1 = new JButton("view schedule");
        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new GridLayout(2, 2));

        Label R1 = new Label("station name:");
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

        jPanel4.add(R1);
        jPanel4.add(n1);
        jPanel4.add(button1);

        jPanel.add(jPanel4);

        jPanel.add(ta);

        jPanel11.add(jPanel);

        return jPanel11;
    }

    JPanel getPassengersByTrainPanel(){
        JPanel jPanel11 = new JPanel();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 1));
        JButton button1 = new JButton("view passengers");
        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new GridLayout(3, 2));

        Label R1 = new Label("train number:");
        Label R2 = new Label("password:");
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



            }
        });

        jPanel4.add(R1);
        jPanel4.add(n1);
        jPanel4.add(R2);
        jPanel4.add(n2);
        jPanel4.add(button1);

        jPanel.add(jPanel4);
        jPanel.add(ta);

        jPanel11.add(jPanel);

        return jPanel11;
    }

    JPanel getAllTrainsPanel(){
        JPanel jPanel11 = new JPanel();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 1));
        JButton button1 = new JButton("view trains");
        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new GridLayout(2, 2));

        Label R1 = new Label("password:");
        final TextField n1 = new TextField(20);
        final JTextArea ta = new JTextArea();


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


            }
        });

        jPanel4.add(R1);
        jPanel4.add(n1);
        jPanel4.add(button1);

        jPanel.add(jPanel4);
        jPanel.add(ta);

        jPanel11.add(jPanel);

        return jPanel11;
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
