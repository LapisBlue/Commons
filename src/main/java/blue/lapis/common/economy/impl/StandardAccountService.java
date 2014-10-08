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

package blue.lapis.common.economy.impl;

import blue.lapis.common.economy.AccountService;
import blue.lapis.common.economy.EconomyAccount;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class StandardAccountService implements AccountService {

    private static final String DEFAULT_ACCOUNT_NAME = "wallet";

    private static HashMap<Object, HashMap<String, EconomyAccount>> accounts = Maps.newHashMap();

    @Nonnull
    @Override
    public ImmutableSet<EconomyAccount> getAccounts(@Nonnull Object owner) {
        HashMap<String,EconomyAccount> ownerAccounts = accounts.get(owner);
        if (ownerAccounts==null) return ImmutableSet.of();
        return ImmutableSet.copyOf(ownerAccounts.values());
    }

    @Override
    public boolean hasAccount(@Nonnull Object owner, @Nonnull String accountName) {
        return getAccount(owner, accountName) != null;
    }

    @Nullable
    @Override
    public EconomyAccount getAccount(@Nonnull Object owner, @Nonnull String accountName) {
        HashMap<String,EconomyAccount> ownerAccounts = accounts.get(owner);
        if (ownerAccounts==null) return null;

        return ownerAccounts.get(accountName);
    }

    @Nonnull
    @Override
    public EconomyAccount getOrCreateAccount(@Nonnull Object owner, @Nonnull String accountName) {
        HashMap<String,EconomyAccount> ownerAccounts = accounts.get(owner);
        if (ownerAccounts==null) {
            ownerAccounts = Maps.newHashMap();
            accounts.put(owner, ownerAccounts);
        }

        EconomyAccount existing = ownerAccounts.get(accountName);
        if (existing==null) {
            existing = new StandardEconomyAccount(accountName);
            ownerAccounts.put(accountName, existing);
        }

        return existing;
    }

    @Nullable
    @Override
    public EconomyAccount getDefaultAccount(@Nonnull Object owner) {
        return getAccount(owner, DEFAULT_ACCOUNT_NAME);
    }

    @Nonnull
    @Override
    public EconomyAccount getOrCreateDefaultAccount(@Nonnull Object owner) {
        return getOrCreateAccount(owner, DEFAULT_ACCOUNT_NAME);
    }



}
