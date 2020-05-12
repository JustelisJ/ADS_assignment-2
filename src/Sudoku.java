import java.util.ArrayList;

public class Sudoku
{

    //---------------------------------------
    //Sudoku generation code taken from here - https://www.geeksforgeeks.org/program-sudoku-generator/
    //---------------------------------------

       int[] mat[];
    int N; // number of columns/rows.
    int SRN; // square root of N
    int K; // No. Of missing digits

    // Constructor
    Sudoku(int K)
    {
        this.N = 9;         //The sudoku will have 9 rows/columns
        this.K = K;

        // Compute square root of N
        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        mat = new int[N][N];

        fillValues();
    }
    Sudoku()
    {
        this.N = 9;         //The sudoku will have 9 rows/columns
        this.K = 20;

        // Compute square root of N
        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        mat = new int[N][N];

        mat[0] = new int[] {7, 0, 9, 2, 1, 3, 6, 5, 8};
        mat[1] = new int[] {2, 3, 6, 4, 8, 5, 7, 1, 9};
        mat[2] = new int[] {8, 0, 0, 6, 9, 7, 2, 3, 4};
        mat[3] = new int[] {0, 0, 0, 1, 6, 2, 8, 7, 5};
        mat[4] = new int[] {5, 2, 0, 8, 3, 4, 9, 6, 1};
        mat[5] = new int[] {1, 6, 8, 7, 5, 9, 4, 2, 3};
        mat[6] = new int[] {4, 5, 2, 9, 7, 1, 3, 8, 6};
        mat[7] = new int[] {6, 7, 1, 3, 4, 8, 5, 9, 2};
        mat[8] = new int[] {9, 8, 3, 5, 2, 6, 1, 4, 7};
    }

    // Sudoku Generator
    public void fillValues()
    {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, SRN);

        // Remove Randomly K digits to make game
        removeKDigits();
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    void fillDiagonal()
    {

        for (int i = 0; i<N; i=i+SRN)

            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }

    // Returns false if given 3 x 3 block contains num.
    boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    // Fill a 3 x 3 matrix.
    void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));

                mat[row+i][col+j] = num;
            }
        }
    }

    // Random generator
    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    // Check if safe to put in cell
    boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num));
    }

    // check in the row for existence
    boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    boolean fillRemaining(int i, int j)
    {
        //  System.out.println(i+" "+j);
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j==(int)(i/SRN)*SRN)
                j =  j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    // Remove the K no. of digits to
    // complete game
    public void removeKDigits()
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N);

            // System.out.println(cellId);
            // extract coordinates i  and j
            int i = (cellId/N)%9;
            int j = cellId%9;
            if (j != 0)
                j = j - 1;

            // System.out.println(i+" "+j);
            if (mat[i][j] != 0)
            {
                count--;
                mat[i][j] = 0;
            }
        }
    }

    // Print sudoku
    public void printSudoku()
    {
        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++)
                System.out.print(mat[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    // Driver code
    public static void main(String[] args)
    {
        int N = 9, K = 20;
        Sudoku sudoku = new Sudoku(20);
        sudoku.printSudoku();

        if(sudoku.solveSudoku())
            sudoku.printSudoku();
        else
            System.out.println("Unable to solve");
    }



//--------------------------------------------------------------------------------------------------------------------
//My code

    int row, col = -1;
    boolean done = false;

    public boolean solveSudoku()
    {
        //Check if theres any zeros. If there is at least one zero, the method finds its location
        findNextZero();

        //if theres no zeros, then return true
        if(done)
            return true;

        //but if there are zeros, then go through every number that fits instead of the zero
        for(int i = 1; i <= 9; i++)
        {
            if(check(row, col, i))
            {
                mat[row][col] = i;

                if(solveSudoku())
                {
                    return true;
                }
                else
                {
                    mat[row][col] = 0;
                }
            }
        }

        return false;   //Returns false when the sudoku is unsolvable
    }
    private void findNextZero()
    {
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if (mat[i][j] == 0)
                {
                    row = i;
                    col = j;
                    return;
                }
            }
        }
        done = true;
    }

    //The check to make sure the digit doesnt break any of the sudoku rules
    public boolean check(int row, int col, int digit)
    {
        //Check the rows
        if(checkRow(row, digit))
            return false;

        //Check the columns
        if(checkColumn(col, digit))
            return false;

        //Check the boxes
        if(checkSquare(row, col, digit))
            return false;

        return true;
    }

    //Checks if the digits in the row are unique
    public boolean checkRow(int row, int digit)
    {
        for(int i = 0; i < N; i++)
        {
            if(mat[row][i] == digit)
                return true;
        }
        return false;
    }

    //Checks if the digits in the column are unique
    public boolean checkColumn(int column, int digit) {
        for(int i = 0; i < N; i++)
        {
            if(mat[i][column] == digit)
                return true;
        }
        return false;
    }

    //Checks if the digits in the 3x3 square are unique.
    //i - row index, j - column index
    public boolean checkSquare(int i, int j, int digit) {
        //Finds out the square and the top left index
        i = i - (i % 3);
        j = j - (j % 3);

        for(int a = i; a < i + 3; a++)
        {
            for(int b = j; b < j + 3; b++)
            {
                if(mat[a][b] == digit)
                    return true;
            }
        }
        return false;
    }
}
