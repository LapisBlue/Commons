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
package blue.lapis.common.command.impl;

import blue.lapis.common.command.token.InvalidTokenException;
import blue.lapis.common.command.token.TokenParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.spongepowered.api.util.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BooleanTokenParser implements TokenParser<Boolean> {
    private static final ImmutableList<String> TRUE = ImmutableList.of("1", "true", "yes", "on");
    private static final ImmutableList<String> FALSE = ImmutableList.of("0", "false", "no", "off");

    @Override
    public Boolean parse(CommandSource source, String token) {
        token = Parsing.toLowerCase(token.trim());
        if (TRUE.contains(token)) return true;
        if (FALSE.contains(token)) return false;
        throw new InvalidTokenException(token, Boolean.class);
    }

    @Override
    public List<String> suggest(CommandSource source, String token) {
        token = token.trim();
        if (!token.isEmpty()) {
            token = Parsing.toLowerCase(token);

            ArrayList<String> result = Lists.newArrayListWithCapacity(1);

            for (String bool : TRUE)
                if (bool.startsWith(token)) {
                    result.add(bool);
                }
            for (String bool : FALSE)
                if (bool.startsWith(token)) {
                    result.add(bool);
                }

            if (!result.isEmpty())
                return ImmutableList.copyOf(result);
        }

        return ImmutableList.of("true", "false");
    }
}
