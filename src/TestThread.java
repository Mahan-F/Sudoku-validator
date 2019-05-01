public class TestThread {

    public static void main(String args[]) {
        Sudoku.readData("test.txt");
        Sudoku.printData();

        if (Sudoku.isValid()) {
            System.out.println("This sudoku is valid!");
        } else {
            System.out.println("This sudoku is not valid!");
        }
    }
}