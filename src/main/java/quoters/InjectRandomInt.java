package quoters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Grinyov Vitaliy on 17.09.15.
 *
 * Аннотация Retention указывает на видимость аннотации
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();
    int max();
}
