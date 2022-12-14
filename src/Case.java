public class Case {
    //L'identifiant de la case + ses coordonnées pour pouvoir gérer les murs ensuite
    int idCase;
    int coordonneeX;
    int coordonneeY;

    public Case(int idCase, int coordonneeX, int coordonneeY){
        this.idCase = idCase;
        this.coordonneeX = coordonneeX;
        this.coordonneeY = coordonneeY;
    }

    public Case() { //Constructeur vide créé pour initialiser les entrees et sorties, pas utilisé ailleurs
        this.idCase = -1;
        this.coordonneeX = -1;
        this.coordonneeY = -1;
    }
}
