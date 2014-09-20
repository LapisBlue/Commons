package blue.lapis.common.command;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 */
public class StandardTokenizer implements Tokenizer {
    @Nonnull @Override public String[] getTokens(@Nonnull final String s) {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
        ArrayList<String> result = new ArrayList<String>();
        try {
            while (tokenizer.nextToken()!=StreamTokenizer.TT_EOF) {
                result.add(tokenizer.sval);
            }
        } catch (IOException ex) {}
        return result.toArray(new String[result.size()]);
    }
}
