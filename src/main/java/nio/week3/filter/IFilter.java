package nio.week3.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface IFilter {
    Object doFilter(FullHttpRequest request, ChannelHandlerContext ctx);
    int Order();
}
