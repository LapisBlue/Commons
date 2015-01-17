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

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents an optimized set of elements which acts like
 */
final class IndexedSet<T> implements Iterable<T> {
    private IndexedSetIndex<T> index;
    private BitSet data = new BitSet();

    protected IndexedSet(IndexedSetIndex<T> index) {
        this.index = index;
    }

    protected BitSet getData() {
        return data;
    }

    protected void setData(BitSet b) {
        data = b;
    }

    public void add(T t) {
        int bit = index.declare(t);
        data.set(bit);
    }

    public void remove(T t) {
        int bit = index.indexOf(t);
        if (bit >= 0) {
            data.clear(bit);
        }
    }

    public void addAll(Collection<IndexedSet<T>> sets) {
        for (IndexedSet<T> set : sets) {
            data.or(set.data);
        }
    }

    public void removeAll(Collection<IndexedSet<T>> sets) {
        for (IndexedSet<T> set : sets) {
            data.andNot(set.data);
        }
    }

    public void addAll(IndexedSet<T> set) {
        data.or(set.data);
    }

    public void removeAll(IndexedSet<T> set) {
        data.andNot(set.data);
    }

    public boolean contains(T t) {
        int bitIndex = index.indexOf(t);
        return bitIndex >= 0 && data.get(bitIndex);
    }

    @Override
    public Iterator<T> iterator() {
        return new IndexedSetIterator<T>(this);
    }

    public ImmutableSet<T> resolve() {
        ImmutableSet.Builder<T> builder = ImmutableSet.builder();
        for (int i = 0; i < data.length(); i++) {
            if (data.get(i)) builder.add(index.get(i));
        }
        return builder.build();
    }

    private static class IndexedSetIterator<T> implements Iterator<T> {
        private final IndexedSet<T> set;
        private int cur = 0;
        private T next;

        public IndexedSetIterator(IndexedSet<T> set) {
            this.set = set;
            for (int i = 0; i < set.data.length(); i++) {
                if (set.data.get(i)) {
                    cur = i;
                    next = set.index.get(cur);
                    return;
                }
            }
            cur = Integer.MAX_VALUE;
            next = null;
        }


        @Override
        public boolean hasNext() {
            return (next != null);
        }

        @Override
        public T next() {
            if (next == null) throw new NoSuchElementException();
            T result = next;
            next = null;
            for (int i = cur + 1; i < set.data.length(); i++) {
                if (i >= set.data.length()) break;
                if (set.data.get(i)) {
                    next = set.index.get(i);
                    cur = i;
                    return result;
                }
            }
            cur = Integer.MAX_VALUE;
            return result;
        }

        @Override
        public void remove() {
            if (cur < set.data.length()) set.data.clear(cur);
        }
    }
}
