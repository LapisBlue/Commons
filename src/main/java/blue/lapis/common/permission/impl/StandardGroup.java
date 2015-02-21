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
package blue.lapis.common.permission.impl;

import blue.lapis.common.permission.Group;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public class StandardGroup implements Group {

    private String id = "untitled";

    IndexedSet<Group> immediateSupers;
    IndexedSet<Group> immediateSubsets;
    IndexedSet<Group> cache;

    public StandardGroup(String name) {
        Preconditions.checkArgument(!name.trim().isEmpty(), "Name cannot be empty or whitespace.");
        id = name.trim().toLowerCase();
    }

    public Group forNode(String nodeName) {
        //in terms of iterations, this is the shortest possible search for implication/compound nodes
        if (nodeName.indexOf('&') >= 0) {

        }

        //This is an ordinary node
        return new StandardGroup(nodeName);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ImmutableSet<Group> getInheritors() {
        return immediateSupers.resolve();
    }

    @Override
    public boolean hasInheritor(final Group superset) {
        return immediateSupers.contains(superset);
    }

    @Override
    public void addInheritor(final Group superset) {
        immediateSupers.add(superset);
    }

    @Override
    public ImmutableSet<Group> getInheritance() {
        return immediateSubsets.resolve();
    }

    @Override
    public boolean inheritsFrom(final Group subset) {
        return immediateSubsets.contains(subset);
    }

    @Override
    public void inheritFrom(final Group subset) {
        immediateSubsets.add(subset);
    }

    @Override
    public boolean declaresPermission(final String node, final Group origin) {
        if (id.equals(node)) return true;
        if (node.startsWith(id + '.')) return true;
        return false;
    }

    @Override
    public boolean grantsPermission(final String node, int depth, final Group origin) {
        if (declaresPermission(node, origin)) return true;
        if (depth > Group.MAX_SEARCH_DEPTH) return false;
        for (Group group : immediateSubsets) {
            if (group.grantsPermission(node, depth, origin)) return true;
        }

        return false;
    }

}
