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
 * Represents a length of time, but is agnostic about whether it refers to milliseconds or ticks
 */
public interface Duration {
    /*
     * Constants used to avoid creating objects for common durations
     */
    public static final RealDuration INSTANT = RealDuration.milliseconds(0);
    public static final RealDuration ONE_MILLISECOND = RealDuration.milliseconds(1);
    public static final RealDuration ONE_SECOND = RealDuration.seconds(1);
    public static final RealDuration ONE_MINUTE = RealDuration.minutes(1);
    public static final RealDuration ONE_HOUR = RealDuration.hours(1);
    public static final RealDuration ONE_DAY = RealDuration.days(1);
    public static final RealDuration ONE_WEEK = RealDuration.weeks(1);
    public static final RealDuration ONE_MONTH = RealDuration.months(1);
    public static final RealDuration ONE_YEAR = RealDuration.years(1);

    /*
     * Actual interface methods
     */

    RealDuration asRealDuration();
    TickDuration asTickDuration();

    /*
     * Inner classes to allow grammar such as new Duration.Minutes(12)
     */

    public static class Milliseconds extends RealDuration {
        public Milliseconds(long millis) {
            super(millis);
        }
    }

    public static class Seconds extends RealDuration {
        public Seconds(long num) {
            super((long)(num*SECONDS_TO_MILLIS));
        }
    }

    public static class Minutes extends RealDuration {
        public Minutes(long num) {
            super((long)(num*MINUTES_TO_MILLIS));
        }
    }

    public static class Hours extends RealDuration {
        public Hours(long num) {
            super((long)(num*HOURS_TO_MILLIS));
        }
    }

    public static class Days extends RealDuration {
        public Days(long num) {
            super((long)(num*DAYS_TO_MILLIS));
        }
    }

    public static class Weeks extends RealDuration {
        public Weeks(long num) {
            super((long)(num*WEEKS_TO_MILLIS));
        }
    }

    public static class Months extends RealDuration {
        public Months(long num) {
            super((long)(num*MONTHS_TO_MILLIS));
        }
    }

    public static class Years extends RealDuration {
        public Years(long num) {
            super((long)(num*YEARS_TO_MILLIS));
        }
    }

    public static class Decades extends RealDuration {
        public Decades(long num) {
            super((long)(num*DECADES_TO_MILLIS));
        }
    }

    public static class Centuries extends RealDuration {
        public Centuries(long num) {
            super((long)(num*CENTURIES_TO_MILLIS));
        }
    }

    public static class Ticks extends TickDuration {
        public Ticks(long num) {
            super(num);
        }
    }
}
