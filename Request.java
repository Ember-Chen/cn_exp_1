import java.io.IOException;
import java.io.InputStream;


public class Request {
	private InputStream input;
	private String uri;
	private static final int BUFFER_SIZE = 2048;
	
	public Request(InputStream input) {
		this.input = input;
	}
	
	public void parser() {
		StringBuffer request = new StringBuffer();
		byte[] buffer = new byte[BUFFER_SIZE];
		int i = 0;
		
		try {
			i = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		
		for(int k = 0; k < i; k++) {
			request.append((char)buffer[k]);
		}
		
		uri = parserUri(request.toString());
	}

	/** 示例
	 * * GET /a.html HTTP/1.1
	 * * ...
	 */
	private String parserUri(String requestData) {
		// System.out.println("------------- data start -------------");
		// System.out.println(requestData);
		// System.out.println("------------- data end -------------");
		int index1 = requestData.indexOf(' ');
		int index2 = requestData.indexOf(' ', index1 + 1);
		if(index1==-1 || index2==-1)
			return null;
		return requestData.substring(index1 + 1, index2);
	}

	public String getUri() {
		return uri;
	}
}
/**
*接收到的请求串-示例:
* GET /a.html HTTP/1.1
* Host: localhost:8080
* Connection: keep-alive
* Cache-Control: max-age=0
* sec-ch-ua: "Google Chrome";v="123", "Not:A-Brand";v="8", "Chromium";v="123"
* sec-ch-ua-mobile: ?0
* sec-ch-ua-platform: "Windows"
* Upgrade-Insecure-Requests: 1
* User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36
* Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,
* Sec-Fetch-Site: none
* Sec-Fetch-Mode: navigate
* Sec-Fetch-User: ?1
* Sec-Fetch-Dest: document
* Accept-Encoding: gzip, deflate, br, zstd
* Accept-Language: zh-CN,zh;q=0.9
*/