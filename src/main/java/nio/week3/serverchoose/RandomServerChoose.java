package nio.week3.serverchoose;

import nio.week3.Server;
import nio.week3.loadbalancer.LoadBalancer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomServerChoose implements ServerChoose{
    @Override
    public Server choose(LoadBalancer loadBalancer) {
        int n  = loadBalancer.getAllServers().size();
        List<Server> servers = loadBalancer.getAvailableServers();
        int ran = ThreadLocalRandom.current().nextInt(n);
        return servers.get(ran);
    }

    public String type(){
        return "random";
    }
}
