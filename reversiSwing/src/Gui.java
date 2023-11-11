import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JButton;


public class Gui implements MouseListener{


    private int player = 1; //starting player is black
    int turn=1;
    private Stack<Board> stack;
    Computer ComputerPlayer = new Computer();
    private Board board;
    private int check;
    private int black = 2;
    private int white = 2;

    private JPanel panel;
    private JPanel startingScreen;

    private JButton undo;
    private JButton giveUp;
    int[][] copyBoard;
    Move AI = new Move();
    JFrame frame = new JFrame();

    public Gui(){
        stack= new Stack<Board>();
        board=new Board();
        startingScreen = new JPanel();
        panel=new JPanel();
        frame.setTitle("reversi");
        frame.setLocationRelativeTo(null);

        frame.setSize(700,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        panel.addMouseListener(this);
        startingScreen.addMouseListener(this);
        frame.addMouseListener(this);

        startingScreen.setPreferredSize(new Dimension(500,500));
        panel.setPreferredSize(new Dimension(700, 600));

        //Button start of Starting Panel
        JButton Start = new JButton("PLAY");
        Start.setBackground(new Color(170,230,255));
        Start.setForeground(new Color(30,30,130));
        Start.setFont(new Font ("Courier", Font.BOLD, 24));
        Start.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));

        //Button Group Difficulty of Starting Panel
        ButtonGroup Difficulty = new ButtonGroup();

