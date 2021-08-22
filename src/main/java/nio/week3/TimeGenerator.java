package nio.week3;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimeGenerator {
    public static long cache;
    static {
        run();
    }
    public static void run(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() ->{
            cache = System.currentTimeMillis();
        }, 0, 1, TimeUnit.MILLISECONDS);
    }

    public static long time(){
        return cache;
    }
}
