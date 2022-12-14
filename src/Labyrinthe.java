import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class Labyrinthe {
    int longueur;
    int hauteur;
    //Pour le moment on va travailler avec un tableau de 5 par 4 cases
    Case entree = new Case();
    Case sortie = new Case();
    //on va stocker l'emplacement de l'entrée et de la sortie
    ArrayList<Mur> mursVertAVisiter = new ArrayList<>();
    ArrayList<Mur> mursHoriAVisiter = new ArrayList<>();
    //listes qui contiennent les murs horizontaux et verticaux qu'on a encore à visiter
    Case[][] labyrinthe;
    Mur[][] mursVerticaux;
    Mur[][] mursHorizontaux;
    //On a un tableau pour stocker les cases, une "matrice" de murs verticaux et une "matrice" de murs horizontaux

    public Labyrinthe(int longueur, int hauteur){
        this.longueur = longueur;
        this.hauteur = hauteur;

        labyrinthe = new Case[longueur][hauteur];
        mursVerticaux = new Mur[longueur + 1][hauteur];
        mursHorizontaux = new Mur[longueur][hauteur + 1];

        setupCases();
        setupMurs();
        entree.idCase = 0;
        entree.coordonneeX = 0;
        entree.coordonneeY = 0;
        mursVerticaux[0][0].estPresent = false;
        mursVerticaux[mursVerticaux.length - 1][mursVerticaux[0].length - 1].estPresent = false;
        sortie.idCase = labyrinthe[longueur - 1][hauteur - 1].idCase;
        sortie.coordonneeX = longueur - 1;
        sortie.coordonneeY = hauteur - 1;

        while (!checkIDCases(labyrinthe)) {
            selectionCase();
        }
    }

    public Tree genererArbreLabyrinthe() {
        //creation d'un arbre où sera stockée la solution
        Tree arbreLabyrinthe = new Tree(entree);
        arbreLabyrinthe.getRoot().addChildren(this);
        return arbreLabyrinthe;
    }

    public Stack<Node> genererCheminSortie() {
        Tree t = genererArbreLabyrinthe();
        Node n = t.getRoot().trouverSortie(sortie);
        Stack<Node> chemin = new Stack<>();
        chemin.add(n);
        while (n != null && n.getParent() != null) {
            n = n.getParent();
            chemin.add(n);
        }
        return chemin;
    }

    /*private void setupEntreeSortie(Case eS){
        //on veut mettre en place une entree ou une sortie
        //a modifier pour écraser les coordonnées de l'entrée et de la sortie
        Random r = new Random();
       
        int[] idTempo = new int[2]; //variable pour stocker les coordonnées de la case à modifier
        int choix = r.nextInt(1,3);
       //on utilise un objet Random pour déjà  choisir si l'entree/sortie sera sur un mur vertical ou horizontal
            if (choix == 1) {
                choix = r.nextInt(1,3);
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
        if(idTempo[0]==0){
            eS.idCase = this.labyrinthe[idTempo[0]][idTempo[1]].idCase;
        } else {
            eS.idCase = this.labyrinthe[idTempo[0]-1][idTempo[1]].idCase;
        }

        eS.coordonneeX = idTempo[0];
        eS.coordonneeY = idTempo[1];
    }*/
    public boolean caseExiste(int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        return x <= longueur && y <= hauteur;
    }

    private void selectionCase() {
        Random r = new Random();
        Mur mur;
        int choix = r.nextInt(1, 3);

        //choix d'un tableaux de murs au hasard puis ensuite choix des coordonnées du mur au hasard (bords exclus)

        if (choix == 1) {
            mur = selectionMurHasard('v');
            //on utilise la méthode pour sélectionner des murs au hasard à partir de listes des murs restants
            if (caseExiste(mur.coordX - 1,mur.coordY) &&
                    labyrinthe[mur.coordX][mur.coordY].idCase != labyrinthe[mur.coordX - 1][mur.coordY].idCase) {
                //si les coordonnées des cases des deux cotés du mur sélectionné ne sont pas les memes, on supprime le
                //mur et on fusionne les cases grace a la methode remplacerID
                mursVerticaux[mur.coordX][mur.coordY].estPresent = false;
                remplacerID(labyrinthe[mur.coordX - 1][mur.coordY].idCase, labyrinthe[mur.coordX][mur.coordY].idCase);
            }
        }
        if (choix == 2) {
            mur = selectionMurHasard('h');
            //idem mais avec les murs horizontaux
            if (caseExiste(mur.coordX ,mur.coordY -1 )
                    && labyrinthe[mur.coordX][mur.coordY].idCase != labyrinthe[mur.coordX][mur.coordY - 1].idCase) {
                mursHorizontaux[mur.coordX][mur.coordY].estPresent = false;
                remplacerID(labyrinthe[mur.coordX][mur.coordY - 1].idCase, labyrinthe[mur.coordX][mur.coordY].idCase);
            }

        }

    }

    private Mur selectionMurHasard(char ch) {
        //on va sélectionner un mur au hasard mais en prenant en entrée un char qui va permettre de savoir dans quelle
        // liste de murs on va le choisir
        Random r = new Random();
        Mur m = new Mur(-1, -1);

        if (ch == 'v' && !mursVertAVisiter.isEmpty()) {
            //on choisit un mur au hasard dans la liste des murs verticaux (si elle n'est pas vide), on le sauvegarde
            //dans la variable temporaire puis on retire ce mur de la liste
            int choix = r.nextInt(mursVertAVisiter.size());
            m = mursVertAVisiter.get(choix);
            mursVertAVisiter.remove(m);
        }
        if (ch == 'h' && !mursHoriAVisiter.isEmpty()) {
            // idem mais avec les murs horizontaux
            int choix = r.nextInt(mursHoriAVisiter.size());
            m = mursHoriAVisiter.get(choix);
            mursHoriAVisiter.remove(m);
        }
        return m; //on retourne le mur trouvé
    }

    private boolean checkIDCases(Case[][] labyrinthe) {
        //méthode qui renvoie true si les ID de toutes les cases sont identiques (ce qui veut dire que toutes les cases
        //ont été fusionnées
        for (int i = 0; i < labyrinthe.length - 1; i++) {
            for (int j = 0; j < labyrinthe[0].length; j++) {
                if (labyrinthe[i][j].idCase != labyrinthe[i + 1][j].idCase) {
                    return false;
                }
            }
        }
        return true;
    }

    private void remplacerID(int idRemplacement, int idEffacer) {
        // on veut fusionner des cases, donc toutes les cases qui ont l ID a effacer verront leur ID remplace par
        // le nouvel ID
        for (Case[] cases : labyrinthe) {
            for (int j = 0; j < labyrinthe[0].length; j++) {
                if (cases[j].idCase == idEffacer) {
                    cases[j].idCase = idRemplacement;
                }
            }
        }
    }

    private void setupCases() {
        int idCase = 0;
        //variable temporaire pour assigner leur id aux cases
        //permet aussi de remettre le labyrinthe à zéro
        for (int i = 0; i < labyrinthe.length; i++) {
            for (int j = 0; j < labyrinthe[0].length; j++) {
                labyrinthe[i][j] = new Case(idCase, i, j);
                idCase++;
            }
        }
    }

    private void setupMurs() {
        //on met en place tous les murs pour avoir un quadrillage comme un tableau pour ensuite pouvoir exécuter
        //l'algorithme de modélisation. Permet aussi de remettre le labyrinthe à zéro
        for (int i = 0; i < mursVerticaux.length; i++) {
            for (int j = 0; j < mursVerticaux[0].length; j++) {
                mursVerticaux[i][j] = new Mur(i, j);
                if (i != 0 && i != mursVerticaux.length - 1) {
                    mursVertAVisiter.add(mursVerticaux[i][j]);
                }
            }
        }
        for (int i = 0; i < mursHorizontaux.length; i++) {
            for (int j = 0; j < mursHorizontaux[0].length; j++) {
                mursHorizontaux[i][j] = new Mur(i, j);
                if (j != 0 && j != mursHorizontaux[0].length - 1) {
                    mursHoriAVisiter.add(mursHorizontaux[i][j]);
                }
            }
        }
    }
}
