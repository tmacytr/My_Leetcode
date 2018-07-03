/*
    690. Employee Importance

    You are given a data structure of employee information, which includes the employee's unique id, his importance value and his direct subordinates' id.

    For example, employee 1 is the leader of employee 2, and employee 2 is the leader of employee 3. They have importance value 15, 10 and 5, respectively. Then employee 1 has a data structure like [1, 15, [2]], and employee 2 has [2, 10, [3]], and employee 3 has [3, 5, []]. Note that although employee 3 is also a subordinate of employee 1, the relationship is not direct.

    Now given the employee information of a company, and an employee id, you need to return the total importance value of this employee and all his subordinates.

    Example 1:
    Input: [[1, 5, [2, 3]], [2, 3, []], [3, 3, []]], 1
    Output: 11
    Explanation:
    Employee 1 has importance value 5, and he has two direct subordinates: employee 2 and employee 3. They both have importance value 3. So the total importance value of employee 1 is 5 + 3 + 3 = 11.
    Note:
    One employee has at most one direct leader and may have several subordinates.
    The maximum number of employees won't exceed 2000.

    Companies: Uber

    Related Topics: DFS, BFS, Hash Table

    Similar Questions: Nested List Weight Sum
 */

/*
// Employee info
class Employee {
    // It's the unique id of each node;
    // unique id of this employee
    public int id;
    // the importance value of this employee
    public int importance;
    // the id of direct subordinates
    public List<Integer> subordinates;
};
*/


//Solution1: My dfs O(N)
class Solution {
    Map<Integer, Integer> map = new HashMap();
    public int getImportance(List<Employee> employees, int id) {
        if (map.containsKey(id))
            return map.get(id);
        int res = 0;
        for (Employee employee : employees) {
            if (employee.id == id) {
                res += employee.importance;
                for (Integer sub : employee.subordinates) {
                    res += getImportance(employees, sub);
                }
            }
        }
        map.put(id, res);
        return res;
    }
}

//Solution2: dfs2, O(N)
class Solution {
    Map<Integer, Employee> map;
    public int getImportance(List<Employee> employees, int id) {
        map = new HashMap();
        for (Employee e : employees)
            map.put(e.id, e);
        return dfs(id);
    }

    private int dfs(int id) {
        Employee employee = map.get(id);
        int res = employee.importance;
        for (Integer subid : employee.subordinates) {
            res += dfs(subid);
        }
        return res;
    }
}

//Solution3: BFS O(N)
class Solution {
    public int getImportance(List<Employee> employees, int id) {
        int total = 0;
        Map<Integer, Employee> map = new HashMap<>();
        for (Employee employee : employees) {
            map.put(employee.id, employee);
        }
        Queue<Employee> queue = new LinkedList<>();
        queue.offer(map.get(id));
        while (!queue.isEmpty()) {
            Employee current = queue.poll();
            total += current.importance;
            for (int subordinate : current.subordinates) {
                queue.offer(map.get(subordinate));
            }
        }
        return total;
    }
}