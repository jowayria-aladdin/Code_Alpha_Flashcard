package flashcard;

public class QuizCard {
    private String question;
    private String answer;

    public QuizCard(String f, String b){
        setQuestion(f);
        setAnswer(b);
    }

    // GETTERS
    String getAnswer(){
        return answer;
    }

    String getQuestion(){
        return question;
    }


    // SETTERS
    void setAnswer(String text){
        answer = text;
    }

    void setQuestion(String text){
        question = text;
    }
}