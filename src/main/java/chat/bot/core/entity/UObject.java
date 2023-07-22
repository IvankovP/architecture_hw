package chat.bot.core.entity;

public interface UObject {
    void setProperty(String key, Object newValue);
    Object getProperty(String key);
}
