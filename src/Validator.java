class Validator implements Runnable {
    private Thread t;
    private String threadName;
    private threadAction action;

    boolean valid = true;
    private int row, col;

    // Colors
    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_CREATE = "\u001B[36m";
    private static final String COLOR_RUN = "\u001B[34m";
    private static final String COLOR_START = "\u001B[32m";
    private static final String COLOR_INVALID = "\u001B[31m";
    private static final String COLOR_EXIT = "\u001B[33m";

    Validator( threadAction action) {
        threadName = action + " ";
        System.out.printf("%-25s : %-25s%n", COLOR_CREATE + "[CREATE]" + COLOR_RESET, threadName);
        this.action = action;
    }

    Validator( threadAction action, int row, int col) {
        threadName = action + " on row: " + row + " column: " + col;
        System.out.printf("%-25s : %-25s%n", COLOR_CREATE + "[CREATE]" + COLOR_RESET, threadName);
        this.action = action;
        this.row = row;
        this.col = col;
    }

    public void run() {
        System.out.printf("%-25s : %-25s%n", COLOR_RUN + "[RUN]" + COLOR_RESET, threadName);
        int[] values = new int[9];

        if (action == threadAction.ROW || action == threadAction.COLUMN) {
            for (int i = 0; i < 9; i++) {
                if (!valid) {
                    break;
                }
                values = new int[9];
                for (int j = 0; j < 9; j++) {
                    if (action == threadAction.ROW  && values[Sudoku.data[i][j] - 1] == 1) {
                        // Two of the same number, not valid
                        valid = false;
                        System.out.printf("%-25s : %-25s%n", COLOR_INVALID + "[INVALID]" + COLOR_RESET, threadName);
                        break;
                    } else if (action == threadAction.COLUMN && values[Sudoku.data[j][i] - 1] == 1) {
                        // Two of the same number, not valid
                        valid = false;
                        System.out.printf("%-25s : %-25s%n", COLOR_INVALID + "[INVALID]" + COLOR_RESET, threadName);
                        break;
                    } else {
                        if (action == threadAction.ROW) {
                            values[Sudoku.data[i][j] - 1] = 1;
                        } else {
                            values[Sudoku.data[j][i] - 1] = 1;
                        }
                    }
                }
            }
        } else {
            for (int i = row; i < row+3; i++) {
                if (!valid) {
                    break;
                }
                for (int j = col; j < col+3; j++) {
                    if (values[Sudoku.data[i][j] - 1] == 1) {
                        // Two of the same number, not valid
                        valid = false;
                        System.out.printf("%-25s : %-25s%n", COLOR_INVALID + "[INVALID]" + COLOR_RESET, threadName);
                        break;
                    } else {
                        values[Sudoku.data[i][j] - 1] = 1;
                    }
                }
            }
        }

        System.out.printf("%-25s : %-25s%n", COLOR_EXIT + "[EXIT]" + COLOR_RESET, threadName);
    }

    void start () {
        System.out.printf("%-25s : %-25s%n", COLOR_START + "[START]" + COLOR_RESET, threadName);

        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
