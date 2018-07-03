/*
	Verify Preorder Serialization of a Binary Tree
	One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record the node's value. If it is a null node, we record using a sentinel value such as #.

	     _9_
	    /   \
	   3     2
	  / \   / \
	 4   1  #  6
	/ \ / \   / \
	# # # #   # #
	For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents a null node.

	Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a binary tree. Find an algorithm without reconstructing the tree.

	Each comma separated value in the string must be either an integer or a character '#' representing null pointer.

	You may assume that the input format is always valid, for example it could never contain two consecutive commas such as "1,,3".

	Example 1:
	"9,3,4,#,#,1,#,#,2,#,6,#,#"
	Return true

	Example 2:
	"1,#"
	Return false

	Example 3:
	"9,#,#,1"
	Return false
 */

//Solution1: prefer
/*
	In a binary tree, if we consider null as leaves, then

	all non-null node provides 2 outdegree and 1 indegree (2 children and 1 parent), 
    except root all null node provides 0 outdegree and 1 indegree (0 child and 1 parent).
	Suppose we try to build this tree. During building, we record the difference between out degree and in degree diff = outdegree - indegree.
	When the next node comes, we then decrease diff by 1, because the node provides an in degree. 
	If the node is not null, we increase diff by 2, because it provides two out degrees. 
	If a serialization is correct, diff should never be negative and diff will be zero when finished.
 */
//Solution1
public class Solution {
    public boolean isValidSerialization(String preorder) {
       String[] nodes = preorder.split(",");
       int diff = 1;
       for (String node : nodes) {
           if (--diff < 0) {
               return false;
           } 
           if (!node.equals("#")) {
               diff += 2;
           }
       }
       return diff == 0;
    }
}
/*
    If we treat null's as leaves, then the binary tree will always be full. A full binary tree has a good property that # of leaves = # of nonleaves + 1.
    Since we are given a pre-order serialization, we just need to find the shortest prefix of the serialization sequence satisfying the property above.
    If such prefix does not exist, then the serialization is definitely invalid; otherwise, the serialization is valid if and only if the prefix is the entire sequence.
*/
//Solution2: prefer, 除了root节点以外，每个非空节点的出度为2入度为1， 空节点的出度为0入度为1，所以
class Solution {
    public boolean isValidSerialization(String preorder) {
        int nonleaves = 0, leaves = 0, i = 0;
        String[] nodes = preorder.split(",");
        for (i = 0; i < nodes.length && nonleaves + 1 != leaves; i++) {
            if (nodes[i].equals("#"))
                leaves++;
            else
                nonleaves++;
        }
        return leaves == nonleaves + 1 && i == nodes.length;
    }
}

//Solution3: use depth to check
public class Solution {
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() == 0) {
            return false;
        }
        String[] strs = preorder.split(",");
        int depth = 0;
        int i = 0;
        while (i < strs.length - 1) {
            if (strs[i++].equals("#")) {
                if (depth == 0) {
                    return false;
                }
                depth--;
            } else {
                depth++;
            }
        }
        if (depth != 0) {
            return false;
        }
        return strs[strs.length - 1].equals("#");
    }
}

