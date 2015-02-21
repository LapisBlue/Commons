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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import blue.lapis.common.CommonsTests;
import blue.lapis.common.command.token.InvalidTokenException;
import blue.lapis.common.command.token.TokenParser;
import blue.lapis.common.command.token.TokenParserRegistry;

import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import javax.annotation.Nonnull;

public class TokenParserTest {
    private final CommandSource source = mock(CommandSource.class);

    @Before
    public void initTests() {
        CommonsTests.mockPlugin();
    }

    @Test
    public void resolvePlayers() {
        CommonsTests.mockPlayers("Foo", "Bar", "Baz", "Lorem", "Ipsum", "Marco", "Polo", "Foot");

        TokenParser<Player> parser = getParser(Player.class);

        assertEquals("Lorem", parser.parse(source, "Lo").getName());
        assertEquals("Foo", parser.parse(source, "Foo").getName());
    }

    @Test
    public void invalidPlayers() {
        CommonsTests.mockPlayers("Foo", "Bar", "Baz", "Lorem", "Ipsum", "Marco", "Polo", "Foot");

        TokenParser<Player> parser = getParser(Player.class);

        assertParseFail(parser, "Player123");
        assertParseFail(parser, "412");
    }

    @Test
    public void resolveIntegers() {
        TokenParser<Integer> parser = getParser(Integer.class);

        assertEquals(1, (long) parser.parse(source, "1"));
        assertEquals(678, (long) parser.parse(source, "678"));
        assertEquals(-2, (long) parser.parse(source, "-2"));
    }

    @Test
    public void invalidIntegers() {
        TokenParser<Integer> parser = getParser(Integer.class);

        assertParseFail(parser, "hi");
        assertParseFail(parser, "----");
        assertParseFail(parser, "%$0");
        assertParseFail(parser, "1.7"); // would like to see this rounded down to 1 instead.
        assertParseFail(parser, "1E3"); // would like to see this accepted as 1000 instead.
    }

    @Test
    public void resolveBooleans() {
        TokenParser<Boolean> parser = getParser(Boolean.class);

        assertTrue(parser.parse(source, "true"));
        assertTrue(parser.parse(source, "yEs"));
        assertTrue(parser.parse(source, "On"));
        assertTrue(parser.parse(source, "1"));

        assertFalse(parser.parse(source, "FalSE"));
        assertFalse(parser.parse(source, "no"));
        assertFalse(parser.parse(source, "oFF"));
        assertFalse(parser.parse(source, "0"));
    }

    @Test
    public void invalidBooleans() {
        TokenParser<Boolean> parser = getParser(Boolean.class);

        assertParseFail(parser, "96588");
        assertParseFail(parser, "hiho");
        assertParseFail(parser, "000001");
        assertParseFail(parser, "1.0");
    }

    @Nonnull
    private <T> TokenParser<T> getParser(Class<T> type) {
        TokenParser<T> parser = TokenParserRegistry.get(type);
        assertNotNull(parser);
        return parser;
    }

    private void assertParseFail(TokenParser t, String s) throws AssertionFailedError {
        try {
            t.parse(source, s);
            Assert.fail("No exception was thrown.");
        } catch (InvalidTokenException ignored) {
        }
    }
}
