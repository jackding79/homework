package nio.week3.http;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import nio.week3.Server;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

public class WebClient {
    /**
     * 创建httpClient的 request 现在只做get和post
     * @param request
     * @param server
     * @return
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     */
    public HttpUriRequest request(FullHttpRequest request, Server server) throws URISyntaxException, UnsupportedEncodingException {
        HttpMethod method = request.method();
        if(HttpMethod.POST == method){
            return createPostRequest(request,server);
        }else if(HttpMethod.GET == method){
            return createGetRequest(request,server);
        }else{
            throw new RuntimeException("不支持的http请求方法");
        }

    }

    private String api(FullHttpRequest request){
        String uri = request.uri();
        String [] splits = uri.split("/");
        String [] apis = Arrays.copyOfRange(splits,2,splits.length);

        return String.join("/",apis);
    }

    private HttpPost createPostRequest(FullHttpRequest request,Server server) throws URISyntaxException, UnsupportedEncodingException {
        HttpPost post = new HttpPost();
        String path = api(request);
        String host = server.getIp() + ":" + server.getPort();
        post.setURI(new URI("http://" +host + "/" + path));
        for (Map.Entry<String, String> entry : request.headers()) {
            if(!entry.getKey().equals("Content-Length")) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }
        String content = request.content().toString(Charset.defaultCharset());
        post.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
        return post;
    }

    private HttpGet createGetRequest(FullHttpRequest request,Server server) throws URISyntaxException {
        String path = api(request);
        String host = server.getIp() + ":" + server.getPort();
        HttpGet get = new HttpGet(new URI("http://" +host + "/" + path));
        return get;
    }

    public HttpResponse execute( FullHttpRequest request,Server server) throws IOException, URISyntaxException {
       HttpClient httpClient = HttpClientBuilder.create().build();
       HttpUriRequest httpUriRequest = request(request,server);
       return  httpClient.execute(httpUriRequest);
    }

}
