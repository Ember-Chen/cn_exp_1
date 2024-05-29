import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
 

public class Response {
	private OutputStream output;
	private String URI = null;
	private static final int BUFFER_SIZE = 1024;

	private static final String ErrorMessage = 
		"HTTP/1.1 404 File Not Found \r\n" +
		"Content-Type: text/html\r\n" +
		"Content-Length: 24\r\n" +
		"\r\n" +
		"<h1>404 \t File Not Found!</h1>";

	private static final String ShutdownMessage = 
		"HTTP/1.1 200 OK\r\n" +
		"Content-Type: text/html\r\n" +
		"Content-Length: 24\r\n" +
		"\r\n" +
		"<h1>Server Shutdown!</h1>";

	public Response(OutputStream output,String URI) {
		this.output = output;
		this.URI = URI;
	}
	
	//发送一个静态资源给客户端，若本地服务器有对应的文件则返回，否则返回404页面
	public void sendStaticResource() {
		byte[] buffer = new byte[BUFFER_SIZE];
		FileInputStream input_stream = null;
		try {
			File file = new File(HttpServer.ROOT, URI);
			String path = HttpServer.ROOT + URI;
			if(URI.equals("/shutdown")){
				output.write(ShutdownMessage.getBytes());
				System.out.println("server shutdown");
			}
			else if(!file.exists()){
				output.write(ErrorMessage.getBytes());
				System.out.println("file not found");
			}
			else {
				output.write("HTTP/1.1 200 OK\n".getBytes());
                output.write("Content-Type: text/html; charset=UTF-8\n\n".getBytes());
				// System.out.println("file path: " + path);
				input_stream = new FileInputStream(path);
				int readLength;
                while((readLength = input_stream.read(buffer, 0, BUFFER_SIZE)) > 0 ) {
                    output.write(buffer, 0, readLength);
                }
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally { // 关闭fileInputStream
			if(input_stream != null) {
				try {
					input_stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}