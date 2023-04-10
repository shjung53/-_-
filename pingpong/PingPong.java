package pingpong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class PingPong extends JFrame implements ActionListener {

    public PingPong() {
        Ball ball = new Ball();
        ball.setBackground(Color.GREEN);
        ball.setPreferredSize(new Dimension(GameBoard.WIDTH, GameBoard.HEIGHT));
        ball.setLayout(new CardLayout());
        add(ball);
        pack();
        setTitle("Pong게임");
        Timer timer = new Timer(10, this);
        timer.start();
        setSize(GameBoard.WIDTH, GameBoard.HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args){
        PingPong f = new PingPong();
    }
}

class GameBoard extends JPanel {
    static final int WIDTH = 1000;
    static final int HEIGHT = 500;
}

//    볼
class Ball extends JPanel{
    int x=50, y=50, xInc=3, yInc=3, diameter=30;

    public Ball(){
        Racket racket = new Racket();
        racket.setPreferredSize(new Dimension(Racket.WIDTH, Racket.HEIGHT));
        racket.setFocusable(true);
        racket.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                switch (keycode){
                    case KeyEvent.VK_UP : Racket.Y -=5;
                    case KeyEvent.VK_DOWN : Racket.Y +=5;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        add(racket);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(x ==0 || x == (GameBoard.WIDTH - diameter)) {
            Fail fail = new Fail();
            fail.fail();
        }

        if(y < 0 || y > (GameBoard.HEIGHT - diameter))
            yInc = - yInc;
//        왼쪽 라켓 부딪힐 때
        if(x <= 20){
            if(y+diameter >= Racket.Y || y <= Racket.Y+ Racket.HEIGHT){
                xInc = -xInc;
                yInc = -yInc;
            }
        }
//        오른쪽 라켓 부딪힐 때
        if(x >= 930){
            if(y+diameter >= Racket.Y || y <= Racket.Y+ Racket.HEIGHT){
                xInc = -xInc;
                yInc = -yInc;
            }
        }
        x += xInc;
        y += yInc;
        g.setColor(Color.RED);
        g.fillOval(x, y, diameter, diameter);
    }
}

//    라켓
class Racket extends JComponent implements ActionListener{
    static int WIDTH=10, HEIGHT=80, X=10, Y=200, X2=960;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(X,Y,WIDTH,HEIGHT);

        g.setColor(Color.YELLOW);
        g.fillRect(X2,Y,WIDTH,HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}

class Fail extends JFrame {
    public void fail() {
        setSize(300, 100);
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2);
        setLayout(new BorderLayout());
        JLabel guide = new JLabel("실패");
        add(guide, BorderLayout.CENTER);


        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
