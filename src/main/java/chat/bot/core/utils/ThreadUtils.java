package chat.bot.core.utils;

import java.util.concurrent.CountDownLatch;

public class ThreadUtils {

    public ThreadUtils() {
        throw new UnsupportedOperationException();
    }

    public static void awaitCountDownLatch(CountDownLatch countDownLatch) {
        if (countDownLatch == null) {
            return;
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void downAndAwaitCountDownLatch(CountDownLatch countDownLatch) {
        if (countDownLatch == null) {
            return;
        }
        countDownLatch.countDown();
        awaitCountDownLatch(countDownLatch);
    }

    public static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
