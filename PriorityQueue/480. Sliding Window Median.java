/*
    480. Sliding Window Median

    Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

    Examples:
    [2,3,4] , the median is 3

    [2,3], the median is (2 + 3) / 2 = 2.5

    Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Your job is to output the median array for each window in the original array.

    For example,
    Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

    Window position                Median
    ---------------               -----
    [1  3  -1] -3  5  3  6  7       1
     1 [3  -1  -3] 5  3  6  7       -1
     1  3 [-1  -3  5] 3  6  7       -1
     1  3  -1 [-3  5  3] 6  7       3
     1  3  -1  -3 [5  3  6] 7       5
     1  3  -1  -3  5 [3  6  7]      6

     Companies: Google

     Similar Questions: Find Median from Data Stream
 */

//Solution: same conecpt with Find Median from Data Stream
//Time complexity: O(logn)
class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length - k + 1];
        MedianQueue medianQueue = new MedianQueue();
        for (int i = 0; i < nums.length; i++) {
            medianQueue.add(nums[i]);
            if (i >= k)
                medianQueue.remove(nums[i - k]); // remove the most left element
            if (i >= k - 1)
                res[i - k + 1] = medianQueue.median(); // when the window has enough elements, we compute the median
        }
        return res;
    }

    class MedianQueue {
        Queue<Long> maxHeap = new PriorityQueue(Collections.reverseOrder());
        Queue<Long> minHeap = new PriorityQueue();

        public void add(long num) {
            maxHeap.add(num);
            minHeap.add(maxHeap.poll());
        }

        public boolean remove(long num) {
            return maxHeap.remove(num) || minHeap.remove(num);
        }

        public double median() {
            while (maxHeap.size() - minHeap.size() >= 2)
                minHeap.offer(maxHeap.poll());
            while (minHeap.size() - maxHeap.size() >= 1)
                maxHeap.offer(minHeap.poll());
            return maxHeap.size() == minHeap.size() ? (maxHeap.peek() + minHeap.peek()) / 2.0 : maxHeap.peek();
        }
    }
}

// Follow up
/*

    There are so many ways around this problem, that frankly, it is scary. Here are a few more that I came across:

    1. Buckets! If the numbers in the stream are statistically distributed, then it is easier to keep track of buckets where the median would land, than the entire array.
       Once you know the correct bucket, simply sort it find the median. If the bucket size is significantly smaller than the size of input processed,
       this results in huge time saving. @mitbbs8080 has an interesting implementation here.

    2. Reservoir Sampling. Following along the lines of using buckets: if the stream is statistically distributed, you can rely on Reservoir Sampling.
       Basically, if you could maintain just one good bucket (or reservoir) which could hold a representative sample of the entire stream, you could estimate the median of the entire stream from just this one bucket.
       This means good time and memory performance. Reservoir Sampling lets you do just that. Determining a "good" size for your reservoir?
       Now, that's a whole other challenge. A good explanation for this can be found in this StackOverflow answer.

    3. Segment Trees are a great data structure if you need to do a lot of insertions or a lot of read queries over a limited range of input values.
       They allow us to do all such operations fast and in roughly the same amount of time, always. The only problem is that they are far from trivial to implement.
       Take a look at my introductory article on Segment Trees if you are interested.

    4. Order Statistic Trees are data structures which seem to be tailor-made for this problem.
       They have all the nice features of a BST, but also let you find the k^​th order element stored in the tree.
       They are a pain to implement and no standard interview would require you to code these up.
       But they are fun to use if they are already implemented in the language of your choice.
 */