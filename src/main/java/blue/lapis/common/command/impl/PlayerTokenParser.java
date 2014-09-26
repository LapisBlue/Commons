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

import blue.lapis.common.LapisCommonsPlugin;
import blue.lapis.common.command.token.InvalidTokenException;
import blue.lapis.common.command.token.TokenParser;
import com.google.common.collect.Lists;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.entity.Player;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

/**
 * Converts a Player name into an online {@link Player}.
 */
public class PlayerTokenParser implements TokenParser<Player> {

    @Override
    @Nonnull
    public Player parse(@Nonnull final CommandSource source, @Nonnull final String token) {
        Collection<Player> players = LapisCommonsPlugin.getGame().getOnlinePlayers();

        // TODO: strip off leading "p:" ?

        // attempt resolving full names
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(token))
                return p;
        }

        // attempt partial names
        for (Player p : players) {
            if (startsWithIgnoreCase(p.getName(), token))
                return p;
        }

        throw new InvalidTokenException(token, Player.class);
    }

    @Override
    @Nonnull
    public List<String> suggest(@Nonnull final CommandSource source, @Nonnull final String partial) {
        List<String> results = Lists.newArrayList();
        Collection<Player> players = LapisCommonsPlugin.getGame().getOnlinePlayers();

        for (Player p : players) {
            if (startsWithIgnoreCase(p.getName(), partial))
                results.add(p.getName());
        }

        return results;
    }

    private static boolean startsWithIgnoreCase(String s, String start) {
        return s.regionMatches(true, 0, start, 0, start.length());
    }
}
