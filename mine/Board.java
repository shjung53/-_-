package mine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame{
    public Board() {
        setSize(700, 720);
        setTitle("Minesweeper");
//        프레임 윈도우 중앙에 위치
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2);
        setLayout(new BorderLayout());
        JPanel main = new JPanel(new GridLayout(7,7));

//배열 세팅
        JPanel[][] panels = new JPanel[7][7];
        JLabel[][] labels = new JLabel[7][7];
        JButton[][] btns = new JButton[7][7];

//        랜덤으로 지뢰 배치후 배열에 넣어두기
        for(int i=0; i<7; i++){
            for(int j=0; j<7; j++){
                panels[i][j] = new JPanel(new BorderLayout());
                panels[i][j].setSize(100,100);
                int p = (int) (Math.random() * 99);
                if(p < 10){
                    labels[i][j] = new JLabel("mine");
                    panels[i][j].add(labels[i][j], BorderLayout.CENTER);
                    main.add(panels[i][j]);
                }else{
                    labels[i][j] = new JLabel("none");
                    panels[i][j].add(labels[i][j], BorderLayout.CENTER);
                    main.add(panels[i][j]);
                }
            }
        }

        add(main);

//        초기화 버튼
        JPanel resetPanel = new JPanel();
        JButton reset = new JButton("초기화");
        Dimension btnSize = new Dimension(100,20);
        reset.setPreferredSize(btnSize);
        reset.addActionListener(new Restart());
        resetPanel.add(reset);
        add(resetPanel, BorderLayout.SOUTH);



        for(int i=0; i<7; i++){
            for(int j=0; j<7; j++){
                //        지뢰 없는 곳에 숫자 라벨
                getMineNum(i, j, labels);
                btns[i][j] = new JButton();
                panels[i][j].add(btns[i][j]);
                int finalI = i;
                int finalJ = j;
//                버튼 누른 곳에 같은 위치에 있는 라벨 보여주기 + 지뢰면 펑
                btns[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton btn = (JButton)e.getSource();
                        btn.setVisible(false);
                        panels[finalI][finalJ].add(labels[finalI][finalJ]);
                        if(labels[finalI][finalJ].getText() == "mine"){
                            Fail f = new Fail();
                            f.fail();
                        }
                    }
                });
            }
        }







        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Board f = new Board();
    }

    private void getMineNum(int i , int j, JLabel[][] labels){
        int[] dx = new int[] { 0, -1, 0, 1, 1, -1, 1, -1 };
        int[] dy = new int[] { 1, 0, -1, 0, 1, -1, -1, 1 };
        int count = 0;

        if(labels[i][j].getText() == "none"){
            for(int x=0; x<8; x++){
                int nx = i + dx[x]; int ny = j + dy[x];
                if(nx >= 0 && nx <= 6 & ny >= 0 && ny<= 6 && labels[nx][ny].getText() == "mine"){
                    count += 1;
                }
            }

            String countMine = String.format("%s", count);

            labels[i][j].setText(countMine);
        }
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
        JLabel guide = new JLabel("지뢰가 터졌습니다! 다시 도전하세요!");
        JButton restart = new JButton("재도전");
        restart.addActionListener(new Restart());
        add(guide, BorderLayout.CENTER);
        add(restart, BorderLayout.SOUTH);


        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

class Restart implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        Board b = new Board();
    }
}


