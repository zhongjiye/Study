package com.zjy.study.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjy.study.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TextFragment extends Fragment {

    @BindView(R.id.tt_content)
    TextView ttContent;

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


    public TextFragment() {
    }


    public static TextFragment newInstance(String param1) {
        TextFragment fragment = new TextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ttContent.setText(mParam1);
    }

}
