package screensaver;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Grinyov Vitaliy on 29.09.15.
 *
 * Класс который регистрирует наш scope(или любой другой кастомный) в контексте до создания самого контекста
 */
@Component
public class CustomScopeRegistryBeanFactoryPostProcessor implements BeanFactoryPostProcessor{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // сюда можно прописывать наши скопы
        beanFactory.registerScope("periodical", new PeriodicalScopeConfigurer());
    }
}
