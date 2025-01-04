package com.devdroid.ad_12_toast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.devdroid.ad_12_toast.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupCustomToasts();
    }

    private void setupCustomToasts() {
        // Find buttons in the grid and set onClickListeners
        binding.getRoot().findViewById(R.id.button_1).setOnClickListener(v ->
                showCustomToast("Button 1 clicked!", Toast.LENGTH_SHORT));

        binding.getRoot().findViewById(R.id.button_2).setOnClickListener(v ->
                showCustomToast("Button 2 clicked with long duration!", Toast.LENGTH_LONG));

        binding.getRoot().findViewById(R.id.button_3).setOnClickListener(v ->
                showCustomToast("Button 3 clicked! Custom style applied!", Toast.LENGTH_SHORT));

        // Add more buttons with different customizations
    }

    private void showCustomToast(String message, int duration) {
        Toast toast = Toast.makeText(requireContext(), message, duration);
        // Apply custom properties (example: set gravity or add a custom view)
        toast.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
