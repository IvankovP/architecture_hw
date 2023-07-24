package chat.bot.core.adapter;

import chat.bot.core.entity.IOrder;
import chat.bot.core.entity.Position;
import chat.bot.core.entity.UObject;
import chat.bot.core.exception.CommandException;

import java.util.List;

public class OrderAdapter implements IOrder {

    private final UObject uObject;

    public OrderAdapter(UObject uObject) {
        this.uObject = uObject;
    }

    @Override
    public void addPosition(String name, int count) {
        ((List<Position>) getProperty("positions")).add(new Position(name, count));
    }

    private Object getProperty(String name) {
        Object property = uObject.getProperty(name);
        if (property != null) {
            return property;
        }
        throw new CommandException("Error get parameter " + name);
    }
}
