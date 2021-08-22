package nio.week3.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

public class FilterExecutor {
    private static final List<IFilter> filters = FilterFactory.filters();
    public void run(FullHttpRequest request, ChannelHandlerContext ctx){
        for (IFilter filter : filters){
          Object o =  filter.doFilter(request,ctx);
          if(o != null){
              break;
          }
        }
    }
}
