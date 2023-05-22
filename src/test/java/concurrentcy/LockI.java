package concurrentcy;

import common.Utis;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockI {

    // ReentrantLock: 可重入鎖
    private final Lock lock = new ReentrantLock();
    // ReentrantReadWriteLock: 讀寫鎖
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Test
    public void testI() throws InterruptedException {
        Thread a = new Thread(this::insert);
        a.start();

        Thread b = new Thread(this::insert);
        b.start();

        Thread.sleep(10000);
    }

    private void insert() {
        lock.lock();

        try {
            System.out.println(Utis.convertDate() + "-當前thread:" + Thread.currentThread().getName() + "獲得鎖");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Utis.convertDate() + "-當前thread" + Thread.currentThread().getName() + "釋放鎖");
            lock.unlock();
        }
    }

    @Test
    public void testII() throws InterruptedException {
        Thread c = new Thread(this::write);
        c.start();

        Thread a = new Thread(this::read);
        a.start();

        Thread b = new Thread(this::read);
        b.start();

        Thread.sleep(20000);
    }

    private void read() {
        readWriteLock.readLock().lock();

        try {
            System.out.println(Utis.convertDate() + "-當前thread" + Thread.currentThread().getName() + "獲得讀鎖");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Utis.convertDate() + "-當前thread" + Thread.currentThread().getName() + "釋放讀鎖");
            readWriteLock.readLock().unlock();
        }
    }

    private void write() {
        readWriteLock.writeLock().lock();

        try {
            System.out.println(Utis.convertDate() + "-當前thread" + Thread.currentThread().getName() + "獲得寫鎖");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Utis.convertDate() + "-當前thread" + Thread.currentThread().getName() + "釋放寫鎖");
            readWriteLock.writeLock().unlock();
        }
    }
}
