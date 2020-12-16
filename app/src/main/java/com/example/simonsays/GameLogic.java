package com.example.simonsays;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private Button[] mButtons;
    private ArrayList<Integer> playerClicks = new ArrayList<Integer>();
    private ArrayList<Integer> simonClicks = new ArrayList<Integer>();
    private eTurnPlayerOrSimon mTurn = eTurnPlayerOrSimon.simon;
    private int currLevel = 1;
    private int score = 0;
    private Context context;
    private CountDownTimer countDownTimer;
    public MediaPlayer soundToPlay;
    private MediaPlayer[] sounds;
    private TextView playerNameTv, scoreTv, simonTv;
    private int toPlay, simonSoundPosition = 0;
    private Random rand = new Random();
    private double timeOfDifficult;
    int difficultPressOnStart;
    boolean firstSimonPlay = true;
    private String computerName;
    private String userName;
    boolean isMatched;

    public GameLogic(String i_computerName,String i_userName, TextView textView, TextView i_score, MediaPlayer[] sounds) {

        mButtons = BoardFactory.getInstance().getButtons();
        playerNameTv = textView;
        scoreTv = i_score;
        computerName = i_computerName;
        userName =i_userName;
        context = BoardFactory.getInstance().getContext();
        this.sounds = sounds;
        timeOfDifficult = BoardFactory.getInstance().getTimeDifficulties().get(BoardFactory.getInstance().getDifficult());
        difficultPressOnStart = BoardFactory.getInstance().getPressDifficulties().get(BoardFactory.getInstance().getDifficult());

        Long timeForEachMove = new Double(timeOfDifficult * 1550L).longValue();
        currLevel = difficultPressOnStart;

        countDownTimer = new CountDownTimer(timeForEachMove, 50L) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (soundToPlay != null) {
                    if (!firstSimonPlay) {
                        if (millisUntilFinished > 300L) mButtons[toPlay].setPressed(true);
                        else {
                            soundToPlay.setVolume(0, 0);
                            mButtons[toPlay].setPressed(false);
                        }
                    } else {
                        soundToPlay.setVolume(0, 0);
                        mButtons[toPlay].setPressed(false);
                    }
                }
            }

            @Override
            public void onFinish() {
                if (mTurn == eTurnPlayerOrSimon.player) {
                    if (playerClicks.size() == simonClicks.size()) {
                        currLevel++;
                        score++;
                        simonSoundPosition = 0;
                        scoreTv.setText(score + "");
                        if (isMatched) {
                            switchTurn();
                            addMoves();
                            simonPlay();
                        }
                    }
                }
                if (firstSimonPlay) {
                    soundToPlay.setVolume(0,0);
                    firstSimonPlay = false;
                    playerNameTv.setText(computerName);
                }
                if (soundToPlay != null) {
                    if (mTurn == eTurnPlayerOrSimon.simon) {
                        simonSoundPosition++;
                        playSimonSounds();
                    }
                }
            }
        };

        simonClicks.add(0);


        for (int i = 0; i < currLevel; i++) {
            addMoves();
        }
    }


    public void addMoves() {
        // check length -1
        int randNum = rand.nextInt(mButtons.length - 1);
        simonClicks.add(randNum);
    }

    public void dictator() {
        isMatched = validateTurn();

        if (!isMatched) {

            if(score==0) {
                Intent intent = new Intent(context, GameOverActivity.class);
                context.startActivity(intent);
            }else {
                Intent intent = new Intent(context, NameForRecordActivity.class);
                intent.putExtra("score", score);
                context.startActivity(intent);
            }
        }

    }


    public void simonPlay() {
        playerClicks.clear(); // should be in switch turn
        playSimonSounds();
    }

    public void playerPlay(int currSound) {
        playerClicks.add(currSound);
        dictator();
    }

    public eTurnPlayerOrSimon getTurn() {
        return mTurn;
    }

    public boolean validateTurn() {
        int currentClick = playerClicks.size();
        boolean res = true;
        int playerLastPressedBtn, simonLastPressedBtn;
        if(currentClick==simonClicks.size()) {
            for (Button button : mButtons) {
                button.setClickable(false);
            }
        }
            playerLastPressedBtn = playerClicks.get(currentClick - 1);
            simonLastPressedBtn = simonClicks.get(currentClick - 1);
            if (playerLastPressedBtn != simonLastPressedBtn) {
                res = false;
            }
        return res;
    }

    public ArrayList<Integer> getPlayerClicks() {
        return playerClicks;
    }

    public void switchTurn() {
            if (mTurn == eTurnPlayerOrSimon.player) {
                firstSimonPlay = true;
                mTurn = eTurnPlayerOrSimon.simon;
                for (Button button : mButtons) {
                    button.setClickable(false);
                }

            } else {
                mTurn = eTurnPlayerOrSimon.player;
                playerNameTv.setText(userName);
                    for (Button button : mButtons) {
                        button.setClickable(true);
                    }

            }
    }


    private void playSimonSounds() {
            if (simonSoundPosition < simonClicks.size()) {
                mButtons[simonClicks.get(simonSoundPosition)].callOnClick();
            } else
                switchTurn();
        }


    public void playOneSound(int toPlay) {
        this.toPlay = toPlay;
        soundToPlay = sounds[toPlay];
        if (!firstSimonPlay) {
            soundToPlay.setVolume(1, 1);
        }
        mButtons[toPlay].setPressed(false);

        if (!firstSimonPlay) {
            soundToPlay.start();
        }
        countDownTimer.start();
        for (int i = 0; i < sounds.length; i++) {
            final int finalCounter = i;
            if (sounds[finalCounter] != sounds[toPlay]) {
                sounds[finalCounter].setVolume(0, 0);
                mButtons[finalCounter].setPressed(false);
            }
        }
    }

}

