package flashcard;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class QuizCardBuilder {
    private Deck deck;
    private JButton button;
    private JFileChooser fileChooser = new JFileChooser();
    private JFrame frame;
    private JTextArea answerText = new JTextArea();
    private JTextArea questionText = new JTextArea();
    private JPanel panel;

    private QuizCardPlayer quizCardPlayer;


    public QuizCardBuilder(Deck deck) {
        this.deck = deck;
        createQuizCardPlayer();
    }

    private void addCard(){
        deck.addQuizCard(getQuestionText().getText(), getAnswerText().getText());
        setQuestionText(null);
        setAnswerText(null);
    }

    void build() {
        SwingUtilities.invokeLater(
                () -> {
                        buildFrame();
                        buildContentPane();
                        buildMenuBar();
                        buildLabel(new JLabel("Question:"));
                        buildTextArea(questionText);
                        buildLabel(new JLabel("Answer:"));
                        buildTextArea(answerText);
                        buildButtonPanel();
                        displayFrame();
                        questionText.requestFocusInWindow();
                }
        );

    }

    private void buildButtonPanel() {
        button = new JButton("Add");
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(ev -> addCard());
        panel.add(button);
    }

    private void buildContentPane() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
        frame.setContentPane(panel);
    }

    private void buildFrame() {
        frame = new JFrame("Quiz card builder - " + deck.getFileName());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
            System.exit(0);
                    }
                }
        );
    }

    private void buildLabel(JLabel label) {
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
    }

    private void buildMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        file.add(Exit);

        JMenu card = new JMenu("Deck");
//        card.add(ShuffleDeck);
        card.add(Play);

        jMenuBar.add(file);
        jMenuBar.add(card);
        frame.setJMenuBar(jMenuBar);
    }

    private void buildTextArea(JTextArea jTextArea) {
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                deck.setIsModified(true);
            }
        });
        JScrollPane jsp = new JScrollPane(jTextArea);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(jsp);
    }

    private void createQuizCardPlayer(){
        quizCardPlayer = new QuizCardPlayer(deck);
        quizCardPlayer.registerQuizCardBuilder(this);   
    }

    private void displayFrame() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // GETTERS
    private JTextArea getAnswerText() {
        return answerText;
    }

    JTextArea getQuestionText() {
        return questionText;
    }


    // SETTERS
    private void setAnswerText(String text) {
        SwingUtilities.invokeLater(() -> answerText.setText(text));
    }

    void setTextAreaEditability(boolean isEditable){
        questionText.setEditable(isEditable);
        answerText.setEditable(isEditable);
        button.setEnabled(isEditable);
    }

    private void setTitle(String newTitle){
        SwingUtilities.invokeLater(() -> frame.setTitle("Quiz Card Builder - " + newTitle));
    }

    private void setQuestionText(String text) {
        SwingUtilities.invokeLater(() -> questionText.setText(text));
    }

    // ACTIONS
    private Action Exit = new AbstractAction("Quit"){
        @Override
        public void actionPerformed(ActionEvent ev){
            System.exit(0);
        }
    };

    private Action Play = new AbstractAction("Begin test"){
        @Override
        public void actionPerformed(ActionEvent ev){
            // Prevents window if there's no QuizCards
            if(deck.getQuizCardList().size() > 0) {
                if (deck.getIsTestRunning()) {
                    Toolkit.getDefaultToolkit().beep();
                    quizCardPlayer.toFront();
                } else {
                    deck.setIsTestRunning(true);
                    setTextAreaEditability(false);
                    createQuizCardPlayer();
                    quizCardPlayer.build();
                }
            }
        }
    };

}