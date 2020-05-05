package com.example.finalproject;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int player1Points;
    private int player2Points;
    private TextView Player1;
    private TextView Player2;
    private int Count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Player1 = findViewById(R.id.text_view_p1);
        Player2 = findViewById(R.id.text_view_p2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_PlayAgain);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        Count++;
        if (CheckWinner()) {
            if (player1Turn) {
                player1Points++;
                Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
                updatePoints();
                resetBoard();
            } else {
                player2Points++;
                Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
                updatePoints();
                resetBoard();
            }
        } else if (Count == 9) {
            Toast.makeText(this, "Players Draw!", Toast.LENGTH_SHORT).show();
            resetBoard();
        } else {
            player1Turn = !player1Turn;
        }
        if (player1Points >= 3) {
            Toast.makeText(this, "Congratulations to Player1 for winning the whole game!", Toast.LENGTH_SHORT).show();
            resetGame();
        } else if (player2Points >= 3){
            Toast.makeText(this, "Congratulations to Player2 for winning the whole game!", Toast.LENGTH_SHORT).show();
            resetGame();
        }
    }
    private boolean CheckWinner() {
        final int SIZE = 3;
        String[][] board = new String[SIZE][SIZE];
        boolean gotresult = false;
        int numOfX = 0;
        int numOfO = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < board.length; i++) {
            numOfX = 0;
            numOfO = 0;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == "X")
                    numOfX++;
                if (board[i][j] == "O")
                    numOfO++;
            }
            if (numOfX == SIZE || numOfO == SIZE) {
                gotresult = true;
                break;
            }

        }
        if (!gotresult) {
            for (int i = 0; i < board.length; i++) {
                numOfX = 0;
                numOfO = 0;
                for (int j = 0; j < board[i].length; j++) {
                    if (board[j][i] == "X")
                        numOfX++;
                    if (board[j][i] == "O")
                        numOfO++;
                }
                if (numOfX == SIZE || numOfO == SIZE) {
                    gotresult = true;
                    break;
                }

            }
        }
        if (!gotresult) {
            numOfX = 0;
            numOfO = 0;
            for (int i = 0; i < board.length; i++) {
                if (board[i][i] == "X")
                    numOfX++;
                if (board[i][i] == "O")
                    numOfO++;
            }
            if (numOfX == SIZE || numOfO == SIZE) {
                gotresult = true;
            }
        }
        if (!gotresult) {
            numOfX = 0;
            numOfO = 0;
            for (int i = 0; i < board.length; i++) {
                if (board[i][SIZE - i - 1] == "X")
                    numOfX++;
                if (board[i][SIZE - i - 1] == "O")
                    numOfO++;
            }
            if (numOfX == SIZE || numOfO == SIZE) {
                gotresult = true;
            }
        }
        return gotresult;
    }
    @SuppressLint("SetTextI18n")
    private void updatePoints() {
        Player1.setText("Player 1 Score: " + player1Points);
        Player2.setText("Player 2 Score: " + player2Points);
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        player1Turn = true;
        Count = 0;
    }
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePoints();
        resetBoard();
    }
}
