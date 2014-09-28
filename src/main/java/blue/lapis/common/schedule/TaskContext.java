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
package blue.lapis.common.schedule;

/**
 *
 */
public final class TaskContext {
    private boolean isActive = true;
    private long nextTick = 0L;
    private long nextTime = 0L;
    private long lastTick = 0L;
    private boolean isTickBased = true;

    protected void setCurrentTick(long tick) {
        long addedTime = tick-lastTick;
        nextTick-=addedTime;
        lastTick = tick; //global tick time for the server
    }

    public void scheduleNextTick(long ticksFromNow) {
        nextTick = ticksFromNow;
        isTickBased = true;
    }

    public void scheduleNextTime(long timestampMillis) {
        nextTime = timestampMillis;
        isTickBased = false;
    }

    public long getSleepTicks() {
        //Simple case
        if (isTickBased) return nextTick;

        //Very difficult case. Hopefully this is right.
        long now = System.currentTimeMillis();
        if (nextTime-now < 0) return 0L;
        double millis = nextTime-now;
        long ticks = (long)(millis*20.0/1000.0);
        return ticks;
    }
}
