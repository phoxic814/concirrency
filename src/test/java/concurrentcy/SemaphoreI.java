package concurrentcy;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

public class SemaphoreI {

    private Semaphore semaphore = new Semaphore(1);

    @Test
    public void test() throws InterruptedException {
        Thread a = new Thread(() -> {
            acquirePrint("a");
        });
        a.start();

        Thread b = new Thread(() -> {
            acquirePrint("b");
        });
        b.start();
        b.interrupt();

        Thread.sleep(15000);
        System.out.println(semaphore.availablePermits());
    }

    public void tryAcquirePrint(String threadCode) {
        try {
            boolean get = semaphore.tryAcquire();
            System.out.println(threadCode + " get semaphore :" + get);
            System.out.println(threadCode + " semaphore start :" + semaphore.availablePermits());
            System.out.println("Thread name :" + Thread.currentThread().getId());
            if (get) {
                Thread.sleep(5000);
                semaphore.release();
                System.out.println(threadCode + " semaphore release :" + semaphore.availablePermits());
            }
        } catch (Exception e) {
            System.out.println(threadCode + " Get semaphore error:" + e);
        }
    }

    public void acquirePrint(String threadCode) {
        try {
            semaphore.acquireUninterruptibly();
            System.out.println(threadCode + " semaphore start :" + semaphore.availablePermits());
            System.out.println(threadCode + " wait queue :" + semaphore.hasQueuedThreads());
            System.out.println("Thread name :" + Thread.currentThread().getName());
            Thread.sleep(5000);
            semaphore.release();
            System.out.println(threadCode + " semaphore release :" + semaphore.availablePermits());
        } catch (Exception e) {
            System.out.println(threadCode + " Get semaphore error:" + e);
        }
    }
}
