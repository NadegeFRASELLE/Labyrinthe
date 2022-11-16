public class Labyrinthe {
    final int longueur = 5;
    final int hauteur = 4;
    //Pour le moment on va travailler avec un tableau de 5 par 4 cases

    Case[][] labyrinthe = new Case[longueur][hauteur];
    boolean[][] mursVerticaux = new boolean[longueur + 1][hauteur];
    boolean[][] mursHorizontaux = new boolean[longueur][hauteur + 1];
    //On a un tableau pour stocker les cases, une "matrice" de murs verticaux et une "matrice" de murs horizontaux

    public void setupCases(){
        int idCase = 0;
        //variable temporaire pour assigner leur id aux cases
        //permet aussi de remettre le labyrinthe à zéro
        for(int i =0; i < labyrinthe.length; i++){
            for(int j =0; j < labyrinthe[0].length; j++){
                labyrinthe[i][j] = new Case(idCase, i, j);
                idCase++;
            }
        }
    }
    public void setupMurs(){
        //on met en place tous les murs pour avoir un quadrillage comme un tableau pour ensuite pouvoir exécuter
        //l'algorithme de modélisation. Permet aussi de remettre le labyrinthe à zéro
        for(int i =0; i < mursVerticaux.length; i++){
            for(int j =0; j < mursVerticaux[0].length; j++){
                mursVerticaux[i][j] = true;
            }
        }
        for(int i =0; i < mursHorizontaux.length; i++){
            for(int j =0; j < mursHorizontaux[0].length; j++){
                mursHorizontaux[i][j] = true;
            }
        }
    }
}
