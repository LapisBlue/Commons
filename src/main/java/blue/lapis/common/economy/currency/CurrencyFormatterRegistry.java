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
package blue.lapis.common.economy.currency;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nonnull;

import blue.lapis.common.economy.api.custom.CurrencyFormatter;
import com.google.common.collect.Maps;

/**
 *
 */
public final class CurrencyFormatterRegistry {

    private static final ReentrantReadWriteLock mutex = new ReentrantReadWriteLock();
    private static final Map<String, CurrencyFormatter> registrations = Maps.newHashMap();

    private CurrencyFormatterRegistry() {
    }

    /**
     * Retrieves a valid CurrencyFormatter for the given prefix, even if one is not registered.
     *
     * @param prefix The account-prefix for the currency in question
     * @return A CurrencyFormatter which can express the quantity and units of currency of this type
     */
    @Nonnull
    public static CurrencyFormatter get(@Nonnull String prefix) {
        if (registrations.containsKey(prefix)) {
            mutex.readLock().lock();
            CurrencyFormatter result = registrations.get(prefix);
            mutex.readLock().unlock();
            return result;
        } else {
            mutex.writeLock().lock();
            CurrencyFormatter formatter = new SuffixCurrencyFormatter(" " + prefix);
            registrations.put(prefix, formatter);
            mutex.writeLock().unlock();
            return formatter;
        }
    }

    /**
     * Register a formatter for the specified currency account-prefix.
     *
     * @param prefix The account-prefix for the currency to register
     * @param formatter A CurrencyFormatter which can turn amounts of this currency into a human-readable
     * String
     */
    public void register(@Nonnull String prefix, @Nonnull CurrencyFormatter formatter) {
        mutex.writeLock().lock();
        registrations.put(prefix, formatter);
        mutex.writeLock().unlock();
    }
}
