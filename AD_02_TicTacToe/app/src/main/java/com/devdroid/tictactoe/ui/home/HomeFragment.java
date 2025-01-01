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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize the ViewModel
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Bind the layout
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize status text
        statusText = binding.statusText;

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

        // Set initial text for the game status
        statusText.setText("Player 1's Turn (X)");

        // Initialize the board array with empty strings
        for (int i = 0; i < 9; i++) {
            board[i] = "";
        }

        // Set onClickListener for each button
        for (int i = 0; i < 9; i++) {
            final int index = i;
            buttons[i].setOnClickListener(v -> onCellClick(index));
        }

        return root;
    }

    // This method handles the logic for each cell click
    private void onCellClick(int index) {
        // Don't allow overwriting an already clicked cell
        if (!board[index].equals("")) {
            return;
        }

        // Update the button with the correct player's mark
        if (player1Turn) {
            buttons[index].setText("X");
            board[index] = "X";
            statusText.setText("Player 2's Turn (O)");
        } else {
            buttons[index].setText("O");
            board[index] = "O";
            statusText.setText("Player 1's Turn (X)");
        }

        // Switch turns
        player1Turn = !player1Turn;

        // Check for a winner
        checkWinner();
    }

    // This method checks if there's a winner
    private void checkWinner() {
        // Define all possible winning combinations
        String[][] winPatterns = {
                {"0", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"},  // Rows
                {"0", "3", "6"}, {"1", "4", "7"}, {"2", "5", "8"},  // Columns
                {"0", "4", "8"}, {"2", "4", "6"}                    // Diagonals
        };

        for (String[] pattern : winPatterns) {
            String a = board[Integer.parseInt(pattern[0])];
            String b = board[Integer.parseInt(pattern[1])];
            String c = board[Integer.parseInt(pattern[2])];

            // Check if all three cells in the pattern are the same and not empty
            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                statusText.setText("Player " + (a.equals("X") ? "1" : "2") + " Wins!");
                disableBoard();  // Disable the board after a win
                return;
            }
        }

        // Check for a draw (all cells filled but no winner)
        boolean isDraw = true;
        for (String cell : board) {
            if (cell.equals("")) {
                isDraw = false;
                break;
            }
        }
        if (isDraw) {
            statusText.setText("It's a Draw!");
        }
    }

    // Disable all buttons after the game ends (win or draw)
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
