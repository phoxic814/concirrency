package concurrentcy;


import common.Utis;
import org.junit.jupiter.api.Test;

public class Synchronized {

    /**
     * synchronized 作用為整個實例, 不同實例間的鎖不衝突
     * 同個實例間的方法互斥
     */
    @Test
    public void testI() throws InterruptedException {
        Synchronized x = new Synchronized();
        Synchronized y = new Synchronized();

        Thread a = new Thread(x::syncA);
        a.start();

        Thread b = new Thread(x::syncB);
        b.start();

        Thread c = new Thread(y::syncA);
        c.start();

        Thread d = new Thread(y::syncB);
        d.start();

        Thread.sleep(10000);
    }

    /**
     * static synchronized 作用為整個對象 class
     * 不同實例間皆作用
     */
    @Test
    public void testII() throws InterruptedException {
        Thread a = new Thread(Synchronized::syncC);
        a.start();

        Thread b = new Thread(Synchronized::syncD);
        b.start();

        Thread.sleep(10000);
    }

    /**
     * 但實例鎖可以跟對象所獨立 不衝突
     */
    @Test
    public void testIII() throws InterruptedException {
        Synchronized x = new Synchronized();
        Synchronized y = new Synchronized();

        Thread a = new Thread(x::syncA);
        a.start();

        Thread b = new Thread(x::syncB);
        b.start();

        Thread c = new Thread(y::syncA);
        c.start();

        Thread d = new Thread(y::syncB);
        d.start();

        Thread e = new Thread(Synchronized::syncC);
        e.start();

        Thread f = new Thread(Synchronized::syncD);
        f.start();

        Thread.sleep(10000);
    }

    private synchronized void syncA() {
        try {
            System.out.println(Utis.convertDate() + "-當前thread:" + Thread.currentThread().getName() + "-執行syncA");
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        System.out.println(Utis.convertDate() + " syncA 執行完畢");
    }

    private synchronized void syncB() {
        try {
            System.out.println(Utis.convertDate() + "-當前thread:" + Thread.currentThread().getName() + "-執行syncB");
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        System.out.println(Utis.convertDate() + " syncB 執行完畢");
    }

    private static synchronized void syncC() {
        try {
            System.out.println(Utis.convertDate() + "-當前thread:" + Thread.currentThread().getName() + "-執行syncC");
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        System.out.println(Utis.convertDate() + " syncC 執行完畢");
    }

    private static synchronized void syncD() {
        try {
            System.out.println(Utis.convertDate() + "-當前thread:" + Thread.currentThread().getName() + "-執行syncD");
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        System.out.println(Utis.convertDate() + " syncD 執行完畢");
    }
}
