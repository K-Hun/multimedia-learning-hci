
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author K Hun
 */
public class Frame {

    static JFrame frame;
    static int numberOfQuestions;
    static Integer currentQuestionIndex = new Integer(0);
    static int width = Utils.getScreenScreenSize().width;
    static int height = Utils.getScreenScreenSize().height;
    static EmbeddedMediaPlayerComponent mediaPlayerComponent;
    static Thread t = new Thread();
    static Question questions[];
    static HashMap<Integer, String> opMap = new HashMap<>();
    static int selectedOptions[] ;

    //------------------------------ PANELS ------------------------------------
    static JPanel signalPanel = new JPanel();
    static JPanel questionPanel = new JPanel(new GridLayout(3, 1));
    static JPanel playerPanel = new JPanel(new BorderLayout());
    static JPanel answerSheet;
    static JPanel answerSheetGrid[][];
    static JPanel sout = new JPanel(new BorderLayout());
    static JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
    static JPanel northPanel;

    //------------------------------ BUTTONS -----------------------------------
    static ButtonGroup bg = new ButtonGroup();
    static JRadioButton options[] = new JRadioButton[4];
    static JButton clearAnswerButton = new JButton();
    static JButton nextQuestionButton = new JButton("→");
    static JButton previousQuestionButton = new JButton("←");
    static JButton finishBt = new JButton("Finish");

    //---------------------------- TEXT PANES & DOCS----------------------------
    static JTextPane msgPanel;
    static JTextPane questionPane = new JTextPane();
    static StyleContext context = new StyleContext();
    static StyledDocument document = new DefaultStyledDocument(context);

    static int examTime;
    static Clock clock;
    static Thread clockThread;
    static Timer clearTimer;
    static JLabel clockLabel;

    static boolean startFlag;
// SHIT
    static String firstName = "Kayhan";
    static String lastName = "Latifzadeh";
    static String birthyear = "1994";
    static String task = "11P";
    static String path = "C:\\Users\\K Hun\\Desktop\\Open Source\\CogMul\\KHANFOLDER";
    static String fileName = "KHAN.txt";
    static int preTimer = 5;
    static PrintWriter logWriter;
    static String screenShotDir = "shots";

    static String MUL_PATH = "C:\\Users\\K Hun\\Desktop\\Open Source\\CogMul\\resources\\ch11\\p\\CH11_P_NQ_02.mkv";
    static String QS_PATH = "C:\\Users\\K Hun\\Desktop\\Open Source\\CogMul\\resources\\ch11\\test.txt";
    static int VID_TIME = 60000; //ms
    static int TEST_TIME = 300000; //ms
    static boolean QS_ONLY = false;
    static boolean isBlinker = false;

