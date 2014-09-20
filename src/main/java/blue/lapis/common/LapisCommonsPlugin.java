package blue.lapis.common;

import org.spongepowered.api.Game;
import org.spongepowered.api.plugin.Plugin;

/**
 * Plugin class. This is not super-important in itself, but needs to be here so we can guarantee that it's available
 * for declared dependencies and gets onto the ClassLoader to be used.
 */
@Plugin(id="commons", name="lapis Commons", version="design-concept")
public class LapisCommonsPlugin {
    private static LapisCommonsPlugin instance;

    private Game gameInstance;

    public static LapisCommonsPlugin getInstance() {
        return instance;
    }

    public LapisCommonsPlugin() {
        instance = this;
    }

    public Game getGameInstance() {
        return gameInstance;
    }

}
