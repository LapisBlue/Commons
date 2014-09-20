package blue.lapis.common.command;

import javax.annotation.Nonnull;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

/**
 *
 */
public interface Tokenizer {
    @Nonnull String[] getTokens(@Nonnull final String s);
}
