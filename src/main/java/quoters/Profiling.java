package quoters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Grinyov Vitaliy on 18.09.15.
 *
 *  Аннотация котрая профилирует класс, сканирует все методы и выводит в лог сколько времени работает
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Profiling {
}
