public class RunTrie {
    public static void main(String[] args) {
        String strings[] = {"ROMANE", "ROMANUS", "ROMULUS", "RUBENS", "RUBER", "RUB", "RUBICON", "RUBICUNDUS", "RUBY", "ROAD"}; // s - String
        String found_msg[] = {"Not found", "Found"};

        Trie root = new Trie();

        for (int i = 0; i < strings.length; i++)
            root.insert(strings[i]);

        String s = "RUBER"; // String: s

        System.out.printf("--- Before Remove ---\n");
        System.out.printf("Search %s in Trie:\t%s\n", s, found_msg[root.search(s)]);
        System.out.printf("Search %s in Trie:\t%s\n", "RUBY", found_msg[root.search("RUBY")]);
        System.out.printf("Search %s in Trie:\t%s\n", "RUBI", found_msg[root.search("RUBI")]);


        System.out.printf("\nDeletes %s...", s);
        if (root.remove(root.getRoot(), s))
            System.out.printf("\n%s Successfully deleted!\n", s);

        System.out.printf("\n--- After Remove ---\n");
        System.out.printf("Search %s in Trie:\t%s\n", s, found_msg[root.search(s)]);
        System.out.printf("Search %s in Trie:\t%s\n", "RUBY", found_msg[root.search("RUBY")]);
        System.out.printf("Search %s in Trie:\t%s\n", "RUBI", found_msg[root.search("RUBI")]);
    }
}

class Trie {
    private TrieNode root;

    public Trie() {
        init();
    }

    // init Trie
    public void init() {
        root = new TrieNode();
    }

    // insert to Trie
    public void insert(String str) {
        int index;
        int len = str.length();

        TrieNode currNode = root;

        for (int i = 0; i < len; i++) {
            index = getIndex(str.charAt(i));
            if (currNode != null && currNode.getChildren(index) == null)
                currNode.setChildren(index, new TrieNode());

            currNode = currNode.getChildren(index);
        }

        currNode.setChildren(0, new TrieNode());
    }

    // Search in Trie
    public int search(String str) {
        int index;
        int len = str.length();
        TrieNode currNode = root;

        for (int i = 0; i < len; i++) {
            index = getIndex(str.charAt(i));

            if (currNode.getChildren(index) == null)
                return 0;

            currNode = currNode.getChildren(index);
        }

        return (currNode != null && currNode.getChildren(0) != null) ? 1 : 0;
    }

    // Recursive function to remove a string from a Trie
    public boolean remove(TrieNode node, String str) {
        int index;
        TrieNode nextNode;

        if (node == null)
            return false;

        if (str.length() > 0) {
            index = getIndex(str.charAt(0));
            nextNode = node.getChildren(index);
            if (nextNode == null)
                return false;

            remove(nextNode, str.substring(1));

            if (nextNode.isLeaf()) {
                node.setChildren(index, null);
                return false;
            }
        }

        return true;
    }

    // Get index by subtracting the ASCII value of ch at the value of 'A'
    private int getIndex(char ch) {
        return (ch == '\0') ? 0 : ((int) ch - (int) 'A' + 1);
    }

    // Get root node
    public TrieNode getRoot() {
        return root;
    }
}

// TrieNode class
class TrieNode {
    private final int SIGMA = 26; // N - Size of the alphabet
    private TrieNode[] children;

    TrieNode() {
        children = new TrieNode[SIGMA + 1];
        init();
    }

    // init node
    public void init() {
        for (int i = 0; i < SIGMA + 1; i++)
            children[i] = null;
    }

    // Get children by index
    public TrieNode getChildren(int index) {
        return children[index];
    }

    // Set children by index
    public void setChildren(int index, TrieNode trieNode) {
        children[index] = trieNode;
    }

    // Check if node is leaf
    public boolean isLeaf() {
        if (children[0] == null)
            return false;
        for (int i = 1; i < SIGMA + 1; i++)
            if (children[i] != null)
                return false;
        return true;
    }
}