import javax.swing.*;
import java.io.IOException;

public class SnakeApp extends JFrame {

    public SnakeApp() {
        try {
            initUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initUI() throws IOException {
        add(new PlayPanel());
        setSize(500, 560);
        setResizable(false);
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            SnakeApp ex = new SnakeApp();
            ex.setVisible(true);
        });

    }
}
