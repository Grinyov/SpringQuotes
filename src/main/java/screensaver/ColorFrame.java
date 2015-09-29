package screensaver;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Grinyov Vitaliy on 28.09.15.
 *
 * Класс рисует в случайном месте с переданным цветом фрейм
 *
 * 2) Делаем класс и метод getColor абстрактным, потому что не будем использовать в нем контекст
 */

@org.springframework.stereotype.Component
public abstract class ColorFrame extends JFrame {

   //1) @Autowired
    // private Color color;

    public ColorFrame(){
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showOnRandomPlace(){
        Random random = new Random();
        setLocation(random.nextInt(1200), random.nextInt(700));
        getContentPane().setBackground(getColor());
        repaint();
    }

    protected abstract Color getColor();
}
