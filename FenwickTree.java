public class FenwickTree {
    private int[] tree;
    private int size;

    public FenwickTree(int size) {
        this.size = size;
        this.tree = new int[size + 1];
    }

    public FenwickTree(int[] array) {
        this(array.length);
        for (int i = 0; i < array.length; i++) {
            update(i, array[i]);
        }
    }

    public void update(int index, int delta) {
        index++;
        while (index <= size) {
            tree[index] += delta;
            index += index & -index;
        }
    }

    public int query(int index) {
        index++;
        int sum = 0;
        while (index > 0) {
            sum += tree[index];
            index -= index & -index;
        }
        return sum;
    }

    public int rangeQuery(int left, int right) {
        return query(right) - query(left - 1);
    }

    public void remove(int index, int delta) {
        update(index, -delta);
    }

    public int find(int sum) {
        int index = 0;
        int mask = highestPowerOfTwo(size);

        while (mask != 0) {
            int temp = index + mask;
            if (temp <= size && sum > tree[temp]) {
                sum -= tree[temp];
                index = temp;
            }
            mask >>= 1;
        }

        return index < size ? index : -1;
    }

    private int highestPowerOfTwo(int n) {
        int power = 1;
        while (power * 2 <= n) {
            power *= 2;
        }
        return power;
    }

    public int getSize() {
        return size;
    }

}