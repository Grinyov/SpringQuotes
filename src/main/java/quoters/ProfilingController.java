package quoters;

/**
 * Created by Grinyov Vitaliy on 24.09.15.
 *
 * �������� � ��������� ����� jmx console ��� ����������
 *
 * ��� ����� �� ��������� ��������� MBean � �������������� �� MBean server
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
