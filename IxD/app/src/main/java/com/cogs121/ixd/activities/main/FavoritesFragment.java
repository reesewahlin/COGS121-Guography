package com.cogs121.ixd.activities.main;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cogs121.ixd.BaseFragment;
import com.cogs121.ixd.Controllers.navigation.Page;
import com.cogs121.ixd.R;
import com.cogs121.ixd.utils.LocusPoint;
import com.cogs121.ixd.utils.ViewUtils;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chad on 6/1/17.
 */

public class FavoritesFragment extends BaseFragment {

    public static final String TAG = FavoritesFragment.class.getName();

    private ImageView backButton;
    private ListView favoritesListView;

    private FavoritesListAdapter favoritesListAdapter;
    private ArrayList<String> favoritesList;

    public FavoritesFragment() {
        page = Page.MAIN_FAVORITES;
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        backButton = ViewUtils.getView(rootView, R.id.iv_favorites_back);
        favoritesListView = ViewUtils.getView(rootView, R.id.lv_favorites);
        favoritesListView.setAdapter(favoritesListAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getControllerFactory().getNavigationController().transitionToPage(getPage(), Page.MAIN_MAP);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get favorites from acct store
        String user = getStoreFactory().getConsumerUserStore().getCurrentUser();
        favoritesList = getStoreFactory().getConsumerUserStore().getUser(user).getFavoriteLocusPoints();
        favoritesListAdapter = new FavoritesListAdapter(getContext(), R.layout.row_favorites, favoritesList);
    }

    private class FavoritesListAdapter extends ArrayAdapter<String> {

        private ArrayList<String> favorites;

        private Address address;

        public FavoritesListAdapter(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            this.favorites = objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if(v == null) {
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_favorites, null);
            }
            String locusPoint = favorites.get(position);
            LocusPoint lp = getStoreFactory().getLocusPointStore().getLocusPointByLocation(locusPoint);
            LatLng latLng = lp.getLpPosition();
            Geocoder geocoder = new Geocoder(getContext());
            try {
                List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                address = addressList.get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (locusPoint != null) {
                TextView title = (TextView) v.findViewById(R.id.tv_favorite_row_title);
                TextView companyName = (TextView) v.findViewById(R.id.tv_favorite_row_company);
                TextView location = (TextView) v.findViewById(R.id.tv_favorite_row_location);
                ImageView navigate = (ImageView) v.findViewById(R.id.iv_favorites_navigate);

                if(title != null) {
                    title.setText(lp.getLpTitle());
                }
                if(companyName != null) {
                    companyName.setText(lp.getLpName());
                }
                if(location != null) {
                    String street = address.getAddressLine(0);
                    String locality = address.getAddressLine(1);
                    location.setText(street + "," + locality);
                }
                navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getControllerFactory().getNavigationController().transitionToPage(getPage(), Page.MAIN_MAP);
                    }
                });
            }

            return v;
        }
    }

}
