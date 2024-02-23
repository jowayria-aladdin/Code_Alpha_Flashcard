package flashcard;

public class Flashcard {
    public static void main(String[] args) {
        Flashcard flashcard = new Flashcard();
        flashcard.startQuiz();
    }

    private void startQuiz() {
        QuizCardBuilder quizCardBuilder = new QuizCardBuilder(new Deck());
        quizCardBuilder.build();
}
}