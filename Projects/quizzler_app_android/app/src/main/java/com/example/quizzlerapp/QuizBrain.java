package com.example.quizzlerapp;

import java.util.ArrayList;
import java.util.List;

public class QuizBrain {
    private List<Question> questionBank = new ArrayList<>();
    private int currentIndex = 0;

    public QuizBrain() {
        questionBank = new ArrayList<>();

        questionBank.add(new Question("Some cats are actually allergic to humans", true));
        questionBank.add(new Question("You can lead a cow down stairs but not up stairs.", false));
        questionBank.add(new Question("Approximately one quarter of human bones are in the feet.", true));
        questionBank.add(new Question("A slug's blood is green.", true));
        questionBank.add(new Question("Buzz Aldrin's mother's maiden name was \"Moon\".", true));
        questionBank.add(new Question("It is illegal to pee in the Ocean in Portugal.", true));
        questionBank.add(new Question("No piece of square dry paper can be folded in half more than 7 times.", false));
        questionBank.add(new Question("In London, UK, if you happen to die in the House of Parliament, you are entitled to a state funeral.", false));
        questionBank.add(new Question("The loudest sound produced by any animal is 188 decibels. That animal is the African Elephant.", false));
        questionBank.add(new Question("The total surface area of two human lungs is approximately 70 square metres.", true));
        questionBank.add(new Question("Google was originally called 'Backrub'.", true));
        questionBank.add(new Question("Chocolate affects a dog's heart and nervous system; a few ounces are enough to kill a small dog.", true));
        questionBank.add(new Question("In West Virginia, USA, if you accidentally hit an animal with your car, you are free to take it home to eat.", true));
    }


    public Question getCurrentQuestion() {
        return questionBank.get(currentIndex);
    }

    public void nextQuestion() {
        if (currentIndex < questionBank.size() - 1) {
            currentIndex++;
        }
    }

    public boolean hasMoreQuestions() {
        return currentIndex < questionBank.size() - 1;
    }

    public void reset() {
        currentIndex = 0;
    }
}
