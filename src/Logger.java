import java.util.*;

public class Logger implements LoggerImpl{
    private HashMap<String, Process> processQueue;
    private NavigableMap<Long, Process> map;

    public Logger() {
        processQueue = new HashMap<>();
        map = new TreeMap<>(Comparator.comparingLong(a -> a));
    }
    @Override
    public void startProcess(String id) {
        System.out.println("id" + id);
        long currTime = System.currentTimeMillis();
        Process process = new Process(id, currTime);
        processQueue.put(id, process);
        map.put(currTime, process);
    }

    @Override
    public void endProcess(String id) {
        long currTime = System.currentTimeMillis();
        Process current = processQueue.get(id);
        current.setEndTime(currTime);
    }

    @Override
    public void poll() {
        if(map.isEmpty())
            System.out.println("No processes started yet!");
        else {
            Process current = map.firstEntry().getValue();

            if(current.getEndTime() != -1) {
                System.out.println(current.getId() + " started at "+ current.getStartTime() + " and ended at " + current.getEndTime());
                map.remove(current.getStartTime());
                processQueue.remove(current.getId());
            }
            else
            {
                System.out.println(current.getId() + " is not complete yet.");
            }
        }
    }
}
