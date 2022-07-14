package com.optimissa.BookShelfApi.Utils;

public class PrimitiveUtils {

    private PrimitiveUtils() {
        throw new IllegalStateException("Cannot instantiate this class");
    }

    public static boolean isInteger(String str) {
        return testAndTry(() -> Integer.parseInt(str));
    }

    public static boolean isDouble(String str) {
        return testAndTry(() -> Double.parseDouble(str));
    }

    public static boolean isBoolean(String str) {
        return testAndTry(() -> Boolean.getBoolean(str));
    }

    @FunctionalInterface
    private interface RunnableThrow {
        void run() throws Exception;
    }

    private static boolean testAndTry(RunnableThrow r) {
        boolean result = true;
        try {
            r.run();
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

}
