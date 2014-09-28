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

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Internal class representing a forecast of future task executions.
 * This class is NOT threadsafe, as it operates within a sequential scheduling context.
 *
 * There are 3 TaskTimingZones in a Scheduler. One is for "dormant" tasks which are not expected to run within
 * the hour, one is for "waiting" tasks which are expected to run within the hour but not for some ticks, and
 * an "immediate" zone for tasks which are scheduled for this tick.
 *
 * Additionally, a TaskTimingZone is aware of the zone on each side, and can push "up" (longer duration) or
 * "down" (shorter duration)
 */
class TaskTimingZone {
    private ArrayList<LapisTask> incoming = Lists.newArrayList();
    private ArrayList<LapisTask> tasks = Lists.newArrayList();
    private ArrayList<LapisTask> outgoing = Lists.newArrayList();
    private int loc = -1; //Location of the last task returned
    private int lapLoc = -1; //Location before the first item returned
    private int tasksPerBatch = 10;
    private int tasksThisBatch = 0;
    private boolean lapStart = true;
    TaskTimingZone up = null;
    TaskTimingZone down = null;

    public void resetPeriod() {
        tasks.addAll(incoming);
        incoming.clear();

        for (LapisTask t : outgoing) {
            int removal = tasks.indexOf(t);
            if (loc >= removal) loc--;
            if (loc < -1) loc = -1;
            if (lapLoc >= removal) lapLoc--;
            if (lapLoc < 0) lapLoc = 0;
            if (removal >= 0) tasks.remove(removal);
        }
        outgoing.clear();


        lapLoc = loc;
        if (lapLoc < 0) lapLoc = tasks.size() - 1;

        tasksThisBatch = 0;
        lapStart = true;
        //return tasks.get(loc);
    }

    @Nullable
    public LapisTask next() {
        if (tasksThisBatch >= tasksPerBatch) return null;
        if (loc == lapLoc && !lapStart) return null;
        tasksThisBatch++;
        loc++;
        if (loc >= tasks.size()) loc = 0;
        lapStart = false;
        return tasks.get(loc);
    }

    public boolean hasNext() {
        return (loc != lapLoc);
    }

    public void insert(LapisTask task) {
        incoming.add(task);
    }

    public void remove(LapisTask task) {
        outgoing.add(task);
    }

    public int getTasksPerBatch() {
        return tasksPerBatch;
    }

    public void setTasksPerBatch(int tasksPerBatch) {
        Preconditions.checkArgument(tasksPerBatch > 0,
                "TasksPerBatch must be more than zero - otherwise no tasks will execute!");

        this.tasksPerBatch = tasksPerBatch;
    }
}
