package blue.lapis.common.command;

import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

/**
 *
 */
public interface TokenParser<T> {
    T parse(@Nonnull CommandSource source, @Nonnull String token);

    /**
     * Get tab-complete suggestions for this token
     * @param token
     * @return
     */
    List<String> suggest(@Nonnull CommandSource source, String token);
}
