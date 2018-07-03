// Solution1: open address hashmap
public class HashEntry {
      private int key;
      private int value;
 
      HashEntry(int key, int value) {
            this.key = key;
            this.value = value;
      }     
 
      public int getKey() {
            return key;
      }
 
      public int getValue() {
            return value;
      }
}

public class HashMap {
      private final static int TABLE_SIZE = 128;
 
      HashEntry[] table;
 
      HashMap() {
            table = new HashEntry[TABLE_SIZE];
            for (int i = 0; i < TABLE_SIZE; i++) {
                  table[i] = null;
            }
      }
 
      public int get(int key) {
            int hash = (key % TABLE_SIZE);
            
            //if find collision
            while (table[hash] != null && table[hash].getKey() != key) {
                  hash = (hash + 1) % TABLE_SIZE;
            }

            return table[hash] == null ? -1 : table[hash].getValue();
      }
 
      public void put(int key, int value) {
            int hash = (key % TABLE_SIZE);
            while (table[hash] != null && table[hash].getKey() != key) {
                  hash = (hash + 1) % TABLE_SIZE;// if find collison rehash
            }
            table[hash] = new HashEntry(key, value);
      }
}

// Solution2: chaining
public class HashEntry {
      private int key;
      private int value;

      HashEntry(int key, int value) {
            this.key = key;
            this.value = value;
      }

      public int getKey() {
            return key;
      }

      public int getValue() {
            return value;
      }

      public void setValue(value) {
            this.value = value;
      }
}

public class HashMap {
      private final static int TABLE_SIZE = 128;

      List<HashEntry>[] table;

      HashMap() {
            table = new HashEntry[TABLE_SIZE];
            for (int i = 0; i < TABLE_SIZE; i++) {
                  table[i] = new LinkedList();
            }
      }

      public int get(int key) {
            int hash = (key % TABLE_SIZE);

            if (table[hash] == null)
                  return -1;
            for (HashEntry entry : table[hash]) {
                  if (entry.getKey() == key) {
                        return entry.getValue();
                  }
            }

            return -1;
      }

      public void put(int key, int value) {
            int hash = (key % TABLE_SIZE);
            table[hash].add(new HashEnytry(key, value));
            if (table[hash] != null) {
                  List<HashEntry> list = table[hash];
                  for (HashEntry entry : list) {
                        if (entry.getKey() == key) {
                              entry.setValue(value);
                        }
                  }
            } else {
                  table[hash].add(new HashEntry(key, value));
            }

      }
}