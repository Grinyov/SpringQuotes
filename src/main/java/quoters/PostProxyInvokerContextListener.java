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
 * ������� listener ������� ����� ������� ContextRefreshedEvent(��� ����� �� ������ � ���������)
 */
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory factory; //������ ������ ������� ����� �������� BeanDefinition
    // �������� ���������� ������� � ������ �����(���������) ���������, ��� ��� �� �������������� ���������� ��������
    // ������ ������������ �������� ���������� ������������(� ������ ������ ���������) � ������� ���, �.�.
    // ����������� ������� �������� � ����������� ��� �� ��� ����, � ����� ���� ��� �� ����� - ����������� ����� �����
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();  // 1) ����������� �������
        String[] names = context.getBeanDefinitionNames(); // 2) ����� ����� ����� �����

        // 3) � ���������� ���� ����� ������ �������� ��� � �������� � ���� getClass,
        // ��� ��� ��� ����� ���� ������( proxy$), � � ��� ������ ������� ��� � ���, ���� ����
        // lazy ���� ������� ��������� ������ � ������ ����� �� ������������ �������� �� ����� �� ���������,
        // ������� �� �������� ������� ������� Spring � ��������� @Autowired � �������� � BeanDefinition

        for (String name : names){
           BeanDefinition beanDefinition = factory.getBeanDefinition(name);
           String  originalClassName = beanDefinition.getBeanClassName(); // ����������� ������������ ��� ������(������� ��� � xml ���������)
            try {
                Class<?> originalClass = Class.forName(originalClassName); // ����������� ��� �����
                Method[] methods = originalClass.getMethods();        // � ��� ������
                // ������ ������ �� ������� � ���� �� ��� ����� ���� ��������� PostProxy
                for (Method method : methods){
                    if (method.isAnnotationPresent(PostProxy.class)){
                       // method.invoke() // ��� ����� �������� � cglib,
                        // ������ ����� ������ �� ����� �������� ����� ������������ ������
                        // ��� ��� ��� ������������ ����� � ��� ����� ������� ���(������� proxy$)

                        Object bean = context.getBean(name); // ������� �� ����� ��� � ���������
                        Method currentMethod =  bean.getClass().getMethod(method.getName(), method.getParameterTypes()); // � � ���� ����������� ������� �����
                        // �� ��� ����� ������� ��� ��� � ��� ���� ��� ����, ������������(TerminatorQuoter) � ������-���(proxy$), ������� ��������� ���������
                        currentMethod.invoke(bean); // � ������� ������� �����
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
