package edu.vuum.mocca;

// Import the necessary Java synchronization and scheduling classes.
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @class PingPongRight
 * 
 * @brief This class implements a Java program that creates two
 *        instances of the PlayPingPongThread and start these thread
 *        instances to correctly alternate printing "Ping" and "Pong",
 *        respectively, on the console display.
 */
public class PingPongRight {
    /**
     * Number of iterations to run the test program.
     */
    public final static int mMaxIterations = 10;

    /**
     * Latch that will be decremented each time a thread exits.
     */
    public static CountDownLatch mLatch = null;

    /**
     * @class PlayPingPongThread
     * 
     * @brief This class implements the ping/pong processing algorithm
     *        using the SimpleSemaphore to alternate printing "ping"
     *        and "pong" to the console display.
     */
    public static class PlayPingPongThread extends Thread {

        /**
         * Constants to distinguish between ping and pong
         * SimpleSemaphores, if you choose to use an array of
         * SimpleSemaphores.  If you don't use this implementation
         * feel free to remove these constants.
         */
        private final static int FIRST_SEMA = 0;
        private final static int SECOND_SEMA = 1;

        /**
         * Maximum number of loop iterations.
         */
        private int mMaxLoopIterations = 0;

        /**
         * String to print (either "ping!" or "pong"!) for each
         * iteration.
         */
        // TODO - You fill in here.
        String stringToPrint;

        /**
         * Two SimpleSemaphores use to alternate pings and pongs.  You
         * can use an array of SimpleSemaphores or just define them as
         * two data members.
         */
        // TODO - You fill in here.
        SimpleSemaphore[] semas = new SimpleSemaphore[2];
//        Semaphore[] semas = new Semaphore[2];

        /**
         * Constructor initializes the data member(s).
         */
        public PlayPingPongThread(String stringToPrint,
                                  SimpleSemaphore semaphoreOne,
                                  SimpleSemaphore semaphoreTwo,
                                  int maxIterations) 
//        public PlayPingPongThread(String stringToPrint,
//                Semaphore semaphoreOne,
//                Semaphore semaphoreTwo,
//                int maxIterations) 

        {
            // TODO - You fill in here.
        	this.stringToPrint = stringToPrint;
        	semas[0] = semaphoreOne;
        	semas[1] = semaphoreTwo;
        	mMaxLoopIterations = maxIterations;
        	
        }

        /**
         * Main event loop that runs in a separate thread of control
         * and performs the ping/pong algorithm using the
         * SimpleSemaphores.
         */
        public void run() 
        {
            /**
             * This method runs in a separate thread of control and
             * implements the core ping/pong algorithm.
             */

            // TODO - You fill in here.
	     for (int i = 1;i <= mMaxIterations;++i) 
	     {
	
	    	
	         acquire();	
	         System.out.println
	         (this.stringToPrint + "(" + i + ")");	         
	         release();
	        
	         

	     }
	     mLatch.countDown();

        }

        /**
         * Hook method for ping/pong acquire.
         */
        void acquire() {
            // TODO fill in here
        	semas[0].acquireUninterruptibly();        	
        
        	
        }

        /**
         * Hook method for ping/pong release.
         */
        void release() {
            // TODO fill in here
        	semas[1].release();
        	
//        	semas[1].acquireUninterruptibly();
//        	mLatch.countDown();
        }
    }

    /**
     * The method that actually runs the ping/pong program.
     */
    public static void process(String startString, 
                               String pingString,
                               String pongString, 
                               String finishString, 
                               int maxIterations) throws InterruptedException {

        // TODO initialize this by replacing null with the appropriate
        // constructor call.
//        mLatch = null;
        mLatch = new CountDownLatch(2);

        // Create the ping and pong SimpleSemaphores that control
        // alternation between threads.

        // TODO - You fill in here, make pingSema start out unlocked.
//        SimpleSemaphore pingSema = null;
        SimpleSemaphore pingSema = new SimpleSemaphore(1, true);
//        Semaphore pingSema = new Semaphore(1, true);
        // TODO - You fill in here, make pongSema start out locked.
//        SimpleSemaphore pongSema = null;
        SimpleSemaphore pongSema = new SimpleSemaphore(0, true);
//        Semaphore pongSema = new Semaphore(0, true);

        System.out.println(startString);

        // Create the ping and pong threads, passing in the string to
        // print and the appropriate SimpleSemaphores.
//        PlayPingPongThread ping = new PlayPingPongThread(/*
//                                                          * TODO - You fill in
//                                                          * here
//                                                          */);
        PlayPingPongThread ping = new PlayPingPongThread(pingString,pingSema,pongSema,maxIterations);

//        PlayPingPongThread pong = new PlayPingPongThread(/*
//                                                          * TODO - You fill in
//                                                          * here
//                                                          */);

        PlayPingPongThread pong = new PlayPingPongThread(pongString,pongSema,pingSema,maxIterations);

        // TODO - Initiate the ping and pong threads, which will call
        // the run() hook method.
        ping.start();
        pong.start();

        // TODO - replace the following line with a barrier
        // synchronizer call to mLatch that waits for both threads to
        // finish.
        mLatch.await();
        
//        throw new java.lang.InterruptedException();

        System.out.println(finishString);
    }

    /**
     * The main() entry point method into PingPongRight program.
     * 
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        process("Ready...Set...Go!", 
                "Ping!  ",
                " Pong! ",
                "Done!",
                mMaxIterations);
    }
}

