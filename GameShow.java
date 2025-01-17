import java.util.*;
import java.util.concurrent.*;

public class gameShow {
    // Questions, Options, and Correct Answers
    static String[] questions = {
        "What is the capital of France?",
        "Who wrote 'Hamlet'?",
        "What is 7 * 8?",
        "What is the largest planet in our solar system?",
        "What does 'HTTP' stand for?"
    };

    static String[][] options = {
        {"A. Berlin", "B. Madrid", "C. Paris", "D. Rome"},
        {"A. Charles Dickens", "B. William Shakespeare", "C. Mark Twain", "D. J.K. Rowling"},
        {"A. 54", "B. 56", "C. 48", "D. 64"},
        {"A. Earth", "B. Jupiter", "C. Saturn", "D. Neptune"},
        {"A. HyperText Transfer Protocol", "B. HighText Transfer Protocol", "C. Hyperlink Transfer Protocol", "D. None of the above"}
    };

    static char[] answers = {'C', 'B', 'B', 'B', 'A'}; // Correct answers

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> questionOrder = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) questionOrder.add(i);

        // Shuffle question order
        Collections.shuffle(questionOrder);

        int score = 0;
        System.out.println("Welcome to the Online Quiz!\n");
        System.out.println("You have 10 seconds to answer each question. If time runs out, you'll move to the next question.\n");

        // Iterate through shuffled questions
        for (int i = 0; i < questionOrder.size(); i++) {
            int questionIndex = questionOrder.get(i);
            System.out.println((i + 1) + ". " + questions[questionIndex]);

            // Display options
            for (String option : options[questionIndex]) {
                System.out.println(option);
            }

            // Timer for the question
            System.out.print("Enter your answer (A, B, C, or D): ");
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> input = executor.submit(scanner::nextLine);
            String userAnswer = "";

            try {
                userAnswer = input.get(10, TimeUnit.SECONDS); // Wait for user input up to 10 seconds
            } catch (TimeoutException e) {
                System.out.println("\nTime's up! Moving to the next question.");
                input.cancel(true);
            } catch (Exception e) {
                System.out.println("\nError occurred. Skipping question.");
            } finally {
                executor.shutdownNow();
            }

            // Validate answer if provided
            if (!userAnswer.isEmpty()) {
                char userChar = Character.toUpperCase(userAnswer.charAt(0));
                if (userChar == answers[questionIndex]) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Wrong! The correct answer was " + answers[questionIndex] + ".\n");
                }
            }
        }

        // Display final score
        System.out.println("Quiz Finished!");
        System.out.println("Your final score is: " + score + "/" + questions.length);

        // Optionally display correct answers
        System.out.println("\nCorrect Answers:");
        for (int i = 0; i < answers.length; i++) {
            System.out.println((i + 1) + ". " + questions[i] + " Correct answer: " + answers[i]);
        }

        scanner.close();
    }
}
