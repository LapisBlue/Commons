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
    @Override public Player parse(@Nonnull CommandSource source, @Nonnull final String token) {
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

        return null;
    }

    @Override public List<String> suggest(@Nonnull final CommandSource source, @Nonnull final String partial) {
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
