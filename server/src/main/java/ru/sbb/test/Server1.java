package ru.sbb.test;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 20.08.14
 */
import ru.sbb.request.Message;

import java.io.*;
import java.net.*;

public class Server1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ServerSocket serverSock = new ServerSocket(8080);

            System.out.println("Listening on 8080 at localhost");
            while (true) {
                Socket clientSock = serverSock.accept();

                    SocketAddress sockAddr = clientSock.getRemoteSocketAddress();
                    System.out.println("Processing new connection from " + sockAddr);
          //          try {
                        ObjectInputStream input = new ObjectInputStream(
                                new BufferedInputStream(clientSock.getInputStream()));
                        receive(sockAddr, input);
                        ObjectOutputStream output = new ObjectOutputStream(
                                new BufferedOutputStream(clientSock.getOutputStream()));
                        send(sockAddr, output, new Message("Hello, I'm a server!"));
                        receive(sockAddr, input);
                        System.out.println("Server thinking on the answer for " + sockAddr);
                       // Thread.sleep(100);
                        send(sockAddr, output, new Message("Fine, thanks! And you?"));
 //                   }
//                    catch (InterruptedException e) {
//                        System.out.println("Delay for " + sockAddr + " interrupted");
//                    }

            }

    }

    private static void send(SocketAddress sockAddr, ObjectOutputStream output, Message m) throws IOException {
        System.out.println("Sending a message to " + sockAddr + ": " + m);
        output.writeObject(m);
        output.flush();
    }

    private static void receive(SocketAddress sockAddr, ObjectInputStream input) throws IOException, ClassNotFoundException {
        System.out.print("Receiving a message from " + sockAddr + ": ");
        Message m = (Message) input.readObject();
        System.out.println(m);
    }

}
