import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImagePanel extends JPanel {
    //on a choisi de réutiliser la classe ImagePanel du TP convolutions

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;

    public final int scaleWidth;
    public int scaleHeight;

    private Image img;

    private InputStream imageStream;
    private String filePath;

    public ImagePanel(int scaleWidth, int scaleHeight) throws IOException {
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
    }

    public ImagePanel(int scaleWidth, int scaleHeight, File imageFile) throws IOException {
        this(scaleWidth, scaleHeight);
        //setImage(imageFile.getAbsolutePath());
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