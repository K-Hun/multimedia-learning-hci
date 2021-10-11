
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author K Hun
 */
public class Utils {

    public static String makeNewSubjectDir(String parent, String fname, String lname) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        String dirName = fname.trim().replace(" ", "") + "_" + lname.trim().replace(" ", "") + "_" + strDate;
        File theDir = new File(parent + "\\" + dirName);

        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
            }
        }
        return theDir.getAbsolutePath();
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getCurrentTimeStampTrimed() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getEventLog(String eventType) throws AWTException, IOException {
        String log = eventType + " @ " + getCurrentTimeStamp();
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "png", new File(Frame.screenShotDir + "/" + getCurrentTimeStampTrimed() + ".png"));
        return log;
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid + "";
    }

    public static Dimension getScreenScreenSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return new Dimension(width, height);
    }

    public static Question[] loadQuestions(String path) {
        File qFile = new File(path);
        Scanner in = null;
        try {
            in = new Scanner(qFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Question file openning error!");
            System.exit(1);
        }
        Question questions[];
        int qIndex = 0;
        int numberOfQuestions = 0;
        in.nextLine(); // to skip first line that is time to answer to questions
        while (in.hasNext()) {

            String line = in.nextLine();
            if (line.length() > 3) {
                numberOfQuestions++;
            }
        }
        numberOfQuestions /= 5;
        questions = new Question[numberOfQuestions];
        in.close();
        try {
            in = new Scanner(qFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Question file openning error!");
        }
        in.nextLine(); // to skip first line that is time to answer to questions
        while (in.hasNext()) {
            if (qIndex > numberOfQuestions - 1) {
                break;
            }
            String ops[] = new String[4];
            String des = in.nextLine();
            for (int i = 0; i < 4; i++) {
                ops[i] = in.nextLine();
            }
            questions[qIndex++] = new Question(des, 4, ops, 0);
        }
        in.close();
        return questions;
    }

    public static int getQuestionsTime(String path) {
        File qFile = new File(path);
        Scanner in = null;
        try {
            in = new Scanner(qFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Question file openning error!");
            System.exit(1);
        }

        String num = in.nextLine();
        if ((int) num.toCharArray()[0] == 65279) {
            num = num.replace(num.toCharArray()[0] + "", "");
        }
        int time = Integer.parseInt(num);
        in.close();
        return time;
    }

    public static void beep(int times) throws LineUnavailableException, InterruptedException {
        int delay = 500;

        if (times <= 0) {

            SoundUtils.tone(900, delay, 0.5);
            Thread.sleep(delay);

        } else {
            for (int i = 0; i < times; i++) {
                SoundUtils.tone(900, delay, 0.5);
                Thread.sleep(delay);
            }

        }
    }

    public static long timeDiff(String time1, String time2) throws ParseException {
        time1 = "00:" + time1;
        time2 = "00:" + time2;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        return Math.abs(date2.getTime() - date1.getTime());
    }

    public static boolean move(File sourceFile, File destFile) {

        try {
            Files.move(Paths.get(sourceFile.getPath()), Paths.get(destFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }

    }
}
