import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	static Labyrinthe l;
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World !");
        
        //pour l'affichage
        JFrame mainFrame = new JFrame();
        //JFrame mainFrame = createMainFrame();
		

		JPanel nordPanel = new JPanel();
		ImagePanel sudPanel = new ImagePanel(400,400);
		
		
		JButton UN = new JButton("Affichage du labyrinthe");
		JButton DEUX = new JButton("resolution");
		JButton TROIS = new JButton("telechargement");

		nordPanel.setBackground(Color.DARK_GRAY);

		
		UN.setBounds(400, 60, 100, 40);
		DEUX.setBounds(400, 60, 100, 40);
		TROIS.setBounds(400, 60, 100, 40);
				
		nordPanel.add(UN);
		nordPanel.add(DEUX);
		nordPanel.add(TROIS);
		
		
		mainFrame.add(nordPanel, BorderLayout.NORTH);
		mainFrame.add(sudPanel, BorderLayout.SOUTH);
				
		mainFrame.setSize(800,800); 
		mainFrame.setVisible(true); 
		mainFrame.getContentPane().setBackground(Color.PINK);
		
		

		//action du premier bouton
		UN.addActionListener(e -> {
			//on créé un nouveau labyrinthe et on le converti en BufferedImage
			l = new Labyrinthe();
			BufferedImage bufferedImage = transformArrayToImage(l);
			try {
				sudPanel.setImage(bufferedImage); //on affiche l'image ainsi générée dans le panel est
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	
 
    

	}
    
    //methode du tp convolution 
	private static BufferedImage transformArrayToImage(Labyrinthe labyrinthe) {
		BufferedImage outputImage = new BufferedImage(labyrinthe.longueur * 5 + 1,
				labyrinthe.hauteur * 5 + 1, BufferedImage.TYPE_INT_RGB);
		//la BufferedImage fait 5 fois la taille de notre tableau car chaque case fait 5 pixels avec les murs (+ 1 pour
		// fermer le labyrinthe)

		for (int i = 0; i < labyrinthe.mursHorizontaux.length; i++) {
			for (int j = 0; j < labyrinthe.mursHorizontaux[i].length; j++) {
				Color color = Color.WHITE; //on décide que les murs seront blancs, donc on stocke cette couleur dans une variable Color
				if (labyrinthe.mursHorizontaux[i][j].estPresent) {
					for(int k = 0; k<6; k++) { // on traite les murs horizontaux, donc quand on trace un mur il fait 5 pixels de long
						outputImage.setRGB(i*5+k, j*5, color.getRGB());
					}
				}
			}
		}

		for (int i = 0; i < labyrinthe.mursVerticaux.length; i++) {
			//pareil qu'au dessus
			for (int j = 0; j < labyrinthe.mursVerticaux[i].length; j++) {
				Color color = Color.WHITE;
				if (labyrinthe.mursVerticaux[i][j].estPresent) {
					for(int k =0; k<6; k++) { // seulement ici, si on a un mur, il fait 5 pixels de haut (car on traite des murs verticaux)
						outputImage.setRGB(i*5, j*5+k, color.getRGB());
					}
				}

			}
		}

		return outputImage; // on renvoie l'image ainsi générée
	}

}