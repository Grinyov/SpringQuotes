package screensaver;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

/**
 * Created by Grinyov Vitaliy on 29.09.15.
 *
 * 2) Тогда здесь создаем наш бин ColorFrame
 */
@Configuration
@ComponentScan(basePackages = "screensaver")
public class Config {
    @Bean
    @Scope("periodical")
    // 1) можно сделать через прокси мод но тогда при каждом обращении к цвету будет создаватся новый бин
    // что является не очень хорошим решением
    //@Scope( value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Color color(){
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(250), random.nextInt(255));
    }

    @Bean
    public ColorFrame frame(){
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();
            }
        };
    }
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while (true){
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(150);
        }
    }
}
