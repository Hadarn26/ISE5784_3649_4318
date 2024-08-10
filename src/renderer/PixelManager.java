package renderer;

/**
 * Pixel is a helper class. It is used for multi-threading in the renderer and
 * for follow up its progress.<br/>
 * There is a main follow up object and several secondary objects - one in each
 * thread.
 */
class PixelManager {
    private static int maxRows = 0;
    private static int maxCols = 0;
    private static long totalPixels = 0l;

    private static volatile int cRow = 0;
    private static volatile int cCol = -1;
    private static volatile long pixels = 0l;
    private static volatile long last = -1l;
    private static volatile int lastPrinted = -1;

    private static boolean print = false;
    private static long printInterval = 100l;
    private static final String PRINT_FORMAT = "%5.1f%%\r";
    private static Object mutexNext = new Object();
    private static Object mutexPixels = new Object();

    int row;
    int col;

    /**
     * Initialize pixel data for multi-threading
     *
     * @param maxRows  the amount of pixel rows
     * @param maxCols  the amount of pixel columns
     * @param interval print time interval in seconds, 0 if printing is not required
     */
    static void initialize(int maxRows, int maxCols, double interval) {
        PixelManager.maxRows = maxRows;
        PixelManager.maxCols = maxCols;
        PixelManager.totalPixels = (long) maxRows * maxCols;
        cRow = 0;
        cCol = -1;
        pixels = 0;
        printInterval = (int) (interval * 1000);
        print = printInterval != 0;
        last = -1l;
    }

    /**
     * Function for thread-safe manipulating of main follow up Pixel object - this
     * function is critical section for all the threads, and static data is the
     * shared data of this critical section.<br/>
     * The function provides next available pixel number each call.
     *
     * @return true if next pixel is allocated, false if there are no more pixels
     */
    public boolean nextPixel() {
        synchronized (mutexNext) {
            if (cRow == maxRows)
                return false;
            ++cCol;
            if (cCol < maxCols) {
                row = cRow;
                col = cCol;
                return true;
            }
            cCol = 0;
            ++cRow;
            if (cRow < maxRows) {
                row = cRow;
                col = cCol;
                return true;
            }
            return false;
        }
    }

    /**
     * Finish pixel processing
     */
    static void pixelDone() {
        synchronized (mutexPixels) {
            ++pixels;
        }
    }

    /**
     * Wait for all pixels to be done and print the progress percentage - must be
     * run from the main thread
     */
    public static void waitToFinish() {
        if (print)
            System.out.printf(PRINT_FORMAT, 0d);

        while (last < totalPixels) {
            printPixel();
            try {
                Thread.sleep(printInterval);
            } catch (InterruptedException ignore) {
                if (print)
                    System.out.print("");
            }
        }
        if (print)
            System.out.println("100.0%");
    }

    /**
     * Print pixel progress percentage
     */
    public static void printPixel() {
        long current = pixels;
        if (print && last != current) {
            int percentage = (int) (1000l * current / totalPixels);
            if (lastPrinted != percentage) {
                last = current;
                lastPrinted = percentage;
                System.out.println( percentage / 10d + "%");
            }
        }
    }
}
//package renderer;
//
///**
// * PixelManager is a helper class. It is used for multi-threading in the
// * renderer and
// * for follow up its progress.<br/>
// * A Camera uses one pixel manager object and several Pixel objects - one in
// * each thread.
// *
// * @author Dan Zilberstein
// */
//class PixelManager {
//    /**
//     * Immutable class for object containing allocated pixel (with its row and
//     * column numbers)
//     */
//    record Pixel(int col, int row) {
//    }
//
//    /**
//     * Maximum rows of pixels
//     */
//    private int maxRows = 0;
//    /**
//     * Maximum columns of pixels
//     */
//    private int maxCols = 0;
//    /**
//     * Total amount of pixels in the generated image
//     */
//    private long totalPixels = 0l;
//
//    /**
//     * Currently processed row of pixels
//     */
//    private volatile int cRow = 0;
//    /**
//     * Currently processed column of pixels
//     */
//    private volatile int cCol = -1;
//    /**
//     * Amount of pixels that have been processed
//     */
//    private volatile long pixels = 0l;
//    /**
//     * Last printed progress update percentage
//     */
//    private volatile int lastPrinted = 0;
//
//    /**
//     * Flag of debug printing of progress percentage
//     */
//    private boolean print = false;
//    /**
//     * Progress percentage printing interval
//     */
//    private long printInterval = 100l;
//    /**
//     * Printing format
//     */
//    private static final String PRINT_FORMAT = "%5.1f%%\r";
//    /**
//     * Mutual exclusion object for synchronizing next pixel allocation between
//     * threads
//     */
//    private Object mutexNext = new Object();
//    /**
//     * Mutual exclusion object for printing progress percentage in console window
//     * by different threads
//     */
//    private Object mutexPixels = new Object();
//
//    /**
//     * Initialize pixel manager data for multi-threading
//     *
//     * @param maxRows  the amount of pixel rows
//     * @param maxCols  the amount of pixel columns
//     * @param interval print time interval in seconds, 0 if printing is not
//     *                 required
//     */
//    PixelManager(int maxRows, int maxCols, double interval) {
//        this.maxRows = maxRows;
//        this.maxCols = maxCols;
//        totalPixels = (long) maxRows * maxCols;
//        printInterval = (int) (interval * 10);
//        if (print = printInterval != 0) System.out.printf(PRINT_FORMAT, 0d);
//    }
//
//    /**
//     * Function for thread-safe manipulating of main follow up Pixel object - this
//     * function is critical section for all the threads, and the pixel manager data
//     * is the shared data of this critical section.<br/>
//     * The function provides next available pixel number each call.
//     *
//     * @return true if next pixel is allocated, false if there are no more pixels
//     */
//    Pixel nextPixel() {
//        synchronized (mutexNext) {
//            if (cRow == maxRows) return null;
//
//            ++cCol;
//            if (cCol < maxCols)
//                return new Pixel(cRow, cCol);
//
//            cCol = 0;
//            ++cRow;
//            if (cRow < maxRows)
//                return new Pixel(cRow, cCol);
//        }
//        return null;
//    }
//
//    /**
//     * Finish pixel processing by updating and printing of progress percentage
//     */
//    void pixelDone() {
//        boolean flag = false;
//        int percentage = 0;
//        synchronized (mutexPixels) {
//            ++pixels;
//            if (print) {
//                percentage = (int) (1000l * pixels / totalPixels);
//                if (percentage - lastPrinted >= printInterval) {
//                    lastPrinted = percentage;
//                    flag = true;
//                }
//            }
//            if (flag) System.out.printf(PRINT_FORMAT, percentage / 10d);
//        }
//    }
//}