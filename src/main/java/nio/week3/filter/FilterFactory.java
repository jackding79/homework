package nio.week3.filter;

import nio.week3.loadbalancer.LoadBalanceFilter;

import java.util.ArrayList;
import java.util.List;

public class FilterFactory {
    static List<IFilter> filters = new ArrayList<>();
    static {
        registry();
    }
    public static void  registry(){
        filters.add(new PreFilter());
        filters.add(new LoadBalanceFilter());
    }

    public static List<IFilter> filters(){
        return filters;
    }
}
