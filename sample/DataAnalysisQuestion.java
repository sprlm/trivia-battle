package sample;

import java.util.Random;

public class DataAnalysisQuestion implements IQuestion {

    // used in generating random numbers
    private Random rand = new Random();

    // randomly chose number that corresponds to the type of question to be created
    private int questionNum;

    // variables used in the various questions
    private int var1;
    private int var2;
    private double var3;

    // computed answer in double form
    private double ans;

    // the final question and answer values
    private String question;
    private String answer;


    DataAnalysisQuestion() {
        questionNum = rand.nextInt(5) + 1;
        createQuestion();
        computeAnswer();
    }

    @Override
    public void createQuestion() {
        switch (questionNum) {

            // Question Type 1: Z-score
            case 1:
                var1 = rand.nextInt(31) + 40;    // mean from 40 to 70
                var2 = rand.nextInt(61) + (var1 - 30);  // value within 30 of mean
                var3 = (rand.nextInt(401) + 100) / 100.0;  // standard deviation from 1.00 to 5.00

                question = "The average score of an exam is " + var1 +" with a standard deviation of " + var3 + ". If you got a score of " + var2 + ", what is the z-score of your grade?";
                break;

            // Question Type 2: Circle Combinatorics
            case 2:
                var1 = rand.nextInt(8) + 3; // num from 3 to 10

                question = "How many ways can you seat " + var1 + " people on a circular table?";
                break;

            // Question Type 3: Row Combinatorics
            case 3:
                var1 = rand.nextInt(8) + 3; // num from 3 to 10

                question = "How many ways can you seat " + var1 + " people in a row?";
                break;

            // Question Type 4: Permutation
            case 4:
                var1 = rand.nextInt(46) + 5;    // val from 5 to 50

                question = "How many ways can first and second place be awarded to " + var1 +" people?";
                break;

            // Question Type 5: Standard Error
            case 5:
                var1 = rand.nextInt(71) + 30;    // sample size from 30 to 100
                var3 = (rand.nextInt(401) + 100) / 100.0;   // standard deviation from 1.00 to 5.00

                question = "A data set has a sample size of " + var1 + " with a standard deviation of " + var3 + ". Find the standard error.";
                break;

        }
    }

    @Override
    public void computeAnswer() {
        switch (questionNum) {

            // Question Type 1: Z-score
            case 1:
                ans = (var2 - var1) / var3;
                ans = Math.round(ans * 100.0) / 100.0;
                answer = Double.toString(ans);
                break;

            // Question Type 2: Circle Combinatorics
            case 2:
                ans = 1.0;
                for (int i = var1 - 1; i > 1; i--) {
                    ans *= i;
                }

                answer = Integer.toString((int) ans);
                break;

            // Question Type 3: Row Combinatorics
            case 3:
                ans = 1;
                for (int i = var1; i > 1; i--) {
                    ans *= i;
                }

                answer = Integer.toString((int) ans);
                break;

            // Question Type 4: Permutation
            case 4:
                ans = var1 * (var1 - 1);
                answer = Integer.toString((int) ans);
                break;

            //  Question Type 5: Standard Error
            case 5:
                ans = var3 / Math.sqrt(var1);
                ans = Math.round(ans * 100.0) / 100.0;
                answer = Double.toString(ans);
                break;

        }
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
