package concurrentcy;

import common.Utis;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockI {

    /**
     * 公平鎖
     */
    @Test
    public void testFair() throws InterruptedException {
        FairLock lock = new FairLock(true);
        Thread t1 = new Thread(lock, "A");
        Thread t2 = new Thread(lock, "B");
        t1.start();
        t2.start();
        Thread.sleep(100000);
    }

    /**
     * 非公平鎖
     */
    @Test
    public void testUnFair() throws InterruptedException {
        FairLock lock = new FairLock(false);
        Thread t1 = new Thread(lock, "A");
        Thread t2 = new Thread(lock, "B");
        t1.start();
        t2.start();
        Thread.sleep(100000);
    }

    public static class FairLock implements Runnable {

        private int seatNum = 100;
        private final ReentrantLock lock;

        public FairLock(boolean bol) {
            lock = new ReentrantLock(bol);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    if (seatNum > 0) {
                        Thread.sleep(1000);
                        seatNum--;
                        System.out.println(Utis.convertDate() + "-thread:" +Thread.currentThread().getName() + "占用1個座位,剩餘:" +seatNum);
                    } else {
                      break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
