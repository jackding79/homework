package nio.week3.serverchoose;

import nio.week3.Server;
import nio.week3.loadbalancer.LoadBalancer;

/**
 * 选择服务实例
 */
public interface ServerChoose {
    String type();
    Server choose(LoadBalancer loadBalancer);
}
