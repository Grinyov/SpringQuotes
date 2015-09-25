package quoters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by green on 26.09.2015.
 *
 * Аннотация показывет что все бины настроены и прокси сгенерированы и
 * можно запускать ContextListener
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PostProxy {
}
