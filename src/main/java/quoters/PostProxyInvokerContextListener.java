package quoters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.beans.BeanDescriptor;
import java.lang.reflect.Method;

/**
 * Created by green on 26.09.2015.
 *
 * Создаем listener который будет слушать ContextRefreshedEvent(для этого он указан в дженерике)
 */
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory factory; //Только данная фабрика может вызывать BeanDefinition
    // инжекция спринговой фабрики в данный класс(каппалинг) правильна, так как мы переопределяем спринговый листенер
    // крайне нежелательна инжекция спринговых внутреностей(в данном случае контекста) в обычный бин, т.е.
    // неправильно создать контекст и заинжектить его во все бины, а потом если что то нужно - вытаскивать через лукап
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();  // 1) вытаскиваем котекст
        String[] names = context.getBeanDefinitionNames(); // 2) берем имена наших бинов

        // 3) У полученных имен бинов нельзя вызывать бин и вызывать у него getClass,
        // так как это будут наши прокси( proxy$), а у них ничего нужного нет и ещё, если есть
        // lazy бины которые создаются только в момент когда их запрпашивают вызывать их будет не правильно,
        // поэтому мы вызываем главную фабрику Spring в аннотации @Autowired и вызываем у BeanDefinition

        for (String name : names){
           BeanDefinition beanDefinition = factory.getBeanDefinition(name);
           String  originalClassName = beanDefinition.getBeanClassName(); // вытаскиваем оригинальное имя класса(которое ещё в xml прописано)
            try {
                Class<?> originalClass = Class.forName(originalClassName); // вытаскиваем сам класс
                Method[] methods = originalClass.getMethods();        // и его методы
                // делаем проход по методам и если на нем стоит наша аннотация PostProxy
                for (Method method : methods){
                    if (method.isAnnotationPresent(PostProxy.class)){
                       // method.invoke() // так будет работать в cglib,
                        // просто вызов метода не будет работать через динамический прокси
                        // так как это оригинальный класс а нам нужен текущий бин(который proxy$)

                        Object bean = context.getBean(name); // поэтому мы берем бин у контекста
                        Method currentMethod =  bean.getClass().getMethod(method.getName(), method.getParameterTypes()); // и у него вытаскиваем текущий метод
                        // мы так можем сделать так как у нас есть два бина, оригинальный(TerminatorQuoter) и прокси-бин(proxy$), который полностью идентичен
                        currentMethod.invoke(bean); // и вызывем текущий метод
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
