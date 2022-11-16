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
}
