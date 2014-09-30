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

/**
 * Represents a number of ticks.
 */
public class TickDuration implements Duration {
    private long ticks = 0L;

    protected TickDuration(long ticks) {
        this.ticks = ticks;
    }

    public static TickDuration of(long ticks) {
        return new TickDuration(ticks);
    }

    public long getTicks() {
        return ticks;
    }

    @Override
    public RealDuration asRealDuration() {
        return new RealDuration((long)(ticks*Time.TICKS_TO_MILLIS));
    }

    @Override
    public TickDuration asTickDuration() {
        return this;
    }

    @Override
    public String toString() {
        return ticks+" Ticks";
    }
}
