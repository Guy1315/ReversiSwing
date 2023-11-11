import java.util.ArrayList;
import java.util.Random;

public class Computer
{
    //Variable that holds the maximum depth the MiniMax algorithm will reach for this game
    private int maxDepth;
    //Variable that holds which disk the computer controls, initialized with white
    private int player = -1;

    private int a = Integer.MIN_VALUE; // alpha value used in a-b pruning
    private int b = Integer.MAX_VALUE; // beta value used in a-b pruning

    public Computer()
    {
        maxDepth = 3; //maxDepth initialized at depth 3
    }

    public Computer(int maxDepth, int player)
    {
        this.maxDepth = maxDepth;
        this.player = player;
    }

    public Move MiniMax(Board board)
    {
        return max(new Board(board), 0, a, b, player);
    }

    // The max and min functions are called one after another until the max depth is reached
    public Move max(Board board, int depth, int a, int b, int player)
    {
        /* If max is called on a state that is terminal or after a maximum depth is reached or player can't make a move,
         * then a move is evaluated on the state and the move gets returned.
         */
        if((board.isFullBoard()) || (!board.canPlay(player))|| (depth == maxDepth))
        {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate(board.getLastPlayer()));
            return lastMove;
        }
        //The possible boards of the state are calculated
        ArrayList<Board> posBoards = new ArrayList<Board>(board.getBoards(player));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board posBoard : posBoards)
        {
            //And for each Board min is called, on a lower depth
            Move move = min(posBoard, depth + 1, a, b, board.getLastPlayer());
            //The possible Board with the greatest value is selected and returned by max
            if(move.getValue() >= maxMove.getValue())
            {
                if ((move.getValue() == maxMove.getValue()))
                {
                    maxMove.setRow(posBoard.getLastMove().getRow());
                    maxMove.setCol(posBoard.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
                else
                {
                    // otherwise if the possible Board value is greater than heuristic value, update maxMove
                    // a-b pruning in case beta is lesser than or equal to alpha
                    if(move.getValue() > a) a = move.getValue();
                    if(b <= a) break;
                }
                maxMove.setRow(posBoard.getLastMove().getRow());
                maxMove.setCol(posBoard.getLastMove().getCol());
                maxMove.setValue(move.getValue());
            }
        }
        return maxMove;
    }


    public Move min(Board board, int depth, int a, int b, int player)
    {

        /* If MIN is called on a state that is terminal or after a maximum depth is reached or player can't make a move,
         * then a heuristic is calculated on the state and the move gets returned.
         */
        if((board.isFullBoard()) || (!board.canPlay(player)) || (depth == maxDepth))
        {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate(board.getLastPlayer()));
            return lastMove;
        }
        //The children-moves of the state are calculated
        ArrayList<Board> posBoards = new ArrayList<Board>(board.getBoards(player));
        Move minMove = new Move(Integer.MAX_VALUE);
        for (Board posBoard : posBoards)
        {
            //And for each child max is called, on a lower depth
            Move move = max(posBoard, depth + 1, a, b, board.getLastPlayer());
            //The child-move with the smallest value is selected and returned by min
            if(move.getValue() <= minMove.getValue())
            {
                if(move.getValue() == minMove.getValue())
                {
                    minMove.setRow(posBoard.getLastMove().getRow());
                    minMove.setCol(posBoard.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }
                else
                {
                    // Otherwise if board-move value is lesser than the evaluated value, update minMove
                    // a-b pruning in case beta is lesser than or equal to alpha
                    if(move.getValue() < b) b = move.getValue();
                    if(b <= a) break;
                }
                minMove.setRow(posBoard.getLastMove().getRow());
                minMove.setCol(posBoard.getLastMove().getCol());
                minMove.setValue(move.getValue());
            }
        }
        return minMove;
    }

    public int getPlayer()
    {
        return player;
    }

    public void setPlayer(int player)
    {
        this.player = player;
    }

    public int getMaxDepth()
    {
        return maxDepth;
    }

    public void setMaxDepth(int depth)
    {
        this.maxDepth = depth;
    }
}