        //Button Easy of Starting Panel
        JRadioButtonMenuItem Easy = new JRadioButtonMenuItem("Easy");
        Easy.setBackground(new Color(170,230,255));
        Easy.setForeground(new Color(30,30,130));
        Easy.setFont(new Font ("Courier", Font.BOLD, 14));
        Easy.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));

        //Button Normal of Starting Panel
        JRadioButtonMenuItem Normal = new JRadioButtonMenuItem("Normal");
        Normal.setBackground(new Color(170,230,255));
        Normal.setForeground(new Color(30,30,130));
        Normal.setFont(new Font ("Courier", Font.BOLD, 14));
        Normal.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));

        //Button Hard of Starting Panel
        JRadioButtonMenuItem Hard = new JRadioButtonMenuItem("Hard");
        Hard.setBackground(new Color(170,230,255));
        Hard.setForeground(new Color(30,30,130));
        Hard.setFont(new Font ("Courier", Font.BOLD, 14));
        Hard.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));

        Difficulty.add(Easy);
        Difficulty.add(Normal);
        Difficulty.add(Hard);

        //Button Group Turn of Starting Panel
        ButtonGroup Turn = new ButtonGroup();

        //Button First of Starting Panel
        JRadioButtonMenuItem First = new JRadioButtonMenuItem(" 1st");
        First.setBackground(new Color(170,230,255));
        First.setForeground(new Color(30,30,130));
        First.setFont(new Font ("Courier", Font.BOLD, 14));
        First.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));

        //Button Second of Starting Panel
        JRadioButtonMenuItem Second = new JRadioButtonMenuItem(" 2nd");
        Second.setBackground(new Color(170,230,255));
        Second.setForeground(new Color(30,30,130));
        Second.setFont(new Font ("Courier", Font.BOLD, 14));
        Second.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));

        Turn.add(First);
        Turn.add(Second);

        // Start removes starting screen and adds panel when pressed, the user may now play
        Start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(startingScreen);
                frame.add(panel);
                frame.setContentPane(panel);
                frame.pack();
                frame.repaint();
            }
        });

        // Easy sets maxDepth to 3 when pressed, Easy is preselected since maxDepth is initialized with 3
        Easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ComputerPlayer.setMaxDepth(3);
            }
        });

        // Normal sets maxDepth to 5 when pressed
        Normal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ComputerPlayer.setMaxDepth(5);
            }
        });

        // Hard sets maxDepth to 8 when pressed
        Hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ComputerPlayer.setMaxDepth(8);
            }
        });
        First.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turn = 1;
                ComputerPlayer.setPlayer(board.White); // AI gets White discs since it plays second
            }
        });

        // Second sets turn to 2 when pressed
        Second.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turn = 2;
                ComputerPlayer.setPlayer(board.Black); // AI gets Black discs since it plays first
            }
        });

        //Button Undo Move of game panel
        undo = new JButton("Undo Move");
        undo.setBounds(580, 60, 100, 40);
        undo.setBackground(new Color(34, 159, 24));
        undo.setForeground(new Color(3, 3, 3));
        undo.setFont(new Font("Courier", Font.BOLD, 15));
        undo.setBorder(BorderFactory.createBevelBorder(2, new Color(34, 159, 24), Color.white));
        undo.setVisible(true);

        //Button Give Up of game panel
        giveUp = new JButton("Give Up");
        giveUp.setBounds(580, 120, 100, 40);
        giveUp.setBackground(new Color(34, 159, 24));
        giveUp.setForeground(new Color(3, 3, 3));
        giveUp.setFont(new Font("Courier", Font.BOLD, 15));
        giveUp.setBorder(BorderFactory.createBevelBorder(2, new Color(34, 159, 24), Color.white));
        giveUp.setVisible(true);


        //Undo Move updates the board to its previous state
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stack.isEmpty()) {
                    board = stack.pop();
                    frame.repaint();
                }
            }
        });

        //Give Up exits the game panel and reopens the Starting Panel
        giveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Gui();
            }
        });

        startingScreen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Adding starting panel's Image
                try {
                    g.drawImage(ImageIO.read(new File("images/startingScreen.png")),  0,  0,  null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                check=0;
                // Button location and size
                Start.setBounds(190, 200, 100, 45);

                // Setting text color and font
                g.setFont(new Font ("Courier", Font.BOLD, 18));
                g.setColor(new Color(30,30,130));

                g.drawString("Choose Difficulty" , 90, 285);
                Easy.setBounds(114, 300, 80, 30);
                Normal.setBounds(114, 335, 80, 30);
                Hard.setBounds(114, 370, 80, 30);

                g.drawString("Turn" , 310, 285);
                First.setBounds(290, 300, 80, 30);
                Second.setBounds(290, 335, 80, 30);

                Easy.setSelected(true);		// Preselected since maxDepth is initialized with value 8
                First.setSelected(true);	// Preselected since turn is initialized with value 1

                // adding buttons
                startingScreen.add(Start);
                startingScreen.add(Easy);
                startingScreen.add(Normal);
                startingScreen.add(Hard);
                startingScreen.add(First);
                startingScreen.add(Second);
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 438);
            }
        };

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                check=1;
                panel.add(undo);
                panel.add(giveUp);
                // Printing the empty board
                for (int i = 0; i < 8; i++){
                    for (int j = 0; j < 8; j++) {
                        g.setColor(new Color(30, 121, 30));
                        g.fillRect(i * 70, j * 70, 70, 70);    // Draws green filled square
                        g.setColor(Color.black);
                        g.drawRect(i * 70, j * 70, 70, 70);    // Draws black square border
                    }
                }
                copyBoard = Available(-(board.getLastPlayer()), board);
                // Printing discs and highlighting available moves
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (copyBoard[i][j] == board.Black) {
                            g.setColor(new Color(16, 15, 15));
                            g.fillOval(5 + i * 70, 5 + j * 70, 60, 60);
                        }
                        if (copyBoard[i][j] == board.White) {
                            g.setColor(new Color(243, 239, 239));
                            g.fillOval(5 + i * 70, 5 + j * 70, 60, 60);
                        }
                        if (copyBoard[i][j] == 2 && ComputerPlayer.getPlayer()==board.getLastPlayer()) {
                            g.setColor(new Color(59, 139, 222));
                            g.drawRect(i * 70, j * 70, 70, 70); // Draws blue square border
                        }
                    }
                }
                copyBoard = Remove2(copyBoard);

                // Updates score values for each color
                black = board.getBlackScore();
                white = board.getWhiteScore();


                // Setting text color and font
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 14));

                //Printing status messages on the side of the window
                if (black > white) {
                    g.drawString("Black is winning.",  575, 200);
                    g.drawString(" Black : " + black + "  White : " + white,565, 230);
                } else if (black == white) {
                    g.drawString("No one is winning.",  575, 200);
                    g.drawString(" Black : " + black + "  White : " + white,565, 230);
                } else {
                    g.drawString("White is winning.",  575, 200);
                    g.drawString(" Black : " + black + "  White : " + white,565, 230);
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(715, 560);
            }

        };

        frame.add(startingScreen);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x, y, i, j, check, delay = 500;
        x = e.getX();
        y = e.getY();
        i = (x - 8) / 70;
        j = (y - 31) / 70;
        frame.repaint();
        System.out.println("i: " + j + " j: " + i);

        // Endgame condition
        if(!(board.canPlay(ComputerPlayer.getPlayer()) || board.canPlay(-ComputerPlayer.getPlayer())))
        {
            if(board.getWhiteScore()>board.getBlackScore())
                gameOver("Computer won!");
            if(board.getBlackScore()>board.getWhiteScore())
                gameOver("you won!");
            if(board.getBlackScore()==board.getWhiteScore())
                gameOver("draw!");
        }
            if (turn == 1) {
                if (board.getLastPlayer() == -1) {
                    //If white played last, then black plays now
                    if (!board.canPlay(board.Black)) {
                        board.setLastPlayer(board.Black);
                    }
                    if (board.isValidMove(i, j, board.Black)) {/*if the move is valid
                                                              make the move and update the board*/
                        Board temp = new Board(board);
                        stack.push(temp);
                        Move BlackMove = new Move(i, j);
                        board.makeMove(BlackMove.getRow(), BlackMove.getCol(), board.Black);
                    }
                    panel.repaint();
                }
                if (board.getLastPlayer() == 1)/*if last player was
                black its computer turn now*/ {
                    moves = board.possibleMoves(board.White);
                    if (!board.canPlay(board.White)) {
                        board.setLastPlayer(board.White);
                    }
                    new Timer(delay , new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Computer_Plays(true);
                            ((Timer)e.getSource()).stop();
                            panel.repaint();
                        }
                    }).start();
                }
            }
            if (turn == 2) {
                if (board.getLastPlayer() == 1) {
                    //If white played last, then black plays now
                    if (!board.canPlay(board.White)) {
                        board.setLastPlayer(board.White);
                    }
                    if (board.isValidMove(i, j, board.White)) /*if the move is valid
                                                              make the move and update the board*/
                    {
                        Board temp = new Board(board);
                        stack.push(temp);
                        Move BlackMove = new Move(i, j);
                        board.makeMove(BlackMove.getRow(), BlackMove.getCol(), board.White);
                    }
                    panel.repaint();
                }
                if (board.getLastPlayer() == -1)/*if last player was
                white its computer turn now*/
                {
                    moves = board.possibleMoves(board.Black);
                    if (!board.canPlay(board.Black)) {
                        board.setLastPlayer(board.Black);
                    }
                    // computer turn
                    new Timer(delay , new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Computer_Plays(true);
                            ((Timer)e.getSource()).stop();
                            panel.repaint();
                        }
                    }).start();
                }
            }
        }

    //initializes the minimax function and executes the returned move
    public void Computer_Plays(boolean aiturn) {
        if(aiturn) {
            AI = ComputerPlayer.MiniMax(board);
            board.makeMove(AI.getRow(), AI.getCol(), ComputerPlayer.getPlayer());
        }
    }
    //creates a dialogue box to choose to exit or begin a new game
    public void gameOver(String s){
        Object[] option={"New Game","Exit"};
        int n=JOptionPane.showOptionDialog(frame, "Game Over\n"+s,"Game Over",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
        frame.dispose();
        if(n==0)
            new Gui();;
    }

    //goes over all the possible moves of the current player and puts the value 2
    ArrayList<int[]> moves;
    public int[][] Available(int currentPlayer, Board myBoard) {

        int [][] copyBoard = myBoard.getGameBoard();
        moves = myBoard.possibleMoves(currentPlayer);
        int[] temp;
        for(int x=0; x < moves.size(); x++)
        {
            temp = moves.get(x);
            copyBoard[temp[0]][temp[1]] = 2;
        }
        return copyBoard;
    }
    //removes every value of 2 in the board
    public int[][] Remove2 (int[][] mat){
        for(int i=0; i<8; i++)
            for(int j=0; j<8; j++)
                if (mat[i][j]==2)
                    mat[i][j]=0;
        return mat;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}

