package top.coos.tool.array;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class ArrayHelper {

    public static <T extends Comparable<? super T>> void sortACS(T[] list) {

        List<T> objects = new ArrayList<T>();
        for (T one : list) {
            objects.add(one);
        }
        sortACS(objects);
        list = objects.toArray(list);
    }

    public static <T extends Comparable<? super T>> void sortDESC(T[] list) {

        List<T> objects = new ArrayList<T>();
        for (T one : list) {
            objects.add(one);
        }
        sortDESC(objects);
        list = objects.toArray(list);
    }

    public static <T extends Comparable<? super T>> void sortACS(List<T> list) {
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                if (o1 instanceof File && o2 instanceof File) {
                    File file1 = (File) o1;
                    File file2 = (File) o2;
                    return file1.getName().compareTo(file2.getName());
                }
                return o1.compareTo(o2);
            }

        });
    }

    public static <T extends Comparable<? super T>> void sortDESC(List<T> list) {
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                if (o1 instanceof File && o2 instanceof File) {
                    File file1 = (File) o1;
                    File file2 = (File) o2;
                    return file2.getName().compareTo(file1.getName());
                }
                return o2.compareTo(o1);
            }

        });
    }

    public static boolean contains(Object[] array, Object object) {
        return indexOf(array, object) > -1;
    }

    public static int indexOf(Object[] array, Object object) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(object))
                return i;
        }
        return -1;
    }

    /*
     * public static Object[] clone(Class elementClass, Object[] array) {
     * Object[] result = (Object[]) Array.newInstance( elementClass,
     * array.length ); System.arraycopy(array, 0, result, 0, array.length);
     * return result; }
     */

    public static String[] toStringArray(Object[] objects) {
        int length = objects.length;
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            result[i] = objects[i].toString();
        }
        return result;
    }

    public static String[] fillArray(String value, int length) {
        String[] result = new String[length];
        Arrays.fill(result, value);
        return result;
    }

    public static int[] fillArray(int value, int length) {
        int[] result = new int[length];
        Arrays.fill(result, value);
        return result;
    }

    public static String[] toStringArray(Collection coll) {
        return (String[]) coll.toArray(new String[coll.size()]);
    }

    public static String[][] to2DStringArray(Collection coll) {
        return (String[][]) coll.toArray(new String[coll.size()][]);
    }

    public static int[][] to2DIntArray(Collection coll) {
        return (int[][]) coll.toArray(new int[coll.size()][]);
    }

    public static int[] toIntArray(Collection coll) {
        Iterator iter = coll.iterator();
        int[] arr = new int[coll.size()];
        int i = 0;
        while (iter.hasNext()) {
            arr[i++] = (Integer) iter.next();
        }
        return arr;
    }

    public static boolean[] toBooleanArray(Collection coll) {
        Iterator iter = coll.iterator();
        boolean[] arr = new boolean[coll.size()];
        int i = 0;
        while (iter.hasNext()) {
            arr[i++] = (Boolean) iter.next();
        }
        return arr;
    }

    public static Object[] typecast(Object[] array, Object[] to) {
        return java.util.Arrays.asList(array).toArray(to);
    }

    // Arrays.asList doesn't do primitive arrays
    public static List toList(Object array) {
        if (array instanceof Object[])
            return Arrays.asList((Object[]) array); // faster?
        int size = Array.getLength(array);
        ArrayList list = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            list.add(Array.get(array, i));
        }
        return list;
    }

    public static String[] slice(String[] strings, int begin, int length) {
        String[] result = new String[length];
        System.arraycopy(strings, begin, result, 0, length);
        return result;
    }

    public static Object[] slice(Object[] objects, int begin, int length) {
        Object[] result = new Object[length];
        System.arraycopy(objects, begin, result, 0, length);
        return result;
    }

    public static List toList(Iterator iter) {
        List list = new ArrayList();
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }

    public static String[] join(String[] x, String[] y) {
        String[] result = new String[x.length + y.length];
        System.arraycopy(x, 0, result, 0, x.length);
        System.arraycopy(y, 0, result, x.length, y.length);
        return result;
    }

    public static String[] join(String[] x, String[] y, boolean[] use) {
        String[] result = new String[x.length + countTrue(use)];
        System.arraycopy(x, 0, result, 0, x.length);
        int k = x.length;
        for (int i = 0; i < y.length; i++) {
            if (use[i]) {
                result[k++] = y[i];
            }
        }
        return result;
    }

    public static int[] join(int[] x, int[] y) {
        int[] result = new int[x.length + y.length];
        System.arraycopy(x, 0, result, 0, x.length);
        System.arraycopy(y, 0, result, x.length, y.length);
        return result;
    }

    public static <T> T[] join(T[] x, T... y) {
        T[] result = (T[]) Array.newInstance(x.getClass().getComponentType(), x.length + y.length);
        System.arraycopy(x, 0, result, 0, x.length);
        System.arraycopy(y, 0, result, x.length, y.length);
        return result;
    }

    public static final boolean[] TRUE = { true };

    public static final boolean[] FALSE = { false };

    private ArrayHelper() {
    }

    public static String toString(Object[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1)
                sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public static boolean isAllNegative(int[] array) {
        for (int anArray : array) {
            if (anArray >= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllTrue(boolean[] array) {
        for (boolean anArray : array) {
            if (!anArray) {
                return false;
            }
        }
        return true;
    }

    public static int countTrue(boolean[] array) {
        int result = 0;
        for (boolean anArray : array) {
            if (anArray) {
                result++;
            }
        }
        return result;
    }

    /*
     * public static int countFalse(boolean[] array) { int result=0; for ( int
     * i=0; i<array.length; i++ ) { if ( !array[i] ) result++; } return result;
     * }
     */

    public static boolean isAllFalse(boolean[] array) {
        for (boolean anArray : array) {
            if (anArray) {
                return false;
            }
        }
        return true;
    }

    public static void addAll(Collection collection, Object[] array) {
        collection.addAll(Arrays.asList(array));
    }

    public static final String[] EMPTY_STRING_ARRAY = {};

    public static final int[] EMPTY_INT_ARRAY = {};

    public static final boolean[] EMPTY_BOOLEAN_ARRAY = {};

    public static final Class[] EMPTY_CLASS_ARRAY = {};

    public static final Object[] EMPTY_OBJECT_ARRAY = {};

    public static final byte[] EMPTY_BYTE_ARRAY = {};

    public static int[] getBatchSizes(int maxBatchSize) {
        int batchSize = maxBatchSize;
        int n = 1;
        while (batchSize > 1) {
            batchSize = getNextBatchSize(batchSize);
            n++;
        }
        int[] result = new int[n];
        batchSize = maxBatchSize;
        for (int i = 0; i < n; i++) {
            result[i] = batchSize;
            batchSize = getNextBatchSize(batchSize);
        }
        return result;
    }

    private static int getNextBatchSize(int batchSize) {
        if (batchSize <= 10) {
            return batchSize - 1; // allow 9,8,7,6,5,4,3,2,1
        } else if (batchSize / 2 < 10) {
            return 10;
        } else {
            return batchSize / 2;
        }
    }

    private static int SEED = 23;

    private static int PRIME_NUMER = 37;

    /**
     * calculate the array hash (only the first level)
     */
    public static int hash(Object[] array) {
        // int length = array.length;
        int seed = SEED;
        for (Object anArray : array) {
            seed = hash(seed, anArray == null ? 0 : anArray.hashCode());
        }
        return seed;
    }

    /**
     * calculate the array hash (only the first level)
     */
    public static int hash(char[] array) {
        // int length = array.length;
        int seed = SEED;
        for (char anArray : array) {
            seed = hash(seed, anArray);
        }
        return seed;
    }

    /**
     * calculate the array hash (only the first level)
     */
    public static int hash(byte[] bytes) {
        // int length = bytes.length;
        int seed = SEED;
        for (byte aByte : bytes) {
            seed = hash(seed, aByte);
        }
        return seed;
    }

    private static int hash(int seed, int i) {
        return PRIME_NUMER * seed + i;
    }

    /**
     * Compare 2 arrays only at the first level
     */
    public static boolean isEquals(Object[] o1, Object[] o2) {
        if (o1 == o2)
            return true;
        if (o1 == null || o2 == null)
            return false;
        int length = o1.length;
        if (length != o2.length)
            return false;
        for (int index = 0; index < length; index++) {
            if (!o1[index].equals(o2[index]))
                return false;
        }
        return true;
    }

    /**
     * Compare 2 arrays only at the first level
     */
    public static boolean isEquals(char[] o1, char[] o2) {
        if (o1 == o2)
            return true;
        if (o1 == null || o2 == null)
            return false;
        int length = o1.length;
        if (length != o2.length)
            return false;
        for (int index = 0; index < length; index++) {
            if (!(o1[index] == o2[index]))
                return false;
        }
        return true;
    }

    /**
     * Compare 2 arrays only at the first level
     */
    public static boolean isEquals(byte[] b1, byte[] b2) {
        if (b1 == b2)
            return true;
        if (b1 == null || b2 == null)
            return false;
        int length = b1.length;
        if (length != b2.length)
            return false;
        for (int index = 0; index < length; index++) {
            if (!(b1[index] == b2[index]))
                return false;
        }
        return true;
    }

    public static Serializable[] extractNonNull(Serializable[] array) throws Exception {
        final int nonNullCount = countNonNull(array);
        final Serializable[] result = new Serializable[nonNullCount];
        int i = 0;
        for (Serializable element : array) {
            if (element != null) {
                result[i++] = element;
            }
        }
        if (i != nonNullCount) {
            throw new Exception("Number of non-null elements varied between iterations");
        }
        return result;
    }

    public static int countNonNull(Serializable[] array) {
        int i = 0;
        for (Serializable element : array) {
            if (element != null) {
                i++;
            }
        }
        return i;
    }

    public static String[] reverse(String[] source) {
        final int length = source.length;
        final String[] destination = new String[length];
        for (int i = 0; i < length; i++) {
            final int x = length - i - 1;
            destination[x] = source[i];
        }
        return destination;
    }
}
