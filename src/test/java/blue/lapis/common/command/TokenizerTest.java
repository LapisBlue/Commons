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

import blue.lapis.common.CommonsTests;
import blue.lapis.common.command.impl.StandardTokenizer;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {
    private final StandardTokenizer tokenizer = new StandardTokenizer();

    @Before
    public void initTests() {
        CommonsTests.mockPlugin();
    }

    @Test
    public void identifiers() {
        // Would prefer that { } [ ] either became their own tokens or delimited token begin/end
        List<String> tokens = tokenizer.getTokens("one two three four 81{ six");
        assertEquals(tokens, ImmutableList.of("one", "two", "three", "four", "81", "{", "six"));
    }

    @Test
    public void quotes() {
        List<String> tokens = tokenizer.getTokens("one \"two three\" four 81 {} [ ]");
        assertEquals(tokens, ImmutableList.of("one", "two three", "four", "81", "{", "}", "[", "]"));
    }
}
