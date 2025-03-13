import java.util.Random;
import java.util.Scanner;

public class Game {
    private int[][] board;
    private int size;
    private int boxSize;

    public Game(int size) {
        this.size=size;
        this.boxSize=(int)Math.sqrt(size);
        board=new int[size][size];
        generateSudoku();
        removeNumbers();
    }

    private void generateSudoku() {
        for (int i=0;i<size;i++) {
            for (int j=0;j<size;j++) {
                board[i][j]=(i*boxSize+i/boxSize+j)%size+1;
            }
        }
    }

    private void removeNumbers() {
        int count=(size*size)/3;
        Random rand=new Random();
        while (count>0) {
            int row=rand.nextInt(size);
            int col=rand.nextInt(size);
            if (board[row][col]!=0) {
                board[row][col]=0;
                count--;
            }
        }
    }

    public void displayGrid() {
        for (int i=0;i<size;i++) {
            System.out.println("-".repeat(size*4));
            for (int j=0;j<size;j++) {
                System.out.print("|");
                System.out.print(board[i][j]==0?"  ":board[i][j]+" ");
            }
            System.out.println("|");
        }
        System.out.println("-".repeat(size*4));
    }

    private boolean isValid(int row,int col,int num) {
        for (int i=0;i<size;i++) {
            if (board[row][i]==num||board[i][col]==num) return false;
        }
        int startRow=(row/boxSize)*boxSize;
        int startCol=(col/boxSize)*boxSize;
        for (int i=0;i<boxSize;i++) {
            for (int j=0;j<boxSize;j++) {
                if (board[startRow+i][startCol+j]==num) return false;
            }
        }
        return true;
    }

    public void startGame() {
        Scanner scanner=new Scanner(System.in);
        while (true) {
            displayGrid();
            System.out.println("Enter row (1 to "+size+") or 0 to exit:");
            int row=scanner.nextInt()-1;
            if (row==-1) break;
            System.out.println("Enter column (1 to "+size+"):");
            int col=scanner.nextInt()-1;
            System.out.println("Enter number (1 to "+size+"):");
            int num=scanner.nextInt();
            if (board[row][col]==0&&isValid(row,col,num)) {
                board[row][col]=num;
                System.out.println("Inserted successfully!");
            } else {
                System.out.println("Try again.");
            }
            if (isGameComplete()) {
                displayGrid();
                System.out.println("Congratulations!Sudoku completed!");
                break;
            }
        }
        scanner.close();
    }

    private boolean isGameComplete() {
        for (int i=0;i<size;i++) {
            for (int j=0;j<size;j++) {
                if (board[i][j]==0) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter Sudoku size:");
        int size=scanner.nextInt();
        Game game=new Game(size);
        game.startGame();
    }
}
