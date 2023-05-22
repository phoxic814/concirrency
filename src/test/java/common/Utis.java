package common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utis {

    public static String convertDate() {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        return f.format(new Date());
    }

    public static String convertMicrosecond() {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
        return f.format(new Date());
    }
}
