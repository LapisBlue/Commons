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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 */
public class TaskTimingZoneTest {

    TaskTimingZone zone;
    LapisTask c = mockTask("C");

    @Before
    public void initTests() {
        zone = new TaskTimingZone();
        zone.setTasksPerBatch(3);
        zone.insert(mockTask("A"));
        zone.insert(mockTask("B"));
        zone.insert(c);
        zone.insert(mockTask("D"));
    }

    @Test
    public void batchLimit() {
        StringBuilder test = new StringBuilder();

        zone.resetPeriod();

        for (int i = 0; i < 12; i++) {
            LapisTask task = zone.next();
            if (task != null) test.append(task);
        }

        Assert.assertEquals("ABC", test.toString());
    }

    @Test
    public void lapLimit() {
        StringBuilder test = new StringBuilder();
        zone.setTasksPerBatch(1000);
        zone.resetPeriod();
        for (int i = 0; i < 100; i++) {
            LapisTask task = zone.next();
            if (task != null) test.append(task);
        }

        Assert.assertEquals("ABCD", test.toString());
    }

    @Test
    public void lapLimitWithPhase() {
        StringBuilder test = new StringBuilder();
        zone.setTasksPerBatch(1000);
        zone.resetPeriod();
        LapisTask a = zone.next();
        LapisTask b = zone.next();

        zone.resetPeriod();
        for (int i = 0; i < 100; i++) {
            LapisTask task = zone.next();
            if (task != null) test.append(task);
        }

        Assert.assertEquals("CDAB", test.toString());
    }

    @Test
    public void roundRobin() {
        StringBuilder test = new StringBuilder();
        zone.setTasksPerBatch(3);

        for (int i = 0; i < 8; i++) {
            test.append('|');
            zone.resetPeriod();
            test.append(zone.next())
                    .append(zone.next())
                    .append(zone.next());
        }

        Assert.assertEquals("|ABC|DAB|CDA|BCD|ABC|DAB|CDA|BCD", test.toString());
    }

    @Test
    public void removals() {
        StringBuilder test = new StringBuilder();

        zone.remove(c);
        zone.setTasksPerBatch(3);
        zone.resetPeriod();

        for (int i = 0; i < 8; i++) {
            test.append('|');
            zone.resetPeriod();
            test.append(zone.next())
                    .append(zone.next())
                    .append(zone.next());
        }

        Assert.assertEquals("|ABD|ABD|ABD|ABD|ABD|ABD|ABD|ABD", test.toString());
    }


    public LapisTask mockTask(String output) {
        LapisTask task = Mockito.mock(LapisTask.class);
        Mockito.when(task.toString()).thenReturn(output);
        return task;
    }
}
