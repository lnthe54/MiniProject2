package com.example.lnthe54.miniproject2.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnthe54.miniproject2.R;

/**
 * @author lnthe54 on 10/6/2018
 * @project MiniProject2
 */
public class FragmentShare extends BottomSheetDialogFragment {
    private View view;

    public FragmentShare() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_share, container, false);
        return view;
    }
}
