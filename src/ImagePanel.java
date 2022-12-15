import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImagePanel extends JPanel {
    //on a choisi de réutiliser la classe ImagePanel du TP convolutions

    public final int scaleWidth;
    public int scaleHeight;

    private Image img;

    public ImagePanel(int scaleWidth, int scaleHeight) {
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
    }


    public void setImage(BufferedImage bufferedImage) throws IOException {
        Image scaledInstance = bufferedImage.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_DEFAULT);
        setImage(scaledInstance);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    private void setImage(Image img) {
        this.img = img;
        Dimension size = new Dimension(scaleWidth, scaleHeight);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        repaint();
        updateUI();
    }

}