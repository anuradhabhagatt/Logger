public class Process {
    private String id;
    private long startTime;
    private long endTime;

    public String getId() {
        return id;
    }

    public long getStartTime() {
        return startTime;
    }

    public Process(String id, long startTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = -1;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    public long getEndTime() {
        return endTime;
    }
}
