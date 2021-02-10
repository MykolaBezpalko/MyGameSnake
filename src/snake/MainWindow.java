package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class MainWindow extends JPanel implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;

    public static JFrame jFrame;

    public static final int SCALE = 26; // ������ ��������
    public static final int WIDTH = 25; // ������ ������� �� ����� ����
    public static final int HEIGHT = 25; // ������ ������� �� ����� ����
    public Image d;
    public Image a;
    public Image b;

    static String rootPath = System.getProperty("user.dir");
    Snake s = new Snake(5, 6, 5, 5, 5, 4);//���������� ����� ������� �����
    Apple apple = new Apple(Math.abs((int) (Math.random() * MainWindow.WIDTH - 1)),
            Math.abs((int) (Math.random() * MainWindow.HEIGHT - 1)));

    Timer timer = new Timer(300, this); //������ ��� ������������ ����

    public MainWindow() {
        timer.start();//����� ������� ��� �������
        addKeyListener(new KeyBoard());//������ ����������
        setFocusable(true);
        loadImages();
    }

    public void loadImages() {
        a = new ImageIcon(rootPath + "\\src\\resources\\a.png").getImage();
        d = new ImageIcon(rootPath + "\\src\\resources\\d.png").getImage();
        b = new ImageIcon(rootPath + "\\src\\resources\\b.png").getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //���� ����
        g.setColor(Color.DARK_GRAY);

        //���������� �������� � ����� ��������� ���� (�������)
        //������ ����� ����������
        g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

        //������� ���� ����� ���� ��� ����������� ����� � �������� ����
        Color lineColor = new Color(194, 25, 93);
        g.setColor(lineColor);
        g.fillRect(0, HEIGHT * SCALE, WIDTH * SCALE, SCALE);

        //�������� �����
        g.setColor(Color.WHITE);
        Font font = new Font("Calibre", Font.PLAIN, 22);
        g.setFont(font);

        g.drawString("�����: " + s.life, 0, HEIGHT * SCALE + SCALE - 6);
        g.drawString("����: " + (s.length - 3), WIDTH * SCALE / 2, HEIGHT * SCALE + SCALE - 6);

//      drawLines(g); //���������� �� �� ���(����� ������� �������� ��� ������)

        //apple
        g.drawImage(a, apple.posX * SCALE, apple.posY * SCALE, this);

        //���������� �����
        for (int l = 1; l < s.length; l++) {
            g.drawImage(d, s.sX[0] * SCALE, s.sY[0] * SCALE, this);
            g.drawImage(b, s.sX[l] * SCALE, s.sY[l] * SCALE, this);
        }
    }

    // ����� ����������� ��� ��� ������� �������� ����
    private void drawLines(Graphics g) {
        for (int x = 0; x <= WIDTH * SCALE; x += SCALE) {
            g.setColor(Color.LIGHT_GRAY); // ���� �������
            g.drawLine(x, 0, x, HEIGHT * SCALE);// ����� ������ ���� �� �(�����������)
        }

        for (int y = 0; y < WIDTH * SCALE; y += SCALE) {
            g.setColor(Color.LIGHT_GRAY); // ���� �������
            g.drawLine(0, y, WIDTH * SCALE, y);// ����� ������ ���� �� y(horizontal)
        }
    }

    //������� ��� ���� ����������� ����� � ������ ������ ���� ���������� ������ ��
    void stopGame() {
        timer.stop();
        JOptionPane.showMessageDialog(null, "�� ���������, ������ �����?");
        jFrame.setVisible(false);
        s.length = 3;
        s.direction = (int) (Math.random() * 3);
        jFrame.setVisible(true);
        apple.setRandomPosition();
        s.life = 3;
        timer.start();
    }

    public static void main(String[] args) {
        jFrame = new JFrame("���� � ������");//��������� ���������
        jFrame.setSize(WIDTH * SCALE + 15, HEIGHT * SCALE + 63); // ����� ����
        jFrame.setResizable(false); //������������ ����
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // ������ �� ������� � ������� ���
        jFrame.setLocationRelativeTo(null); // ���� ��������� ���� �� ������
        jFrame.add(new MainWindow());//������ ����������� ��������� �����
        jFrame.setVisible(true); // ���� ������ ������ ��� �����
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //��� �����
        s.move();
        if ((s.sX[0] == apple.posX) && (s.sY[0] == apple.posY)) {
            apple.setRandomPosition();
            s.length++;
        }

        //���� ������ �� ����i �� ������� ���� ������
        for (int l = 1; l < s.length; l++) {
            if ((s.sX[l] == apple.posX) && (s.sY[l] == apple.posY)) {
                apple.setRandomPosition();
            }

        //���� ��������� ���� � ����� �� ���� ���� �����
            if ((s.sX[0] == s.sX[l]) && (s.sY[0] == s.sY[l])) {
                s.life--;
            //���� ����� 0 �����
                if (s.life < 0) {
                    stopGame();
                }
            }
        }
        repaint();// ����������� ����� ����� ���
    }

    // ���� �������� ���� ��� ��������� �� ������
    public class KeyBoard extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            int key = event.getKeyCode();
            if ((key == KeyEvent.VK_UP) && !(s.direction == 2)) s.direction = 0;
            if (key == KeyEvent.VK_DOWN && !(s.direction == 0)) s.direction = 2;
            if (key == KeyEvent.VK_LEFT && !(s.direction == 1)) s.direction = 3;
            if (key == KeyEvent.VK_RIGHT && !(s.direction == 3)) s.direction = 1;
        }
    }
}
