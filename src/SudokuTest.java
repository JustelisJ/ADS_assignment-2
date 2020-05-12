import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuTest {
    private Sudoku sudoku;

    @Before
    public void setUp()
    {
        //sudoku = new Sudoku();    //If working with a specific sudoku table
        sudoku = new Sudoku(20);
    }

    @Test
    public void genereteSudoku()
    {
        assertNotNull(sudoku);
    }

    @Test
    public void solveSudoku()
    {
        assertEquals(true, sudoku.solveSudoku());
    }

    @Test
    public void checkRow()
    {
        assertEquals(true, sudoku.checkRow(0, 0));
    }

    @Test
    public void checkColumn()
    {
        assertEquals(true, sudoku.checkColumn(0, 0));
    }

    @Test
    public void checkSquare()
    {
        assertEquals(true, sudoku.checkSquare(0, 0, 0));
    }

    @Test
    public void check()
    {
        assertEquals(false, sudoku.check(0, 0, 0));
    }
}