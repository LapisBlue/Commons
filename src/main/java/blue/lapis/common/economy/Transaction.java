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

import blue.lapis.common.LapisCommonsPlugin;
import blue.lapis.common.economy.account.EconomyAccount;
import blue.lapis.common.economy.event.TransactionEvent;
import blue.lapis.common.economy.event.impl.TransactionEventImpl;
import com.google.common.base.Preconditions;
import org.spongepowered.api.event.Result;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 */
public final class Transaction {
    private Status status = Status.INCOMPLETE;
    private EconomyAccount account;
    private Object initiator;
    private Object target;
    private Object reason;
    private double delta;
    private Double balance;

    private Transaction(EconomyAccount account) {
        this.account = Preconditions.checkNotNull(account, "account");
    }

    @Nonnull
    public static Transaction of(EconomyAccount account) {
        return new Transaction(account);
    }

    @Nonnull
    public static Transaction setBalance(EconomyAccount account, double balance) {
        Transaction result = of(account);
        result.balance = balance;
        return result;
    }

    @Nonnull
    public Transaction withInitiator(Object initiator) {
        validateState(Status.INCOMPLETE);
        this.initiator = initiator;
        return this;
    }

    @Nonnull
    public Transaction withTarget(Object target) {
        validateState(Status.INCOMPLETE);
        this.target = target;
        return this;
    }

    @Nonnull
    public Transaction withReason(Object reason) {
        validateState(Status.INCOMPLETE);
        this.reason = reason;
        return this;
    }

    @Nonnull
    public EconomyAccount getAccount() {
        return account;
    }

    @Nullable
    public Object getReason() {
        return reason;
    }

    @Nullable
    public Object getInitiator() {
        return initiator;
    }

    @Nullable
    public Object getTarget() {
        return target;
    }

    public double getDelta() {
        return delta;
    }

    @Nullable
    public Double getAbsolute() {
        return balance;
    }

    @Nonnull
    public Status getStatus() {
        return status;
    }

    @Nonnull
    public Transaction add(double amount) {
        delta += amount;
        return this;
    }

    @Nonnull
    public Transaction subtract(double amount) {
        delta -= amount;
        return this;
    }

    @Nonnull
    public Transaction multiply(double amount) {
        delta *= amount;
        return this;
    }

    @Nonnull
    public Transaction divide(double amount) {
        delta /= amount;
        return this;
    }

    @Nonnull
    public Transaction setAbsolute(double amount) {
        balance = amount;
        return this;
    }

    @Nonnull
    public Status commit() {
        validateState(Status.INCOMPLETE);
        TransactionEvent event = new TransactionEventImpl(this);
        LapisCommonsPlugin.getGame().getEventManager().call(event);

        if (event.getResult() == Result.DENY || event.isCancelled()) {
            return status = Status.CANCELLED;
        }

        if (account.apply(this)) {
            status = Status.COMPLETE;
        } else {
            status = Status.FAILED;
        }

        return status;
    }

    private void validateState(Status expected) {
        Preconditions.checkState(status == expected, "Expected transaction state %s, " +
                "found %s instead.", expected, status);
    }

    static enum Status {
        INCOMPLETE,
        CANCELLED,
        FAILED,
        COMPLETE
    }

}
