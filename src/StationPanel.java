import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StationPanel extends JPanel {
    private Image backgroundImage;
    public StationPanel() {
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/bg.png"))).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
