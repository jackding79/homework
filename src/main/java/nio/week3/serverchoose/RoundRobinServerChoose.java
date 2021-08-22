package nio.week3.serverchoose;

import nio.week3.Server;
import nio.week3.loadbalancer.LoadBalancer;

public class RoundRobinServerChoose implements ServerChoose{
    @Override
    public String type() {
        return "round";
    }

    @Override
    public Server choose(LoadBalancer loadBalancer) {
        return null;
    }
}
