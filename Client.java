import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    private static String path;
    public static void main(String[] args) {
        System.out.print("plz enter url: " + HOST + ":" + PORT);
        Scanner sc = new Scanner(System.in);
        path = sc.nextLine();
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        try {
            // 创建Socket连接到服务器
            socket = new Socket(HOST, PORT); // 连接到HOST:PORT

            // 创建输出流，发送HTTP请求
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET " + path + " HTTP/1.1");
            out.println("HOST: " + HOST);
            out.println("Connection: Close");
            out.println(); // 空行表示请求头结束

            // 创建输入流，读取服务器响应
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

            // 关闭流和Socket
            in.close();
            out.close();
            socket.close();
            sc.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about HOST: " + HOST);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + HOST);
            e.printStackTrace();
        }
    }
}
