package nio.week3;

import java.util.ArrayList;
import java.util.List;

public class ServerGenerator {
    public static List<Server> mock(String service){
        List<Server> servers = new ArrayList<>();
        for(int i = 0; i < 3 ; i++){
            Server server = new Server();
            server.setAlive(true);
            server.setServiceName(service);
            server.setInstanceId(service + i);
            server.setIp("127.0.0.1");
            server.setPort(Integer.parseInt("808" + i));
            servers.add(server);
        }
        return servers;
    }
}
