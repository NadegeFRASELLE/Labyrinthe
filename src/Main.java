import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World !");
        
        //pour l'affichage
        JFrame mainFrame = new JFrame();
        //JFrame mainFrame = createMainFrame();
		ImagePanel eastPanel = new ImagePanel(400,400);

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

		UN.addActionListener(e -> {
			BufferedImage bufferedImage = transformArrayToImage(new Labyrinthe());
			try {
				eastPanel.setImage(bufferedImage);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	
 
    

	}
    
    //methode du tp convolution 
	private static BufferedImage transformArrayToImage(Labyrinthe labyrinthe) {
		BufferedImage outputImage = new BufferedImage(labyrinthe.longueur * 5 + 1,
				labyrinthe.hauteur * 5 + 1, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < labyrinthe.mursHorizontaux.length; i++) {
			for (int j = 0; j < labyrinthe.mursHorizontaux[i].length; j++) {
				Color color = Color.WHITE;
				if (labyrinthe.mursHorizontaux[i][j].estPresent) {
					for(int k =0; k<5; k++) {
						outputImage.setRGB(i*5+k, j*5, color.getRGB());
					}
				}
			}
		}

		for (int i = 0; i < labyrinthe.mursVerticaux.length; i++) {
			for (int j = 0; j < labyrinthe.mursVerticaux[i].length; j++) {
				Color color = Color.WHITE;
				if (labyrinthe.mursVerticaux[i][j].estPresent) {
					for(int k =0; k<5; k++) {
						outputImage.setRGB(i*5, j*5+k, color.getRGB());
					}
				}

			}
		}

		return outputImage;
	}

}