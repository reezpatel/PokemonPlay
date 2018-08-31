package com.reezx.android.pokeplay2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.reezx.android.pokeplay2.MainActivity.font_1;
import static com.reezx.android.pokeplay2.MainActivity.font_2;


public class FinalScoreFragment extends Fragment {

    private OnFinalScoreFragmentInteractionListener mListener;
    private int score = 0;

    public FinalScoreFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        score = getArguments().getInt("SCORE");
    }

    public static FinalScoreFragment newInstance(int score) {
        Bundle args = new Bundle();
        args.putInt("SCORE",score);
        FinalScoreFragment fragment = new FinalScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_final_score, container, false);
        TextView scoreText = (TextView) view.findViewById(R.id.textViewFinalFragmentScore);
        scoreText.setText("Your Score: " + score);

        TextView heading = (TextView) view.findViewById(R.id.textViewFinalFragmentHeading);

        scoreText.setTypeface(font_1);
        heading.setTypeface(font_2);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);/*
        if (context instanceof OnFinalScoreFragmentInteractionListener) {
            mListener = (OnFinalScoreFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFinalScoreFragmentInteractionListener {
        void onFinalScoreFragmentInteraction(int n);
    }
}
