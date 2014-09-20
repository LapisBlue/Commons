/*
 * The MIT License (MIT)
 *
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
package blue.lapis.common.econ;

import java.util.UUID;

/**
 * @author Dragonfire
 */
public interface Economy {
    public static final String ACCOUNT_TYPE_PLAYER = "player";
    public static final String ACCOUNT_TYPE_BANK = "bank";

    public void createAccount(UUID player);

    public void createAccount(String type, String accountName);

    public void deleteAccount(UUID player);

    public void deleteAccount(String type, String accountName);

    public boolean accountExists(UUID player);

    public boolean accountExists(String type, String accountName);

    public double getBalance(UUID player);

    public double getBalance(String type, String accountName);

    public String getFormattedBalance(UUID player);

    public String getFormattedBalance(String type, String accountName);

    public String getFormattedAmount(double amount);

    public double parseCurrencyInput(String input);

    public boolean hasEnough(UUID player, double amount);

    public boolean hasEnough(String type, String accountName, double amount);

    public void substract(UUID player, double amount);

    public void substract(String type, String accountName, double amount);

    public void substract(UUID player, double amount, String source, String details);

    public void substract(String type, String accountName, double amount, String source, String details);

    public void add(UUID player, double amount);

    public void add(String type, String accountName, double amount);

    public void add(UUID player, double amount, String source, String detail);

    public void add(String type, String accountName, double amount, String source, String detail);

    public void modify(UUID player, double amount);

    public void modify(String type, String accountName, double amount);

    public void modify(UUID player, double amount, String source, String detail);

    public void modify(String type, String accountName, double amount, String source, String detail);

    public void set(UUID player, double amount);

    public void set(String type, String accountName, double amount);

    public void set(UUID player, double amount, String source, String detail);

    public void set(String type, String accountName, double amount, String source, String detail);

    public String getCurrencyNameSingular();

    public String getCurrencyNamePlural();

    public void printFlow(Object sender, String type, String accountName, int entries);

    public void printFlow(Object sender, UUID playerId, int entries);
}
