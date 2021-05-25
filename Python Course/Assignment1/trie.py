def getIndex(ch):
    return (ord(ch) - ord('A') + 1) if ord(ch) != 0 else 0


class TrieNode:
    sigma = 26  # N - Size of the alphabet

    # init node
    def __init__(self):
        self.children = [None] * (self.sigma + 1)

    # Get children by index
    def getChildren(self, index):
        return self.children[index]

    # Set children by index
    def setChildren(self, index, node):
        self.children[index] = node

    # Check if node is leaf
    def isLeaf(self):
        if self.children[0] is None:
            return False
        for i in range(1, self.sigma + 1):
            if self.children[i] is not None:
                return False
        return True


class Trie:
    # init Trie
    def __init__(self):
        self.root = TrieNode()

    # insert to Trie
    def insert(self, str):
        curr = self.root

        for ch in str:
            index = getIndex(ch)
            if curr is not None and curr.getChildren(index) is None:
                curr.setChildren(index, TrieNode())

            curr = curr.getChildren(index)

        curr.setChildren(0, TrieNode())

    # Search in Trie
    def search(self, str):
        curr = self.root

        for ch in str:
            index = getIndex(ch)
            if curr.getChildren(index) is None:
                return 0
            curr = curr.getChildren(index)
        return 1 if curr is not None and curr.getChildren(0) is not None else 0

    # Recursive function to remove a string from a Trie
    def remove(self, node, str):
        str_len = len(str)

        if node is None:
            return False

        if str_len > 0:
            index = getIndex(str[0])
            next_node = node.getChildren(index)
            if next_node is None:
                return False

            self.remove(next_node, str[1:])

            if next_node.isLeaf():
                node.setChildren(index, None)
                return False

        return True


# Test
strings = ["ROMANE", "ROMANUS", "ROMULUS", "RUBENS", "RUBER", "RUB", "RUBICON", "RUBICUNDUS", "RUBY", "ROAD"]
found_msg = ["Not found", "Found"]

root = Trie()

for s in strings:
    root.insert(s)

s = "RUBER"  # String: s

print("--- Before Remove ---")
print("Search %s in Trie:\t%s" % (s, found_msg[root.search(s)]))
print("Search %s in Trie:\t%s" % ("RUBY", found_msg[root.search("RUBY")]))
print("Search %s in Trie:\t%s" % ("RUBI", found_msg[root.search("RUBI")]))

print("\nDeletes %s..." % s)
if root.remove(root.root, s):
    print(s, "Successfully deleted!")


print("\n--- After Remove ---")
print("Search %s in Trie:\t%s" % (s, found_msg[root.search(s)]))
print("Search %s in Trie:\t%s" % ("RUBY", found_msg[root.search("RUBY")]))
print("Search %s in Trie:\t%s" % ("RUBI", found_msg[root.search("RUBI")]))
