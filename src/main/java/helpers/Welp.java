package helpers;

public class Welp {
    public static String Uwufy(String stwing) {
        stwing = Helper.ReplaceString(stwing, "r", "w");
        stwing = Helper.ReplaceString(stwing, "R", "W");
        stwing = Helper.ReplaceString(stwing, "l", "w");
        stwing = Helper.ReplaceString(stwing, "L", "W");
        return stwing;
    }
}
