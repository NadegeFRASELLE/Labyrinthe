import java.util.Random;

public class Labyrinthe {
    final int longueur = 5;
    final int hauteur = 4;
    //Pour le moment on va travailler avec un tableau de 5 par 4 cases
    Case[][] labyrinthe = new Case[longueur][hauteur];
    boolean[][] mursVerticaux = new boolean[longueur + 1][hauteur];
    boolean[][] mursHorizontaux = new boolean[longueur][hauteur + 1];
    //On a un tableau pour stocker les cases, une "matrice" de murs verticaux et une "matrice" de murs horizontaux

    public Labyrinthe() {
        setupCases();
        setupMurs();
    }

    private void selectionCaseHasard(){
        Random r = new Random();
        int[] idTempo = new int[2];

        int choix = r.nextInt(1,3);
        //choix d'un tableaux de murs au hasard puis ensuite choix des coordonnées du mur au hasard
        if(choix == 1){
            idTempo[0] = r.nextInt(mursVerticaux.length);
            idTempo[1] = r.nextInt(mursVerticaux[0].length);
            // si les id des cases sont différents, alors on les fusionne (retirer le mur + elles ont le meme id)
            //on a choisi un mur au hasard et on regarde les cases de part et d'autre de ce mur
            if(labyrinthe[idTempo[0] - 1][idTempo[1]].idCase != labyrinthe[idTempo[0] - 1][idTempo[1]].idCase) {
                mursVerticaux[idTempo[0]][idTempo[1]] = false;
                labyrinthe[idTempo[0]][idTempo[1]].idCase = labyrinthe[idTempo[0] - 1][idTempo[1]].idCase;
            }

        }
        if(choix == 2){
            //meme traitement mais pour les murs horizontaux
            idTempo[0] = r.nextInt(mursHorizontaux.length);
            idTempo[1] = r.nextInt(mursHorizontaux[0].length);
            if(labyrinthe[idTempo[0]][idTempo[1] - 1].idCase != labyrinthe[idTempo[0]][idTempo[1]].idCase) {
                mursHorizontaux[idTempo[0]][idTempo[1]] = false;
                labyrinthe[idTempo[0]][idTempo[1]].idCase = labyrinthe[idTempo[0]][idTempo[1] - 1].idCase;
            }
        }
    }
    private void setupCases(){
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
    private void setupMurs(){
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
