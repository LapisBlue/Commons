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
package blue.lapis.common.economy.event;

import blue.lapis.common.LapisCommonsPlugin;
import blue.lapis.common.economy.Transaction;
import blue.lapis.common.economy.account.EconomyAccount;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Result;

import javax.annotation.Nonnull;

/**
 * Signals that a {@link Transaction} is about to complete, and offers a chance to change its details before it
 * is applied.
 */
public class TransactionEvent implements Cancellable, EconomyEvent {
    private final Transaction transaction;
    private boolean cancelled = false;
    private Result result = Result.NO_RESULT;

    public TransactionEvent(@Nonnull Transaction transaction) {
        this.transaction = transaction;
    }

    @Nonnull
    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    @Nonnull
    public EconomyAccount getAccount() {
        return transaction.getAccount();
    }

    @Override
    @Nonnull
    public Game getGame() {
        return LapisCommonsPlugin.getGame();
    }

    @Override
    @Nonnull
    public String getSimpleName() {
        return "TransactionEvent";
    }

    @Override
    public boolean isCancellable() {
        return true;
    }

    @Override
    @Nonnull
    public Result getResult() {
        return result;
    }

    @Override
    public void setResult(@Nonnull Result result) {
        this.result = result;
    }
}
