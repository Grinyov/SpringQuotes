package quoters;

/**
 * Created by Grinyov Vitaliy on 24.09.15.
 *
 * Включает и выключает через jmx console наш профайлинг
 *
 * для этого он наследует интерфейс MBean и регестрируется на MBean server
 */
public class ProfilingController implements ProfilingControllerMBean{

    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
