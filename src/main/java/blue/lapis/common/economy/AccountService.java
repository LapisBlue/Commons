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

import blue.lapis.common.economy.formatter.BalanceFormatter;
import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface AccountService {

    // getAccount != null
    boolean hasAccount(@Nonnull Object owner, @Nonnull String accountName);

    @Nullable
    ImmutableSet<String> getAccountNames(@Nonnull Object owner);

    @Nullable
    ImmutableSet<EconomyAccount> getAccounts(@Nonnull Object owner);

    @Nullable
    EconomyAccount getAccount(@Nonnull Object owner, @Nonnull String accountName);

    @Nonnull
    EconomyAccount createAccount(@Nonnull Object owner, @Nonnull String accountName);

    @Nullable
    BalanceFormatter getFormatter();

    @Nullable
    String formatBalance(double amount);

    @Nullable
    String getDefaultAccountName(@Nonnull Object owner);

    // getAccount(owner, getDefaultAccountName())
    @Nullable
    EconomyAccount getDefaultAccount(@Nonnull Object owner);

    // createDefaultAccount(owner, getDefaultAccountName())
    // null if no default account configured
    @Nullable
    EconomyAccount createDefaultAccount(@Nonnull Object owner);
}
