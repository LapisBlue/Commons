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
 *
 */
public class RealDuration implements Duration {
    public static final double CENTURIES_TO_MILLIS = 3.155692597470e12;
    public static final double MILLIS_TO_CENTURIES = 1 / CENTURIES_TO_MILLIS;
    public static final double DECADES_TO_MILLIS = 3.15569259747e11;
    public static final double MILLIS_TO_DECADES = 1 / DECADES_TO_MILLIS;
    public static final double YEARS_TO_MILLIS = 3.15569e10;

    public static final double MONTHS_TO_MILLIS = 2.62974e9;
    public static final double MILLIS_TO_MONTHS = 1.0 / MONTHS_TO_MILLIS;
    public static final double WEEKS_TO_MILLIS = 6.048e8;
    public static final double MILLIS_TO_WEEKS = 1.0 / WEEKS_TO_MILLIS;
    public static final double DAYS_TO_MILLIS = 8.64e7;
    public static final double MILLIS_TO_DAYS = 1.0 / DAYS_TO_MILLIS;
    public static final double HOURS_TO_MILLIS = 3.60e6;
    public static final double MILLIS_TO_HOURS = 1.0 / HOURS_TO_MILLIS;
    public static final double MINUTES_TO_MILLIS = 6.0e4;
    public static final double MILLIS_TO_MINUTES = 1.0 / MINUTES_TO_MILLIS;
    public static final double SECONDS_TO_MILLIS = 1.0e3;
    public static final double MILLIS_TO_SECONDS = 1.0 / SECONDS_TO_MILLIS;

    //public static final double YEARS_TO_MILLIS = 12*MONTHS_TO_MILLIS;
    public static final double MILLIS_TO_YEARS = 1.0 / YEARS_TO_MILLIS;


    private long milliseconds = 0L;

    protected RealDuration(final long millis) {
        this.milliseconds = millis;
    }

    @Nonnull
    public static RealDuration milliseconds(final long num) {
        return new RealDuration(num);
    }

    @Nonnull
    public static RealDuration seconds(final double num) {
        return new RealDuration((long) (num * SECONDS_TO_MILLIS));
    }

    @Nonnull
    public static RealDuration minutes(final double num) {
        return new RealDuration((long) (num * MINUTES_TO_MILLIS));
    }

    @Nonnull
    public static RealDuration hours(final double num) {
        return new RealDuration((long) (num * HOURS_TO_MILLIS));
    }

    @Nonnull
    public static RealDuration days(final double num) {
        return new RealDuration((long) (num * DAYS_TO_MILLIS));
    }

    @Nonnull
    public static RealDuration weeks(final double num) {
        return new RealDuration((long) (num * WEEKS_TO_MILLIS));
    }

    @Nonnull
    public static RealDuration months(final double num) {
        return new RealDuration((long) (num * MONTHS_TO_MILLIS));
    }

    @Nonnull
    public static RealDuration years(final double num) {
        return new RealDuration((long) (num * YEARS_TO_MILLIS));
    }

    @Nonnull
    public static RealDuration decades(final double num) {
        return new RealDuration((long) (num * DECADES_TO_MILLIS));
    }

    @Nonnull
    public static RealDuration centuries(final double num) {
        return new RealDuration((long) (num * CENTURIES_TO_MILLIS));
    }

    public long asMilliseconds() {
        return milliseconds;
    }

    public double asSeconds() {
        return milliseconds * MILLIS_TO_SECONDS;
    }

    public double asMinutes() {
        return milliseconds * MILLIS_TO_MINUTES;
    }

    public double asHours() {
        return milliseconds * MILLIS_TO_HOURS;
    }

    public double asDays() {
        return milliseconds * MILLIS_TO_DAYS;
    }

    public double asWeeks() {
        return milliseconds * MILLIS_TO_WEEKS;
    }

    public double asMonths() {
        return milliseconds * MILLIS_TO_MONTHS;
    }

    public double asYears() {
        return milliseconds * MILLIS_TO_YEARS;
    }

    public double asDecades() {
        return milliseconds * MILLIS_TO_DECADES;
    }

    public double asCenturies() {
        return milliseconds * MILLIS_TO_CENTURIES;
    }

    @Override
    @Nonnull
    public TickDuration asTickDuration() {
        return new TickDuration((long) (milliseconds * Time.MILLIS_TO_TICKS));
    }

    @Override
    @Nonnull
    public RealDuration asRealDuration() {
        return this;
    }

    @Nonnull
    public RealDuration add(@Nonnull RealDuration other) {
        return new RealDuration(this.milliseconds + other.milliseconds);
    }

    @Nonnull
    public RealDuration subtract(@Nonnull final RealDuration other) {
        return new RealDuration(this.milliseconds - other.milliseconds);
    }

    @Nonnull
    public RealDuration plus(@Nonnull final RealDuration other) {
        return add(other);
    }

    @Nonnull
    public RealDuration minus(@Nonnull final RealDuration other) {
        return subtract(other);
    }

    @Override
    public String toString() {
        if (milliseconds == 0) return "zero";

        return new ToStringBuilder(milliseconds)
                .process(YEARS_TO_MILLIS, MILLIS_TO_YEARS, "years")
                .process(MONTHS_TO_MILLIS, MILLIS_TO_MONTHS, "months")
                .process(WEEKS_TO_MILLIS, MILLIS_TO_WEEKS, "weeks")
                .process(DAYS_TO_MILLIS, MILLIS_TO_DAYS, "days")
                .process(HOURS_TO_MILLIS, MILLIS_TO_HOURS, "hr")
                .process(MINUTES_TO_MILLIS, MILLIS_TO_MINUTES, "min")
                .process(SECONDS_TO_MILLIS, MILLIS_TO_SECONDS, "sec")
                .process(1.0, 1.0, "msec")
                .build();
    }

    private static class ToStringBuilder {
        private StringBuilder builder = new StringBuilder();
        private long temp;
        private boolean first = true;

        public ToStringBuilder(long initialTime) {
            this.temp = initialTime;

            if (temp < 0) {
                builder.append('-');
                temp = -temp;
            }
        }

        public ToStringBuilder process(double forward, double inverse, String suffix) {
            if (temp >= forward) {
                if (!first) builder.append(' ');
                long d = (long) (temp * inverse);
                temp -= (long) (d * forward);
                builder.append(d);
                builder.append(' ');
                builder.append(suffix);
                first = false;
            }
            return this;
        }

        public String build() {
            return builder.toString();
        }
    }
}
