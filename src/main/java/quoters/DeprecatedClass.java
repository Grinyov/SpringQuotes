package quoters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by green on 27.09.2015.
 *
 * ��������� ����������� ��� ����� ������� � ���� ��� ����� ������
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DeprecatedClass {
    Class newImpl();
}
