/*
 * The MIT License (MIT)
 *
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

import blue.lapis.common.LapisCommonsPlugin;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class PlayerTokenParser implements TokenParser<Player> {
    @Override public @Nonnull Player parse(@Nonnull CommandSource source, @Nonnull final String token) {
        Collection<Player> players = LapisCommonsPlugin.getInstance().getGameInstance().getOnlinePlayers();
        String matchString = token.toLowerCase();

        //TODO: strip off leading p: ?

        //attempt resolving full names
        for(Player p : players) {
            if (p.getName().toLowerCase().equals(matchString)) {
                return p;
            }
        }

        //attempt partial names
        for(Player p : players) {
            if (p.getName().toLowerCase().startsWith(matchString)) {
                return p;
            }
        }

        throw new InvalidTokenException(token, Player.class);
    }

    @Override public @Nonnull List<String> suggest(@Nonnull final CommandSource source, @Nonnull final String partial) {
        ArrayList<String> results = new ArrayList<String>();
        Collection<Player> players = LapisCommonsPlugin.getInstance().getGameInstance().getOnlinePlayers();
        String matchString = partial.toLowerCase();

        for(Player p : players) {
            if (p.getName().toLowerCase().startsWith(matchString)) {
                results.add(p.getName());
            }
        }

        return results;
    }
}
