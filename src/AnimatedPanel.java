import javax.swing.*;

public abstract class AnimatedPanel extends JPanel {
    protected int x = 0;
    protected int y = 0;
    protected int xSpeed = 1;
    protected int ySpeed = 1;

    public AnimatedPanel() {
        Main.animatedPanels.add(this);
    }

    // This method will be called every frame
    public abstract void update();
}
