/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 10.08.14
 */

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * User: ilya
 * Date: 22.10.12
 */


public class TestClient {
    private static HttpClient client = new DefaultHttpClient();
    static int routerPort = 8000;


//    public static void main(String[] args) throws IOException, URISyntaxException {
//        PrintWriter pw = new PrintWriter(System.out, true);
//        Scanner in = new Scanner(System.in);
//        while (in.hasNext()) {
//            String command = in.nextLine();
//           perform(command, pw);
//        }
//    }

    public void send() throws IOException, URISyntaxException {
        PrintWriter pw = new PrintWriter(System.out, true);
        perform("", pw);
    }

    public static void perform(String command, PrintWriter out) throws IOException, URISyntaxException {
        command = command.replaceAll(" ", "");


            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("command", command));

            HttpPost post1 = new HttpPost(Server.defaultHttp + routerPort + "/");
            post1.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            try {
                HttpResponse response = client.execute(post1);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = rd.readLine()) != null) {
                    out.println(line);
                }
            } catch (HttpHostConnectException e1) {
                out.println("server is unavailable.");
            } catch (HttpException e) {
                e.printStackTrace();
            }
            if (command.equals("exit")) {
                out.println("client is stopped.");
                System.exit(0);
            }


    }




}
