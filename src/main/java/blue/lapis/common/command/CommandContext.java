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
package blue.lapis.common.command;

import blue.lapis.common.command.token.InvalidTokenException;
import com.google.common.collect.ImmutableList;
import org.spongepowered.api.util.command.CommandSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents the environment in which a command has been called; its CommandConfiguration, its CommandSource,
 * parsed arguments, etc.
 */
public interface CommandContext<S extends CommandSource> {

    /**
     * @return the CommandSource which this command was issued from
     */
    S getSource();

    /**
     * @return the entire line of text entered, except for the portion recognized as the command
     */
    public String getLine();

    /**
     * @return the tokens in String format, as determined by the Tokenizer for this command
     */
    public ImmutableList<String> getTokens();

    /**
     * Gets the specified typesafe argument. Throws {@link InvalidTokenException} if the argument is not of the
     * expected type.
     *
     * @param clazz The expected class of the argument
     * @param argNum The index of the argument. Note that this is the argument number, not the token number.
     * @param <T> The expected class of the argument
     * @return The argument if it exists, or null if the index is past the end of the list
     */
    @Nullable
    public <T> T get(Class<T> clazz, int argNum);
}
