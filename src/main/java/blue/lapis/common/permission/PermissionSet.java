/*
 * LapisCommons
 * Copyright (c) 2014, LapisDev <https://github.com/LapisDev>
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
package blue.lapis.common.permission;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import java.util.BitSet;
import java.util.Set;

/**
 * Abstract super for collections of Permissions
 */
public abstract class PermissionSet {
    private BitSet derivedPermissions = new BitSet();
    private Set<PermissionSet> parents = Sets.newHashSet();
    private Set<PermissionSet> children = Sets.newHashSet();

    @Nonnull
    protected BitSet getDerivedPermissions() {
        return derivedPermissions;
    }

    protected void setDerivedPermissions(@Nonnull BitSet b) {
        derivedPermissions = b;
    }

    /**
     * Internal method for finalizing the two-way link between parent and child
     *
     * @param set The set which is now an immediate parent of this set
     */
    protected void addParent(@Nonnull PermissionSet set) {
        if (parents.contains(set)) return;
        parents.add(set);
        _calculate();
    }

    /**
     * Add a child to this set. Triggers a (potentially recursive) recalculate for the new node
     *
     * @param set The new child
     */
    public void addChild(@Nonnull PermissionSet set) {
        children.add(set);
        set.addParent(this);
    }

    protected void _calculate() {
        BitSet oldPerms = derivedPermissions;
        calculate();
        if (oldPerms.equals(derivedPermissions)) {
            for (PermissionSet child : children) {
                child.calculate();
            }
        }
    }

    protected abstract void calculate();

    @Nonnull
    public ImmutableSet<PermissionSet> getParents() {
        return ImmutableSet.copyOf(parents);
    }

    @Nonnull
    public ImmutableSet<PermissionSet> getChildren() {
        return ImmutableSet.copyOf(children);
    }

    public boolean grantsPermission(String perm) {
        int bitIndex = PermissionBitIndex.getInstance().getPermission(perm);
        if (bitIndex < 0) return false;
        return derivedPermissions.get(bitIndex);
    }
}
