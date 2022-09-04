package helpers;

import kraken.plugin.api.Inventory;
import kraken.plugin.api.Rng;
import kraken.plugin.api.WidgetItem;

import java.text.MessageFormat;
import java.util.*;

import static kraken.plugin.api.Time.waitFor;

public class Helper {
    public static void Wait(int delay) {
        int time = Rng.i32(delay, delay / 5);
        Log.Information(MessageFormat.format("Waiting for {0}ms.", time));
        waitFor(time);
    }

    public static int GetIndexOf(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(value))
                return i;
        }

        return 0;
    }

    public static <T> List<T> ToArrayList(T value) {
        return new ArrayList<T>(Arrays.asList(value));
    }

    public static int RandomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
