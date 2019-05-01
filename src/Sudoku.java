import java.io.*;

class Sudoku {

    static int[][] data = new int[9][9];
    private static int currentLine = 0;

    /**
     * Reads comma-separated integers from a file into the data array
     * @param fileName Name of the file to read
     */
    static void readData(String fileName) {

        String line;

        currentLine = 0;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                for (int i = 0; i < parts.length; i++) {
                    data[currentLine][i] = Integer.parseInt(parts[i]);
                }
                currentLine++;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println( "Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println( "Error reading file '" + fileName + "'");
            // ex.printStackTrace();
        }

    }

    /**
     * Creates all the threads and executes them to check for validity
     * @return true if sudoku is valid and false otherwise
     */
    static boolean isValid() {

        Validator rowThread = new Validator(threadAction.ROW);
        rowThread.start();
        Validator colThread = new Validator(threadAction.COLUMN);
        colThread.start();

        Validator[] boxThreads = new Validator[9];

        int[] possibleVals = {0, 3, 6};
        int row = 0, col = 0;

        for (int i =0; i < 9; i++) {
            boxThreads[i] = new Validator(threadAction.BOX, row, col);
            boxThreads[i].start();

            if (i != 8) {
                row = possibleVals[(i + 1) / 3];
                col = possibleVals[(i + 1) % 3];
            }
        }

        boolean boxesValid = true;
        for (int i = 0; i < 9; i++) {
            if (!boxThreads[i].valid) {
                boxesValid = false;
                break;
            }
        }

        if (boxesValid && rowThread.valid && colThread.valid) {
            return true;
        }

        return false;
    }

    /**
     * Prints the sudoku in a human-readable format
     */
    static void printData() {
        System.out.println("The following sudoku has been loaded:\n");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(data[i][j] + "\t");
                if ((j+1)%3 == 0 && j != 8) {
                    System.out.print("|\t");
                }
            }
            System.out.print("\n");
            if ((i+1)%3 == 0 && i != 8) {
                System.out.println("-----------------------------------------");
            }
        }
    }
}
