package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {
        ONE, TWO, NO
    }

    Player currentPlayer = Player.ONE;
    Player[] playerChoices = new Player[9];

    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver = false;
    private boolean noWinner = false;

    private Button btnReset;

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < playerChoices.length; i++) {
            playerChoices[i] = Player.NO;
        }

        gridLayout = findViewById(R.id.gridLayout);

        btnReset = findViewById(R.id.btnReset);
        btnReset.setTranslationX(3000);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        });
    }

    public void imageViewTapped(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;

        int imageViewTag = Integer.parseInt(tappedImageView.getTag().toString());

        if (playerChoices[imageViewTag] == Player.NO && gameOver == false) {

            tappedImageView.setTranslationX(-2000);

            playerChoices[imageViewTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.TWO;
            } else {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.ONE;
            }

            tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);

            String winnerOfGame = "";

            for (int[] winnerColumns : winnerRowsColumns) {
                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]] &&
                        playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]] &&
                        playerChoices[winnerColumns[0]] != Player.NO) {

                    buttonVisible();

                    if (currentPlayer == Player.ONE) {
                        winnerOfGame = "Tiger is winner";
                    } else {
                        winnerOfGame = "Lion is winner";
                    }
                    Toast.makeText(this, winnerOfGame, Toast.LENGTH_SHORT).show();
                }

            }
            if (gameOver == false && checkNoWinner()) {
                buttonVisible();
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Set button visible
    public void buttonVisible() {
        btnReset.animate().translationXBy(-3000).setDuration(1000);
        btnReset.setVisibility(View.VISIBLE);
        gameOver = true;
    }

    //Check noWinner
    public boolean checkNoWinner() {
        for (int i = 1; i < playerChoices.length; i++)
            if (playerChoices[i] == Player.NO)
                return false;
        return true;
    }

    //Reset Game Function
    private void resetTheGame() {
        btnReset.animate().translationX(3000).setDuration(1000);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        currentPlayer = Player.ONE;
        for (int i = 0; i < playerChoices.length; i++) {
            playerChoices[i] = Player.NO;
        }
        gameOver = false;
    }
}
