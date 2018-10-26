import java.util.Random;

public class test
{

    static private Random r = new Random();
    public static void main(String[] args)
    {
        try
        {
            int[] vals = new int[100000];
            for ( int i = 0; i < vals.length; i++ )
            {
                vals[i] = r.nextInt(1000);
            }

            long startTime = System.nanoTime();
            quickSort( vals, 0, vals.length - 1, false, true );
            long endTime = System.nanoTime();
            long netTime = endTime - startTime;
            System.out.printf("Time elapsed (ms): %.2f\n",
                    (double) netTime / 1000000);
            /*for (int i=0; i<1000000; i++) {
                System.out.println(vals[i]);
            }*/

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }


    static void swap(int[] array, int left, int right)
    {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }


    static void insertionSort(int[] array, int start, int end)
    {
        int j, temp;
        for ( int i = start; i <= end; i++ )
        {
            temp = array[i];
            j = i;
            while ( j > 0 && array[j - 1] > temp )
            {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = temp;
        }
    }


    static void quickSort(int[] array, int first, int last,
                           boolean randomPivot, boolean insertionSort)
    {
        int left = first;
        int right = last;
        int pivot;

        if ( insertionSort )
        {
            if ( last - first <= 1000 )
            {
                insertionSort( array, first, last );
                return;
            }
        }

        if ( randomPivot )
        {
            pivot = array[first + r.nextInt( last - first )];
        }
        else
        {
            pivot = array[(last + first) / 2];
        }

        while ( left <= right )
        {
            while ( array[left] < pivot )
                left++;
            while ( array[right] > pivot )
                right--;
            if ( left <= right )
            {
                if ( left != right )
                    swap( array, left, right );
                left++;
                right--;
            }
        }
        if ( first < right )
            quickSort( array, first, right, randomPivot, insertionSort );
        if ( left < last )
            quickSort( array, left, last, randomPivot, insertionSort );
    }
}