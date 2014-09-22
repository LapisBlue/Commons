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
package blue.lapis.common.command;

import blue.lapis.common.LapisCommonsPlugin;
import blue.lapis.common.command.impl.StandardCommandRecognizer;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Player;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.ArrayList;

import javax.annotation.Nonnull;

/**
 * A registered command
 */
public class LapisCommand<S extends CommandSource> implements CommandHolder, CommandInvocationTarget<S> {

    private CommandRecognizer recognizer;
    private ArrayList<LapisCommand> commands = new ArrayList<LapisCommand>();
    private String name;

    public LapisCommand(@Nonnull final String name) {
        this.name = name;
        recognizer = StandardCommandRecognizer.INSTANCE;
    }

    @Override
    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public void register(@Nonnull String pluginID, @Nonnull CommandInvocationTarget command) {
        //verify pluginID. It pays to be paranoid.
        PluginContainer plug =
                LapisCommonsPlugin.getInstance().getGameInstance().getPluginManager().getPlugin(pluginID);
        if (plug==null) {
            LapisCommonsPlugin.getLogger().warn(
                    "Attempted registration for a command for nonexistant plugin \""+pluginID+"\"!");
        } else {
            //TODO: Registrations
        }
    }

    @Override
    public void fireCommand(@Nonnull final CommandSource source, @Nonnull final String inputline) {
        //Construct a context
        CommandContext context;
        if (source instanceof Player) {
            //TODO: Generate CommandContext<Player> - API is not solid yet!
        }
    }

    public void invoke(@Nonnull CommandContext<S> context) {

    }

    public void setRecognizer(@Nonnull final CommandRecognizer recognizer) {
        this.recognizer = recognizer;
    }
}
