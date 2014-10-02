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
import blue.lapis.common.economy.impl.AbstractEconomyAccount;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.junit.Assert.assertEquals;

public class TransactionTest {


    private final Economy economy = Economy.getInstance();

    @Test
    public void getterSetterTest() {
        EconomyAccount account = new AbstractEconomyAccount() {
            @Nonnull
            @Override
            public String getID() {
                return "TestAccount";
            }
            @Override
            public double getBalance() {
                return -1;
            }
            @Override
            public BalanceFormatter getFormatter() {
                return null;
            }
            @Override
            public void apply(Transaction t) {}
        };
        Transaction t = Transaction.on(account).withInitiator("initator")
                .withTarget("target")
                .withReason("reason")
                .add(1234);

        assertEquals("initator", t.getInitiator());
        assertEquals("target", t.getTarget());
        assertEquals("reason", t.getReason());
        assertEquals(1234.0, t.getDelta(), 0);
        assertEquals(account, t.getAccount());
        assertEquals(null, t.getAbsolute());

        Transaction t2 = Transaction.on(account).subtract(1234);
        assertEquals(-1234.0, t2.getDelta(), 0);

        Transaction t3 = Transaction.on(account).setAbsolute(1234);
        assertEquals(1234, t3.getAbsolute().doubleValue(), 0);
        assertEquals(0, t3.getDelta(), 0);
    }
}
