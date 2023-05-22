package concurrentcy;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * https://juejin.cn/post/7212466685450207290
 */
public class CompleteFutureI {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        System.out.println("Main thread: " + Thread.currentThread().getId() + ", name: " + Thread.currentThread().getName());
        CompletableFuture future = CompletableFuture.runAsync(this::task).thenRun(this::action);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(this::delayTask).thenRun(this::action);
        future.get();
        future1.get();
    }

    @Test
    public void testI() throws ExecutionException, InterruptedException {
        CompletableFuture<String> step1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("execute one");
            return "step 1";
        }).thenApply(request -> {
            System.out.println("last step: " + request);
            return "step 2";
        });

        System.out.println("result: " + step1.get());
    }

    @Test
    public void testII() throws ExecutionException, InterruptedException {
        CompletableFuture<String> step1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("execute 1");
            try {
                throw new Exception("test");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
//            return "step 1 ";
        });
        CompletableFuture<String> step2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("execute 2");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "step 2 ";
        });
        CompletableFuture<String> step3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("execute 3");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "step 3 ";
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(step1, step2, step3);
        CompletableFuture<String> last = all.thenApply(req -> {
            System.out.println("execute last");
            String result1 = step1.join();
            String result2 = step2.join();
            String result3 = step3.join();
            return result1 + result2 + result3;
        });
        System.out.println("result: " + last.get());
    }

    public Integer task() {
        // do something
        System.out.println("Method thread id in task: " + Thread.currentThread().getId() + ", name: " + Thread.currentThread().getName());
        return 1;
    }

    public Integer delayTask() {
        // do something
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Method thread id in task: " + Thread.currentThread().getId() + ", name: " + Thread.currentThread().getName());
        return 1;
    }

    public void action() {
        System.out.println("Method thread id in action: " + Thread.currentThread().getId() + ", name:" + Thread.currentThread().getName());
    }

}
