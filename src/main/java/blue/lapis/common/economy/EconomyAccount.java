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
package blue.lapis.common.economy;

import blue.lapis.common.economy.Transaction;
import blue.lapis.common.economy.formatter.BalanceFormatter;

import javax.annotation.Nonnull;

/**
 * <p>Represents a single account of a single type of currency. Generally, accounts of a common currency have a
 * common prefix in their ID (such as "gold:savings" and "gold:inventory", or even "gold"). This interface does
 * not address who or what the holder is, or the number of holders, only the balance.</p>
 *
 * <p>This interface is available for custom economy systems to implement, either to allow credit, store
 * transaction logs, or create other unforseen behaviors.</p>
 */
public interface EconomyAccount {

    @Nonnull
    String getID();

    /**
     * @return The current balance on this account
     */
    double getBalance();

    /**
     * @return a formatter which can display this account's currency
     */
    BalanceFormatter getFormatter();

    /**
     * Apply a Transaction's proposed change to this EconomyAccount. The Transaction MUST be in
     * State.EVENT_FIRED, otherwise this method can choose to throw an IllegalStateException or return false.
     *
     * @param t The Transaction to apply
     * @return False if the transaction could not be applied, such as if it would drop a non-credit account
     * below zero.
     */
    boolean apply(Transaction t);

}
