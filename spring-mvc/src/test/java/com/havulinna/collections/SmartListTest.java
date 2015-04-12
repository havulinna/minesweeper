package com.havulinna.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Test;

public class SmartListTest {
    private static final Integer ZERO = Integer.valueOf(0);
    private static final Integer ONE = Integer.valueOf(1);
    private static final Integer TWO = Integer.valueOf(2);

    private final SmartList<Integer> list = new SmartList<Integer>(ZERO, ONE);
    private final SmartList<Integer> frozenList = new SmartList<Integer>(ZERO, ONE).freeze();

    @After
    public void verifyFrozenListWasNotModifiedDuringTest() {
        assertEquals(Arrays.asList(ZERO, ONE), frozenList);
    }

    @Test
    public void firstReturnsEnEmptyOptionalWhenListIsEmpty() {
        assertFalse(new SmartList<Integer>().first().isPresent());
    }

    @Test
    public void firstReturnsFirstElementFromTheList() {
        assertEquals(ZERO, list.first().get());
    }

    @Test
    public void constructorsDoNotAcceptNullValues() {
        // Null collection as argument
        verifyIllegalArgumentException(() -> new SmartList<Integer>((Collection<Integer>)null));

        // Varargs constructor with null value
        verifyIllegalArgumentException(() -> new SmartList<Integer>(ZERO, null));

        // A collection containing null value
        verifyIllegalArgumentException(() -> new SmartList<Integer>(Arrays.asList(ZERO, null)));
    }

    @Test
    public void addMethodDoesNotAcceptNullValues() {
        verifyIllegalArgumentException(() -> list.add(null));
        verifyIllegalArgumentException(() -> list.add(0, null));
    }

    @Test
    public void setMethodDoesNotAcceptNullValues() {
        verifyIllegalArgumentException(() -> list.set(0, null));
    }

    @Test
    public void addAllMethodDoesNotAcceptCollectionsWithNullValues() {
        List<Integer> listWithNull = Arrays.asList(TWO, null);

        verifyIllegalArgumentException(() -> list.addAll(listWithNull));
        verifyIllegalArgumentException(() -> list.addAll(0, listWithNull));
    }

    @Test
    public void newElementsCanBeAddedToNonFrozenLists() {
        list.add(TWO);
        assertEquals(Arrays.asList(ZERO, ONE, TWO), list);
    }


    @Test
    public void elementsCanBeRemovedWhenListIsNotFrozen() {
        list.remove(0);
        assertEquals(Arrays.asList(ONE), list);
    }

    @Test
    public void elementsCannotBeAddedToFrozenList() {
        verifyUnsupportedOperationException(() -> frozenList.add(TWO));
        verifyUnsupportedOperationException(() -> frozenList.addAll(Arrays.asList(TWO)));
    }

    @Test
    public void elementsCannotBeRemovedFromFrozenList() {
        verifyUnsupportedOperationException(() -> frozenList.remove(0));
        verifyUnsupportedOperationException(() -> frozenList.remove(ZERO));

        verifyUnsupportedOperationException(() -> frozenList.removeAll(Arrays.asList(ZERO)));
    }

    @Test
    public void subListModificationsAreNotAllowedForSmartLists() {
        List<Integer> subList = list.subList(0, 1);

        verifyUnsupportedOperationException(() -> subList.add(null));
        verifyUnsupportedOperationException(() -> subList.add(TWO));

        assertEquals(Arrays.asList(ZERO, ONE), list); // Verify that original list remained untouched
    }

    @Test
    public void selectReturnsEmptyListWhenNoElementsMatch() {
        SmartList<Integer> matches = list.select(x -> x.intValue() > Integer.MAX_VALUE);
        assertTrue(matches.isEmpty());
    }

    @Test
    public void selectReturnsMatchingElementsInOriginalOrder() {
        SmartList<String> strings = new SmartList<String>("y", "ABC", "z", "DEF", "x");

        SmartList<String> singleChars = strings.select(x -> x.length() == 1);
        assertEquals(Arrays.asList("y", "z", "x"), singleChars);
    }

    @Test
    public void findReturnsFirstMatchingElementsFromTheList() {
        SmartList<String> strings = new SmartList<String>("1", "123", "XYZ");

        String found = strings.find(x -> x.length() > 2).get();
        assertEquals("123", found);
    }

    @Test
    public void containsAnyFindsMatchingObject() {
        SmartList<String> strings = new SmartList<String>("12", "1234");

        assertTrue(strings.containsAny(x -> x.length() > 2));
    }

    @Test
    public void containsAnyReturnsFalseWhenNoMatchingObjectsAreFound() {
        SmartList<String> strings = new SmartList<String>("1", "12");

        assertFalse(strings.containsAny(x -> x.length() > 10));
    }

    @Test
    public void findReturnsAnEmptyOptionalWhenNoElementsMatch() {
        Optional<Integer> largerThanMaximum = list.find(x -> x.intValue() > Integer.MAX_VALUE);
        assertFalse(largerThanMaximum.isPresent());
    }

    @Test
    public void shuffleRandomisesTheOrderOfTheCalledList() {
        List<Integer> input = IntStream.range(0, 100).boxed().collect(Collectors.toList());

        SmartList<Integer> list1 = new SmartList<Integer>(input).shuffle();
        SmartList<Integer> list2 = new SmartList<Integer>(input).shuffle();

        // The lists are identical in size but in different order than the input
        assertEquals(input.size(), list1.size());
        assertNotEquals(list1, input);
        assertEquals(input.size(), list2.size());
        assertNotEquals(list2, input);

        // The shuffled lists are not identical
        assertNotEquals(list1, list2);
    }

    @Test
    public void shufflingFrozenListIsNotSupported() {
        verifyUnsupportedOperationException(() -> frozenList.shuffle());
    }

    /**
     * Verifies that the given callback throws UnsupportedOperationException. If not,
     * throws AssertionError.
     */
    private static void verifyUnsupportedOperationException(Callback expectedToFail) {
        executeAndExpect(expectedToFail, UnsupportedOperationException.class);
    }

    /**
     * Verifies that the given callback throws IllegalArgumentException. If not,
     * throws AssertionError.
     */
    private static void verifyIllegalArgumentException(Callback expectedToFail) {
        executeAndExpect(expectedToFail, IllegalArgumentException.class);
    }

    private static void executeAndExpect(Callback expectedToFail, Class<? extends RuntimeException> expectedExceptionType) {
        try {
            expectedToFail.call();
            fail("Exception was expected, but not thrown.");
        } catch (RuntimeException e) {
            assertEquals(expectedExceptionType, e.getClass());
        }
    }

    /**
     * Functional interface for making a call to a parameterless void method.
     */
    private interface Callback {
        void call();
    }
}
