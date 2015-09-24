package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by Grinyov Vitaliy on 17.09.15.
 *
 * Класс, отвечающий за обработку бинов имеющих аннотацию @InjectRandomInt
 */
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for(Field field : fields){
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            if (annotation != null){                        // если аннотация есть, то
                int min = annotation.min();
                int max = annotation.max();
                Random random = new Random();
                int i = min + random.nextInt(max - min);    // создаем случайное число из диапозона min - max
                field.setAccessible(true);                  // получаем доступ к полю класса
                ReflectionUtils.setField(field, bean, i);   // используем Спринговские утилиты рефлексии, указывая какое поле какого бина установить в значение i
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
