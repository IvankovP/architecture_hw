package space.battle.core;

import space.battle.core.command.Command;
import space.battle.core.exception.ExceptionHandler;
import space.battle.core.utils.ThreadUtils;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

public class Game implements Runnable {

    public CountDownLatch countDownLatch;
    private final BlockingQueue<Command> commands;
    private final ExceptionHandler handler;
    private Supplier<Boolean> stoppedFunction;
    private Thread currentThread;
    private final int id;

    public Game(BlockingQueue<Command> commands, ExceptionHandler handler, int id) {
        this.commands = commands;
        this.handler = handler;
        this.id = id;
    }

    public void setStoppedFunction(Supplier<Boolean> stoppedFunction) {
        this.stoppedFunction = stoppedFunction;
    }

    public Queue<Command> getCommands() {
        return commands;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public Thread getCurrentThread() {
        return currentThread;
    }

    @Override
    public void run() {
        currentThread = Thread.currentThread();

        while (stoppedFunction != null && stoppedFunction.get()) {
            Command command = null;
            try {
                command = commands.take();
                System.out.println(currentThread.getName() + " Take!!");
                command.execute();

                ThreadUtils.sleep(300);
            } catch (Exception e) {
                handler.handle(e, command);
            }
        }

        System.out.println(Thread.currentThread().getName() + " is stopped");
        ThreadUtils.downAndAwaitCountDownLatch(countDownLatch);
    }
}