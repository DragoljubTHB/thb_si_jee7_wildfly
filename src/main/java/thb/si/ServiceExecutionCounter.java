package thb.si;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

public class ServiceExecutionCounter {

    private Map<String, Queue<Long>> serviceNameExecutionMap;

    private static ServiceExecutionCounter ourInstance = new ServiceExecutionCounter();

    public static ServiceExecutionCounter getInstance() {
        return ourInstance;
    }

    private ServiceExecutionCounter() {
        serviceNameExecutionMap = new ConcurrentHashMap<>();
    }

    public void put(String serviceName, Long timeStamp) {

        if (serviceNameExecutionMap.containsKey(serviceName)) {
            serviceNameExecutionMap.get(serviceName).add(timeStamp);
        } else {
            serviceNameExecutionMap.put(serviceName, new ConcurrentLinkedQueue<>());
            serviceNameExecutionMap.get(serviceName).add(timeStamp);
        }
    }
    public Long getActualNumberOfExecutionsByServiceName(String serviceName) {
        //AtomicReference<Long> sum = new AtomicReference<>(0L);
        //serviceNameExecutionMap.get(serviceName).forEach((el)-> {
         //   sum.updateAndGet(v -> v + el);
        //});
//        return sum.get();
        return (long) serviceNameExecutionMap.get(serviceName).size();
    }

}
