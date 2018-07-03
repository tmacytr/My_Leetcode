/*
    353. Design Snake Game

    Design a Snake game that is played on a device with screen size = width x height. Play the game online if you are not familiar with the game.

    The snake is initially positioned at the top left corner (0,0) with length = 1 unit.

    You are given a list of food's positions in row-column order. When a snake eats the food, its length and the game's score both increase by 1.

    Each food appears one by one on the screen. For example, the second food will not appear until the first food was eaten by the snake.

    When a food does appear on the screen, it is guaranteed that it will not appear on a block occupied by the snake.

    Example:
    Given width = 3, height = 2, and food = [[1,2],[0,1]].

    Snake snake = new Snake(width, height, food);

    Initially the snake appears at position (0,0) and the food at (1,2).

    |S| | |
    | | |F|

    snake.move("R"); -> Returns 0

    | |S| |
    | | |F|

    snake.move("D"); -> Returns 0

    | | | |
    | |S|F|

    snake.move("R"); -> Returns 1 (Snake eats the first food and right after that, the second food appears at (0,1) )

    | |F| |
    | |S|S|

    snake.move("U"); -> Returns 1

    | |F|S|
    | | |S|

    snake.move("L"); -> Returns 2 (Snake eats the second food)

    | |S|S|
    | | |S|

    snake.move("U"); -> Returns -1 (Game over because snake collides with border)
*/

// Solution: use Set to fast lookup whether is visiting the body, and use Double Linked list deque to represent the body. The position is like: x * width + y
class SnakeGame {
    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    Set<Integer> set;
    Deque<Integer> body;
    int score; // use score to represent the score and food index
    int[][] food;
    int foodIndex;
    int width;
    int height;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        score = 0;
        set = new HashSet();
        body = new LinkedList();
        body.offerLast(0);
        set.add(0);
    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if (score == -1)
            return -1;
        int position = body.peekFirst();
        int rowHead = position / width;
        int colHead = position % width;

        switch (direction) {
            case "U":
                rowHead--;
                break;
            case "D":
                rowHead++;
                break;
            case "L":
                colHead--;
                break;
            case "R":
                colHead++;
                break;
        }

        int newHead = rowHead * width + colHead;
        set.remove(body.peekLast()); // new head is legal to be in old tail's position, remove from set temporarily
        //case 1: out of boundary or eating body
        if (rowHead < 0 || colHead < 0 || rowHead >= height || colHead >= width || set.contains(newHead))
            return -1;

        // add head for case2 and case3
        set.add(newHead);
        body.offerFirst(newHead);

        //case2: eating food, keep tail, add head
        if (score < food.length && food[score][0] == rowHead && food[score][1] == colHead) {
            set.add(body.peekLast());
            score++;
        } else {
            //case3: normal move, remove tail, add head
            body.pollLast();
        }

        return score;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */