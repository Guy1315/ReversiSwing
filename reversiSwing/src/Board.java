
import java.util.ArrayList;

public class Board{
    public final int Black = 1; 	// Black disks
    public final int White = -1; 	// White disks
    public final int Default = 0;	// Empty cells
    public int Black_disks = 2;		// Black score counter, initialized as 2 because starting board has 2 black disks
    public int White_disks = 2;		// White score counter, initialized as 2 because starting board has 2 white disks

    /* Variable containing who played last; whose turn resulted in this board state,
     * even a new Board has lastColorPlayed value; which dictates who will play first */
    private int lastPlayer;

    private Move lastMove;

    /* Table that holds values for each board cell, 1 for Black disks, -1 for White disks,
     * 0 for EMPTY cells (later on 2 for player available moves)*/
    private int [][] gameBoard;

    public Board() {
        gameBoard= new int[8][8]; // 8x8 table for an 8x8 grid board
        lastPlayer=White; // Initialized with O because Black always goes first, by the rules
        lastMove = new Move();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                // Creating an empty board
                gameBoard[i][j] = Default;
            }
        // Placing starting board disks
        gameBoard[3][3] = White;
        gameBoard[3][4] = Black;
        gameBoard[4][3] = Black;
        gameBoard[4][4] = White;
    }

    // Copy constructor
    public Board(Board board)
    {
        lastMove = board.lastMove;
        lastPlayer = board.lastPlayer;
        gameBoard = new int[8][8];
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                gameBoard[i][j] = board.gameBoard[i][j];
            }
        }
        White_disks = board.White_disks;
        Black_disks = board.Black_disks;
    }

    // make a move, places 1 or -1 for black and white
    public void makeMove(int i, int j, int player)
    {
        gameBoard[i][j] = player;
        lastMove = new Move(i, j);
        lastPlayer = player;

        if (player == Black) {
            Black_disks++; 		// increases number of black disks by one when black makes a move
        }else {
            White_disks++;		// increases number of white disks by one when white makes a move
        }

        /* turns flanked disks to opposite color
         * by going over each direction from placed disk
         */

        int Row , Col;

        // opponent of player which is currently playing
        int opponent = ((player == 1) ? -1 : 1);

        // search up
        Row = i - 1;
        Col = j;

        while(Row>0 && gameBoard[Row][Col] == opponent)
        {

            if(gameBoard[Row-1][Col] == player)
            {
                while(Row>0 && gameBoard[Row][Col] == opponent)
                {
                    gameBoard[Row][Col] = player;
                    Row++;
                    if (player == Black)
                    {
                        Black_disks++;
                        White_disks--;
                    }else {
                        White_disks++;
                        Black_disks--;
                    }
                }
            }

            Row--;
        }

        // search down
        Row = i + 1;
        Col = j;

        while(Row<7 && gameBoard[Row][Col] == opponent)
        {

            if(gameBoard[Row+1][Col] == player)
            {
                while(Row<7 && gameBoard[Row][Col] == opponent)
                {
                    gameBoard[Row][Col] = player;
                    Row--;
                    if (player == Black)
                    {
                        Black_disks++;
                        White_disks--;
                    }else {
                        White_disks++;
                        Black_disks--;
                    }
                }
            }

            Row++;
        }

        // search left
        Row = i;
        Col = j - 1;

        while(Col>0 && gameBoard[Row][Col] == opponent)
        {

            if(gameBoard[Row][Col-1] == player)
            {
                while(Col>0 && gameBoard[Row][Col] == opponent)
                {
                    gameBoard[Row][Col] = player;
                    Col++;
                    if (player == Black)
                    {
                        Black_disks++;
                        White_disks--;
                    }else {
                        White_disks++;
                        Black_disks--;
                    }
                }
            }

            Col--;
        }

        // search right
        Row = i;
        Col = j + 1;

        while(Col<7 && gameBoard[Row][Col] == opponent)
        {

            if(gameBoard[Row][Col+1] == player)
            {
                while(Col<7 && gameBoard[Row][Col] == opponent)
                {
                    gameBoard[Row][Col] = player;
                    Col--;
                    if (player == Black)
                    {
                        Black_disks++;
                        White_disks--;
                    }else {
                        White_disks++;
                        Black_disks--;
                    }
                }
            }

            Col++;
        }

        // search up left
        Row = i - 1;
        Col = j - 1;

        while(Row>0 && Col>0 && gameBoard[Row][Col] == opponent)
        {

            if(gameBoard[Row-1][Col-1] == player)
            {
                while(Row>0 && Col>0 && gameBoard[Row][Col] == opponent)
                {
                    gameBoard[Row][Col] = player;
                    Row++;
                    Col++;
                    if (player == Black)
                    {
                        Black_disks++;
                        White_disks--;
                    }else {
                        White_disks++;
                        Black_disks--;
                    }
                }
            }

            Row--;
            Col--;
        }

        // search up right
        Row = i - 1;
        Col = j + 1;

        while(Row>0 && Col<7 && gameBoard[Row][Col] == opponent)
        {

            if(gameBoard[Row-1][Col+1] == player)
            {
                while(Row>0 && Col<7 && gameBoard[Row][Col] == opponent)
                {
                    gameBoard[Row][Col] = player;
                    Row++;
                    Col--;
                    if (player == Black)
                    {
                        Black_disks++;
                        White_disks--;
                    }else {
                        White_disks++;
                        Black_disks--;
                    }
                }
            }

            Row--;
            Col++;
        }

        // search down left
        Row = i + 1;
        Col = j - 1;

        while(Row<7 && Col>0 && gameBoard[Row][Col] == opponent)
        {

            if(gameBoard[Row+1][Col-1] == player)
            {
                while(Row<7 && Col>0 && gameBoard[Row][Col] == opponent)
                {
                    gameBoard[Row][Col] = player;
                    Row--;
                    Col++;
                    if (player == Black)
                    {
                        Black_disks++;
                        White_disks--;
                    }else {
                        White_disks++;
                        Black_disks--;
                    }
                }
            }

            Row++;
            Col--;
        }

        // search down right
        Row = i + 1;
        Col = j + 1;

        while(Row<7 && Col<7 && gameBoard[Row][Col] == opponent)
        {

            if(gameBoard[Row+1][Col+1] == player)
            {
                while(Row<7 && Col<7 && gameBoard[Row][Col] == opponent)
                {
                    gameBoard[Row][Col] = player;
                    Row--;
                    Col--;
                    if (player == Black) {
                        Black_disks++;
                        White_disks--;
                    }else {
                        White_disks++;
                        Black_disks--;
                    }
                }
            }

            Row++;
            Col++;
        }

    }

    //Checks if a move is valid
    public boolean isValidMove(int i, int j, int player)
    {
        if ((i < 0) || (j < 0 ) || (i > 7) || (j > 7)) return false; // If move is out of bounds
        if(gameBoard[i][j] != Default) return false; // If board cell is not empty

         /* Searches for required disk that will trap opponent disks
            by traversing to each direction starting from placed disk
          */

        // flanked variable counts trapped opponent disks between allied ones for each direction
        int row , col , flanked;
        int opponent;
        if (player == 1) opponent = -1;
        else opponent = 1;

        // move up
        row = i - 1;
        col = j;
        flanked = 0;
        while(row>0 && gameBoard[row][col] == opponent)
        {
            row--;
            flanked++;
        }
        if(row>=0 && gameBoard[row][col] == player && flanked>0) return true;


        // move down
        row = i + 1;
        col = j;
        flanked = 0;
        while(row<7 && gameBoard[row][col] == opponent)
        {
            row++;
            flanked++;
        }
        if(row<=7 && gameBoard[row][col] == player && flanked>0) return true;

        // move left
        row = i;
        col = j - 1;
        flanked = 0;
        while(col>0 && gameBoard[row][col] == opponent)
        {
            col--;
            flanked++;
        }
        if(col>=0 && gameBoard[row][col] == player && flanked>0) return true;

        // move right
        row = i;
        col = j + 1;
        flanked = 0;
        while(col<7 && gameBoard[row][col] == opponent)
        {
            col++;
            flanked++;
        }
        if(col<=7 && gameBoard[row][col] == player && flanked>0) return true;

        // move up left
        row = i - 1;
        col = j - 1;
        flanked = 0;
        while(row>0 && col>0 && gameBoard[row][col] == opponent)
        {
            row--;
            col--;
            flanked++;
        }
        if(row>=0 && col>=0 && gameBoard[row][col] == player && flanked>0) return true;

        // move up right
        row = i - 1;
        col = j + 1;
        flanked = 0;
        while(row>0 && col<7 && gameBoard[row][col] == opponent)
        {
            row--;
            col++;
            flanked++;
        }
        if(row>=0 && col<=7 && gameBoard[row][col] == player && flanked>0) return true;

        // move down left
        row = i + 1;
        col = j - 1;
        flanked = 0;
        while(row<7 && col>0 && gameBoard[row][col] == opponent)
        {
            row++;
            col--;
            flanked++;
        }
        if(row<=7 && col>=0 && gameBoard[row][col] == player && flanked>0) return true;

        // move down right
        row = i + 1;
        col = j + 1;
        flanked = 0;
        while(row<7 && col<7 && gameBoard[row][col] == opponent)
        {
            row++;
            col++;
            flanked++;
        }
        if(row<=7 && col<=7 && gameBoard[row][col] == player && flanked>0) return true;

        // when there are no available moves
        return false;
    }


    /*The heuristic we use in MiniMax to evaluate the weight of
      our current state for each color depending on the tree level
     */
    public int evaluate(int color)
    {
        int sum = 0;
        int opponent;
        if (color == Black) opponent = White;
        else opponent = Black;

        // "Killer move" cases

        //when there are only black disks on the board
        if(color == Black && White_disks == 0) {
            sum += 100;
        }
        //when there are only white disks on the board
        if(color == White && Black_disks == 0) {
            sum += 100;
        }

        for(int row=0; row<8; row++)
        {
            for(int col=0; col<8; col++)
            {
                if(this.gameBoard[row][col] == color)
                {
                    if((row == 0 || row==7) && (col == 0 || col == 7))
                    {
                        sum += 15;    // State is appointed an additional weight of 15 due to corner location
                    }
                    else if ((row == 0 || row==7) || (col == 0 || col == 7))
                    {
                        sum += 5;    // State is appointed an additional weight of 5 due to edge location
                    }
                    else
                    {
                        sum +=1;    // State is appointed an additional weight of 1 due to insignificant location
                    }
                }
                else if (this.gameBoard[row][col] == opponent) {
                    if((row == 0 || row==7) && (col == 0 || col == 7))
                    {
                        sum -= 15;    // State is decreased by a weight of 15 due to corner location of opponent
                    }
                    else if ((row == 0 || row==7) || (col == 0 || col == 7))
                    {
                        sum -= 5;    // State is decreased by a weight of 5 due to edge location of opponent
                    }
                    else
                    {
                        sum -=1;    // State is decreased by a weight of 1 due to insignificant location of opponent
                    }
                }
            }
        }
        return sum;
    }


    /* Generates the boards of the state,
     * any available move that can be potentially played results to a board
     */
    public ArrayList<Board> getBoards(int color)
    {
        ArrayList<Board> posBoards = new ArrayList<Board>();
        for(int row=0; row<8; row++)
        {
            for(int col=0; col<8; col++)
            {
                if(isValidMove(row, col, color))
                {
                    Board posBoard = new Board(this);
                    posBoard.makeMove(row, col, color);
                    posBoards.add(posBoard);
                }
            }
        }
        return posBoards;
    }

    //search the board for moves the player can make and keep them in an arrayList
    public ArrayList<int[]> possibleMoves(int color)
    {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[] temp;
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                if (isValidMove(i, j, color))
                {
                    temp = new int[2];
                    temp[0] = i;
                    temp[1] = j;
                    moves.add(temp);
                }
            }
        }
        return moves;
    }
    //check whether the player can make moves
    public boolean canPlay(int player)
    {
        if (possibleMoves(player).isEmpty())
        {
            return false;
        }
        return true;
    }
    public Move getLastMove()
    {
        return lastMove;
    }

    public void setLastPlayer(int lastPlayer)
    {
        this.lastPlayer = lastPlayer;
    }

    public int getLastPlayer(){
        return lastPlayer;
    }

    public int[][] getGameBoard()
    {
        return gameBoard;
    }

    public int getWhiteScore()
    {
        return White_disks;
    }

    public int getBlackScore()
    {
        return Black_disks;
    }

    /* Method isFullBoard checks whether if the board is full or not */
    public boolean isFullBoard()
    {
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                if (gameBoard[i][j] == Default)
                {
                    return false;
                }
            }
        }
        return true;
    }


}

