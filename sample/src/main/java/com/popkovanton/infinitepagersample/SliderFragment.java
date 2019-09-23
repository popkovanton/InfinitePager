package com.popkovanton.infinitepagersample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SliderFragment extends Fragment {

    private int number;

    static SliderFragment newInstance(int number) {
        SliderFragment sliderFragment = new SliderFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("number", number);
        sliderFragment.setArguments(arguments);
        return sliderFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number = getArguments().getInt("number");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slider_fragment, container, false);
        TextView textView = view.findViewById(R.id.id);
        textView.setText(String.valueOf(number));

        return view;
    }
}
