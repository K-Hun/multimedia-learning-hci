

/**
 *
 * @author K Hun
 */
public class Question {
    private String questionDescription;
    private int numberOfOptions;
    private String options[];
    private int key;

    public Question(String description , int numOfOption , String options[] , int key) {
        this.questionDescription = description;
        this.numberOfOptions = numOfOption;
        this.options = new String[numberOfOptions];
        for(int i = 0 ; i < this.numberOfOptions ; i++){
            this.options[i] = options[i];
        }
        this.key = key;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public int getNumberOfOptions() {
        return numberOfOptions;
    }

    public String[] getOptions() {
        return options;
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString() {
        String str = this.getQuestionDescription();
        for(int i = 0 ; i < this.numberOfOptions ; i++){
            str = str+"\n("+(i+1)+") " + this.options[i];
        }
       return str;
    }
    
    
}