    public static void goToQuestion(int index) throws AWTException, IOException {
        try {

            currentQuestionIndex = index;

            StyleContext context = new StyleContext();
            StyledDocument document = new DefaultStyledDocument(context);
            Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
            document.insertString(document.getLength(), "\n\n(" + (index + 1) + ") " + questions[index].getQuestionDescription().replace("<br>", "\n"), style);

            questionPane.setDocument(document);
            int size = Constants.OPTIONS_FONT_SIZE;
            HashMap<Integer, String> opMap = new HashMap<>();
            opMap.put(1, "A");
            opMap.put(2, "B");
            opMap.put(3, "C");
            opMap.put(4, "D");
            bg = new ButtonGroup();

            for (int i = 0; i < 4; i++) {
                options[i].setText("(" + (opMap.get(i + 1)) + ") " + questions[index].getOptions()[i]);
                options[i].setFont(new Font("Tahoma", 0, size));
                options[i].setBackground(Color.WHITE);
                bg.add(options[i]);
                final int ii = i;
                for (ActionListener al : options[i].getActionListeners()) {
                    options[i].removeActionListener(al);
                }
                options[i].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        selectedOptions[currentQuestionIndex] = ii;
                        updateAnswerSheetView();
                        try {
                            //-------------------------------<LOG>----------------------------------
                            logWriter.println(Utils.getEventLog(Constants.QANS + (currentQuestionIndex + 1) + "_" + (ii + 1)));
                            //----------------------------------------------------------------------
                        } catch (AWTException ex) {
                            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });

                options[i].addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        options[ii].setBackground(Color.LIGHT_GRAY);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        options[ii].setBackground(Color.WHITE);
                    }
                });
            }
            bg.clearSelection();
            int selectedItem = selectedOptions[index];
            if (selectedItem != -1) {
                for (int i = 0; i < 4; i++) {
                    options[i].setSelected(false);
                }
                options[selectedItem].setSelected(true);
            }
            updateAnswerSheetView();

        } catch (BadLocationException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (ActionListener al : clearAnswerButton.getActionListeners()) {
            clearAnswerButton.removeActionListener(al);
        }
        clearAnswerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                selectedOptions[currentQuestionIndex] = -1;
                bg.clearSelection();
                updateAnswerSheetView();
                try {
                    //-------------------------------<LOG>----------------------------------
                    logWriter.println(Utils.getEventLog(Constants.QCLR + (currentQuestionIndex + 1)));
                    //----------------------------------------------------------------------
                } catch (AWTException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        //-------------------------------<LOG>----------------------------------
        logWriter.println(Utils.getEventLog(Constants.Q + (currentQuestionIndex + 1)));
        //----------------------------------------------------------------------
    }

    public static void changeQuestion(boolean isNext) throws AWTException, IOException {

        if (isNext && (currentQuestionIndex < questions.length - 1)) {
            ++currentQuestionIndex;
        } else if (!isNext && currentQuestionIndex > 0) {
            --currentQuestionIndex;
        }
        goToQuestion(currentQuestionIndex);

    }

    public static void endOperation(boolean isByUser) throws AWTException, IOException {
        //-------------------------------<LOG>----------------------------------
        if (isByUser) {
            logWriter.println(Utils.getEventLog(Constants.QS_FINISH_USER));
        } else {
            logWriter.println(Utils.getEventLog(Constants.QS_FINISH_TIMER));
        }
        logWriter.println("#LOG_FINISH @ " + Utils.getCurrentTimeStamp());
        logWriter.println("#ANSWESHEET:");
        for (int i = 0; i < selectedOptions.length - 1; i++) {
            logWriter.print(selectedOptions[i] + "\t");
        }
        logWriter.println(selectedOptions[ selectedOptions.length - 1]);
        logWriter.println("#SCREENSHOTDIR:");
        logWriter.println(screenShotDir);
        String moveDir = Utils.makeNewSubjectDir(path, firstName, lastName);
        logWriter.close();
        Utils.move(new File(path + "\\" + fileName), new File(moveDir + "\\" + fileName));
        File f = new File(moveDir + "\\" + screenShotDir);
        f.mkdir();
        for (File file : (new File(screenShotDir).listFiles())) {
            Utils.move(file, new File(f.getAbsolutePath() + "\\" + file.getName()));
        }
        (new File(screenShotDir)).delete();

        //----------------------------------------------------------------------
        signalPanel.setBackground(Color.black);
        frame.remove(nextQuestionButton);
        frame.remove(previousQuestionButton);
        frame.remove(sout);
        frame.remove(questionPanel);

        // frame.remove(northPanel);
        msgPanel.setText("<html><body><p style=\"text-align:center;font-size:60px;margin:40px;color:red;\"><br><br>Thank you for your contribution!</p></body></html>");
        msgPanel.setEditable(true);

        msgPanel.setEditable(false);

        frame.add(msgPanel);

        //northPanel.remove(clockLabel);
        frame.validate();
        frame.repaint();
        if (isByUser) {
            clearTimer = new Timer(2000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        for (int i = 0; i < 5; i++) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            SoundUtils.tone(900, 500, 0.5);
                        }

                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    System.exit(0);

                }
            });
            clearTimer.setRepeats(false);
            clearTimer.start();
        } else {

            try {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    SoundUtils.tone(900, 500, 0.5);
                }

            } catch (LineUnavailableException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("");
            for (int selected : selectedOptions) {
                System.out.print((selected + 1) + " ");
            }
            System.out.println("");
            System.exit(0);
        }

    }

    public static void updateAnswerSheetView() {
        for (int j = 0; j < numberOfQuestions; j++) {
            if (selectedOptions[j] != -1) {
                answerSheetGrid[1][j].setBackground(Color.GREEN);
            } else {
                answerSheetGrid[1][j].setBackground(Color.WHITE);
            }
        }
    }

    public static void startTask() throws InterruptedException, FileNotFoundException, BadLocationException, ParseException, AWTException, IOException {
        Constants.init();
        
        preTimer = Constants.PRE_TIMER;
        startFlag = false;
        frame = new JFrame();
        //new InputFrame().setVisible(true);
        startFlag = true;
        while (!startFlag) {
            Thread.sleep(1000);
        }

        opMap.put(1, "A");
        opMap.put(2, "B");
        opMap.put(3, "C");
        opMap.put(4, "D");

        questions = Utils.loadQuestions(QS_PATH);
        numberOfQuestions = questions.length;
        selectedOptions = new int[numberOfQuestions];
        for(int i = 0 ; i < numberOfQuestions ; i++){
            selectedOptions[i] = -1;
        }
        /*examTime = Utils.getQuestionsTime(Constants.LESSON6_QS_PATH);
         examTime = TEST_TIME / 1000;
         clock = new Clock(examTime);*/
        examTime = TEST_TIME;
        examTime = TEST_TIME / 1000;
        clock = new Clock(examTime);

        answerSheet = new JPanel(new GridLayout(2, numberOfQuestions));
        answerSheetGrid = new JPanel[2][numberOfQuestions];
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), Constants.VLC_PATH);
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        int topMargin = Constants.TOP_MARGIN;
        mediaPlayerComponent.setPreferredSize(new Dimension(width, height - topMargin));
        signalPanel.setPreferredSize(new Dimension(200, topMargin));
        signalPanel.setBackground(Color.white);
        northPanel = new JPanel(new GridLayout(1, Constants.SIGNAL_PANEL_DEG + 2));
        northPanel.setBackground(Color.BLACK);
        northPanel.add(signalPanel);
        for (int i = 0; i < Constants.SIGNAL_PANEL_DEG; i++) {
            JPanel p = new JPanel();
            p.setBackground(Color.BLACK);
            northPanel.add(p);
        }
        JPanel nullPanel = new JPanel();
        nullPanel.setBackground(Color.BLACK);
        northPanel.add(nullPanel);

        frame.setLayout(new BorderLayout());

        frame.add(northPanel, BorderLayout.NORTH);

        CenterTestPanel centerTestPanel = new CenterTestPanel();
        frame.add(centerTestPanel, BorderLayout.CENTER);

        frame.toFront();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        if(isBlinker){
            while(true){
                signalPanel.setBackground(Color.RED);
                Thread.sleep(1000);
                signalPanel.setBackground(Color.BLACK);
                Thread.sleep(1000);
                signalPanel.setBackground(Color.WHITE);
                Thread.sleep(1000);
                signalPanel.setBackground(Color.BLACK);
                Thread.sleep(1000);
                
            }
        }

        //---------------------------- <PRE-TIMER Alert> ------------------------
        msgPanel = new JTextPane();
        msgPanel.setContentType("text/html");
        frame.add(msgPanel, BorderLayout.CENTER);
        Thread.sleep(1000);
        frame.validate();
        for (int i = preTimer; i > -1; i--) {
            msgPanel.setText("<html><body><p style=\"text-align:center;font-size:60px;margin:40px;color:red;\"><br>" + (i) + "</p></body></html>");
            msgPanel.setEditable(true);
            msgPanel.setEditable(false);
            Thread.sleep(1000);
            if (i == preTimer) {
                frame.validate();
            }
        }
        frame.remove(msgPanel);
        //----------------------------------------------------------------------

        //-------------------------------<LOG>----------------------------------
        logWriter = new PrintWriter( new File(path + "\\" + fileName));
        logWriter.println(firstName);
        logWriter.println(lastName);
        logWriter.println(birthyear);
        logWriter.println(task);
        logWriter.println("#LOGS");
        logWriter.println(Utils.getEventLog(Constants.CENTER_TEST_START));
        //----------------------------------------------------------------------
        int r = 30;
        centerTestPanel.repaint();
        Thread.sleep(500);
        centerTestPanel.getGraphics().fillOval((Utils.getScreenScreenSize().width / 2) - (r / 2), ((Utils.getScreenScreenSize().height) / 2) - (r / 2) - topMargin, r, r);
        Thread.sleep(4500);
        for (int i = 0; i < 3; i++) {
            centerTestPanel.repaint();
            Thread.sleep(500);
            centerTestPanel.getGraphics().fillOval((Utils.getScreenScreenSize().width / 2) - (r / 2), ((Utils.getScreenScreenSize().height) / 2) - (r / 2) - topMargin, r, r);
            Thread.sleep(500);
            try {
                SoundUtils.tone(500, 250, 0.5);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        frame.remove(centerTestPanel);
        //-------------------------------<LOG>----------------------------------
        logWriter.println(Utils.getEventLog(Constants.CENTER_TEST_END));
        //----------------------------------------------------------------------

        playerPanel.add(mediaPlayerComponent);

        frame.add(playerPanel, BorderLayout.CENTER);
        frame.pack();

        //-------------------------------<LOG>----------------------------------
        logWriter.println(Utils.getEventLog(Constants.MUL_START));
        //----------------------------------------------------------------------

        mediaPlayerComponent.getMediaPlayer().playMedia(MUL_PATH);
        signalPanel.setBackground(Color.black);
        if (!QS_ONLY) {
            t.sleep(VID_TIME);
        }

        signalPanel.setBackground(Color.white);
        mediaPlayerComponent.getMediaPlayer().stop();

        //-------------------------------<LOG>----------------------------------
        logWriter.println(Utils.getEventLog(Constants.MUL_END));
        //----------------------------------------------------------------------

        frame.remove(playerPanel);

        //---------------------------- <Question Alert> ------------------------
        msgPanel.setText("<html><body><p style=\"text-align:center;font-size:25px;margin:40px;\"><br><br>In the next step, you have to answer " + numberOfQuestions + " questions within " + clock.getStirng() + ". The questions are about the concepts outlined in the multimedia.</p></body></html>");
        frame.add(msgPanel, BorderLayout.CENTER);
        msgPanel.setEditable(true);
        msgPanel.setEditable(false);
        frame.validate();
        Thread.sleep(13000);
        frame.validate();
        //----------------------------------------------------------------------

        //---------------------- <Question Start Timer> ------------------------        
        for (int i = 5; i > -1; i--) {
            msgPanel.setText("<html><body><p style=\"text-align:center;font-size:60px;margin:40px;color:red;\"><br>" + (i) + "</p></body></html>");
            msgPanel.setEditable(true);
            msgPanel.setEditable(false);
            Thread.sleep(1000);
            if (i == 1) {
                frame.validate();
            }
        }
        frame.remove(msgPanel);
        //----------------------------------------------------------------------

        signalPanel.setBackground(Color.black);
        Thread.sleep(500);
        signalPanel.setBackground(Color.white);

        frame.add(nextQuestionButton, BorderLayout.EAST);
        frame.add(previousQuestionButton, BorderLayout.WEST);
        frame.add(questionPanel, BorderLayout.CENTER);

        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
        document.insertString(document.getLength(), "\n\n(1) " + questions[0].getQuestionDescription().replace("<br>", "\n"), style);
        questionPane.setDocument(document);

        questionPane.setFont(new Font("Tahoma", 0, Constants.QUESTIONS_FONT_SIZE));
        questionPane.setEditable(false);
        questionPanel.add(questionPane);
        //questionPane.setBorder(new LineBorder(Color.BLACK));
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bg = new ButtonGroup();
        int size = Constants.OPTIONS_FONT_SIZE;

        ImageIcon image = new ImageIcon(Constants.RESOURCES_PATH + "\\assets\\images\\eraser.png");
        clearAnswerButton.setIcon(image);
        clearAnswerButton.setPreferredSize(new Dimension(100, 100));
        clearAnswerButton.setMaximumSize(new Dimension(100, 100));
        clearPanel.add(clearAnswerButton);
        clearPanel.setBackground(Color.WHITE);
       /*
        for (int i = 0; i < 6; i++) {
            JPanel e = new JPanel();
            e.setBackground(Color.white);
            clearPanel.add(e);
        }
*/
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton("\n\n(" + (opMap.get(i + 1)) + ") " + questions[0].getOptions()[i]);
            optionsPanel.add(options[i]);
            clearAnswerButton.setBackground(Color.WHITE);

            options[i].setFont(new Font("Tahoma", 0, size));
            options[i].setBackground(Color.WHITE);
            bg.add(options[i]);
        }

        questionPanel.add(optionsPanel);
        questionPanel.add(clearPanel);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numberOfQuestions; j++) {
                answerSheetGrid[i][j] = new JPanel();
                answerSheet.add(answerSheetGrid[i][j]);
                if (i == 0) {
                    JButton goToQBt = new JButton((j + 1) + "");
                    final int jj = j;
                    answerSheetGrid[i][j].add(goToQBt);
                    goToQBt.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                goToQuestion(jj);
                            } catch (AWTException ex) {
                                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
                answerSheetGrid[i][j].setBorder(new LineBorder(Color.BLACK));
            }
        }

        sout.add(answerSheet, BorderLayout.CENTER);
        sout.add(finishBt, BorderLayout.SOUTH);
        frame.add(sout, BorderLayout.SOUTH);

        //-------------------------------<LOG>----------------------------------
        logWriter.println(Utils.getEventLog(Constants.QS_START));
        //----------------------------------------------------------------------

        goToQuestion(0);

        finishBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //-------------------------------<LOG>----------------------------------
                    logWriter.println(Utils.getEventLog(Constants.QS_FINISHBT_PRESSED));
                } catch (AWTException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
                //----------------------------------------------------------------------
                int ans = JOptionPane.showConfirmDialog(frame, "Are you sure to finish?", "", JOptionPane.YES_NO_OPTION);

                if (ans == 0) {
                    try {
                        endOperation(true);
                    } catch (AWTException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        //-------------------------------<LOG>----------------------------------
                        logWriter.println(Utils.getEventLog(Constants.QS_FINISHBT_CANCELED));
                        //----------------------------------------------------------------------
                    } catch (AWTException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        nextQuestionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    changeQuestion(true);
                } catch (AWTException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        previousQuestionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    changeQuestion(false);
                } catch (AWTException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frame.validate();

        clockThread = new Thread(new Runnable() {

            @Override
            public void run() {
                /*
                 JPanel nullPanel = new JPanel();
                 nullPanel.setBackground(Color.WHITE);
                 northPanel.add(nullPanel);
                 */
                northPanel.remove(nullPanel);
                clockLabel = new JLabel(clock.toString(), SwingConstants.CENTER);
                clockLabel.setFont(new Font("Tahoma", Font.PLAIN, Constants.CLOCK_FONT_SIZE));
                clockLabel.setBackground(Color.BLACK);
                clockLabel.setForeground(Color.WHITE);
                clockLabel.setOpaque(true);
                northPanel.add(clockLabel);
                while (!clock.isFinished()) {
                    clock.subtract();
                    clockLabel.setText(clock.toString() + "    ");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    endOperation(false);
                } catch (AWTException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        });
        clockThread.run();

    }

    public static void main(String args[]) throws InterruptedException, FileNotFoundException, BadLocationException, ParseException, AWTException, IOException {
        startTask();
    }

}
