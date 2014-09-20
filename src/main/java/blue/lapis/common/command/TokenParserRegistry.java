package net.spongedev.commons.command;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 */
public class TokenParserRegistry {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Map<Class<?>, TokenParser<?>> tokenParserMap = new HashMap<Class<?>, TokenParser<?>>();

    @SuppressWarnings("unchecked")
    public static @Nullable <T> TokenParser<T> get(@Nonnull Class<T> clazz) {
        TokenParser<T> t;
        lock.readLock().lock(); {
            t = (TokenParser<T>) tokenParserMap.get(clazz);
        } lock.readLock().unlock();
        return t;
    }

    public static <T> void egister(Class<T> clazz, TokenParser<T> parser) {
        lock.writeLock().lock(); {
            tokenParserMap.put(clazz, parser);
        } lock.writeLock().unlock();
    }
}
