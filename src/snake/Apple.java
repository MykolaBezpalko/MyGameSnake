package snake;

public class Apple {
    int posX;
    int posY;

    Apple(int x, int y) {
        posX = x;
        posY = y;
    }

    void setRandomPosition() {
        posX = Math.abs((int) (Math.random() * MainWindow.WIDTH - 1));
        posY = Math.abs((int) (Math.random() * MainWindow.HEIGHT - 1));
    }

}
