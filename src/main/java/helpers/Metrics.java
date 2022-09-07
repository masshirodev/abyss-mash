package helpers;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

public class Metrics {
    public static float CalculateXpPerHour(String skill, float startXp, Stopwatch timer) {
        int current = Skills.GetStats(skill).getXp();
        if (startXp != current)
            return (current - startXp) / ((float) timer.elapsed(TimeUnit.MINUTES) / 60L);
        return 0L;
    }
}
