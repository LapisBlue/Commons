/*
 * LapisCommons
 * Copyright (c) 2014, LapisDev <https://github.com/LapisDev>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package blue.lapis.common.command.token;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe registry/factory for {@link TokenParser}s, which resolve Strings into typesafe game objects.
 */
public class TokenParserRegistry {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Map<Class<?>, TokenParser<?>> tokenParserMap = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> TokenParser<T> get(@Nonnull Class<T> clazz) {
        TokenParser<T> t;
        lock.readLock().lock();
        {
            t = (TokenParser<T>) tokenParserMap.get(clazz);
        }
        lock.readLock().unlock();
        return t;
    }

    public static <T> void register(Class<T> clazz, TokenParser<T> parser) {
        lock.writeLock().lock();
        {
            tokenParserMap.put(clazz, parser);
        }
        lock.writeLock().unlock();
    }
}
