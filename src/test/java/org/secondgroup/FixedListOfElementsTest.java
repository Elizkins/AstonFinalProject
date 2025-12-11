package org.secondgroup;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.secondgroup.customcollection.FixedListOfElements;

import java.util.Arrays;

@DisplayName("FixedListOfElements tests")
public class FixedListOfElementsTest {

    private static final String TEST_VALUE = "test value";
    private static final int TEST_CAPACITY = 10;
    private FixedListOfElements<String> list;
    private String[] strings = {"rachel", "ross", "monica", "chandler", "joey", "phoebe"};

    @BeforeEach
    void setUp(){
        list = new FixedListOfElements<>(strings);
    }

    @Test
    @DisplayName("Get by position test")
    public void getByPositionTest(){
        String string = list.getByPosition(0);

        Assertions.assertEquals(strings[0], string);
    }

    @Test
    @DisplayName("Get by position test")
    public void getByPositionFailTest(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.getByPosition(6));
    }

    @Test
    @DisplayName("Replace by position test")
    public void replaceByPositionTest(){
        int positionToReplace = 0;

        list.replaceByPosition(TEST_VALUE, positionToReplace);

        Assertions.assertEquals(list.getByPosition(positionToReplace), TEST_VALUE);
    }

    @Test
    @DisplayName("Replace by position test")
    public void replaceByPositionFailTest(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.replaceByPosition(TEST_VALUE, 6));
    }

    @Test
    @DisplayName("Get array test")
    public void getStandardArrayTest(){
        String[] result = list.getStandardArray(String.class);

        Assertions.assertArrayEquals(strings, result);
    }

    @Test
    @DisplayName("Add in tail test")
    public void addInTailTest(){
        list.addInTail(TEST_VALUE);
        Assertions.assertEquals(TEST_VALUE, list.getByPosition(list.showValuesCount() - 1));
    }

    @Test
    @DisplayName("Add in tail test")
    public void addInTailArrayTest(){
        list.addInTail(new String[]{TEST_VALUE});
        Assertions.assertEquals(TEST_VALUE, list.getByPosition(list.showValuesCount() - 1));
    }

    @Test
    @DisplayName("Add in tail test")
    public void addInTailListTest(){
        list.addInTail(new FixedListOfElements<>(TEST_VALUE));
        Assertions.assertEquals(TEST_VALUE, list.getByPosition(list.showValuesCount() - 1));
    }

    @Test
    @DisplayName("Add in tail test")
    public void addInTailFailTest(){
        FixedListOfElements<String> stringsFL = new FixedListOfElements<>(TEST_CAPACITY);
        for(int i = 0; i < TEST_CAPACITY; i++){
            stringsFL.addInTail("");
        }
        list.addInTail(TEST_VALUE);
        Assertions.assertEquals(TEST_CAPACITY, stringsFL.showValuesCount());
    }

    @Test
    @DisplayName("Add in any place test")
    public void addInAnyPlaceTest(){
        list.addInAnyPlace(TEST_VALUE, 1);
        Assertions.assertEquals(TEST_VALUE, list.getByPosition(1));
    }

    @Test
    @DisplayName("Add in any place test")
    public void addInAnyPlaceArrayTest(){
        list.addInAnyPlace(new String[]{TEST_VALUE}, 1);
        Assertions.assertEquals(TEST_VALUE, list.getByPosition(1));
    }

    @Test
    @DisplayName("Add in any place test")
    public void addInAnyPlaceListTest(){
        list.addInAnyPlace(new FixedListOfElements<>(TEST_VALUE), 1);
        Assertions.assertEquals(TEST_VALUE, list.getByPosition(1));
    }

    @Test
    @DisplayName("Add in any place test")
    public void addInAnyPlaceFailTest(){
        FixedListOfElements<String> stringsFL = new FixedListOfElements<>(TEST_CAPACITY);

        Assertions.assertThrows(IllegalArgumentException.class, () -> stringsFL.addInAnyPlace(strings, 5));

    }

    @Test
    @DisplayName("Remove at index test")
    public void removeAtIndexTest(){
        list.removeAtIndex(0);

        Assertions.assertEquals(strings[1], list.getByPosition(0));
    }

    @Test
    @DisplayName("Remove at index test")
    public void removeAtIndexFailTest(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.removeAtIndex(-1));
    }

    @Test
    @DisplayName("Count occurrences test")
    public void countOccurrencesParallelTest(){
        long expected = Arrays.stream(strings).filter(s -> s.equals(strings[0])).count();
        int count = list.countOccurrencesParallel(strings[0], 3);

        Assertions.assertEquals(expected, count);
    }
}
