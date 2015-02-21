/*
 * LapisCommons
 * Copyright (c) 2014, Lapis <https://github.com/LapisBlue>
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
package blue.lapis.common.economy.formatter;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 */
public final class BalanceFormatterRegistry {

    private static final ReentrantReadWriteLock mutex = new ReentrantReadWriteLock();
    private static final Map<String, BalanceFormatter> registrations = Maps.newHashMap();

    private BalanceFormatterRegistry() {
    }

    /**
     * Retrieves a valid CurrencyFormatter for the given prefix, even if one is not registered.
     *
     * @param prefix The account-prefix for the currency in question
     * @return A CurrencyFormatter which can express the quantity and units of currency of this type
     */
    public static BalanceFormatter get(String prefix) {
        if (registrations.containsKey(prefix)) {
            mutex.readLock().lock();
            BalanceFormatter result = registrations.get(prefix);
            mutex.readLock().unlock();
            return result;
        } else {
            mutex.writeLock().lock();
            BalanceFormatter formatter = new SuffixBalanceFormatter(" " + prefix);
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
    public void register(String prefix, BalanceFormatter formatter) {
        mutex.writeLock().lock();
        registrations.put(prefix, formatter);
        mutex.writeLock().unlock();
    }
}
