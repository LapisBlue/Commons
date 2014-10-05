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
package blue.lapis.common.persistence.jpa;


import blue.lapis.common.persistence.collections.PersistentMap;
import com.google.common.base.Function;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper class that abstracts all the JPA pain like transactions
 * and entity manager.
 */
public class PersistentStringMapWrapper implements PersistentMap<String, String> {

    private EntityManager em;
    private long mapId;

    public PersistentStringMapWrapper(EntityManager em, long mapId) {
        this.em = em;
        this.mapId = mapId;
    }

    @Override
    public long getId() {
        return mapId;
    }

    @Override
    public int size() {
        return operateOnMap(new MapOperation<Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable Map<String, String> input) {
                return input.size();
            }
        });
    }

    @Override
    public boolean isEmpty() {
        return operateOnMap(new MapOperation<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable Map<String, String> input) {
                return input.isEmpty();
            }
        });
    }

    @Override
    public boolean containsKey(final Object key) {
        return operateOnMap(new MapOperation<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable Map<String, String> input) {
                return input.containsKey(key);
            }
        });
    }

    @Override
    public boolean containsValue(final Object value) {
        return operateOnMap(new MapOperation<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable Map<String, String> input) {
                return input.containsValue(value);
            }
        });
    }

    @Override
    public String get(final Object key) {
        return operateOnMap(new MapOperation<String>() {
            @Nullable
            @Override
            public String apply(@Nullable Map<String, String> input) {
                return input.get(key);
            }
        });
    }

    @Override
    public String put(final String key, final String value) {
        return operateTransactionalOnMap(new MapOperation<String>() {
            @Nullable
            @Override
            public String apply(@Nullable Map<String, String> input) {
                return input.put(key, value);
            }
        });
    }

    @Override
    public String remove(final Object key) {
        return operateTransactionalOnMap(new MapOperation<String>() {
            @Nullable
            @Override
            public String apply(@Nullable Map<String, String> input) {
                return input.remove(key);
            }
        });
    }

    @Override
    public void putAll(final Map<? extends String, ? extends String> m) {
        operateTransactionalOnMap(new MapOperation<Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable Map<String, String> input) {
                input.putAll(m);
                return null;
            }
        });
    }

    @Override
    public void clear() {
        operateTransactionalOnMap(new MapOperation<Void>() {
            @Nullable
            @Override
            public Void apply(@Nullable Map<String, String> input) {
                input.clear();
                return null;
            }
        });
    }

    @Override
    public Set<String> keySet() {
        return operateOnMap(new MapOperation<Set<String>>() {
            @Nullable
            @Override
            public Set<String> apply(@Nullable Map<String, String> input) {
                return input.keySet();
            }
        });
    }

    @Override
    public Collection<String> values() {
        return operateOnMap(new MapOperation<Collection<String>>() {
            @Nullable
            @Override
            public Collection<String> apply(@Nullable Map<String, String> input) {
                return input.values();
            }
        });
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return operateOnMap(new MapOperation<Set<Entry<String, String>>>() {
            @Nullable
            @Override
            public Set<Entry<String, String>> apply(@Nullable Map<String, String> input) {
                return input.entrySet();
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersistentStringMapWrapper that = (PersistentStringMapWrapper) o;

        if (mapId != that.mapId) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(mapId).hashCode();
    }

    /**
     * Prints the id of the backing map
     */
    @Override
    public String toString() {
        return String.format("Map id: %d", mapId);
    }

    /**
     * Helper method that manages transactions and database access
     *
     * @param operation Operation to perform.
     * @param <R> Return type.
     * @return the result from the operation.
     */
    private <R> R operateTransactionalOnMap(MapOperation<R> operation) {
        EntityTransaction t = em.getTransaction();
        t.begin();

        PersistentStringMap map = em.find(PersistentStringMap.class, mapId);
        R r = operation.apply(map.getBackingMap());

        t.commit();
        return r;
    }

    /**
     * Helper method that manages transactions and database access
     *
     * @param operation Operation to perform.
     * @param <R> Return type.
     * @return the result from the operation.
     */
    private <R> R operateOnMap(MapOperation<R> operation) {
        PersistentStringMap map = em.find(PersistentStringMap.class, mapId);
        R r = operation.apply(map.getBackingMap());
        return r;
    }

    private static interface MapOperation<T> extends Function<Map<String, String>, T> {}
}
