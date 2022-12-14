import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class DessinerLabyrinthe {

    public static BufferedImage newBuffredImage(Labyrinthe l, int ratio) {
        return new BufferedImage(l.longueur * ratio + 1,
                l.hauteur * ratio + 1, BufferedImage.TYPE_INT_RGB);
    }

    public static void colorierFond(BufferedImage image) {

        Graphics2D graphics = image.createGraphics();

        graphics.setPaint(Color.PINK);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    //methode du tp convolution
    public static BufferedImage transformArrayToImage(BufferedImage image, Labyrinthe labyrinthe, int ratio) {

        //la BufferedImage fait 5 fois la taille de notre tableau car chaque case fait 5 pixels avec les murs (+ 1 pour
        // fermer le labyrinthe)
        Color color = Color.DARK_GRAY;
        for (int i = 0; i < labyrinthe.mursHorizontaux.length; i++) {
            for (int j = 0; j < labyrinthe.mursHorizontaux[i].length; j++) {

                if (labyrinthe.mursHorizontaux[i][j].estPresent) {
                    for (int k = 0; k < ratio + 1; k++) { // on traite les murs horizontaux, donc quand on trace un mur il fait 5 pixels de long
                        image.setRGB(i * ratio + k, j * ratio, color.getRGB());
                    }
                }
            }
        }

        for (int i = 0; i < labyrinthe.mursVerticaux.length; i++) {
            //pareil qu'au dessus
            for (int j = 0; j < labyrinthe.mursVerticaux[i].length; j++) {
                if (labyrinthe.mursVerticaux[i][j].estPresent) {
                    for (int k = 0; k < ratio + 1; k++) { // seulement ici, si on a un mur, il fait 5 pixels de haut (car on traite des murs verticaux)
                        image.setRGB(i * ratio, j * ratio + k, color.getRGB());
                    }
                }

            }
        }

        return image; // on renvoie l'image ainsi générée
    }

    public static BufferedImage colorierSolution(BufferedImage image, Stack<Node> chemin, int ratio) {
        Case c0 = chemin.get(0).getNoeud();
        tracerTraitEst(c0.coordonneeX, c0.coordonneeY, image, ratio);

        for (int k = 1; k < chemin.size(); k++) {
            Case c1 = chemin.get(k - 1).getNoeud();
            Case c2 = chemin.get(k).getNoeud();

            Direction direction = sensTrait(c1, c2);
            switch (direction) {
                case EST -> {
                    tracerTraitEst(c1.coordonneeX, c1.coordonneeY, image, ratio);
                    tracerTraitOuest(c2.coordonneeX, c2.coordonneeY, image, ratio);
                }
                case OUEST -> {
                    tracerTraitOuest(c1.coordonneeX, c1.coordonneeY, image, ratio);
                    tracerTraitEst(c2.coordonneeX, c2.coordonneeY, image, ratio);
                }
                case SUD -> {
                    tracerTraitSud(c1.coordonneeX, c1.coordonneeY, image, ratio);
                    tracerTraitNord(c2.coordonneeX, c2.coordonneeY, image, ratio);
                }
                default -> {
                    tracerTraitNord(c1.coordonneeX, c1.coordonneeY, image, ratio);
                    tracerTraitSud(c2.coordonneeX, c2.coordonneeY, image, ratio);
                }
            }
            Case cFin = chemin.get(chemin.size() - 1).getNoeud();
            tracerTraitOuest(cFin.coordonneeX, cFin.coordonneeY, image, ratio);
        }

        return image;
    }

    private static void tracerTraitEst(int x, int y, BufferedImage image, int ratio) {
        Color color = new Color(0, 160, 223);//51 51 153
        for (int j = ratio / 2; j < ratio + 1; j++) {
            image.setRGB(x * ratio + j, y * ratio + (ratio / 2), color.getRGB());
        }
    }

    private static void tracerTraitOuest(int x, int y, BufferedImage image, int ratio) {
        Color color = new Color(0, 160, 223);
        for (int j = 0; j < ratio - (ratio / 2) + 1; j++) {
            image.setRGB(x * ratio + j, y * ratio + (ratio / 2), color.getRGB());
        }
    }

    private static void tracerTraitNord(int x, int y, BufferedImage image, int ratio) {
        Color color = new Color(0, 160, 223);
        for (int j = 0; j < ratio - (ratio / 2); j++) {
            image.setRGB(x * ratio + (ratio / 2), y * ratio + j, color.getRGB());
        }
    }

    private static void tracerTraitSud(int x, int y, BufferedImage image, int ratio) {
        Color color = new Color(0, 160, 223);
        for (int j = ratio / 2; j < ratio + 2; j++) {
            image.setRGB(x * ratio + (ratio / 2), y * ratio + j, color.getRGB());
        }
    }

    private static Direction sensTrait(Case c1, Case c2) {
        if (c1.coordonneeX - c2.coordonneeX < 0) {
            return Direction.EST;
        }
        if (c1.coordonneeX - c2.coordonneeX > 0) {
            return Direction.OUEST;
        }
        if (c1.coordonneeY - c2.coordonneeY < 0) {
            return Direction.SUD;
        }
        return Direction.NORD;
    }

}
