// Peak finder algorithms for 2D array
// by Yusuf Özkan

import java.util.Random;

public class PeakFinder2D 
{
    int[][] a;

    static int ncol = 0;
    static int nrow = 0;
    static int gpeak = 0; // static greedy peak point

    // Constructor of PeakFinder2D that create random array for given numbers of row and col
    public PeakFinder2D(int nrow, int ncol) 
    {
        a = new int[nrow][ncol];
        Random r = new Random();
        PeakFinder2D.nrow = nrow;
        PeakFinder2D.ncol = ncol;

        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                a[i][j] = r.nextInt(10) ; 
            }
        }
    }

    // Function that find peak with greedy algorithm
    public int greedyAlg() {
        int peak = a[0][0];

        int i = 0;
        int j = 0;

        while (true) 
        {
            if (i > 0 && a[i - 1][j] > a[i][j]) 
            {
                i -= 1;
            } 
            else if (i < nrow - 1 && a[i + 1][j] > a[i][j]) 
            {
                i += 1;
            } 
            else if (j > 0 && a[i][j - 1] > a[i][j]) 
            {
                j -= 1;
            } 
            else if (j < ncol - 1 && a[i][j + 1] > a[i][j]) 
            {
                j += 1;
            } 
            else 
            {
                peak = a[i][j];
                break;
            }
        }
        return peak;
    }

    // Function for find max index in the column
    public int findMaxOnCol(int col) 
    {
        int imax = 0;
        for (int i = 0; i < nrow; i++) 
        {
            if (a[i][col] > a[imax][col]) 
            {
                imax = i;
            }
        }
        return imax;
    }

    // Function for find max index in the row
    public int findMaxOnRow(int row) 
    {
        int imax = 0;
        for (int i = 0; i < ncol; i++) 
        {
            if (a[row][i] > a[row][imax]) 
            {
                imax = i;
            }
        }
        return imax;
    }

    // Function that find peak (Divide and conquer 1. version)
    public int divideAndConquer1(int startcol, int endcol) 
    {
        int midcol = (startcol + endcol) / 2; 
        int imax = findMaxOnCol(midcol); // number of rows: m

        // Boundry conditions 

        if( (midcol == startcol+1) && (a[imax][midcol] <= a[imax][startcol]) ) // If midcol is just after the startcol (Boundry condition)
        {
            imax = findMaxOnCol(startcol) ;
            return a[imax][startcol];

        }
        else if( (midcol == endcol-1) && (a[imax][midcol] <= a[imax][endcol]) ) // If midcol is just after the endcol (Boundry condition)
        {
            imax = findMaxOnCol(endcol) ;
            return a[imax][endcol];
        }

        if (a[imax][midcol] >= a[imax][midcol + 1] && a[imax][midcol] >= a[imax][midcol - 1]) 
        {   
            return a[imax][midcol];
        }
        if (a[imax][midcol] <= a[imax][midcol + 1])
        {    
            return divideAndConquer1(midcol, endcol);
        }
        if (a[imax][midcol] <= a[imax][midcol - 1])
        {
            return divideAndConquer1(startcol, midcol);
        }

        return -1;
    }

    // Function that find peak in O(n+m) time (Divide and conquer 2. version)
    public int divideAndConquer2(int startrow, int startcol, int endrow, int endcol) 
    {   
        int imax;

        if( (endcol-startcol) < (endrow-startrow)) // Checking for searching direction (on row or on column)
        {
            int midrow = (startrow + endrow) / 2; 
            imax = findMaxOnRow(midrow);

            if( (midrow == startrow+1) && (a[midrow][imax] <= a[startrow][imax]) ) // If midrow is just after the startrow (Boundry condition)
            {
                imax = findMaxOnRow(startrow) ;
                return a[startrow][imax];
    
            }
            else if( (midrow == endrow-1) && (a[midrow][imax] <= a[endrow][imax]) ) // If midrow is just before the endrow (Boundry condition)
            {
                imax = findMaxOnCol(endrow) ;
                return a[endrow][imax];
            }
    
            if (a[midrow][imax] >= a[midrow-1][imax] && a[midrow][imax] >= a[midrow+1][imax]) // Comparisons
            {   
                return a[midrow][imax];
            }
            if (a[midrow][imax] <= a[midrow+1][imax])
            {    
                return divideAndConquer2(midrow,startcol,endrow,endcol); // Recursive
            }
            if (a[midrow][imax] <= a[midrow-1][imax])
            {
                return divideAndConquer2(startrow,startcol,midrow,endcol);
            }
        }
        else if( (endcol-startcol) >= (endrow-startrow))
        {
            int midcol = (startcol + endcol) / 2;
            imax = findMaxOnCol(midcol);

            if( (midcol == startcol+1) && (a[imax][midcol] <= a[imax][startcol]) ) // Boundry conditions 
            {
                imax = findMaxOnCol(startcol) ;
                return a[imax][startcol];
            }
            else if( (midcol == endcol-1) && (a[imax][midcol] <= a[imax][endcol]) )
            {
                imax = findMaxOnCol(endcol) ;
                return a[imax][endcol];
            }

            if (a[imax][midcol] >= a[imax][midcol + 1] && a[imax][midcol] >= a[imax][midcol - 1]) 
            {   
                return a[imax][midcol];
            }
            if (a[imax][midcol] <= a[imax][midcol + 1])
            {    
                return divideAndConquer2(startrow,midcol,endrow, endcol); // Recursive
            }
            if (a[imax][midcol] <= a[imax][midcol - 1])  
            {
                return divideAndConquer2(startrow,midcol,endrow, midcol);
            }
        }

        return -1;
    }

    // Prints elements of a[][]
    void printArray() 
    {
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                System.out.printf("%2d",a[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    // Test Functions
    
    static void testGreedyAlg() 
    {
        PeakFinder2D greedy = new PeakFinder2D(4, 4);

        gpeak = greedy.greedyAlg();

        System.out.println("\n");
        for(int i=0 ; i<20 ; i++)
            if(i == 10)
                System.out.print("GREEDY");
            else
                System.out.print("-");

        System.out.print("\n");
        greedy.printArray();
        System.out.print("\n->");
        System.out.print(gpeak);
    }
    static void testDivideAndConq1()
    {
        PeakFinder2D dc = new PeakFinder2D(6, 6);

        System.out.println("\n");
        for(int i=0 ; i<20 ; i++)
            if(i == 10)
                System.out.print("DIVIDE AND CONQ1");
            else
                System.out.print("-");
            
        System.out.print("\n");
        dc.printArray();
        System.out.print("\n->");
        System.out.print(dc.divideAndConquer1(0, ncol-1)); 

    }
    static void testDivideAndConq2()
    {
        PeakFinder2D dc2 = new PeakFinder2D(6, 6);

        System.out.println("\n");
        for(int i=0 ; i<20 ; i++)
            if(i == 10)
                System.out.print("DIVIDE AND CONQ2");
            else
                System.out.print("-");
            
        System.out.print("\n");
        dc2.printArray();
        System.out.print("\n->");
        System.out.print(dc2.divideAndConquer2(0, 0, nrow-1, ncol-1)); 
    }
    public static void main(String args[])
    {
        testGreedyAlg();
        testDivideAndConq1();
        testDivideAndConq2();
    }
}
