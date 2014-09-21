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
package blue.lapis.commons.test;

import blue.lapis.common.LapisCommonsPlugin;
import blue.lapis.common.command.StandardTokenizer;
import blue.lapis.common.command.token.PlayerTokenParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.spongepowered.api.Game;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Platform;
import org.spongepowered.api.entity.Player;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.math.Vector3d;
import org.spongepowered.api.math.Vector3f;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.world.World;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 */
public class TokenizerVerification {
    LapisCommonsPlugin plugin = new LapisCommonsPlugin();
    public StandardTokenizer tokenizer = new StandardTokenizer();
    public PlayerTokenParser playerParser = new PlayerTokenParser();
    public Game gameProxy = new GameProxy();

    public String identifiers = "one two three four 81 {} six [ ]";
    public String quotes = "one \"two three\" four 81 {} [ ]";

    @Before
    public void initTests() {

    }

    @Test
    public void identifiers() {
        String[] tokens = tokenizer.getTokens(identifiers);
        Assert.assertArrayEquals(new String[]{"one","two","three","four","81.0","six"}, tokens);
    }

    @Test
    public void quotes() {
        String[] tokens = tokenizer.getTokens(quotes);
        Assert.assertArrayEquals(new String[]{"one","two three","four","81.0"}, tokens);
    }

    @Test
    public void resolvePlayer() {
        LapisCommonsPlugin.getInstance().setGameInstance(gameProxy);
        String resolution = playerParser.parse(null, "Lo").getName();
        Assert.assertEquals("Lorem", resolution);
        resolution = playerParser.parse(null, "Foo").getName();
        Assert.assertEquals("Foo", resolution);
    }



    private class GameProxy implements Game {
        @Override
        public Platform getPlatform() {
            throw new UnsupportedOperationException();
        }

        @Override
        public PluginManager getPluginManager() {
            throw new UnsupportedOperationException();
        }

        @Override
        public EventManager getEventManager() {
            throw new UnsupportedOperationException();
        }

        @Override
        public GameRegistry getRegistry() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<Player> getOnlinePlayers() {
            Set<Player> result = new HashSet<Player>();
            result.add(new PlayerProxy("Foo"));
            result.add(new PlayerProxy("Bar"));
            result.add(new PlayerProxy("Baz"));
            result.add(new PlayerProxy("Lorem"));
            result.add(new PlayerProxy("Ipsum"));
            result.add(new PlayerProxy("Marco"));
            result.add(new PlayerProxy("Polo"));
            result.add(new PlayerProxy("Foot"));
            return result;
        }

        @Override
        public int getMaxPlayers() {
            return 0;
        }

        @Nullable
        @Override
        public Player getPlayer(UUID uuid) {
            return null;
        }

        @Override
        public Collection<World> getWorlds() {
            return null;
        }

        @Override
        public World getWorld(UUID uuid) {
            return null;
        }

        @Override
        public World getWorld(String s) {
            return null;
        }

        @Override
        public void broadcastMessage(String s) {

        }

        @Override
        public String getAPIVersion() {
            return null;
        }

        @Override
        public String getImplementationVersion() {
            return null;
        }
    }

    private class PlayerProxy implements Player {
        private final String name;

        public PlayerProxy(@Nonnull final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDisplayName() {
            return name;
        }

        @Override
        public void damage(double v) {
        }

        @Override
        public double getHealth() {
            return 0;
        }

        @Override
        public void setHealth(double v) {
        }

        @Override
        public UUID getUniqueID() {
            return null;
        }

        @Override
        public boolean isBurning() {
            return false;
        }

        @Override
        public int getDuration() {
            return 0;
        }

        @Override
        public void setDuration(int i) {
        }

        @Override
        public Vector3f getVelocity() {
            return null;
        }

        @Override
        public void setVelocity(Vector3f vector3f) {

        }

        @Override
        public Vector3d getPosition() {
            return null;
        }

        @Override
        public void setPosition(Vector3d vector3d) {
        }

        @Override
        public Vector3f getRotation() {
            return null;
        }

        @Override
        public void setRotation(Vector3f vector3f) {
        }
    }
}
