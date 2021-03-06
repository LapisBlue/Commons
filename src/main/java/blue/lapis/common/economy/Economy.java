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
package blue.lapis.common.economy;

import blue.lapis.common.economy.formatter.BalanceFormatterRegistry;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Represents the economy as a whole. This class is currently under API scrutiny,
 * so details may change frequently.
 */
public final class Economy {
    private static Economy INSTANCE;

    /*
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private HashMap<Object, EconomyAccount> accounts = Maps.newHashMap();
    */

    private Economy() {
        // TODO
    }

    public static Economy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Economy();
        }
        return INSTANCE;
    }

    public String formatBalance(double amount, String prefix) {
        return BalanceFormatterRegistry.get(prefix).format(amount);
    }

    public List<EconomyAccount> getAccounts(Object owner) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public EconomyAccount getAccount(Object owner, String accountName) {
        throw new UnsupportedOperationException();
    }

    public EconomyAccount createAccount(Object owner, String accountName) {
        throw new UnsupportedOperationException();
    }
}
