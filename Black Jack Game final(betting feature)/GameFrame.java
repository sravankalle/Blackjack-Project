import javax.swing.*;

public class GameFrame extends JFrame{

    public static GamePanel board;
    GameFrame()
    {
        board = new GamePanel();
        this.setContentPane(board);
        // this.add(new GamePanel());
        this.setTitle("Black Jack ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
