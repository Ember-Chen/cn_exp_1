import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8080;
        String path = "/a.html";

        try {
            // 创建Socket连接到服务器
            Socket socket = new Socket(host, port);

            // 创建输出流，发送HTTP请求
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: " + host);
            out.println("Connection: Close");
            out.println(); // 空行表示请求头结束

            // 创建输入流，读取服务器响应
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

            // 关闭流和Socket
            in.close();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host);
            e.printStackTrace();
        }
    }
}
