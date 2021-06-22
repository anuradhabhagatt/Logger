public class Driver {
    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.startProcess("1", 1l);
        logger.poll();
        logger.startProcess("3", 2l);
        logger.poll();
        logger.endProcess("1");
        logger.poll();
        logger.startProcess("2", 3l);
        logger.poll();
        logger.endProcess("2");
        logger.poll();
        logger.endProcess("3");
        logger.poll();
        logger.poll();
        logger.poll();
    }
}
