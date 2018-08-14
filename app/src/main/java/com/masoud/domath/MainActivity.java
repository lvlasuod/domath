package com.masoud.domath;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // declare ui elements
    Button goButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button retryButton;
    TextView timerTextView;
    TextView scoreTextView;
    TextView formulaTextView;
    TextView resultTextView;
    ConstraintLayout gameLayout;
    //declare variables
    ArrayList<Integer> answers=new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score=0;
    int numberOfQuestions=0;

    public void chooseAnswer(View view){
        if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            resultTextView.setText("Correct");
            resultTextView.setBackgroundColor(getResources().getColor(R.color.colorCorrect));
            score++;
        }else{
            resultTextView.setText("Wrong :(");
            resultTextView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        numberOfQuestions++;
        scoreTextView.setText(score+" / "+numberOfQuestions);
        newQuestion();
    }

    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
        playAgain(findViewById(R.id.retryButton));
        gameLayout.setVisibility(View.VISIBLE);


    }

    public void newQuestion(){
        Random random=new Random();

        int a=random.nextInt(21);
        int b=random.nextInt(21);

        formulaTextView.setText(Integer.toString(a) +" + "+Integer.toString(b));

        locationOfCorrectAnswer=random.nextInt(4);

        answers.clear();
        for(int i=0;i<4;i++){
            if(i==locationOfCorrectAnswer){
                //add correct answer
                answers.add(a+b);
            }else{
                int wrongAnswer=random.nextInt(41);
                while (wrongAnswer==a+b) {
                    wrongAnswer=random.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void playAgain(View view){
        score=0;
        numberOfQuestions=0;
        resultTextView.setText(" ");
        timerTextView.setText("30s");
        scoreTextView.setText("0 / 0");
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        retryButton.setVisibility(View.INVISIBLE);
        newQuestion();
        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
                if((millisUntilFinished/1000)<=5){
                    MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.clunks);
                    mediaPlayer.start();
                }
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                retryButton.setVisibility(View.VISIBLE);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);

            }
        }.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fetching views from UI
        goButton = findViewById(R.id.goButton);
        button0=findViewById(R.id.button0);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        retryButton=findViewById(R.id.retryButton);

        formulaTextView=findViewById(R.id.quizTextView);
        resultTextView=findViewById(R.id.resultTextView);
        scoreTextView=findViewById(R.id.scoreTextView);
        timerTextView=findViewById(R.id.timerTextView);


        gameLayout=findViewById(R.id.gameLayout);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);


    }
}
