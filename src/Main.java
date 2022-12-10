import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;

import javax.swing.*;

public class Main {
    static Labyrinthe l;
    static int ratio = 6;

    public static void main(String[] args) throws IOException {

        //pour l'affichage
        JFrame mainFrame = new JFrame();
        //JFrame mainFrame = createMainFrame();


        JPanel nordPanel = new JPanel();
        ImagePanel westPanel = new ImagePanel(400, 400);
        westPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        ImagePanel eastPanel = new ImagePanel(400, 400);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JButton UN = new JButton("Affichage du labyrinthe");
        JButton DEUX = new JButton("Résolution");
        JButton TROIS = new JButton("Téléchargement");

        nordPanel.setBackground(Color.DARK_GRAY);


        UN.setBounds(400, 60, 100, 40);
        DEUX.setBounds(400, 60, 100, 40);
        TROIS.setBounds(400, 60, 100, 40);

        nordPanel.add(UN);
        nordPanel.add(DEUX);
        nordPanel.add(TROIS);


        mainFrame.add(nordPanel, BorderLayout.NORTH);
        mainFrame.add(westPanel, BorderLayout.WEST);
        mainFrame.add(eastPanel, BorderLayout.EAST);

        mainFrame.setSize(1000, 800);
        mainFrame.setVisible(true);
        mainFrame.getContentPane().setBackground(Color.PINK);


        //action du premier bouton
        UN.addActionListener(e -> {
            //on créé un nouveau labyrinthe et on le converti en BufferedImage
            l = new Labyrinthe();
            BufferedImage inputImage = newBuffredImage();
            BufferedImage bufferedImage = transformArrayToImage(inputImage, l);
            try {
                westPanel.setImage(bufferedImage); //on affiche l'image ainsi générée dans le panel est
                eastPanel.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        DEUX.addActionListener(e -> {
            Stack<Node> c = l.genererCheminSortie();
            BufferedImage inputImage = newBuffredImage();

            BufferedImage solution = colorierSolution(inputImage, c);
            BufferedImage solutionAvecMurs = transformArrayToImage(solution, l);


            try {
                eastPanel.setVisible(true);
                eastPanel.setImage(solutionAvecMurs);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    private static BufferedImage newBuffredImage() {
        return new BufferedImage(l.longueur * ratio + 1,
                l.hauteur * ratio + 1, BufferedImage.TYPE_INT_RGB);
    }

    //methode du tp convolution
    private static BufferedImage transformArrayToImage(BufferedImage image, Labyrinthe labyrinthe) {

        //la BufferedImage fait 5 fois la taille de notre tableau car chaque case fait 5 pixels avec les murs (+ 1 pour
        // fermer le labyrinthe)

        for (int i = 0; i < labyrinthe.mursHorizontaux.length; i++) {
            for (int j = 0; j < labyrinthe.mursHorizontaux[i].length; j++) {
                Color color = Color.WHITE; //on décide que les murs seront blancs, donc on stocke cette couleur dans une variable Color
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
                Color color = Color.WHITE;
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
            System.out.println(direction);
            switch (direction) {
                case 'E' -> tracerTraitEst(c1.coordonneeX, c1.coordonneeY, image);
                case 'W' -> tracerTraitOuest(c1.coordonneeX, c1.coordonneeY, image);
                case 'S' -> tracerTraitSud(c1.coordonneeX, c1.coordonneeY, image);
                default -> tracerTraitNord(c1.coordonneeX, c1.coordonneeY, image);
            }
            Case cFin = chemin.get(chemin.size()-1).getNoeud();
            tracerTraitOuest(cFin.coordonneeX, cFin.coordonneeY, image);
        }

//        for (Node n : chemin) {
//            Color color = new Color(51, 51, 153);
//            for (int i = 3; i < 4; i++) {
//                for (int j = 3; j < 4; j++) {
//                    image.setRGB(n.getNoeud().coordonneeX * 6 + i, n.getNoeud().coordonneeY * 6 + j, color.getRGB());
//                }
//            }
//        }
        return image;
    }

    private static void tracerTraitEst(int x, int y, BufferedImage image) {
        Color color = new Color(51, 51, 153);
        for (int j = 3; j < ratio+1; j++) {
            image.setRGB(x * ratio + j, y * ratio + 3, color.getRGB());
        }
    }

    private static void tracerTraitOuest(int x, int y, BufferedImage image) {
        Color color = new Color(51, 51, 153);
        for (int j = 0; j < ratio-2; j++) {
            image.setRGB(x * ratio + j, y * ratio + 3, color.getRGB());
        }
    }

    private static void tracerTraitNord(int x, int y, BufferedImage image) {
        Color color = new Color(51, 51, 153);
        for (int j = 0; j < ratio-2; j++) {
            image.setRGB(x * ratio + 3, y * ratio + j, color.getRGB());
        }
    }


    private static void tracerTraitSud(int x, int y, BufferedImage image) {
        Color color = new Color(51, 51, 153);
        for (int j = 3; j < ratio+1; j++) {
            image.setRGB(x * ratio + 3, y * ratio + j, color.getRGB());
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

}

