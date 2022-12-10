import java.util.ArrayList;
import java.util.List;

public class Node {
    private final Case noeud;
    private final Node parent;
    private final List<Node> children;

    public Node(Case noeud, Node parent) {
        this.noeud = noeud;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public Case getNoeud() {
        return noeud;
    }

    public Node getParent() {
        return parent;
    }

    public void addChildren(Labyrinthe l) {
        //case au nord
        //on vérifie si la case au dessus de notre noeud existe dans le tableau labyrinthe
        if (l.caseExiste(noeud.coordonneeX, noeud.coordonneeY - 1)) {
            //si oui, on vérifie si le noeud et la case au dessus son séparées par un mur
            if (!l.mursHorizontaux[noeud.coordonneeX][noeud.coordonneeY].estPresent) {
                //s'il n'y a pas de mur, on rajoute la case comme enfant
                Node child = new Node(l.labyrinthe[noeud.coordonneeX][noeud.coordonneeY - 1], this);
                if (isNodeDifferent(parent, child)) {
                    this.children.add(child);
                    //si on a trouvé la sortie, on arrete la méthode
                    if (compareNodeCase(child, l.sortie)) {
                        return;
                    }
                }
            }
        }
        //même procédure pour la case au sud
        if (l.caseExiste(noeud.coordonneeX, noeud.coordonneeY + 1)) {
            if (!l.mursHorizontaux[noeud.coordonneeX][noeud.coordonneeY + 1].estPresent) {
                Node child = new Node(l.labyrinthe[noeud.coordonneeX][noeud.coordonneeY + 1], this);
                if (isNodeDifferent(parent, child)) {
                    this.children.add(child);
                    //si on a trouvé la sortie, on arrete la méthode
                    if (compareNodeCase(child, l.sortie)) {
                        return;
                    }
                }
            }
        }
        //même procédure pour la case a l'est
        if (l.caseExiste(noeud.coordonneeX + 1, noeud.coordonneeY)) {
            if (!l.mursVerticaux[noeud.coordonneeX + 1][noeud.coordonneeY].estPresent) {
                Node child = new Node(l.labyrinthe[noeud.coordonneeX + 1][noeud.coordonneeY], this);
                if (isNodeDifferent(parent, child)) {
                    this.children.add(child);
                    //si on a trouvé la sortie, on arrete la méthode
                    if (compareNodeCase(child, l.sortie)) {
                        return;
                    }
                }
            }
        }
        //même procédure pour la case à l'ouest
        if (l.caseExiste(noeud.coordonneeX - 1, noeud.coordonneeY)) {
            if (!l.mursVerticaux[noeud.coordonneeX][noeud.coordonneeY].estPresent) {
                Node child = new Node(l.labyrinthe[noeud.coordonneeX - 1][noeud.coordonneeY], this);
                if (isNodeDifferent(parent, child)) {
                    this.children.add(child);
                    //si on a trouvé la sortie, on arrete la méthode
                    if (compareNodeCase(child, l.sortie)) {
                        return;
                    }
                }
            }
        }
        //on réitère la méthode sur chaque enfant récursivement
        for (Node n : this.children) {
            n.addChildren(l);
        }

    }

    public Node trouverSortie(Case sortie) {
        if (compareNodeCase(this, sortie)) {
            return this;
        }

        for (Node n : children) {
            Node resultat =  n.trouverSortie(sortie);
            if (compareNodeCase(resultat, sortie)) {
                return resultat;
            }
        }

        return this;
    }

    private boolean compareNodeCase(Node n, Case c) {
        //juste un booléen qui compare le contenu d'un node(une Case) avec un objet Case
        return n.getNoeud().coordonneeX == c.coordonneeX && n.getNoeud().coordonneeY == c.coordonneeY;
    }

    private boolean isNodeDifferent(Node node1, Node node2) {
        if(node1 == null || node2 ==null) {
            return true;
        }
        //juste un booléen qui compare le contenu de deux nodes
        return node1.getNoeud().coordonneeX != node2.getNoeud().coordonneeX || node1.getNoeud().coordonneeY != node2.getNoeud().coordonneeY;
    }

}
