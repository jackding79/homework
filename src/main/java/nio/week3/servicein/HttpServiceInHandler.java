package nio.week3.servicein;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import nio.week3.filter.FilterExecutor;


public class HttpServiceInHandler extends ChannelInboundHandlerAdapter {
    private FilterExecutor executor;
    public HttpServiceInHandler(FilterExecutor executor){
        this.executor = executor;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            executor.run(request, ctx);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
