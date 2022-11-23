import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World !");
        
        //pour l'affichage
        JFrame mainFrame = new JFrame();
        //JFrame mainFrame = createMainFrame();
		JPanel eastPanel = new JPanel();
		JPanel westPanel = new JPanel();
		JButton UN = new JButton("Affichage du labyrinthe");
		JButton DEUX = new JButton("resolution");
		JButton TROIS = new JButton("telechargement");
		
		
		
		UN.setBounds(400, 60, 100, 40);
		DEUX.setBounds(400, 60, 100, 40);
		TROIS.setBounds(400, 60, 100, 40);
				
		eastPanel.add(UN);
		eastPanel.add(DEUX);
		eastPanel.add(TROIS);
		
		mainFrame.add(eastPanel, BorderLayout.EAST);
		mainFrame.add(westPanel, BorderLayout.WEST);
				
		mainFrame.setSize(800,800); 
		mainFrame.setVisible(true); 
		mainFrame.getContentPane().setBackground(Color.CYAN);
	
 
    

	}
    
    //methode du tp convolution 
    /**

		private BufferedImage transformArrayToImage(BufferedImage originalImage, double[][] arrayImage) throws IOException {
		BufferedImage outputImage = new BufferedImage(originalImage.getWidth() - 3 + 1,
				originalImage.getHeight() - 3 + 1, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < arrayImage.length; i++) {
			for (int j = 0; j < arrayImage[i].length; j++) {
				Color color = new Color(fixOutOfRangeRGBValues(arrayImage[i][j]),
						fixOutOfRangeRGBValues(arrayImage[i][j]), fixOutOfRangeRGBValues(arrayImage[i][j]));
				outputImage.setRGB(j, i, color.getRGB());
			}
		}
		return outputImage;
	}
	**/
}