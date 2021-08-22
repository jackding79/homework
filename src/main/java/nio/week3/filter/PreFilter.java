package nio.week3.filter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import nio.week3.TimeGenerator;

import java.nio.charset.StandardCharsets;

/**
 * 一些简单的校验并在请求头添加流水序号 简单实现序号---当前时间
 */
public class PreFilter implements IFilter{

    @Override
    public Object doFilter(FullHttpRequest request, ChannelHandlerContext ctx) {
        request.headers().add("serial_number", TimeGenerator.time());
        if(0 == request.content().readableBytes()  &&  HttpMethod.POST == request.method() ){
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST,
                    Unpooled.wrappedBuffer("no request entity".getBytes(StandardCharsets.UTF_8)));
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            return -1;
        }
        String uri = request.uri();
        if(uri.split("/").length < 2){
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer("hello netty!".getBytes(StandardCharsets.UTF_8)));
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            return -1;
        }
        return null;
    }

    @Override
    public int Order() {
        return 0;
    }
}
