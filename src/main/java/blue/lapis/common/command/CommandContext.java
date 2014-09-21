package blue.lapis.common.command;

import org.spongepowered.api.command.CommandSource;

import javax.annotation.Nonnull;

/**
 * Represents the environment in which a command has been called; its CommandConfiguration, its CommandSource,
 * parsed arguments, etc.
 */
public interface CommandContext {

    /**
     * @return the Player, CommandBlock, or Console which this command was issued from
     */
    @Nonnull
    CommandSource getCommandSource();

    /**
     * @return the entire line of text entered, except for the portion recognized as the command
     */
    @Nonnull
    public String getLine();

    /**
     * @return the tokens in String format, as determined by the Tokenizer for this command
     */
    @Nonnull
    public String[] getTokens();
}
