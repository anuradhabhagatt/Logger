public class Driver {
    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.startProcess("P1");
        logger.startProcess("P2");
        logger.poll();
        logger.endProcess("P2");
        logger.endProcess("P1");
        logger.poll();

        logger.poll();
        logger.poll();
    }
}
