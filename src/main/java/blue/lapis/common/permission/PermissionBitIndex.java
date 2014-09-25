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

import com.google.common.collect.HashBiMap;

import javax.annotation.Nonnull;

/**
 * <p>Holds the global enumeration of permission nodes, and their corresponding bits in a flat bitfield.</p>
 *
 * <p>Because permissions come from the server configuration, and only a couple hundred nodes exist on even the
 * most complex server configurations, the HashMap retrieval of a bit index is negligible. Once converted into
 * bitfields, unions and intersections of permission sets are extremely efficient to compute. This class is
 * intentionally package-private and final; actual interactions with the permission system refer to String
 * nodes. </p>
 */
final class PermissionBitIndex {
    private static PermissionBitIndex INSTANCE;

    private HashBiMap<String, Integer> data = HashBiMap.create();
    private int curIndex = 0;

    public static PermissionBitIndex getInstance() {
        if (INSTANCE == null) INSTANCE = new PermissionBitIndex();
        return INSTANCE;
    }

    /**
     * No, no, no. I make.
     *
     * @see PermissionBitIndex#getInstance()
     */
    private PermissionBitIndex() {
    }

    /**
     * Ensures that permission node {@code perm} exists in this index. If it's already in the index, the index
     * remains unchanged and its existing bit number is returned.
     *
     * @param perm The permission node being described
     * @return The bit number which represents this permission node String
     */
    public int declarePermission(@Nonnull String perm) {
        if (data.containsKey(perm)) return data.get(perm);
        data.put(perm, curIndex);
        curIndex++;
        return curIndex - 1;
    }

    /**
     * Finds the bit index for a permission node
     *
     * @param perm The permission node being queried
     * @return The bit index of the supplied permission node, or -1 if no such entry exists.
     */
    public int getPermission(@Nonnull String perm) {
        if (!data.containsKey(perm)) return -1;
        return data.get(perm);
    }
}
