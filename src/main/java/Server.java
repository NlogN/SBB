/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 10.08.14
 */
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

/**
 * User: ilya
 * Date: 21.10.12
 */

public class Server {
    private HttpServer server;
    public static String defaultHttp = "http://127.0.0.1:";
    static int port = 8000;


    Server() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 10);

        server.createContext("/", new MyHandler());
        server.start();
        System.out.println("server started");
    }

    class MyHandler implements HttpHandler {

        public void handle(final HttpExchange exc) throws IOException {
            exc.sendResponseHeaders(200, 0);

            final InputStreamReader isr = new InputStreamReader(exc.getRequestBody(), "UTF-8");
            final BufferedReader br = new BufferedReader(isr);

            String value = br.readLine();
            value = URLDecoder.decode(value, "UTF-8");

            PrintWriter out =  new PrintWriter(exc.getResponseBody());
            perform(value, out);
            exc.close();
        }

        protected void perform(final String value, PrintWriter out) throws IOException {

            out.println("123");

            out.close();
        }

    }


    public static void main(String[] args) throws IOException {
        Server server1 = new Server();


    }



}
