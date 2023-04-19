package space.battle.core;

import space.battle.core.command.Command;
import space.battle.core.exception.ExceptionHandler;

import java.util.Queue;

public class CommandRunner implements Runnable {

    private final Queue<Command> commands;
    ExceptionHandler handler;

    public CommandRunner(Queue<Command> commands, ExceptionHandler handler) {
        this.commands = commands;
        this.handler = handler;
    }

    @Override
    public void run() {
        while (!commands.isEmpty()) {
            Command command = commands.poll();
            try {
                command.execute();
            } catch (Exception ex) {
                handler.handle(ex, command);
            }
        }
    }
}