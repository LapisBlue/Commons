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

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

import javax.annotation.Nonnull;

/**
 * Reference implementation of the Tokenizer; generally splits on spaces, except inside quoted strings.
 */
public class StandardTokenizer implements Tokenizer {

    @Nonnull
    @Override
    public String[] getTokens(@Nonnull final String s) {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
        tokenizer.eolIsSignificant(false);
        tokenizer.slashSlashComments(false);
        tokenizer.slashStarComments(false);

        ArrayList<String> result = new ArrayList<String>();
        try {
            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                result.add(tokenizer.sval);
            }
        } catch (IOException ex) {
        }
        return result.toArray(new String[result.size()]);
    }
}
