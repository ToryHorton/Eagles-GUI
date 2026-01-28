import java.util.*;

/**
 * Generic Dictionary template backed by a LinkedHashMap.
 * Simple convenience wrapper around a Map for educational/demo use.
 */
public class Dictionary<K, V> implements Iterable<Map.Entry<K, V>> {
    private final Map<K, V> map;

    public Dictionary() {
        this.map = new LinkedHashMap<>();
    }

    public Dictionary(Map<? extends K, ? extends V> initial) {
        this.map = new LinkedHashMap<>(initial);
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public V remove(K key) {
        return map.remove(key);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
    }

    public Set<K> keys() {
        return Collections.unmodifiableSet(map.keySet());
    }

    public Collection<V> values() {
        return Collections.unmodifiableCollection(map.values());
    }

    public Set<Map.Entry<K, V>> entries() {
        return Collections.unmodifiableSet(map.entrySet());
    }

    public void forEachEntry(java.util.function.BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return entries().iterator();
    }

    @Override
    public String toString() {
        return "Dictionary" + map.toString();
    }
}
