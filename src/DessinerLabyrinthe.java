import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class DessinerLabyrinthe {

    public static BufferedImage newBuffredImage(Labyrinthe l, int ratio) {
        //la taille de la BufferedImage est modifiable en changeant le ratio dans la classe main qui correspond au nombre
        //pixel par case +1 pour pouvoir fermer le labyrinthe

        return new BufferedImage(l.longueur * ratio + 1,
                l.hauteur * ratio + 1, BufferedImage.TYPE_INT_RGB);
    }

    public static void colorierFond(BufferedImage image) {
        //méthode trouvée sur StackOverflow pour colorier le fond d'une BufferedImage
        Graphics2D graphics = image.createGraphics();

        //on choisit la meme couleur que pour le fond de la mainframe, et on remplit un rectangle qui fait pile la
        //taille de la BufferedImage
        graphics.setPaint(Color.PINK);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    //methode du tp convolution
    public static BufferedImage transformArrayToImage(BufferedImage image, Labyrinthe labyrinthe, int ratio) {

        //on va dessiner les murs du tableau horizontal d'abord
        Color color = Color.DARK_GRAY;
        for (int i = 0; i < labyrinthe.mursHorizontaux.length; i++) {
            for (int j = 0; j < labyrinthe.mursHorizontaux[i].length; j++) {

                if (labyrinthe.mursHorizontaux[i][j].estPresent) {
                    for (int k = 0; k < ratio + 1; k++) {
                        //on veut que notre mur fasse une case de long, donc on introduit la variable k pour colorier k pixels
                        //(ce nombre correspond au ratio qu'on a fixé dans la classe main)
                        image.setRGB(i * ratio + k, j * ratio, color.getRGB());
                    }
                }
            }
        }
        //même procédé que précédemment mais pour les murs verticaux
        for (int i = 0; i < labyrinthe.mursVerticaux.length; i++) {
            for (int j = 0; j < labyrinthe.mursVerticaux[i].length; j++) {
                if (labyrinthe.mursVerticaux[i][j].estPresent) {
                    for (int k = 0; k < ratio + 1; k++) {
                        //ici on veut que notre mur fasse k cases de haut
                        image.setRGB(i * ratio, j * ratio + k, color.getRGB());
                    }
                }

            }
        }

        return image; // on renvoie l'image ainsi générée
    }

    public static BufferedImage colorierSolution(BufferedImage image, Stack<Node> chemin, int ratio) {

        //on récupère la premiere case de la pile qui contient le chemin et on trace un trait
        // comme l'entree est toujours en haut à gauche du labyrinthe, on trace un trait vers la droite
        Case c0 = chemin.get(0).getNoeud();
        tracerTraitEst(c0.coordonneeX, c0.coordonneeY, image, ratio);

        //on va recuperer ensuite chaque case et la comparer avec celle qui la précède dans la pile pour savoir dans
        //quelle direction on doit tracer le trait
        for (int k = 1; k < chemin.size(); k++) {
            Case c1 = chemin.get(k - 1).getNoeud();
            Case c2 = chemin.get(k).getNoeud();

            Direction direction = sensTrait(c1, c2);
            switch (direction) {
                case EST -> {
                    //on trace le trait est et ouest pour avoir un trait continu
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
                case NORD -> {
                    tracerTraitNord(c1.coordonneeX, c1.coordonneeY, image, ratio);
                    tracerTraitSud(c2.coordonneeX, c2.coordonneeY, image, ratio);
                }
            }
            //pour la derniere case, on sait que la sortie est forcément en bas à droite du labyrinthe, donc on trace
            //un trait qui va vers la gauche
            Case cFin = chemin.get(chemin.size() - 1).getNoeud();
            tracerTraitOuest(cFin.coordonneeX, cFin.coordonneeY, image, ratio);
        }
        return image;
    }

    private static Direction sensTrait(Case c1, Case c2) {
        //on determine la direction du trait en comparant les coordonnees en x et en y des cases
        //par exemple, si x1 - x2 est inférieur a 0, c'est que c1 est a droite de la case c2
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

    private static void tracerTraitEst(int x, int y, BufferedImage image, int ratio) {
        Color color = new Color(0, 160, 223);
        //on va tracer un trait d'un pixel du milieu de la case à son bord droit (ratio est le nombre de pixels qu'il
        // y a dans une case)
        for (int j = ratio / 2; j < ratio + 1; j++) {
            image.setRGB(x * ratio + j, y * ratio + (ratio / 2), color.getRGB());
        }
    }

    private static void tracerTraitOuest(int x, int y, BufferedImage image, int ratio) {
        Color color = new Color(0, 160, 223);
        //ici on trace un trait au milieu de la case du bord droit jusqu'au centre
        for (int j = 0; j < ratio - (ratio / 2) + 1; j++) {
            image.setRGB(x * ratio + j, y * ratio + (ratio / 2), color.getRGB());
        }
    }

    private static void tracerTraitNord(int x, int y, BufferedImage image, int ratio) {
        Color color = new Color(0, 160, 223);
        //on trace un trait au milieu de la case du bord haut de la case jusqu'au centre
        for (int j = 0; j < ratio - (ratio / 2); j++) {
            image.setRGB(x * ratio + (ratio / 2), y * ratio + j, color.getRGB());
        }
    }

    private static void tracerTraitSud(int x, int y, BufferedImage image, int ratio) {
        Color color = new Color(0, 160, 223);
        //on trace un trait au milieu de la case du centre au bord bas
        for (int j = ratio / 2; j < ratio + 2; j++) {
            image.setRGB(x * ratio + (ratio / 2), y * ratio + j, color.getRGB());
        }
    }


}
