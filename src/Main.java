import java.awt.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    static Labyrinthe l;
    static int ratio = 12; //le nombre de pixels d'une case
    static BufferedImage labyrinthe;
    static BufferedImage solution;

    public static void main(String[] args) throws IOException {

        //creation des différents éléments de l'affichage
        JFrame mainFrame = new JFrame();
        JPanel principal = new JPanel();
        JPanel nordPanel = new JPanel();
        ImagePanel westPanel = new ImagePanel(400, 400);
        ImagePanel eastPanel = new ImagePanel(400, 400);
        String[] text = Stream.of("Facile", "Moyen", "Difficile").toArray(String[]::new);
        JComboBox<String> difficulte = new JComboBox<>(text);

        JButton afficher = new JButton("Affichage du labyrinthe");
        JButton resoudre = new JButton("Resolution");
        JButton telecharger = new JButton("Telechargement");

        //couleurs choisies pour notre programme

        nordPanel.setBackground(Color.DARK_GRAY);
        principal.setBackground(Color.PINK);

        // dimensions des boutons et ajout des differents elements sur le panel principal puis sur la mainframe
        afficher.setBounds(400, 60, 100, 40);
        resoudre.setBounds(400, 60, 100, 40);
        telecharger.setBounds(400, 60, 100, 40);

        nordPanel.add(difficulte);
        nordPanel.add(afficher);
        nordPanel.add(resoudre);
        nordPanel.add(telecharger);

        //ajout de bordures
        principal.setLayout(new BorderLayout(20, 15));
        principal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        principal.add(westPanel, BorderLayout.WEST);
        principal.add(eastPanel, BorderLayout.EAST);

        mainFrame.add(principal);
        mainFrame.add(nordPanel, BorderLayout.NORTH);

        //notre fenetre a une taille minimum pour eviter que le labyrinthe soit mal affiche

        mainFrame.setMinimumSize(new Dimension(900, 550));
        mainFrame.setVisible(true);
        mainFrame.getContentPane().setBackground(Color.PINK);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //ajout pour que le programme cesse de s'executer quand on ferme la fenetre avec la croix

        //ajout des differentes actions des boutons
        afficher.addActionListener(e -> {
            //on créé un nouveau labyrinthe et on le converti en BufferedImage
            if (difficulte.getSelectedItem() == "Facile") {
                l = new Labyrinthe(5, 5);
            }
            if (difficulte.getSelectedItem() == "Moyen") {
                l = new Labyrinthe(7, 7);
            }
            if (difficulte.getSelectedItem() == "Difficile") {
                l = new Labyrinthe(9, 9);
            }
            labyrinthe = DessinerLabyrinthe.newBuffredImage(l, ratio);
            DessinerLabyrinthe.colorierFond(labyrinthe);
            DessinerLabyrinthe.transformArrayToImage(labyrinthe, l, ratio);

            try {
                westPanel.setImage(labyrinthe); //on affiche l'image ainsi générée dans le panel est
                eastPanel.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        resoudre.addActionListener(e -> {
            Stack<Node> c = l.genererCheminSortie();
            BufferedImage inputImage = DessinerLabyrinthe.newBuffredImage(l, ratio);
            DessinerLabyrinthe.colorierFond(inputImage);
            BufferedImage sol = DessinerLabyrinthe.colorierSolution(inputImage, c, ratio);
            solution = DessinerLabyrinthe.transformArrayToImage(sol, l, ratio);


            try {
                eastPanel.setVisible(true);
                eastPanel.setImage(solution);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        telecharger.addActionListener(e -> {
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

}

