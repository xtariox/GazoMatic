package com.gazomatic;

public interface IAnimated extends Runnable {
    // This method will be called every frame
    void update();

    @Override
    default void run() {
        System.out.println("Running thread");
        while (true) {
            // Call the update method every frame
            update();
            try {
                // Delay the thread to achieve 60 FPS
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
