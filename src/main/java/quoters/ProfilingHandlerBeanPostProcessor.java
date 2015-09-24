package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Grinyov Vitaliy on 24.09.15.
 *
 *  Постпроцессор обрабатывает бины, если находит бин аннотированный @Profiling, то кладет его в мапу
 *  для дальнейшей обработки
 */
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    private ProfilingController controller = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer =  ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();

        if (beanClass.isAnnotationPresent(Profiling.class)){
            map.put(beanName, beanClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class beanClass = map.get(beanName);
        if (beanClass != null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // здесь логика профилирования, которая замеряет время действия метода,
                    // если у нас флаг enable(включен)

                    if (controller.isEnable()) {
                        System.out.println("Профилирование начато...");
                        long before = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        long after = System.nanoTime();
                        System.out.println("Время работы метода " + (after - before) + "мс");
                        System.out.println("Профилирование закончено");
                        return retVal;
                    }else {
                        return method.invoke(bean, args); // если флаг выключен прокси ничего не делает
                    }
                }
            });
        }

        return bean;
    }
}
