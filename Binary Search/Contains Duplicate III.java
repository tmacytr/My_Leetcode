/*
	Contains Duplicate III 
	Given an array of integers, find out whether there are two distinct indices i and j 
	in the array such that the difference between nums[i] and nums[j] is at most t and 
	the difference between i and j is at most k.
	Tags: Binary Search, TreeSet, SortedSet

*/

/*
    参考LeetCode Discuss：https://leetcode.com/discuss/38177/java-o-n-lg-k-solution

    TreeSet数据结构（Java）使用红黑树实现，是平衡二叉树的一种。

    该数据结构支持如下操作：

    1. floor()方法返set中不大于给定元素的最大元素；如果不存在这样的元素，则返回 null。

    2. ceiling()方法返回set中不小于给定元素的最小元素；如果不存在这样的元素，则返回 null。
*/

//Solution1:
public class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        //input check
        if (k < 1 || t < 0 || nums == null || nums.length < 2) return false;

        SortedSet<Long> set = new TreeSet<Long>();

        for (int j = 0; j < nums.length; j++) {
            SortedSet<Long> subSet = set.subSet((long) nums[j] - t, (long) nums[j] + t + 1);
            if (!subSet.isEmpty()) return true;

            if (j >= k) {
                set.remove((long) nums[j - k]);
            }
            set.add((long) nums[j]);
        }
        return false;
    }
}

//Solution2 prefer
class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k < 1 || t < 0)
            return false;
        TreeSet<Integer> set = new TreeSet();
        for (int i = 0; i < nums.length; i++) {
            if ((set.floor(nums[i]) != null && nums[i] <= t + set.floor(nums[i]))
                    || (set.ceiling(nums[i]) != null && set.ceiling(nums[i]) <= t + nums[i])) {
                return true;
            }
            set.add(nums[i]);
            if (i >= k)
                set.remove(nums[i - k]);
        }
        return false;
    }
}


/*
    1. 给每一个元素设置一个桶， bucket = num[i] / (t + 1), 桶序号为Key， 元素值为value 将元素存入HashMap<bucket, num>
         比如当 t = 3时， 0 , 1 ,2 ,3 就在 序号为0的桶中，表示在这个桶中的所有数都是满足互相的差值 <= t(3)
         因此如果我们找到相同桶序号的元素返回true，或者在bucket-1 以及bucket+1的桶中去找，并且这个元素与bucket元素的差值也要<= t

    2. 我们注意到，上面的方法虽然可以很方便的解决两元素差值的判断，但是我们如何确保两个元素的间距最多不超过k呢？
        如果map.keySet().size() == k，表示HashMap里面的条目已经等于K，
        由于我们的put操作是在比较以后才放入，也就意味着如果不remove最左边的放入的元素，put操作以后，map.keySet().size() 会大于k，会导致出现在下一次循环的时候出现t符合，但是k不符合仍然返回true的错误情况
        因此每当能到map.keySet().size() == k这个判断 就意味我们就要从hashmap中移除num[i - k], 这样在后面put元素进来，并进行下一次循环的时候保证在范围k内。

*/
//Solution3 time O(n)! Best!
public class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k < 1 || t < 0) {
            return false;
        }
        Map<Long, Long> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            long remappedNum = (long)nums[i] - Integer.MIN_VALUE;
            long bucket = remappedNum / ((long) t + 1);
            if (map.containsKey(bucket) || 
                (map.containsKey(bucket - 1) && remappedNum - map.get(bucket - 1) <= t) || 
                (map.containsKey(bucket + 1) && map.get(bucket + 1) - remappedNum <= t)) {
                    return true;
                }
            /*
                if k == 3, t == 3,

                0, 1, 2 , 3 是一个bucket，
                当0, 4 , 8， 12时  map里有0， 4， 8，size() == k, 我们需要remove 1， 如果不move不然下一次如果进来2 会出现 （0， 4， 8， 11）错误匹配 0和2
            */
            if (map.keySet().size() == k) {
                long lastBucket = ((long)nums[i - k] - Integer.MIN_VALUE) / ((long)t + 1);
                map.remove(lastBucket);
            }
            map.put(bucket, remappedNum);
        }
        return false;
    }
}

//Solution4: hashmap + bucket + prefer
class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int maxIndexDiff, int maxValueDiff) {
        if (maxValueDifference < 0)
            return false;
        HashMap<Integer, Integer> map = new HashMap();
        maxValueDifference++; // +1是为了处理一个bucket的size index问题， 比如 [0, 1, 2, 3], maxValueDiff = 3, 0 ~ 3 都应该在 index为0的bucket里， 也就是使得从 0 ~ 3 / 4 == 0
        for (int i = 0; i < nums.length; i++) {
            //距离只要超过就移除最左边界限的数，保证了现在map里的所有值距离都一定在maxIndexDiff之内
            if (i > maxIndexDiff)
                map.remove(getKey(nums[i - maxIndexDiff - 1], maxValueDiff));
            int bucketIndex = getKey(nums[i], maxValueDiff);
            // 在同一个bucket里的数肯定符合条件
            if (map.containsKey(bucketIndex))
                return true;
            if (map.containsKey(bucketIndex - 1) && Math.abs(nums[i] - map.get(bucketIndex - 1)) < maxValueDiff)
                return true;
            if (map.containsKey(bucketIndex + 1) && Math.abs(nums[i] - map.get(bucketIndex + 1)) < maxValueDiff)
                return true;
            map.put(bucketIndex, nums[i]);
        }
        return false;
    }

    // 根据数组的value计算bucke
    private int getKey(int value, int maxValueDiff) {
        if (value < 0)
            return (value + 1) / maxValueDiff - 1;
        else
            return value / maxValueDiff;
    }

    /*
        为什么要这么做？因为在java中 -3/5 == 0 而不是-1 所以i < 0 == (i + 1) / maxValueDiff - 1是为了解决负数时的索引问题
        距离比如maxValueDiff= 4
                               （-5， -4， -3， -2， -1） 在bucket为 -1的桶里，
                                （0，  1，  2，  3， 4）  在bucket为0的桶里，
                              （-10， -9， -8， -7， -6） 在bucket为-2 的桶里，
    */
}
