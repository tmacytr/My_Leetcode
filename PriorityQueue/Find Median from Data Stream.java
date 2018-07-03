/*
	Find Median from Data Stream
	Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

	Examples: 
	[2,3,4] , the median is 3

	[2,3], the median is (2 + 3) / 2 = 2.5

	Design a data structure that supports the following two operations:

	void addNum(int num) - Add a integer number from the data stream to the data structure.
	double findMedian() - Return the median of all elements so far.
	For example:

	add(1)
	add(2)
	findMedian() -> 1.5
	add(3) 
	findMedian() -> 2
	Tags: Heap, Design
*/



//Solution1 prefer
/*
    Use a max-heap smaller to store the smaller half of the input numbers
    Use a min-heap larger to store the larger half of the input numbers
 */
class MedianFinder {
    PriorityQueue<Integer> larger;
    PriorityQueue<Integer> smaller;

    public MedianFinder() {
        larger = new PriorityQueue();
        smaller = new PriorityQueue<Integer>((a, b) -> b - a);
    }

    public void addNum(int num) {
        smaller.offer(num);
        larger.offer(smaller.poll());
        if (smaller.size() < larger.size()) {
            smaller.offer(larger.poll());
        }

    }

    public double findMedian() {
        if (larger.size() == smaller.size())
            return (larger.peek() + smaller.peek()) / 2.0;
        else
            return smaller.peek();
    }
}

/*
	思路:
		首先用large（最小堆）来存放num，之后large出列num，将-num存放到small中，因为small存放的是负数，因此可以成是一个最大堆
		根据large 和 small的 size来回倒腾，如果large的size 小于small.size 则将-small.peek()放入large中

		large和small的size之差不会超过1,

		形如small = (-1, -2, -3)
		large = (6, 5, 4)
		保证两个peek一定是median（size相等时,偶数长度）
		large的peek是median（size不等时，奇数长度）
*/
//Solution2 stefan, store negative
class MedianFinder {
	 private Queue<Long> small = new PriorityQueue<Long>();
	 private Queue<Long> large = new PriorityQueue<Long>(); 
	 public void addNum(int num) {
	 	large.add((long)num);
	 	small.add(-large.poll());
	 	if (large.size() < small.size()) {
	 		large.offer(-small.poll());
	 	}
	 }
	 public double findMedian() {
	 	if (large.size() > small.size()) {
	 		return large.peek();
	 	} else {
	 		return (large.peek() - small.peek()) / 2.0;
	 	}
	 }
};


// Follow up
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




