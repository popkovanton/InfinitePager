package com.popkovanton.infinitepagersample;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SliderFragment extends Fragment {

    private int number;
    private View rootView;
    private TextView textView;

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
        rootView = inflater.inflate(R.layout.slider_fragment, container, false);
        textView = rootView.findViewById(R.id.id);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView.setText(String.valueOf(number));
    }

    @Override
    public void onDestroyView() {
        rootView = null;
        textView = null;
        super.onDestroyView();
    }
}
