import java.util.Random;

public class Labyrinthe {
    final int longueur = 5;
    final int hauteur = 4;
    //Pour le moment on va travailler avec un tableau de 5 par 4 cases
    Case entree = new Case();
    Case sortie = new Case();
    Case[][] labyrinthe = new Case[longueur][hauteur];
    boolean[][] mursVerticaux = new boolean[longueur + 1][hauteur];
    boolean[][] mursHorizontaux = new boolean[longueur][hauteur + 1];
    //On a un tableau pour stocker les cases, une "matrice" de murs verticaux et une "matrice" de murs horizontaux

    public Labyrinthe() {
        setupCases();
        setupMurs();
        setupEntreeSortie(entree);
        setupEntreeSortie(sortie);
        //écrire une méthode booléenne qui vérifie si toutes les cases on le meme ID
        //écrire les ToString pour pouvoir afficher les cases et le labyrinthe (pour tester l'algo)
        //finir le setup
        //écrire l'algo de résolution du labyrinthe puis en faire une méthode

    }
    private void setupEntreeSortie(Case eS){
        //on veut mettre en place une entree ou une sortie
        //a modifier pour écraser les coordonnées de l'entrée et de la sortie
        Random r = new Random();
        int[] idTempo = new int[2]; //variable pour stocker les coordonnées de la case à modifier
        int choix = r.nextInt(1,3);
       //on utilise un objet Random pour déjà  choisir si l'entree/sortie sera sur un mur vertical ou horizontal
            if (choix == 1) {
                choix = r.nextInt(1, 3);
                //ensuite on veut savoir si l'eS sera sur un mur a droite ou a gauche du tableau
                if (choix == 1) {
                    idTempo[0] = 0;
                    idTempo[1] = r.nextInt(mursVerticaux[0].length);
                }
                if (choix == 2) {
                    idTempo[0] = mursVerticaux.length - 1;
                    idTempo[1] = r.nextInt(mursVerticaux[0].length);
                }
                //on supprime le mur dans la matrice correspondante
                mursVerticaux[idTempo[0]][idTempo[1]] = false;
            }
            if (choix == 2) {
                choix = r.nextInt(1, 3);
                if (choix == 1) {
                    idTempo[0] = r.nextInt(mursHorizontaux.length);
                    idTempo[1] = 0;
                }
                if (choix == 2) {
                    idTempo[0] = r.nextInt(mursHorizontaux[0].length);
                    idTempo[1] = mursHorizontaux[0].length - 1;
                }
                mursHorizontaux[idTempo[0]][idTempo[1]] = false;
            }
            //réelle intialisation de la Case entrée ou sortie
        eS.idCase = this.labyrinthe[idTempo[0]][idTempo[1]].idCase;
        eS.coordonneeX = idTempo[0];
        eS.coordonneeY = idTempo[1];
    }
    private int[] selectionCaseHasard(){
        Random r = new Random();
        int[] idTempo = new int[2];

        int choix = r.nextInt(1,3);
        //choix d'un tableaux de murs au hasard puis ensuite choix des coordonnées du mur au hasard (bords exclus)
        if(choix == 1){
            idTempo[0] = r.nextInt(1, mursVerticaux.length-1);
            idTempo[1] = r.nextInt(1,mursVerticaux[0].length-1);
            // si les id des cases sont différents, alors on les fusionne (retirer le mur + elles ont le meme id)
            //on a choisi un mur au hasard et on regarde les cases de part et d'autre de ce mur
            if(labyrinthe[idTempo[0] - 1][idTempo[1]].idCase != labyrinthe[idTempo[0] - 1][idTempo[1]].idCase) {
                mursVerticaux[idTempo[0]][idTempo[1]] = false;
                labyrinthe[idTempo[0]][idTempo[1]].idCase = labyrinthe[idTempo[0] - 1][idTempo[1]].idCase;
            }
        }
        if(choix == 2){
            //meme traitement mais pour les murs horizontaux
            idTempo[0] = r.nextInt(1,mursHorizontaux.length-1);
            idTempo[1] = r.nextInt(1, mursHorizontaux[0].length-1);
            if(labyrinthe[idTempo[0]][idTempo[1] - 1].idCase != labyrinthe[idTempo[0]][idTempo[1]].idCase) {
                mursHorizontaux[idTempo[0]][idTempo[1]] = false;
                labyrinthe[idTempo[0]][idTempo[1]].idCase = labyrinthe[idTempo[0]][idTempo[1] - 1].idCase;
            }
        }
        return idTempo;
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
