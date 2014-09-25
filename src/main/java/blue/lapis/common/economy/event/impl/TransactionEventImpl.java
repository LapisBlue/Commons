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
package blue.lapis.common.economy.event.impl;

import blue.lapis.common.economy.Transaction;
import blue.lapis.common.economy.event.TransactionEvent;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;

/**
 * Signals that a {@link blue.lapis.common.economy.Transaction} is about to complete, and offers a chance to
 * change its details before it is applied.
 */
public class TransactionEventImpl extends EconomyEventImpl implements TransactionEvent {
    private final Transaction transaction;

    public TransactionEventImpl(@Nonnull Transaction transaction) {
        super(Preconditions.checkNotNull(transaction, "transaction").getAccount());
        this.transaction = transaction;
    }

    @Override
    @Nonnull
    public Transaction getTransaction() {
        return transaction;
    }
}
