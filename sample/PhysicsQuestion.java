package sample;

import java.util.Random;

public class PhysicsQuestion implements IQuestion {

    // used in generating random numbers
    private Random rand = new Random();

    // randomly chose number that corresponds to the type of question to be created
    private int questionNum;

    // variables used in the various questions
    private double var1;
    private double var2;
    private double var3;

    // computed answer in double form
    private double ans;

    // the final question and answer values
    private String question;
    private String answer;


    PhysicsQuestion() {
        questionNum = rand.nextInt(5) + 1;
        createQuestion();
        computeAnswer();
    }

    @Override
    public void createQuestion() {
        switch (questionNum) {

            // Question Type 1: Stress
            case 1:
                var1 = (rand.nextInt(401) + 100) / 100.0;    // radius of rope, from 1.00 to 5.00 cm
                var2 = rand.nextInt(91) + 10 / 1.0;    // force, value from 10 to 100 N

                question = "A piece of rope has a radius of " + var1 + " cm, pulled by a force of " + var2 + " N. Determine the stress in Pascals.";
                break;

            // Question Type 2: Specific Heat
            case 2:
                var1 = rand.nextInt(901) + 100 / 1.0;   // mass, from 100 to 1000 g
                var2 = rand.nextInt(31) + 20 / 1.0;   // initial temperature, from 20 to 50 C
                var3 = rand.nextInt(31) + var2 + 1 / 1.0;   // final temperature, from 1 to 30 above initial temp

                question = "A " + var1 + " gram cube of lead is heated from " + var2 + " °C to " + var3 + " °C. How much energy was required to heat the lead? The specific heat of lead is 0.129 J/g°C.";
                break;

            // Question Type 3: Moment of Inertia
            case 3:
                var1 = rand.nextInt(91) + 10 / 1.0;     // mass, from 10 to 100 kg
                var2 = (rand.nextInt(401) + 100) / 100.0;    // radius of sphere, from 1.00 to 5.00 m

                question = "A sphere has a mass of " + var1 + " kg and a radius of " + var2 + " m. If the axis of rotation is located at the center, what is its moment of inertia?";
                break;

            // Question Type 4: Angular Momentum
            case 4:
                var1 = rand.nextInt(20) + 1 / 1.0;   // moment of inertia, from 1 to 20 kg * m^2
                var2 = rand.nextInt(20) + 1 / 1.0;   // angular velocity, from 1 to 20 rad/s

                question = "An object with a moment of inertia of " + var1 + " kg*m^2 rotates at " + var2 + " rad/s. What is the angular momentum of the object?";
                break;

            // Question Type 5: Flow Rate
            case 5:
                var1 = rand.nextInt(91) + 10 / 1.0;     // capacity, from 10 to 100 m^3
                var2 = rand.nextInt(271) + 30 / 1.0;    // time, from 30 to 300 seconds

                question = "A tank of capacity " + var1 + " m^3 takes " + var2 + " seconds to fill. Compute the flow rate.";
                break;
        }
    }

    @Override
    public void computeAnswer() {
        switch (questionNum) {

            // Question Type 1: Stress
            case 1:
                ans = var2 / (Math.PI * var1 * var1);   // solves for answer
                ans = Math.round(ans * 100.0) / 100.0;  // rounds the number to two decimal places, do this for every answer
                answer = Double.toString(ans);          // converts answer to string, the one to be used in the actual program
                break;

            // Question Type 2: Specific Heat
            case 2:
                ans = var1 * 0.129 * (var3 - var2);
                ans = Math.round(ans * 100.0) / 100.0;
                answer = Double.toString(ans);
                break;

            // Question Type 3: Moment of Inertia
            case 3:
                ans = (2.0 / 5.0) * var1 * var2 * var2;
                ans = Math.round(ans * 100.0) / 100.0;
                answer = Double.toString(ans);
                break;

            // Question Type 4: Angular Momentum
            case 4:
                ans = var1 * var2;
                ans = Math.round(ans * 100.0) / 100.0;
                answer = Double.toString(ans);
                break;

            // Question Type 5: Flow Rate
            case 5:
                ans = var1 / var2;
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
