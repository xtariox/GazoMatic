package com.gazomatic;

public class Time {
    private static double deltaTime = 0; // in seconds
    private static long lastTime = System.nanoTime(); // in nanoseconds
    private static boolean paused = false;

    public static void update() {
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / 1e9; // convert to seconds
        lastTime = currentTime;
    }

    public static double getDeltaTime() {
        return paused ? 0 : deltaTime;
    }

    public static long getTime() {
        return System.nanoTime();
    }

    public static void pause() {
        System.out.println("Pausing time");
        paused = true;
    }

    public static void resume() {
        System.out.println("Resuming time");
        paused = false;
    }

    public static boolean isPaused() {
        return paused;
    }
}
