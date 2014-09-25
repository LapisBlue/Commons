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

import blue.lapis.common.command.CommandContext;
import blue.lapis.common.command.token.InvalidTokenException;
import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Lapis implementation of {@link CommandContext}
 */
public class CommandContextImpl<S extends CommandSource> implements CommandContext<S> {

    private final S source;
    private String line = "";
    private String[] tokens = new String[0];
    private Object[] args = new Object[0];

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
    public String[] getTokens() {
        return tokens.clone(); //prevent tampering
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, int argNum) {
        if (args.length <= argNum) return null;

        if (clazz.isAssignableFrom(args[argNum].getClass())) {
            return (T) args[argNum];
        } else {
            //convenience fallbacks
            if (clazz.equals(String.class)) return (T) args[argNum].toString();

            throw new InvalidTokenException(args[argNum].getClass().getSimpleName(), clazz);
        }
    }

    /**
     * Sets the raw input line, minus the recognized command.
     *
     * @param line the raw input line
     */
    @Nonnull
    public CommandContext withLine(@Nonnull final String line) {
        this.line = line;
        return this;
    }

    /**
     * Sets the parsed tokens
     */
    @Nonnull
    public CommandContext withTokens(@Nonnull final String[] tokens) {
        this.tokens = tokens;
        return this;
    }
}
