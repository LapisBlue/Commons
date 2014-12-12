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

import blue.lapis.common.command.CommandContext;
import blue.lapis.common.command.token.InvalidTokenException;
import blue.lapis.common.command.token.TokenParser;
import blue.lapis.common.command.token.TokenParserRegistry;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.spongepowered.api.util.command.CommandSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Lapis implementation of {@link CommandContext}
 */
public class CommandContextImpl<S extends CommandSource> implements CommandContext<S> {

    private final S source;
    private String line = "";
    private ImmutableList<String> tokens = ImmutableList.of();
    private List<Object> args = Lists.newArrayList();

    /**
     * Create an empty context for a command to run in.
     *
     * @param source the Player, CommandBlock, or Console the command was run from
     */
    public CommandContextImpl(@Nonnull final S source) {
        this.source = source;
    }

    @Override
    @Nonnull
    public S getSource() {
        return source;
    }

    @Override
    @Nonnull
    public String getLine() {
        return line;
    }

    @Override
    @Nonnull
    public ImmutableList<String> getTokens() {
        return tokens;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, int argNum) {
        Preconditions.checkState(args.size() == tokens.size()); //invariant
        Preconditions.checkArgument(argNum >= 0);

        //Requesting past the end of args is still valid
        if (args.size() <= argNum) return null;

        Object o = args.get(argNum);
        //Just-in-time resolve the argument if we need to
        if (o == null) {
            try {
                TokenParser<T> parser = TokenParserRegistry.get(clazz);
                String token = tokens.get(argNum);
                assert (token != null); //Also invariant. Tokenizer is not allowed to produce nulls.
                T t = parser.parse(source, token);
                args.set(argNum, t);
                return t;
            } catch (Throwable ignored) {
            }
            return null;
        }

        if (clazz.isAssignableFrom(o.getClass())) {
            return (T) args.get(argNum);
        } else {
            //convenience fallbacks
            if (clazz.equals(String.class)) return (T) args.get(argNum).toString();

            throw new InvalidTokenException(args.get(argNum).getClass().getSimpleName(), clazz);
        }
    }

    /**
     * Sets the raw input line, minus the recognized command.
     *
     * @param line the raw input line
     * @return This Object for further modification
     */
    @Nonnull
    public CommandContextImpl<S> withLine(@Nonnull final String line) {
        this.line = line;
        return this;
    }

    /**
     * Sets the parsed tokens
     *
     * @param tokens The raw tokens as defined by the Tokenizer for this command
     * @return This object for further modification
     */
    @Nonnull
    public CommandContextImpl<S> withTokens(@Nonnull final List<String> tokens) {
        this.tokens = ImmutableList.copyOf(tokens);
        return this;
    }
}
