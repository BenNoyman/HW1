package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.hw1.Logic.GameManager;
import com.example.hw1.Utilities.SignalManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer timer;
    private ExtendedFloatingActionButton main_LBL_left;
    private ExtendedFloatingActionButton main_LBL_right;
    private AppCompatImageView[] main_IMG_planes;
    private AppCompatImageView[][] main_IMG_obstacles;
    private AppCompatImageView[] main_IMG_hearts;
    private boolean timerOn = false;
    GameManager gameManager;
    SignalManager signalManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager();
        signalManager.init(this);
        initViews();
    }

    private void findViews() {
        main_LBL_left = findViewById(R.id.main_LBL_left);
        main_LBL_right = findViewById(R.id.main_LBL_right);
        main_IMG_planes = new AppCompatImageView[]{
                findViewById(R.id.LeftPlane),
                findViewById(R.id.MidPlane),
                findViewById(R.id.RightPlane),
        };
        main_IMG_obstacles = new AppCompatImageView[][]{
                {findViewById(R.id.main_LBL_api1), findViewById(R.id.main_LBL_api2), findViewById(R.id.main_LBL_api3)},
                {findViewById(R.id.main_LBL_api4), findViewById(R.id.main_LBL_api5), findViewById(R.id.main_LBL_api6),},
                {findViewById(R.id.main_LBL_api7), findViewById(R.id.main_LBL_api8), findViewById(R.id.main_LBL_api9),},
                {findViewById(R.id.main_LBL_api10), findViewById(R.id.main_LBL_api11), findViewById(R.id.main_LBL_api12),},
                {findViewById(R.id.main_LBL_api13), findViewById(R.id.main_LBL_api14), findViewById(R.id.main_LBL_api15)}
        };
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
    }

    private void initViews() {

        for (int i = 0; i < main_IMG_planes.length; i++) {
            if (gameManager.getCurrentPlane()[i] == 1)
                main_IMG_planes[i].setVisibility(View.VISIBLE);
            else
                main_IMG_planes[i].setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < main_IMG_obstacles.length - 1; i++) {
            for (int j = 0; j < main_IMG_obstacles[0].length; j++) {
                main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
            }
        }
        main_IMG_obstacles[4][0].setVisibility(View.INVISIBLE);
        main_IMG_obstacles[4][1].setVisibility(View.INVISIBLE);
        main_IMG_obstacles[4][2].setVisibility(View.INVISIBLE);

        startTimer();

        main_LBL_left.setOnClickListener(v -> moveLeft());
        main_LBL_right.setOnClickListener(v -> moveRight());
    }

    private void moveLeft() {
        if (gameManager.getCurrentPlane()[2] == 1) {
            main_IMG_planes[0].setVisibility(View.INVISIBLE);
            main_IMG_planes[1].setVisibility(View.VISIBLE);
            main_IMG_planes[2].setVisibility(View.INVISIBLE);
            gameManager.getCurrentPlane()[0] = 0;
            gameManager.getCurrentPlane()[1] = 1;
            gameManager.getCurrentPlane()[2] = 0;
        } else {
            main_IMG_planes[0].setVisibility(View.VISIBLE);
            main_IMG_planes[1].setVisibility(View.INVISIBLE);
            main_IMG_planes[2].setVisibility(View.INVISIBLE);
            gameManager.getCurrentPlane()[0] = 1;
            gameManager.getCurrentPlane()[1] = 0;
            gameManager.getCurrentPlane()[2] = 0;
        }
    }

    private void moveRight() {
        if (gameManager.getCurrentPlane()[0] == 1) {
            main_IMG_planes[0].setVisibility(View.INVISIBLE);
            main_IMG_planes[1].setVisibility(View.VISIBLE);
            main_IMG_planes[2].setVisibility(View.INVISIBLE);
            gameManager.getCurrentPlane()[0] = 0;
            gameManager.getCurrentPlane()[1] = 1;
            gameManager.getCurrentPlane()[2] = 0;

        } else {
            main_IMG_planes[0].setVisibility(View.INVISIBLE);
            main_IMG_planes[1].setVisibility(View.INVISIBLE);
            main_IMG_planes[2].setVisibility(View.VISIBLE);
            gameManager.getCurrentPlane()[0] = 0;
            gameManager.getCurrentPlane()[1] = 0;
            gameManager.getCurrentPlane()[2] = 1;
        }
    }

    private void startTimer() {
        if (!timerOn) {
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> updateUI());
                }
            }, 0L, 750); // Update every 1 second
        }
    }

    private void updateUI() {
        gameManager.updateObstacleUI();

        for (int i = 0; i < gameManager.getCurrentObstacles().length; i++) {
            for (int j = 0; j < gameManager.getCurrentObstacles()[0].length; j++) {
                if (gameManager.getCurrentObstacles()[i][j] == 1)
                    main_IMG_obstacles[i][j].setVisibility(View.VISIBLE);
                else
                    main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
            }
        }

        for (int i = 0; i < gameManager.getCurrentPlane().length; i++) {
            if (gameManager.getCurrentObstacles()[gameManager.getCurrentObstacles().length - 1][i] == 1 &&
                    gameManager.getCurrentPlane()[i] == 1){
                main_IMG_hearts[gameManager.getCrash()-1].setVisibility(View.INVISIBLE);
                main_IMG_obstacles[4][i].setVisibility(View.INVISIBLE); //after the crash the obstacle will not continue
                SignalManager.getInstance().toast("BOOM 💥");
                SignalManager.getInstance().vibrate(300L);
            }
        }
        if (gameManager.getCrash() >= 3) {
            timer.cancel();
            changeActivity("GAME OVER");
        }
    }

    private void changeActivity(String status) {
        Intent intent = new Intent(this, EndOfGameActivity.class);
        intent.putExtra(EndOfGameActivity.KEY_STATUS, status);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }
}