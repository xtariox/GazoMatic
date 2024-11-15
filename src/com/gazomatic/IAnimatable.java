package com.gazomatic;

public interface IAnimatable extends Runnable {
    // This method will be called every frame
    void update();

    @Override
    default void run() {
        while (true) {
            // Call the update method every frame
            if (!Time.isPaused()) {
                update();
            }
            try {
                // Delay the thread to achieve 60 FPS
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
