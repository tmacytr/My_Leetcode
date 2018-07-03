/*
    379. Design a Phone Directory which supports the following operations:

    get: Provide a number which is not assigned to anyone.
    check: Check if a number is available or not.
    release: Recycle or release a number.
    Example:

    // Init a phone directory containing a total of 3 numbers: 0, 1, and 2.
    PhoneDirectory directory = new PhoneDirectory(3);

    // It can return any available phone number. Here we assume it returns 0.
    directory.get();

    // Assume it returns 1.
    directory.get();

    // The number 2 is available, so return true.
    directory.check(2);

    // It returns 2, the only number that is left.
    directory.get();

    // The number 2 is no longer available, so return false.
    directory.check(2);

    // Release number 2 back to the pool.
    directory.release(2);

    // Number 2 is available again, return true.
    directory.check(2);

    Companies: Google
    Related Topics: Design, Linkedlist, queue, hashtable
 */

//Solution1: Using a hashset to store the used number, and put/poll all the available number from queue
class PhoneDirectory {
    Set<Integer> used;
    Queue<Integer> available;
    int max;
    /** Initialize your data structure here
     @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    public PhoneDirectory(int maxNumbers) {
        used = new HashSet();
        available = new LinkedList();
        max = maxNumbers;
        for (int i = 0; i < max; i++) {
            available.offer(i);
        }
    }

    /** Provide a number which is not assigned to anyone.
     @return - Return an available number. Return -1 if none is available. */
    public int get() {
        Integer res = available.poll();
        if (res == null)
            return -1;
        used.add(res);
        return res;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        return number >= 0 && number < max && !used.contains(number);
    }

    /** Recycle or release a number. */
    public void release(int number) {
        if (used.remove(number))
            available.offer(number);
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */