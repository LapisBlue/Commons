package blue.lapis.common.command;

import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

/**
 *
 */
public interface TokenParser<T> {
    /**
     * Parse the provided token and return a typesafe object for it.
     * @param source    the Player, console, or command block which initiated this object resolution
     * @param token     the token to be resolved into an object of type {@code T}
     * @return          an object of type T which corresponds to the String entered
     * @throws          InvalidTokenException if the String does not appear to match any object
     */
    @Nonnull T parse(@Nonnull CommandSource source, @Nonnull String token);

    /**
     * Get tab-complete suggestions for this token
     * @param token
     * @return
     */
    @Nonnull List<String> suggest(@Nonnull CommandSource source, String token);
}
