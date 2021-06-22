public interface LoggerImpl {
    public void startProcess(String id, Long timestamp);
    public void endProcess(String id);
    public void poll();
}
