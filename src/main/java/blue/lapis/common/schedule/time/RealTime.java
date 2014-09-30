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
import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Represents a time since the Epoch
 */
public final class RealTime implements Time {
    public static final RealTime EPOCH = new RealTime(0L);

    long timestamp = 0L;

    protected RealTime(long timestamp) {
        this.timestamp = timestamp;
    }

    @Nonnull
    public static RealTime now() {
        return new RealTime(System.currentTimeMillis());
    }

    @Nullable
    public static RealTime forDate(String date) {
        DateFormat parser = DateFormat.getDateInstance();
        parser.setCalendar(Calendar.getInstance());
        try {
            return new RealTime(parser.parse(date).getTime());
        } catch (ParseException ex) {
            return null;
        }
    }

    @Nonnull
    public TickTime toTickTime() {
        return TickTime.now().plus(this.minus(RealTime.now()).asTickDuration());
    }

    public RealTime plus(RealDuration d) {
        return new RealTime(timestamp + d.asMilliseconds());
    }

    public RealTime minus(RealDuration d) {
        return new RealTime(timestamp - d.asMilliseconds());
    }

    public RealTime add(RealDuration d) {
        return plus(d);
    }

    public RealTime subtract(RealDuration d) {
        return minus(d);
    }

    public RealDuration minus(RealTime other) {
        return new RealDuration(this.timestamp - other.timestamp);
    }

    public RealDuration subtract(RealTime other) {
        return minus(other);
    }
}
