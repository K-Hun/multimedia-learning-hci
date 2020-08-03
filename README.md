# multimedia-learning-hci
- An HCI software for video multimedia learning and performance test.
- Just put the instructional videos and your question file and configure the software. It plays the instructional video, then runs the performance test for the participant. The procedure is integrated and continuous, with a detailed logger for behavioral analysis. This software can be used in many educational cognitive neuroscience types of research.
- It's a part of my MSc thesis at [ebrahimpourlab](http://ebrahimpourlab.ir/people/).

# Features
  - Cross-platform (MS Windows, macOS, and Linux)
  - Simply configurable
  - Full screen and minimal design to avoid imposing an extra cognitive load on the participants
  - Area for logging with EEG optical trigger for EEG based researches
  - With a baseline phase and calibration for eye-tracking based researches
  - Adjustable fonts and blocks sizes
  - Detailed logger for behavioral analysis
  - Ability to play any format of videos, powered by [vlcj](https://github.com/caprica/vlcj)



![Features](http://blog.k-hun.ir/wp-content/uploads/2020/08/soft-fig.png)
*A: Fully screen multimedia playing.
B: Performance test phase.
A1 and B1:  Optical color change trigger for EEG based research
B2: Remaining time to answer the performance test.
B3: A button to show the next question, also there is a corresponding button at the left side of the screen to show the previous question.
B4: A button to clear the selected option of the current question.
B5: An overview and status of the questions which can help the subject to know which questions are answered and which ones are not answered yet. A participant can access any question by clicking on the question number.
B.6: A button to terminate the performance test by the subject. a confirmation will show after clicking on this button. It should be mention that the task will be finished automatically if the remaining time is up.*

# Sample log file

```sh
Kayhan
Latifzadeh
1994
11P
#LOGS
CENTER_TEST_START @ 2020-06-19 02:24:43:857
CENTER_TEST_END @ 2020-06-19 02:24:54:060
MUL_START @ 2020-06-19 02:24:54:211
MUL_END @ 2020-06-19 02:25:54:423
QS_START @ 2020-06-19 02:26:14:335
Q_1 @ 2020-06-19 02:26:14:430
Q_2 @ 2020-06-19 02:26:18:738
QANS_2_3 @ 2020-06-19 02:26:21:704
QCLR_2 @ 2020-06-19 02:26:23:035
Q_1 @ 2020-06-19 02:26:24:787
Q_1 @ 2020-06-19 02:26:25:622
Q_2 @ 2020-06-19 02:26:27:313
Q_3 @ 2020-06-19 02:26:28:069
Q_4 @ 2020-06-19 02:26:28:844
Q_5 @ 2020-06-19 02:26:29:662
Q_12 @ 2020-06-19 02:26:31:312
Q_11 @ 2020-06-19 02:26:32:408
Q_6 @ 2020-06-19 02:26:34:585
QCLR_6 @ 2020-06-19 02:26:36:094
QANS_6_2 @ 2020-06-19 02:26:37:002
Q_8 @ 2020-06-19 02:26:38:814
QANS_8_4 @ 2020-06-19 02:26:40:123
Q_12 @ 2020-06-19 02:26:43:074
QANS_12_4 @ 2020-06-19 02:26:43:909
Q_13 @ 2020-06-19 02:26:45:433
QANS_13_2 @ 2020-06-19 02:26:46:357
Q_2 @ 2020-06-19 02:26:48:553
QANS_2_3 @ 2020-06-19 02:26:49:449
QANS_2_2 @ 2020-06-19 02:26:51:655
QS_FINISHBT_PRESSED @ 2020-06-19 02:26:57:447
QS_FINISH_USER @ 2020-06-19 02:26:58:826
#LOG_FINISH @ 2020-06-19 02:26:58:927
#ANSWESHEET:
-1	1	-1	-1	-1	1	-1	3	-1	-1	-1	3	1
#SCREENSHOTDIR:
C:\Users\lab\Desktop\data\kl\shots

```

# Screenshots
![Participant's and task information inputs](http://blog.k-hun.ir/wp-content/uploads/2020/08/hci0.png)
*Participant and task information inputs*

![Eye-tracking baseline and calibration](http://blog.k-hun.ir/wp-content/uploads/2020/08/mv1.png)
*Eye-tracking baseline and calibration*

![Playing multimedia](http://blog.k-hun.ir/wp-content/uploads/2020/08/mv2.png)
*Playing multimedia*

![Performance test ](http://blog.k-hun.ir/wp-content/uploads/2020/08/hci3-Copy.png)
*Performance test*

### Todos

 - Code cleaning
 - Publish the executable standalone version
 - Write the manual file

License
----

MIT
