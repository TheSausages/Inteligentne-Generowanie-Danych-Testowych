package DataCreation;

public class RandBetween {

    public static long randlong(long start, long end, double param) {

        return start + (int)Math.round(param * (end - start));
    }

    public static int randint(int start, int end, double param) {

        return start + (int)Math.round(param * (end - start));
    }
}
