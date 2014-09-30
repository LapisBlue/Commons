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
package blue.lapis.common.schedule.time;

import javax.annotation.Nonnull;

/**
 * <p>Represents an absolute number of ticks since the overworld was first created.</p>
 *
 * <p>Because tick times are not precisely 20 per second, and because the server can suspend ticks if all
 * players are logged out, actual times may wind up being much longer than the estimates would suggest.</p>
 *
 * <p>Other things which affect server world time can make this TickTime happen drastically earlier than
 * planned. This MAY include setting daytime in the overworld, as historically this has changed the raw tick
 * time of the world.</p>
 */
public final class TickTime implements Time {
    private long time = 0L;

    protected TickTime(long serverTime) {}

    /**
     * @return The current TickTime in "world" time (e.g. the world players join when they login)
     */
    public static TickTime now() {
        return new TickTime(0L); //TODO: Implement
    }

    /**
     * Estimates the actual time this tick will (or did) fire on, based on the current real and tick time, and
     * an estimated 20 ticks per second.
     * @return A RealTime estimating when this tick fires
     */
    public RealTime toRealTime() {
        return RealTime.now().plus(this.minus(TickTime.now()).asRealDuration());

    }

    public long getTicks() {
        return time;
    }

    @Nonnull
    public TickTime add(TickDuration duration) {
        return new TickTime(time+duration.getTicks());
    }

    @Nonnull
    public TickTime subtract(TickDuration duration) {
        return new TickTime(time-duration.getTicks());
    }

    @Nonnull
    public TickTime plus(TickDuration duration) {
        return add(duration);
    }

    @Nonnull
    public TickTime minus(TickDuration duration) {
        return subtract(duration);
    }

    public TickDuration minus(TickTime other) {
        return new TickDuration(time-other.time);
    }
}
