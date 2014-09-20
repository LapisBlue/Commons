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
package blue.lapis.common.command;

import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;

/**
 * Represents the environment in which a command has been called; its CommandConfiguration, its CommandSource,
 * parsed arguments, etc.
 */
public class CommandContext {

    private final CommandSource source;
    private String line = "";
    private String[] tokens = new String[0];

    public CommandContext(@Nonnull final CommandSource source) {
        this.source = source;
    }

    /**
     * @return the Player, CommandBlock, or Console which this command was issued from
     */
    @Nonnull
    public CommandSource getCommandSource() {
        return source;
    }

    /**
     * @return the entire line of text entered, except for the portion recognized as the command
     */
    @Nonnull
    public String getLine() {
        return line;
    }

    /**
     * @return the tokens in String format, as determined by the Tokenizer for this command
     */
    @Nonnull
    public String[] getTokens() {
        return tokens;
    }
}
