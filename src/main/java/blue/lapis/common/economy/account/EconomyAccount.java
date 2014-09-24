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
package blue.lapis.common.economy.account;

import blue.lapis.common.economy.currency.CurrencyFormatter;

/**
 * Represents a single account of a single type of currency. Generally, accounts of a common currency have a
 * common prefix in their ID (such as "gold:savings" and "gold:inventory", or even "gold"). This interface does
 * not address who or what the holder is, or the number of holders, only the balance.
 */
public interface EconomyAccount {

    String getID();

    /**
     * @return The current balance on this account
     */
    double getBalance();

    /**
     * Sets a new currency balance on this account
     *
     * @param balance The new balance for this account to carry
     */
    void setBalance(double balance);

    /**
     * Deposits currency into this account
     *
     * @param amount The amount of currency to deposit
     * @return The new balance on this account
     */
    double add(double amount);

    /**
     * Withdraws currency from this account
     *
     * @param amount The amount of currency to withdraw
     * @return The new balance on this account
     */
    double subtract(double amount);

    CurrencyFormatter getFormatter();

}
