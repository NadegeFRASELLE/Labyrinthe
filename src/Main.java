import java.awt.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    static Labyrinthe l;
    static int ratio = 12;
    static BufferedImage labyrinthe;
    static BufferedImage solution;

    public static void main(String[] args) throws IOException {

        //pour l'affichage
        JFrame mainFrame = new JFrame();
        //JFrame mainFrame = createMainFrame();

        JPanel principal = new JPanel();
        JPanel nordPanel = new JPanel();
        ImagePanel westPanel = new ImagePanel(400, 360);
        ImagePanel eastPanel = new ImagePanel(400, 360);


        JButton UN = new JButton("Affichage du labyrinthe");
        JButton DEUX = new JButton("Resolution");
        JButton TROIS = new JButton("Telechargement");

        nordPanel.setBackground(Color.DARK_GRAY);
        principal.setBackground(Color.PINK);

        UN.setBounds(400, 60, 100, 40);
        DEUX.setBounds(400, 60, 100, 40);
        TROIS.setBounds(400, 60, 100, 40);

        nordPanel.add(UN);
        nordPanel.add(DEUX);
        nordPanel.add(TROIS);

        principal.setLayout(new BorderLayout(20,15));
        principal.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        principal.add(westPanel, BorderLayout.WEST);
        principal.add(eastPanel, BorderLayout.EAST);

        mainFrame.add(principal);
        mainFrame.add(nordPanel, BorderLayout.NORTH);
        mainFrame.setSize(1000,600);
        mainFrame.setVisible(true);
        mainFrame.getContentPane().setBackground(Color.PINK);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }
    );

        //action du premier bouton
        UN.addActionListener(e -> {
            //on créé un nouveau labyrinthe et on le converti en BufferedImage
            l = new Labyrinthe();
            labyrinthe = newBuffredImage();
            colorierFond(labyrinthe);
            transformArrayToImage(labyrinthe, l);

            try {
                westPanel.setImage(labyrinthe); //on affiche l'image ainsi générée dans le panel est
                eastPanel.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        DEUX.addActionListener(e -> {
            Stack<Node> c = l.genererCheminSortie();
            BufferedImage inputImage = newBuffredImage();
            colorierFond(inputImage);
            BufferedImage sol = colorierSolution(inputImage, c);
            solution = transformArrayToImage(sol, l);


            try {
                eastPanel.setVisible(true);
                eastPanel.setImage(solution);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        TROIS.addActionListener(e -> {
            try {
                File out = new File("labyrinthe.png");
                ImageIO.write(labyrinthe, "png", out);
                File outputfile = new File("solution.png");
                ImageIO.write(solution, "png", outputfile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private static BufferedImage getMyImage(BufferedImage solution) {
		// TODO Auto-generated method stub
		return null;
	}

	private static BufferedImage newBuffredImage() {
        return new BufferedImage(l.longueur * ratio + 1,
                l.hauteur * ratio + 1, BufferedImage.TYPE_INT_RGB);
    }

    //methode du tp convolution
    private static BufferedImage transformArrayToImage(BufferedImage image, Labyrinthe labyrinthe) {

        //la BufferedImage fait 5 fois la taille de notre tableau car chaque case fait 5 pixels avec les murs (+ 1 pour
        // fermer le labyrinthe)
        Color color = Color.DARK_GRAY;
        for (int i = 0; i < labyrinthe.mursHorizontaux.length; i++) {
            for (int j = 0; j < labyrinthe.mursHorizontaux[i].length; j++) {

                if (labyrinthe.mursHorizontaux[i][j].estPresent) {
                    for (int k = 0; k < ratio+1; k++) { // on traite les murs horizontaux, donc quand on trace un mur il fait 5 pixels de long
                        image.setRGB(i * ratio + k, j * ratio, color.getRGB());
                    }
                }
            }
        }

        for (int i = 0; i < labyrinthe.mursVerticaux.length; i++) {
            //pareil qu'au dessus
            for (int j = 0; j < labyrinthe.mursVerticaux[i].length; j++) {
                if (labyrinthe.mursVerticaux[i][j].estPresent) {
                    for (int k = 0; k < ratio+1; k++) { // seulement ici, si on a un mur, il fait 5 pixels de haut (car on traite des murs verticaux)
                        image.setRGB(i * ratio, j * ratio + k, color.getRGB());
                    }
                }

            }
        }

        return image; // on renvoie l'image ainsi générée
    }

    private static BufferedImage colorierSolution(BufferedImage image, Stack<Node> chemin) {
        Case c0 = chemin.get(0).getNoeud();
        tracerTraitEst(c0.coordonneeX, c0.coordonneeY, image);

        for (int k = 1; k < chemin.size(); k++) {
            Case c1 = chemin.get(k - 1).getNoeud();
            Case c2 = chemin.get(k).getNoeud();

            char direction = sensTrait(c1, c2);
            switch (direction) {
                case 'E' -> {tracerTraitEst(c1.coordonneeX, c1.coordonneeY, image);
                    tracerTraitOuest(c2.coordonneeX, c2.coordonneeY, image);}
                case 'W' -> { tracerTraitOuest(c1.coordonneeX, c1.coordonneeY, image);
                tracerTraitEst(c2.coordonneeX, c2.coordonneeY, image); }
                case 'S' -> {tracerTraitSud(c1.coordonneeX, c1.coordonneeY, image);
                    tracerTraitNord(c2.coordonneeX, c2.coordonneeY, image);}
                default -> {tracerTraitNord(c1.coordonneeX, c1.coordonneeY, image);
                    tracerTraitSud(c2.coordonneeX, c2.coordonneeY, image);}
            }
            Case cFin = chemin.get(chemin.size()-1).getNoeud();
            tracerTraitOuest(cFin.coordonneeX, cFin.coordonneeY, image);
        }

        return image;
    }

    private static void tracerTraitEst(int x, int y, BufferedImage image) {
        Color color = new Color(51, 51, 153);
        for (int j = ratio/2; j < ratio+1; j++) {
            image.setRGB(x * ratio + j, y * ratio + (ratio/2), color.getRGB());
        }
    }

    private static void tracerTraitOuest(int x, int y, BufferedImage image) {
        Color color = new Color(51, 51, 153);
        for (int j = 0; j < ratio-(ratio/2)+1; j++) {
            image.setRGB(x * ratio + j, y * ratio + (ratio/2), color.getRGB());
        }
    }

    private static void tracerTraitNord(int x, int y, BufferedImage image) {
        Color color = new Color(51, 51, 153);
        for (int j = 0; j < ratio-(ratio/2); j++) {
            image.setRGB(x * ratio + (ratio/2), y * ratio + j, color.getRGB());
        }
    }


    private static void tracerTraitSud(int x, int y, BufferedImage image) {
        Color color = new Color(51, 51, 153);
        for (int j = ratio/2; j < ratio+2; j++) {
            image.setRGB(x * ratio + (ratio/2), y * ratio + j, color.getRGB());
        }
    }

    private static char sensTrait(Case c1, Case c2) {
        if (c1.coordonneeX - c2.coordonneeX < 0) {
            return 'E';
        }
        if (c1.coordonneeX - c2.coordonneeX > 0) {
            return 'W';
        }
        if (c1.coordonneeY - c2.coordonneeY < 0) {
            return 'S';
        }
        return 'N';
    }
    private static void colorierFond(BufferedImage image){

        Graphics2D graphics = image.createGraphics();

        graphics.setPaint (Color.PINK);
        graphics.fillRect ( 0, 0, image.getWidth(), image.getHeight() );
    }

}

