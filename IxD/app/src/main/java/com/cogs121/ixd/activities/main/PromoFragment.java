package com.cogs121.ixd.activities.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cogs121.ixd.BaseFragment;
import com.cogs121.ixd.Controllers.navigation.Page;
import com.cogs121.ixd.R;
import com.cogs121.ixd.utils.ViewUtils;

/**
 * Created by rjw on 4/26/17.
 */

public class PromoFragment extends BaseFragment {

    public static final String TAG = PromoFragment.class.getName();

    public PromoFragment() {
        page = Page.PROMO;
    }

    public static PromoFragment newInstance() {
        PromoFragment fragment = new PromoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_promo, container, false);

        return rootView;
    }
}