import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Logger implements LoggerImpl{
    private Map<String, Process> processQueue;
    private ConcurrentSkipListMap<Long, Process> map;
    private List<CompletableFuture<Void>> futures;
    private Lock lock;
    private ExecutorService[] threads;

    public Logger() {
        processQueue = new ConcurrentHashMap<String, Process>();
        map = new ConcurrentSkipListMap<>(Comparator.comparingLong(a -> a));
        futures = new CopyOnWriteArrayList<>();
        lock = new ReentrantLock();
        threads = new ExecutorService[10];

        for(int i = 0; i < threads.length; i++) {
            threads[i] = Executors.newSingleThreadExecutor();
        }
    }
    @Override
    public void startProcess(String id, Long timestamp) {
        threads[id.hashCode()% threads.length].execute(() -> {
            System.out.println("id" + id);
            Process process = new Process(id, timestamp);
            processQueue.put(id, process);
            map.put(timestamp, process);
        });
    }

    @Override
    public void endProcess(String id) {
        threads[id.hashCode()% threads.length].execute(() -> {
            long currTime = System.currentTimeMillis();
            lock.lock();
            try {
                Process current = processQueue.get(id);
                current.setEndTime(currTime);

                if(!futures.isEmpty() && map.firstEntry().getValue().getId().equals(id)) {
                    pollNow();
                    final var res = futures.get(0);
                    res.complete(null);
                }
            }
            finally {
                lock.unlock();
            }
        });
    }

    @Override
    public void poll() {
        final CompletableFuture future = new CompletableFuture<Void>();
        lock.lock();
        try {
            if (!map.isEmpty() && map.firstEntry().getValue().getEndTime() != -1) {
                pollNow();
            } else {
                futures.add(future);
            }
        }
        finally {
            lock.unlock();
        }
    }

    private void pollNow() {
        Process current = map.firstEntry().getValue();
        System.out.println(current.getId() + " started at "+ current.getStartTime() + " and ended at " + current.getEndTime());
        map.remove(current.getStartTime());
        processQueue.remove(current.getId());
    }
}
