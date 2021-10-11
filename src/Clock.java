
/**
 *
 * @author K Hun
 */
public class Clock {

    private int minute, second;
    private int sumSecs;

    private void sumSecsToReg() {
        minute = sumSecs / 60;
        second = sumSecs % 60;
    }

    public Clock(int s) {
        sumSecs = s;
        sumSecsToReg();
    }

    public void subtract() {
        sumSecs--;
        sumSecsToReg();
    }

    public boolean isFinished() {
        if (sumSecs == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {

        String m = "" + minute;
        String s = "" + second;
        if (second < 10) {
            s = "0" + s;
        }
        if (minute < 10) {
            m = "0" + m;
        }

        return m + ":" + s;
    }

    public String getStirng() {
        String str = "";
        if (minute > 0) {
            str = minute + " minute(s)";
            if (second > 0) {
                str = str + " and " + second + " second(s)";
            }
        } else {
            str = second + " second(s)";
        }

        return str;
    }
}
