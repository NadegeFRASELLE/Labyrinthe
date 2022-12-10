
public class Tree {
    private final Node root;

    public Tree(Case rootData){
        root = new Node(rootData,null);
    }

    public Node getRoot() {
        return root;
    }
}
