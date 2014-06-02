package edu.vuum.mocca;

import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 *
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject.  It must implement both "Fair" and
 *        "NonFair" semaphore semantics, just liked Java Semaphores. 
 */
public class SimpleSemaphore {
	
	   /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    private ReentrantLock mLock;

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
    private Condition mCond;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here
    private volatile int mPermits;
	
    /**
     * Constructor initialize the data members.  
     */
    public SimpleSemaphore (int permits,
                            boolean fair)
    { 
        // TODO - you fill in here
    	mPermits = permits;
    	mLock = new ReentrantLock(fair);
    	mCond = mLock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can
     * be interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
    	mLock.lockInterruptibly();
      	try{
    		if(mPermits<=0)
				try {
					mCond.await();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
    			mPermits--;
    	}
    	finally
    	{    
    		mLock.unlock();
    	}

    }

    /**
     * Acquire one permit from the semaphore in a manner that
     * cannot be interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
    	
    
    	mLock.lock();
    	try{
    		if(mPermits<=0)
				try {
					mCond.await();
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
    			mPermits--;
    	}
    	finally
    	{    
    		mLock.unlock();
    	}
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here
//    	mPermits++;
    	mLock.lock();
    	try{
    		
    		mPermits++;
    		mCond.signal();
    	}
    	finally
    	{
    		mLock.unlock();
    	}
  
    }
    
    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
        return mPermits;
    }
 
}
