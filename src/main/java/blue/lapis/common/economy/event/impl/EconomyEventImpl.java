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

import blue.lapis.common.economy.account.EconomyAccount;
import blue.lapis.common.economy.event.EconomyEvent;
import com.google.common.base.Preconditions;
//import org.spongepowered.api.event.BaseEvent;
import org.spongepowered.api.event.Result;

import javax.annotation.Nonnull;

public class EconomyEventImpl implements EconomyEvent {

    private final EconomyAccount account;
    private Result result = Result.NO_RESULT;
    private boolean cancelled = false;

    public EconomyEventImpl(@Nonnull EconomyAccount account) {
        this.account = Preconditions.checkNotNull(account, "account");
    }

    @Override
    @Nonnull
    public EconomyAccount getAccount() {
        return account;
    }


    //Uncomment when BaseEvent stops being stupid.

    @Override
    public boolean isCancellable() {
        return true;
    }

    @Override
    public Result getResult() {
        return result;
    }

    @Override
    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

}
