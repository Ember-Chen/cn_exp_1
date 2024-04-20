import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class HttpServer {
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	private boolean shutdown = false;
    private static final int PORT = 8080;
	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.start();
	}
	
	//启动服务器，并接收用户请求进行处理
	public void  start() {
		ServerSocket serverSocket = null;
	    try {
			serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		//若请求的命令不为SHUTDOWN时，循环处理请求
		while(!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			
			try {
				//创建socket进行请求处理
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				//接收请求
				Request request = new Request(input);
				request.parser();
				//处理请求并返回结果
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				//关闭socket
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
		
	}
}