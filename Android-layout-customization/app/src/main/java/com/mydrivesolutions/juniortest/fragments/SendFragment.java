package com.mydrivesolutions.juniortest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydrivesolutions.juniortest.R;

import butterknife.ButterKnife;

/**
 * Created by pascalh on 4/12/2015.
 */
public class SendFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);

        ButterKnife.bind(this, view);

        return view;
    }
}
