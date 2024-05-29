import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class HttpServer {
	public static final String ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	private boolean shutdown = false;
    private static final int PORT = 8080;
	ServerSocket serverSocket = null;
	public HttpServer(){
		System.out.println("ROOT: " + ROOT);
		// 创建服务器端socket
	    try {
			serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	// 启动服务器，开始处理请求
	public void start(){
		// 循环处理请求
		while(!shutdown) {
			Socket socket;
			try {
				// 2.1 创建socket进行请求处理
				socket = serverSocket.accept();
				// System.out.println("accpted"); // 验证accept()阻塞
				Connection connection = new Connection(socket);
				connection.run();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	class Connection implements Runnable {
		private Socket socket;
		public Connection(Socket _socket){
			socket = _socket;
		}
		@Override
		public void run() {
			InputStream input = null;
			OutputStream output = null;
			// 1 获得输入输出流
			try{
				input = socket.getInputStream();
				output = socket.getOutputStream();
			} catch(IOException e){
				e.printStackTrace();
			}
			// 2 接收并处理请求
			Request request = new Request(input);
			String uri = request.getUri();
			System.out.println("uri: "+uri);
			if(uri.equals("/shutdown")){
				shutdown = true;
			}
			// 3 返回结果
			Response response = new Response(output,request.getUri());
			response.sendStaticResource();
			// 4 关闭socket
			try{
				socket.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}