/**
 *
 * @author K Hun
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {

    //----------------------------- <EVENTS> -----------------------------------
    final static String MUL_START = "MUL_START";
    final static String MUL_END = "MUL_END";
    final static String CENTER_TEST_START = "CENTER_TEST_START";
    final static String CENTER_TEST_END = "CENTER_TEST_END";
    final static String QS_START = "QS_START";
    final static String Q = "Q_";
    final static String QANS = "QANS_";
    final static String QCLR = "QCLR_";
    final static String QS_FINISHBT_PRESSED = "QS_FINISHBT_PRESSED";
    final static String QS_FINISHBT_CANCELED = "QS_FINISHBT_CANCELED";
    final static String QS_FINISH_USER = "QS_FINISH_USER";
    final static String QS_FINISH_TIMER = "QS_FINISH_TIMER";
    //--------------------------------------------------------------------------
    static String WORKSPACE_PATH;
    static String DEFAULT_DIR;
    static int CLOCK_FONT_SIZE;
    static int PRE_TIMER;
    static int NUM_OF_QUESTIONS;
    static int ANSWERING_TIME;
    static int TIMER_MINUTE;
    static int RIGHT_PANEL_WIDTH;
    static int REC_ICON_SIZE;
    static String VLC_PATH = "";
    static int TOP_MARGIN;
    static int SIGNAL_PANEL_DEG;
    static int QUESTIONS_FONT_SIZE;
    static int OPTIONS_FONT_SIZE;
    static String RESOURCES_PATH;

    public static void init() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);

            PRE_TIMER = Integer.parseInt(prop.getProperty("PRE_TIMER").trim());
            WORKSPACE_PATH = prop.getProperty("WORKSPACE_PATH");
            VLC_PATH = prop.getProperty("VLC_PATH");
            DEFAULT_DIR = prop.getProperty("DEFAULT_DIR");
            CLOCK_FONT_SIZE = Integer.parseInt(prop.getProperty("CLOCK_FONT_SIZE").trim());
            TOP_MARGIN = Integer.parseInt(prop.getProperty("TOP_MARGIN").trim());
            SIGNAL_PANEL_DEG = Integer.parseInt(prop.getProperty("SIGNAL_PANEL_DEG").trim());
            QUESTIONS_FONT_SIZE = Integer.parseInt(prop.getProperty("QUESTIONS_FONT_SIZE").trim());
            OPTIONS_FONT_SIZE = Integer.parseInt(prop.getProperty("OPTIONS_FONT_SIZE").trim());
            RESOURCES_PATH = prop.getProperty("RESOURCES_PATH");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
