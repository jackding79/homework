package nio.week3.loadbalancer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import nio.week3.Server;
import nio.week3.filter.IFilter;
import nio.week3.http.WebClient;
import nio.week3.serverchoose.ServerChooseFactory;
import org.apache.http.HttpResponse;

import java.io.InputStream;

/**
 * api 网关转发逻辑
 */
public class LoadBalanceFilter implements IFilter {


    public LoadBalanceFilter(){

    }

    @Override
    public Object doFilter(FullHttpRequest request, ChannelHandlerContext ctx) {
        String uri = request.uri();
        String[] splits = uri.split("/");
        String service = splits[1];
        String ruleType = request.headers().get("ruleType") == null ? "random" : request.headers().get("ruleType");
        LoadBalancer loadBalancer = LoadBalancerFactory.loadBalancer(service);
        Server server = ServerChooseFactory.get(ruleType).choose(loadBalancer);
        try {
            HttpResponse response = new WebClient().execute(request , server);
            InputStream inputStream = response.getEntity().getContent();
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, Unpooled.wrappedBuffer(buffer));
            ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
        }catch (Exception e){
            e.printStackTrace();
            ctx.close();
        }
        return null;
    }

    @Override
    public int Order() {
        return 0;
    }
}
