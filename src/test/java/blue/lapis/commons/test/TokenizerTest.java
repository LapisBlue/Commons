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
package blue.lapis.commons.test;

import blue.lapis.common.LapisCommonsPlugin;
import blue.lapis.common.command.impl.StandardTokenizer;
import blue.lapis.common.command.token.InvalidTokenException;
import blue.lapis.common.command.token.TokenParser;
import blue.lapis.common.command.token.TokenParserRegistry;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import junit.framework.AssertionFailedError;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Player;
import org.spongepowered.api.event.state.PreInitializationEvent;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenizerTest {
    private StandardTokenizer tokenizer = new StandardTokenizer();

    @Before
    public void initTests() {
        Game gameProxy = mock(Game.class);
        PreInitializationEvent init = mock(PreInitializationEvent.class);
        when(init.getGame()).thenReturn(gameProxy);
        when(init.getPluginLog()).thenReturn(LogManager.getLogger(LapisCommonsPlugin.class));
        new LapisCommonsPlugin().initialize(init);
    }

    @Test
    public void identifiers() {
        //Would prefer that "81"->81, rather than converting it into double notation.
        //Would prefer that { } [ ] either became their own tokens or delimited token begin/end
        ImmutableList<String> tokens = tokenizer.getTokens("one two three four 81 {} six [ ]");
        Assert.assertArrayEquals(new String[]{"one", "two", "three", "four", "81.0", "six"}, tokens.toArray());
    }

    @Test
    public void quotes() {
        ImmutableList<String> tokens = tokenizer.getTokens("one \"two three\" four 81 {} [ ]");
        Assert.assertArrayEquals(new String[]{"one", "two three", "four", "81.0"}, tokens.toArray());
    }

    @Test
    public void resolvePlayer() {
        List<Player> playerList = Lists.newArrayList();
        for (String name : new String[]{"Foo", "Bar", "Baz", "Lorem", "Ipsum", "Marco", "Polo", "Foot"}) {
            Player p = mock(Player.class);
            when(p.getName()).thenReturn(name);
            playerList.add(p);
        }

        when(LapisCommonsPlugin.getGame().getOnlinePlayers()).thenReturn(playerList);

        TokenParser<Player> parser = TokenParserRegistry.get(Player.class);
        String resolution = parser.parse(null, "Lo").getName();
        Assert.assertEquals("Lorem", resolution);
        resolution = parser.parse(null, "Foo").getName();
        Assert.assertEquals("Foo", resolution);
    }

    @Test
    public void resolveIntegers() {
        TokenParser<Integer> parser = TokenParserRegistry.get(Integer.class);

        Assert.assertEquals(   1, (long)parser.parse(null,   "1"  ));
        Assert.assertEquals( 678, (long)parser.parse(null, "678"));
        Assert.assertEquals(  -2, (long)parser.parse(null,  "-2"  ));
    }

    @Test
    public void invalidIntegers() {
        TokenParser<Integer> parser = TokenParserRegistry.get(Integer.class);

        assertParseFail(parser, "hi");
        assertParseFail(parser, "----");
        assertParseFail(parser, "%$0");
        assertParseFail(parser, "1.7"); //would like to see this rounded down to 1 instead.
        assertParseFail(parser, "1E3"); //would like to see this accepted as 1000 instead.
    }


    public void assertParseFail(TokenParser t, String s) throws AssertionFailedError {
        CommandSource source = Mockito.mock(CommandSource.class);
        try {
            t.parse(source, s);
            Assert.fail("No exception was thrown.");
        } catch (InvalidTokenException e) {}
    }

}
