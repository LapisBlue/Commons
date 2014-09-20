/*
 * The MIT License (MIT)
 *
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
