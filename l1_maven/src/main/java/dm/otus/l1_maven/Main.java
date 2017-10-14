package dm.otus.l1_maven;

import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Collection<Integer> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 999_999 + 1; i++) {
            list.add(rand.nextInt(100000));
        }
        ImmutableSet<Integer> set = ImmutableSet.copyOf(list);
        System.out.println(String.format("Quantity of unique elements = %d", set.size()));
    }
}
