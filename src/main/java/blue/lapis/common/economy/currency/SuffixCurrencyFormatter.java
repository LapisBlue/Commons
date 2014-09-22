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
package blue.lapis.common.economy.currency;

import java.text.NumberFormat;

import javax.annotation.Nonnull;

/**
 * Formats currency by simply appending a currency symbol to a formatted number.
 */
public class SuffixCurrencyFormatter implements CurrencyFormatter {
    private final String singular;
    private final String plural;
    private final NumberFormat formatter;

    public SuffixCurrencyFormatter(String singular, String plural) {
        this.singular = singular;
        this.plural = plural;
        formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
    }

    public SuffixCurrencyFormatter(String prefix) {
        this(prefix, prefix);
    }


    @Nonnull
    @Override
    public String format(double amount) {
        if (amount==1.0d) {
            return formatter.format(amount)+singular;
        } else {
            return formatter.format(amount)+plural;
        }
    }


}
