package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Grinyov Vitaliy on 17.09.15.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean(TerminatorQuoter.class).sayQuote();
    }
}
