package com.jndx.csp.goodsprice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jszx on 2018/10/18.
 */

public class GoodPriceFragment  extends Fragment {
    private View _contentView;
    public GoodPriceFragment(View contentView)
    {
        _contentView=contentView;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return _contentView;
    }
}
