/*
 * LapisCommons
 * Copyright (c) 2014, Lapis <https://github.com/LapisBlue>
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
import blue.lapis.common.command.impl.CommandContextImpl;
import blue.lapis.common.command.impl.StandardCommandRecognizer;
import blue.lapis.common.command.impl.StandardTokenizer;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.util.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A registered command
 */
public class LapisCommand<S extends CommandSource> implements CommandHolder, CommandInvocationTarget<S> {

    private CommandRecognizer recognizer = StandardCommandRecognizer.INSTANCE;
    private Tokenizer tokenizer = new StandardTokenizer();
    private List<LapisCommand> commands = Lists.newArrayList();
    private String name;

    public LapisCommand(@Nonnull final String name) {
        this.name = name;
    }

    @Override
    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public void register(@Nonnull String pluginID, @Nonnull CommandInvocationTarget command) {
        //verify pluginID. It pays to be paranoid.
        Optional<PluginContainer> plug = LapisCommonsPlugin.getGame().getPluginManager().getPlugin(pluginID);
        if (plug.isPresent()) {
            //TODO: Registrations
        } else {
            LapisCommonsPlugin.getLogger().warn(
                    "Attempted registration for a command for nonexistant plugin \"" + pluginID + "\"!");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fireCommand(@Nonnull final CommandSource source, @Nonnull final String inputline) {
        //Tokenize inputline
        ImmutableList<String> tokens = tokenizer.getTokens(inputline);

        //Construct a context
        CommandContext context;

        context = new CommandContextImpl(source)
                .withLine(inputline)
                .withTokens(tokens);
    }

    public void invoke(@Nonnull CommandContext<S> context) {

    }

    public void setRecognizer(@Nonnull final CommandRecognizer recognizer) {
        this.recognizer = recognizer;
    }
}
