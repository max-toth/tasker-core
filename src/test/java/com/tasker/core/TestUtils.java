package com.tasker.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mtolstyh
 * @since 14.03.2016.
 */
public class TestUtils {

    class Item {
        Long f;
        Long s;
        String name;

        public Item(long i, long j, String s) {
            this.f = i;
            this.s = j;
            this.name = s;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "f=" + f +
                    ", s=" + s +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testList() {
        Map<Long, List<Item>> cache = new HashMap<>();

        for (long i = 0; i < 10; i++) {
            List<Item> l = new ArrayList<>();
            for (long j = 0; j < 5; j++) {
                l.add(new Item(i, j, "fieldA_" + i + "_" + j));
                l.add(new Item(i, j, "fieldB_" + i + "_" + j));
                l.add(new Item(i, j, "fieldC_" + i + "_" + j));
                l.add(new Item(i, j, "fieldD_" + i + "_" + j));
            }
            cache.put(i, l);
        }

        List<Item> items = cache.get(5l);
        for (Item item: items) {
            if (item.s.equals(4l)) {
                System.out.println(item.toString());
            }
        }
    }

    @Test
    public void testTrimExcessSpaces() {
        String test = "    asd   d    d   dsa   s   ";
        String testValid = "asd d d dsa s";

        System.out.println(test);
        System.out.println(testValid);

        Assert.assertEquals(testValid, test.trim().replaceAll(" +", " "));
    }

}
