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

import blue.lapis.common.schedule.time.Duration;
import blue.lapis.common.schedule.time.RealDuration;
import blue.lapis.common.schedule.time.RealTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TimeTest {

    @Before
    public void initTests() {

    }

    @Test
    public void reversible() {
        long fiveSeconds = (long) RealDuration.minutes(RealDuration.seconds(5).asMinutes()).asSeconds();
        Assert.assertEquals(5L, fiveSeconds);

        fiveSeconds = (long) RealDuration.centuries(RealDuration.seconds(5).asCenturies()).asSeconds();
        Assert.assertEquals(5L, fiveSeconds);
    }

    @Test
    public void knownAmounts() {
        long twoMinutes = (long) RealDuration.seconds(120).asMinutes();
        Assert.assertEquals(2L, twoMinutes);

        //TODO: large, out-of-phase known amounts
    }

    @Test
    public void checkUnits() {
        //All of these quantities are super well known. Every one of these should be millisecond-accurate.

        Assert.assertEquals(Duration.ONE_WEEK.asMilliseconds(), RealDuration.days(7).asMilliseconds());

        Assert.assertEquals(Duration.ONE_DAY.asMilliseconds(), RealDuration.hours(24).asMilliseconds());

        Assert.assertEquals(Duration.ONE_HOUR.asMilliseconds(), RealDuration.minutes(60).asMilliseconds());

        Assert.assertEquals(Duration.ONE_MINUTE.asMilliseconds(), RealDuration.seconds(60).asMilliseconds());

        //This one *seems* kind of superfluous, but it's good to run it through the whole wringer anyway.
        Assert.assertEquals(Duration.ONE_SECOND.asMilliseconds(),
                RealDuration.milliseconds(1000).asMilliseconds());
    }

    @Test
    public void stringOutput() {
        //verify that RealDuration.toString() outputs correct, readable information.

        String display = new RealTime.DurationBuilder()
                .years(1)
                .seconds(10)
                .build()
                .toString();

        Assert.assertEquals("1 years 10 sec", display);

        display = new RealTime.DurationBuilder()
                .seconds(81)
                .milliseconds(13861)
                .build()
                .toString();

        Assert.assertEquals("1 min 34 sec 861 msec", display);

        display = RealDuration.seconds(0).toString();

        Assert.assertEquals("zero", display);

        display = RealDuration.hours(-10).toString();

        Assert.assertEquals("-10 hr", display);
    }

}