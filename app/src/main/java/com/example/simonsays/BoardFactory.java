package com.example.simonsays;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;


public class BoardFactory extends AppCompatActivity {
    private Button[] buttons;
    private Player mPlayer;
    private String typeBoard;
    private eTurnPlayerOrSimon mTurn = eTurnPlayerOrSimon.simon;
    private static BoardFactory instance;
    private Map<String, Integer> numberOfButtonsInBoard = new HashMap<String, Integer>();
    private Map<String, Integer> pressDifficulties = new HashMap<String, Integer>();
    private Map<String, Double> timeDifficulties = new HashMap<String, Double>();
    private Map<String, Class> layouts = new HashMap<String, Class>();
    private Context context;
    private GameLogic gameLogic;
    private MediaPlayer[] sounds;
    private String difficult;


    public static BoardFactory getInstance() {
        if (instance == null) instance = new BoardFactory();

        return instance;
    }

    public BoardFactory() {

        numberOfButtonsInBoard.put("normal", 4);
        numberOfButtonsInBoard.put("drum", 5);
        numberOfButtonsInBoard.put("piano", 6);
        numberOfButtonsInBoard.put("xylophone", 7);

        pressDifficulties.put("beginner", 1);
        pressDifficulties.put("amateur", 3);
        pressDifficulties.put("professional", 5);

        timeDifficulties.put("beginner", 1.0);
        timeDifficulties.put("amateur", 0.7);
        timeDifficulties.put("professional", 0.4);

        layouts.put("normal", NormalActivity.class);
        layouts.put("drum", DrumActivity.class);
        layouts.put("piano", PianoActivity.class);
        layouts.put("xylophone", XylophoneActivity.class);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void boardInitialize() {
        buttons = new Button[numberOfButtonsInBoard.get(typeBoard)];
        Class nextLayout = layouts.get(typeBoard);
        Intent intent = new Intent(context, nextLayout);
        context.startActivity(intent);
        finish();
    }

    public void logicInitialize(TextView playerNameTv,TextView score) {
        gameLogic = new GameLogic(this.context.getResources().getString(R.string.simon),this.context.getResources().getString(R.string.your_turn),playerNameTv,score, sounds);
        setButtonsListeners();
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public void setPlayer(String player) {
        mPlayer = new Player(player);
    }

    public String getTypeBoard() {
        return typeBoard;
    }

    public void setTypeBoard(String typeBoard) {
        this.typeBoard = typeBoard;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public void setButtons(Button[] mButtons) {
        this.buttons = mButtons;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public Map<String, Integer> getPressDifficulties() {
        return pressDifficulties;
    }

    public Map<String, Double> getTimeDifficulties() {
        return timeDifficulties;
    }

    //This function init the sound of each button
    private void setButtonsListeners() {

        for (int btnNum = 0; btnNum < buttons.length; btnNum++) {
            final int soundToPlay = btnNum;
            buttons[btnNum].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (gameLogic.getTurn() == eTurnPlayerOrSimon.player) {
                        if(gameLogic.getPlayerClicks().size() == 0){
                            gameLogic.getPlayerClicks().add(0);

                        }
                        gameLogic.playOneSound(soundToPlay);
                        gameLogic.playerPlay(soundToPlay);
                    } else {
                        gameLogic.playOneSound(soundToPlay);
                    }
                }
            });
        }
    }


    public void setSounds(MediaPlayer[] sounds) {
        this.sounds = sounds;
    }
}
