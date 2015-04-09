package com.havulinna.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * SmartList is a List implementation, which internally delegates all
 * functionality from List interface to an internal List object.
 * 
 * Unlike most collections, SmartList does not accept null values.
 * 
 * The class contains the {@link #freeze()} method, which can be used to make
 * any list instance immutable on runtime.
 * 
 * This class is safe to extend.
 */
public class SmartList<T> implements List<T> {

    private static final String NULL_ERROR_MESSAGE = "Null values are not accepted by SmartList objects.";
    private List<T> list = new ArrayList<T>();

    public SmartList() {
        super();
    }

    public SmartList(T ... initialValues) {
        this(Arrays.asList(initialValues));
    }

    public SmartList(Collection<T> values) {
        this();
        this.addAll(values);
    }

    /**
     * Protects the List from future modifications by making the internal list
     * object unmodifiable.
     */
    public SmartList<T> freeze() {
        this.list = Collections.unmodifiableList(this.list);
        return this;
    }

    /**
     * Returns an unmodifiable view to this list.
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(list.subList(fromIndex, toIndex));
    }

    /**
     * Returns the first item on this list as an optional, which is empty if
     * there are no elements on this list.
     */
    public Optional<T> first() {
        return this.isEmpty() ? Optional.empty() : Optional.of(get(0));
    }


    /**
     * Shuffles the current list into random order.
     * 
     * @return the list itself
     * @see Collections#shuffle(List)
     */
    public SmartList<T> shuffle() {
        Collections.shuffle(this);
        return this;
    }

    /**
     * Returns a new SmartList containing the values that match the given predicate. The
     * returned list is mutable, even if this list would be frozen.
     *
     * @param predicate to evaluate each item against
     * @return A new SmartList containing matching items, or empty list if none match
     */
    public SmartList<T> select(Predicate<? super T> predicate) {
        return new SmartList<T>(stream().filter(predicate).collect(Collectors.toList()));
    }

    /**
     * Returns the first item on the list that matches the given predicate.
     * @param predicate
     * @return first object that matches, or null if no match
     */
    public Optional<T> find(Predicate<? super T> predicate) {
        return stream().filter(predicate).findFirst();
    }

    /**
     * @return true if this list contains any elements that match the given predicate.
     */
    public boolean containsAny(Predicate<? super T> predicate) {
        return find(predicate).isPresent();
    }

    /**
     * @throws IllegalArgumentException if the given element is null
     */
    @Override
    public boolean add(T e) {
        verifyNotNull(e);
        return list.add(e);
    }

    /**
     * @throws IllegalArgumentException if the given element is null
     */
    @Override
    public void add(int index, T element) {
        verifyNotNull(element);
        list.add(index, element);
    }

    /**
     * @throws IllegalArgumentException if the given element is null
     */
    @Override
    public T set(int index, T element) {
        verifyNotNull(element);
        return list.set(index, element);
    }


    /**
     * @throws IllegalArgumentException if the given collection contains any null values
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        verifyNoNulls(c);
        return list.addAll(c);
    }

    /**
     * @throws IllegalArgumentException if the given collection contains any null values
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        verifyNoNulls(c);
        return list.addAll(index, c);
    }

    /**
     * @throws IllegalArgumentException if the given object is null.
     */
    private void verifyNotNull(T e) {
        if (e == null) {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
    }

    /**
     * @throws IllegalArgumentException if the given collection is null or contains null values.
     */
    private void verifyNoNulls(Collection<? extends T> c) {
        if (c == null || c.contains(null)) {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
    }



    /* Methods that are delegated to the backing List object */

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }


    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public boolean equals(Object obj) {
        return list.equals(obj);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
