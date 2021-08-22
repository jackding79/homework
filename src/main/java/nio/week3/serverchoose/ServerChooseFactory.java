package nio.week3.serverchoose;

import java.util.HashMap;

public class ServerChooseFactory {
    private static final HashMap<String,ServerChoose> chooses = new HashMap<>();
    static {
        registryAllRule();
    }
    private static void registryAllRule(){
        chooses.put("random",new RandomServerChoose());
        chooses.put("round",new RoundRobinServerChoose());
    }

    public static ServerChoose get(String key){
        return chooses.getOrDefault(key,chooses.get("random"));
    }
}
