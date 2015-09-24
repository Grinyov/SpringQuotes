package quoters;

/**
 * Created by green on 25.09.2015.
 *
 * В этом интерфейсе прописываем методы которые зарегистрируются на MBean server
 * и будут доступны в jmx console
 */
public interface ProfilingControllerMBean {
   void setEnable(boolean enable);
}
