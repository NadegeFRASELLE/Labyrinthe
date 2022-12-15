import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel {
    //on a choisi de réutiliser la classe ImagePanel du TP convolutions et de la modifier légèrement
    public final int scaleWidth;
    public int scaleHeight;
    private Image img;

    public ImagePanel(int scaleWidth, int scaleHeight) {
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
    }

    public void setImage(BufferedImage bufferedImage) throws IOException {
        //pour afficher l'image, on la met à la bonne échelle pour qu'elle rentre dans l'ImagePanel
        this.img = bufferedImage.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_DEFAULT);
        Dimension size = new Dimension(scaleWidth, scaleHeight);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        repaint();
        updateUI();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}