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
import blue.lapis.common.command.impl.PlayerTokenParser;
import blue.lapis.common.command.impl.StandardTokenizer;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.Player;
import org.spongepowered.api.event.state.PreInitializationEvent;

import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class TokenizerVerification {
    LapisCommonsPlugin plugin = new LapisCommonsPlugin();
    public StandardTokenizer tokenizer = new StandardTokenizer();
    public PlayerTokenParser playerParser = new PlayerTokenParser();
    public Game gameProxy;

    public String identifiers = "one two three four 81 {} six [ ]";
    public String quotes = "one \"two three\" four 81 {} [ ]";

    @Before
    public void initTests() {
        List<Player> playerList = new ArrayList<Player>();
        for (String name : new String[]{"Foo", "Bar", "Baz", "Lorem", "Ipsum", "Marco", "Polo", "Foot"}) {
            Player p = mock(Player.class);
            when(p.getName()).thenReturn(name);
            playerList.add(p);
        }
        gameProxy = mock(Game.class);
        when(gameProxy.getOnlinePlayers()).thenReturn(playerList);

        PreInitializationEvent init = mock(PreInitializationEvent.class);
        when(init.getGame()).thenReturn(gameProxy);
        when(init.getPluginLog()).thenReturn(LogManager.getLogger(LapisCommonsPlugin.class));
        plugin.initialize(init);
    }

    @Test
    public void identifiers() {
        String[] tokens = tokenizer.getTokens(identifiers);
        Assert.assertArrayEquals(new String[]{"one", "two", "three", "four", "81.0", "six"}, tokens);
    }

    @Test
    public void quotes() {
        String[] tokens = tokenizer.getTokens(quotes);
        Assert.assertArrayEquals(new String[]{"one", "two three", "four", "81.0"}, tokens);
    }

    @Test
    public void resolvePlayer() {
        String resolution = playerParser.parse(null, "Lo").getName();
        Assert.assertEquals("Lorem", resolution);
        resolution = playerParser.parse(null, "Foo").getName();
        Assert.assertEquals("Foo", resolution);
    }

}
