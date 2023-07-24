package chat.bot.core.utils;

import chat.bot.core.command.Command;
import chat.bot.core.exception.ExceptionHandler;

import java.util.Queue;

public class CommandRunner implements Runnable {

    private final Queue<Command> commands;
    private final ExceptionHandler handler;
    private boolean stop;

    public CommandRunner(Queue<Command> commands, ExceptionHandler handler) {
        this.commands = commands;
        this.handler = handler;
    }

    @Override
    public void run() {
        while (!stop) {
            Command command = commands.poll();
            try {
                if (command != null) command.execute();
            } catch (Exception ex) {
                handler.handle(ex, command);
            }
        }
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Queue<Command> getCommands() {
        return commands;
    }
}