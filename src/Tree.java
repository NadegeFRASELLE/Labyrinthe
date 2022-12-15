
public class Tree {
    //classe trouvée sur StackOverflow pour creer des arbres en java
    private final Node root;

    public Tree(Case rootData){ //notre arbre est composé de Nodes contenant chacun une Case
        root = new Node(rootData,null);
    }

    public Node getRoot() {
        return root;
    }
}
