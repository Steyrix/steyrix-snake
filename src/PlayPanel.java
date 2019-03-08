import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class PlayPanel extends JPanel implements ActionListener {

    private Timer timer;
    private int delays;
    private boolean sceneChanged;

    private final int DELAY = 450;
    private final int delaysToWait = 3;

    public PlayPanel() throws IOException {
        initPlayPanel();
    }

    private void initPlayPanel() throws IOException {

        addKeyListener(new TAdapter());
        setFocusable(true);

        delays = 0;
        sceneChanged = false;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        //scene.draw()

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //scene.action()
        repaint();
    }

    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            //scene.keypressed()
        }

        @Override
        public void keyReleased(KeyEvent e){
            //scene.keyreleased
        }
    }

    //Logic for removing J-elements
    private void removeUi() {
        for( Component c : this.getComponents())
            if(c instanceof JButton || c instanceof JLabel)
                this.remove(c);

        this.requestFocusInWindow();
    }
}