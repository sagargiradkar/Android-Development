package com.devdroid.tictactoe.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devdroid.tictactoe.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private boolean player1Turn = true;  // Player 1 starts (X)
    private String[] board = new String[9]; // Tic-Tac-Toe board state
    private Button[] buttons = new Button[9];  // Store references to the buttons
    private TextView statusText;
    private Button resetButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize UI elements
        statusText = binding.statusText;
        resetButton = binding.resetButton;  // Reference to the reset button

        // Initialize buttons for the Tic-Tac-Toe grid
        buttons[0] = binding.button1;
        buttons[1] = binding.button2;
        buttons[2] = binding.button3;
        buttons[3] = binding.button4;
        buttons[4] = binding.button5;
        buttons[5] = binding.button6;
        buttons[6] = binding.button7;
        buttons[7] = binding.button8;
        buttons[8] = binding.button9;

        resetGame();  // Initialize game state

        // Set onClickListener for each button
        for (int i = 0; i < 9; i++) {
            final int index = i;
            buttons[i].setOnClickListener(v -> onCellClick(index));
        }

        // Set onClickListener for the reset button
        resetButton.setOnClickListener(v -> resetGame());

        return root;
    }

    private void resetGame() {
        // Reset the board state
        for (int i = 0; i < 9; i++) {
            board[i] = "";
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        // Reset status and player turn
        player1Turn = true;
        statusText.setText("Player 1's Turn (X)");
    }

    private void onCellClick(int index) {
        if (!board[index].equals("")) {
            return;
        }

        if (player1Turn) {
            buttons[index].setText("X");
            board[index] = "X";
            statusText.setText("Player 2's Turn (O)");
        } else {
            buttons[index].setText("O");
            board[index] = "O";
            statusText.setText("Player 1's Turn (X)");
        }

        player1Turn = !player1Turn;

        checkWinner();
    }

    private void checkWinner() {
        String[][] winPatterns = {
                {"0", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"},
                {"0", "3", "6"}, {"1", "4", "7"}, {"2", "5", "8"},
                {"0", "4", "8"}, {"2", "4", "6"}
        };

        for (String[] pattern : winPatterns) {
            String a = board[Integer.parseInt(pattern[0])];
            String b = board[Integer.parseInt(pattern[1])];
            String c = board[Integer.parseInt(pattern[2])];

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                statusText.setText("Player " + (a.equals("X") ? "1" : "2") + " Wins !");
                disableBoard();
                return;
            }
        }

        boolean isDraw = true;
        for (String cell : board) {
            if (cell.equals("")) {
                isDraw = false;
                break;
            }
        }
        if (isDraw) {
            statusText.setText("It's a Draw !");
        }
    }

    private void disableBoard() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
