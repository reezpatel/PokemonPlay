package layout;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.reezx.android.pokeplay2.R;

import static com.reezx.android.pokeplay2.MainActivity.font_1;
import static com.reezx.android.pokeplay2.MainActivity.font_2;

public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnMainFragmentInteractionListener mListener;

    public MainFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        TextView heading = (TextView) view.findViewById(R.id.textViewMainFragmentHeading);
        heading.setTypeface(font_2);

        Button whosThatPoekmon = (Button) view.findViewById(R.id.buttonOneMainFragmentOne);
        whosThatPoekmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMainFragmentInteraction(1);
            }
        });
        Button trivia = (Button) view.findViewById(R.id.buttonTwoMainFragmentTwo);
        whosThatPoekmon.setTypeface(font_1);
        trivia.setTypeface(font_1);
        trivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMainFragmentInteraction(2);
            }
        });
        ViewGroup.LayoutParams layoutParams = trivia.getLayoutParams();
        layoutParams.width = whosThatPoekmon.getLayoutParams().width;

        trivia.setLayoutParams(layoutParams);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainFragmentInteractionListener) {
            mListener = (OnMainFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMainFragmentInteractionListener {
        void onMainFragmentInteraction(int buttonID);
    }
}
