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
package blue.lapis.common.economy.account.detail;

import blue.lapis.common.economy.Economy;
import blue.lapis.common.economy.account.EconomyAccount;

import java.util.Optional;

public abstract class AbstractTransactionDetail implements TransactionDetail {

    protected final long id;
    protected double amount;
    protected EconomyAccount account;

    public AbstractTransactionDetail(EconomyAccount account, double amount) {
        this.amount = amount;
        this.id = Economy.inst().getTransactionDetailFactory().nextTransactionId();
    }

    @Override
    public double getDelta() {
        return this.amount;
    }

    @Override
    public long getTransactionId() {
        return this.id;
    }

    @Override
    public EconomyAccount getEconomyAccount() {
        return this.account;
    }

    @Override
    public Optional<String> getTransactionPurpose() {
        return Economy.inst().getTransactionDetailFactory().getPurpose(getTransactionId());
    }
    @Override
    public Optional<String> getSource() {
        return Economy.inst().getTransactionDetailFactory().getSource(getTransactionId());
    }
}
