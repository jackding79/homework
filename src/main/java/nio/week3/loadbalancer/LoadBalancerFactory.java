package nio.week3.loadbalancer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的获取每个服务的负载均衡器
 */
public class LoadBalancerFactory {
    private static ConcurrentHashMap<String, LoadBalancer> cache = new ConcurrentHashMap<>(8);
    public static LoadBalancer loadBalancer(String service){
        LoadBalancer loadBalancer;
        if(cache.containsKey(service)){
            loadBalancer = cache.get(service);
        }else{
            loadBalancer = new LoadBalancer(service);
        }
        return loadBalancer;
    }
}
