/*
	Sparse Matrix Multiplication
	Given two sparse matrices A and B, return the result of AB.

	You may assume that A's column number is equal to B's row number.

	Example:

	A = [
	  [ 1, 0, 0],
	  [-1, 0, 3]
	]

	B = [
	  [ 7, 0, 0 ],
	  [ 0, 0, 0 ],
	  [ 0, 0, 1 ]
	]


	     |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
	AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
	                  | 0 0 1 |
	Tags: HashTable, Math                
*/


public class Solution {
    public int[][] multiply(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, nB = B[0].length;
        int[][] C = new int[m][nB];
        for(int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0) {
                    for (int j = 0; j < nB; j++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
        return C;   
    }
}



/*
	A sparse matrix can be represented as a sequence of rows, each of which is a sequence of (column-number, value) pairs of the nonzero values in the row.
	So let's create a non-zero array for A, and do multiplication on B.
*/

public class Solution {
	public int[][] multiply(int[][] A, int[][] B) {
	    int m = A.length, n = A[0].length, nB = B[0].length;
	    int[][] result = new int[m][nB];

	    List[] indexA = new List[m];
	    for(int i = 0; i < m; i++) {
	        List<Integer> numsA = new ArrayList<>();
	        for(int j = 0; j < n; j++) {
	            if(A[i][j] != 0){
	                numsA.add(j); 
	                numsA.add(A[i][j]);
	            }
	        }
	        indexA[i] = numsA;
	    }

	    for(int i = 0; i < m; i++) {
	        List<Integer> numsA = indexA[i];
	        for(int p = 0; p < numsA.size() - 1; p += 2) {
	            int colA = numsA.get(p);
	            int valA = numsA.get(p + 1);
	            for(int j = 0; j < nB; j ++) {
	                int valB = B[colA][j];
	                result[i][j] += valA * valB;
	            }
	        }
	    }

	    return result;   
	}
}

// Solution3: one hashtable
public class Solution {
	public int[][] multiply(int[][] A, int[][] B) {
		int m = A.length, g = A[0].length, n = B[0].length;
		Map<Integer, HashMap<Integer, Integer>> mapA = new HashMap<>();
		int[][] c = new int[m][n];
		// Map<Integer, HashMap<Integer, Integer>> mapB = new HashMap<>();
		// add matrix A
		for (int i = 0; i < m; ++i) {
			HashMap<Integer, Integer> tmp = new HashMap<>();
			for (int j = 0; j < g; ++j) {
				if (A[i][j] != 0) tmp.put(j, A[i][j]);
			}
			mapA.put(i, tmp);
		}
		// Multiplication
		for (int i = 0; i < m; ++i) {
			HashMap<Integer, Integer> tmp = mapA.get(i);
			for (Integer k : tmp.keySet()) {
				for (int j = 0; j < n; ++j) c[i][j] += tmp.get(k) * B[k][j];
			}
		}
		return c;
	}
}