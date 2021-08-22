package nio.week3.loadbalancer;

import nio.week3.Server;
import nio.week3.ServerGenerator;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *  服务的负载均衡器
 */
public class LoadBalancer {
    private List<Server> serverList;
    private String service;

    public LoadBalancer(String service){
        this.service = service;
        init(service);
        update();
    }

    public List<Server> getAllServers(){
        return serverList;
    }

    public List<Server> getAvailableServers(){
        return serverList.stream().filter(Server::isAlive).collect(Collectors.toList());
    }

    // 模拟一个服务的实例
    private void init (String service){
        serverList = ServerGenerator.mock(service);
    }

    private void update(){
        updateOfMock();
    }

    private void updateOfMock(){
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(new Task(), 1000, 30000, TimeUnit.MILLISECONDS);
    }

    private  class Task implements Runnable{

        @Override
        public void run() {
            for (Server server : serverList){
               if(random() == 10){
                   server.setAlive(false);
               }
            }
        }

        public int random(){
            return ThreadLocalRandom.current().nextInt(20);
        }
    }

}
