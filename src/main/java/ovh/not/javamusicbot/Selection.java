package ovh.not.javamusicbot;

import java.util.function.BiConsumer;

public class Selection<T, R> {
    public final T[] items;
    private final Formatter<T, R> formatter;
    public final BiConsumer<Boolean, T> callback;

    public Selection(T[] items, Formatter<T, R> formatter, BiConsumer<Boolean, T> callback) {
        this.items = items;
        this.formatter = formatter;
        this.callback = callback;
    }

    public String createMessage() {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (T t : items) {
            builder.append("\n`").append(i).append("` ").append(formatter.format(t));
            i++;
        }
        return builder.append("\n\nTo choose a song, type ?choose (number). If these don't match your search results, type ?cancel and try again.").toString();
    }

    public interface Formatter<T, R> {
        R format(T t);
    }
}