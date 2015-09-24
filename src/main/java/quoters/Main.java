package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Grinyov Vitaliy on 17.09.15.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        while (true) {
            Thread.sleep(100);
            context.getBean(TerminatorQuoter.class).sayQuote();
        }
    }
}
