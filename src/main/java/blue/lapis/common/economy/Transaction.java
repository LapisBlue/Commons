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
public class Transaction {

    private boolean isSetter = false;
    private Status status = Status.INCOMPLETE;
    private EconomyAccount account;
    private Object initiator = null;
    private Object target = null;
    private Object reason = null;
    private double delta = 0.0d;
    private double balance = Double.NaN;

    private Transaction(EconomyAccount account) {
        this.account = account;
    }

    public static Transaction of(EconomyAccount account) {
        return new Transaction(account);
    }

    public static Transaction setBalance(EconomyAccount account, double balance) {
        Transaction result = new Transaction(account);
        result.balance = balance;
        result.isSetter = true;
        return result;
    }

    public Transaction withInitiator(Object o) {
        if (status == Status.INCOMPLETE) this.initiator = o;
        return this;
    }

    public Transaction withTarget(Object o) {
        if (status == Status.INCOMPLETE) this.target = o;
        return this;
    }

    public Transaction withReason(Object o) {
        if (status == Status.INCOMPLETE) this.reason = o;
        return this;
    }

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
    public Transaction fireEvent() {
        status = Status.EVENT_FIRED;

        TransactionEvent event = new TransactionEventImpl(this);
        LapisCommonsPlugin.getGame().getEventManager().call(event);

        if (event.getResult() == Result.DENY || event.isCancelled()) {
            status = Status.CANCELLED;
        }

        return this;
    }

    public Status commit() {
        Preconditions.checkState(status == Status.EVENT_FIRED, "Expected transaction state %s, " +
                "found %s instead.", Status.EVENT_FIRED, status);
        if (account.apply(this)) {
            status = Status.COMPLETE;
        } else {
            status = Status.FAILED;
        }

        return status;
    }

    static enum Status {
        INCOMPLETE,
        EVENT_FIRED,
        CANCELLED,
        FAILED,
        COMPLETE
    }

}
