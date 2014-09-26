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
package blue.lapis.common.command.impl;

import blue.lapis.common.command.Tokenizer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Reference implementation of the Tokenizer; generally splits on spaces, except inside quoted strings.
 */
public class StandardTokenizer implements Tokenizer {

    @Nonnull
    @Override
    public ImmutableList<String> getTokens(@Nonnull final String s) {
        List<String> result = Lists.newArrayList();

        String token = "";
        char delimiter = ' ';
        for(int i=0; i<s.length(); i++) {
            char cur = s.charAt(i);

            if (delimiter!=' ') {
                if (cur!=delimiter) {
                    token += cur;
                } else {
                    delimiter = ' ';
                    result.add(token);
                    token = "";
                }
            } else {
                if (cur=='"') {
                    delimiter = '"';
                    continue;
                } else {
                    if (Character.isWhitespace(cur)) {
                        if (!token.isEmpty()) {
                            result.add(token);
                            token = "";
                        }
                    } else {
                        if (Character.isLetterOrDigit(cur)) {
                            token += cur;
                        } else {
                            //Commit both the existing token and this new symbol
                            if (!token.isEmpty()) {
                                result.add(token);
                                token = "";
                            }
                            result.add(""+cur);
                        }
                    }
                }
            }
        }
        if (!token.isEmpty()) result.add(token);

        return ImmutableList.copyOf(result);
    }
}
