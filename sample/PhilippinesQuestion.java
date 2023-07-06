package sample;

import sample.IQuestion;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class PhilippinesQuestion implements IQuestion {

    // used in generating random numbers
    private Random rand = new Random();

    // randomly chose number that corresponds to the type of question to be created
    private int questionNum;

    // the final question and answer values
    private String question;
    private String answer;

    PhilippinesQuestion() {
        questionNum = rand.nextInt(10) + 1;
        createQuestion();
    }

    @Override
    public void createQuestion() {
        File file = new File("src/sample/PhilQuestionsList.txt");
        Scanner scanner;

        try {
            scanner = new Scanner(file);

            for (int i = 0; i < questionNum - 1; i++) {
                scanner.nextLine();
                scanner.nextLine();
            }

            question = scanner.nextLine();
            answer = scanner.nextLine();

            scanner.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void computeAnswer() {
        // Space for code involving math computations
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getAnswer() {
        return answer;
    }
}
