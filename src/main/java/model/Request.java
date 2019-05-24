package model;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * User: u6613739
 * Date: 2019/5/24
 * Time: 15:56
 * Description:
 */
@Data
public class Request
{
    private String method;

    private String uri;

    private String version;

    private Map<String,String> headers;

    private String message;

    public  static Request parse2request(InputStream requestStream) throws IOException {
        BufferedReader httpReader = new BufferedReader(new InputStreamReader(requestStream, "UTF-8"));
        Request httpRequest = new Request();
        decodeRequestLine(httpReader, httpRequest);
        decodeRequestHeader(httpReader, httpRequest);
        decodeRequestMessage(httpReader, httpRequest);
        return httpRequest;
    }

    private static void decodeRequestHeader(BufferedReader bufferedReader, Request request) throws IOException
    {
        Map<String, String> headers = new HashMap<>(16);
        String line = bufferedReader.readLine();
        String[] kv;
        while (!"".equals(line)) {
            kv = line.split(":");
            assert kv.length == 2;
            headers.put(kv[0].trim().toLowerCase(), kv[1].trim());
            line = bufferedReader.readLine();
        }

        request.setHeaders(headers);
    }

    private static void decodeRequestMessage(BufferedReader bufferedReader,Request request) throws IOException
    {
        int contentLen = Integer.parseInt(request.getHeaders().getOrDefault("Content-Length".toLowerCase(), "0"));
        if (contentLen == 0) {
            return;
        }
        char[] bodymessage = new char[contentLen];
        bufferedReader.read(bodymessage);
        request.setMessage(new String(bodymessage));
    }
    private static void decodeRequestLine(BufferedReader reader, Request request) throws IOException {
        String[] strs = reader.readLine().split(" ");
        assert strs.length == 3;
        request.setMethod(strs[0]);
        request.setUri(strs[1]);
        request.setVersion(strs[2]);
    }
}
