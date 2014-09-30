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
 * Represents our crazy notion of time in the Minecraft world. Contains facilities to rapidly convert between
 * tick time and real time
 */
public interface Time {
    public static final double TICKS_TO_MILLIS = 50.0d;
    public static final double MILLIS_TO_TICKS = 0.02d;

    public class DurationBuilder {
        private long time = 0L;

        public DurationBuilder years(long l) {
            time += (long) (l * RealDuration.YEARS_TO_MILLIS);
            return this;
        }

        public DurationBuilder months(long l) {
            time += (long) (l * RealDuration.MONTHS_TO_MILLIS);
            return this;
        }

        public DurationBuilder weeks(long l) {
            time += (long) (l * RealDuration.WEEKS_TO_MILLIS);
            return this;
        }

        public DurationBuilder days(long l) {
            time += (long) (l * RealDuration.DAYS_TO_MILLIS);
            return this;
        }

        public DurationBuilder hours(long l) {
            time += (long) (l * RealDuration.HOURS_TO_MILLIS);
            return this;
        }

        public DurationBuilder minutes(long l) {
            time += (long) (l * RealDuration.MINUTES_TO_MILLIS);
            return this;
        }

        public DurationBuilder seconds(long l) {
            time += (long) (l * RealDuration.SECONDS_TO_MILLIS);
            return this;
        }

        public DurationBuilder milliseconds(long l) {
            time += l;
            return this;
        }

        public RealDuration build() {
            return new RealDuration(time);
        }
    }
}
