package helpers;

import kraken.plugin.api.Debug;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.MessageFormat;

public class Log {
    public static void Information(String text) {
        Debug.log(
            MessageFormat.format(
                "[{0}] [{1}] {2}",
                new SimpleDateFormat("HH:mm:ss").format(new Timestamp(new Date().getTime())),
                "Mash",
                text
            )
        );
    }
}
