package task1;

import java.util.Objects;

public class SuperCustomHashMap<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V>[] buckets;

    private int size;

    @SuppressWarnings("unchecked")
    public SuperCustomHashMap() {
        buckets = new Node[16];
        size = 0;
    }

    private int getBucketIndex(K key) {

        int hash = (key == null) ? 0 : key.hashCode();

        return (hash & 0x7fffffff) % buckets.length;
    }

    public V put(K key, V value) {

        int index = getBucketIndex(key);

        Node<K, V> current = buckets[index];

        while (current != null) {

            if (Objects.equals(current.key, key)) {

                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;

        size++;

        return null;
    }

    public V get(K key) {

        int index = getBucketIndex(key);

        Node<K, V> current = buckets[index];

        while (current != null) {

            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public V remove(K key) {
        int index = getBucketIndex(key);

        Node<K, V> current = buckets[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {

                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}