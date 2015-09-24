package quoters;

/**
 * Created by green on 25.09.2015.
 *
 * ¬ этом интерфейсе прописываем методы которые зарегистрируютс€ на MBean server
 * и будут доступны в jmx console
 */
public interface ProfilingControllerMBean {
   void setEnable(boolean enable);
}
