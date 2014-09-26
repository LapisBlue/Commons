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

import blue.lapis.common.command.token.InvalidTokenException;
import blue.lapis.common.command.token.TokenParser;
import com.google.common.collect.ImmutableList;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Converts tokens into Integers, much like the name would suggest.
 */
public class IntegerTokenParser implements TokenParser<Integer> {
    @Nonnull
    @Override
    public Integer parse(@Nonnull CommandSource source, @Nonnull String token) {
        try {
            return Integer.valueOf(token.trim());
        } catch (NumberFormatException e) {
            throw new InvalidTokenException(token, Integer.class);
        }
    }

    @Nonnull
    @Override
    public List<String> suggest(@Nonnull CommandSource source, String token) {
        String match = token.trim();
        if (match.length()==0) return ImmutableList.of("1");
        char ch = match.charAt(0);

        if (Character.isDigit(ch)) {
            try {
                //Parse it out into an integer and return it back. This trims off any garbage.
                return ImmutableList.of(Integer.valueOf(match).toString());
            } catch (NumberFormatException e) {
                //The first character was a digit. We checked.
                //This code section is probably unreachable but I like to be prepared for the impossible.
                return ImmutableList.of(""+ch);
            }
        } else {
            //This String is invalid, so suggest a number
            return ImmutableList.of("1");
        }
    }
}
