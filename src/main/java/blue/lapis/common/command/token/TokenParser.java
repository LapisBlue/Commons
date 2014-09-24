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
package blue.lapis.common.command.token;

import java.util.List;
import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;

/**
 * Converts a String parameter, as from a Command, into a game object of type {@code T}. Additionally, implementors
 * of this class are able to produce suggestions for completing a partial entry, such as for tab-complete.
 */
public interface TokenParser<T> {

    /**
     * Parse the provided parser and return a typesafe object for it.
     *
     * @param source the Player, console, or command block which initiated this object resolution
     * @param token the token to be resolved into an object of type {@code T}
     * @return an object of type T which corresponds to the String entered
     *
     * @throws InvalidTokenException if the String does not appear to match any object
     */
    @Nonnull
    T parse(@Nonnull CommandSource source, @Nonnull String token);

    /**
     * Get tab-complete suggestions for this parser
     *
     * @param token The parser to generate tab-complete suggestions for
     * @return a List of possible completions, or an empty List if none are available
     */
    @Nonnull
    List<String> suggest(@Nonnull CommandSource source, String token);
}
