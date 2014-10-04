package blue.lapis.common.persistence.jpa;


import com.google.common.base.Function;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created on 05.10.2014.
 * @author Thomas
 */
public class PersistentStringMapWrapper implements Map<String,String> {

    private EntityManager em;
    private Long mapId;

    public PersistentStringMapWrapper(EntityManager em, Long mapId) {
        this.em = em;
        this.mapId = mapId;
    }

    public Long getMapId(){
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
        return operateOnMap(new MapOperation<String>() {
            @Nullable
            @Override
            public String apply(@Nullable Map<String, String> input) {
                return input.put(key,value);
            }
        });
    }

    @Override
    public String remove(final Object key) {
        return operateOnMap(new MapOperation<String>() {
            @Nullable
            @Override
            public String apply(@Nullable Map<String, String> input) {
                return input.remove(key);
            }
        });
    }

    @Override
    public void putAll(final Map<? extends String, ? extends String> m) {
        operateOnMap(new MapOperation<Object>() {
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
        operateOnMap(new MapOperation<Void>() {
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

        if (!mapId.equals(that.mapId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return mapId.hashCode();
    }

    @Override
    public String toString(){
        return String.format("Map id: %d",mapId);
    }

    private <R> R operateOnMap(MapOperation<R> operation){
        EntityTransaction t = em.getTransaction();
        t.begin();

        PersistentStringMap map = em.find(PersistentStringMap.class, mapId);
        R r = operation.apply(map.getBackingMap());

        t.commit();
        return r;
    }

    private static interface MapOperation<T> extends Function<Map<String,String>,T>{

    }
}
