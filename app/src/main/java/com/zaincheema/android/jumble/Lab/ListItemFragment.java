package com.zaincheema.android.jumble.Lab;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaincheema.android.jumble.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INDEX = "index";

    private int mIndex;
    ItemViewModel mViewModel;
    View mInflatedView;

    public int getShownIndex() {
        return mIndex;
    }

    public ListItemFragment() {
        // Required empty public constructor
    }


    public static ListItemFragment newInstance(int index) {
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_INDEX);
        }

        mViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        mViewModel.selectItem(mIndex);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflatedView = inflater.inflate(R.layout.fragment_list_item, container, false);

        // Create the observer which updates the UI.
        final Observer<Item> itemObserver = new Observer<Item>() {
            @Override
            public void onChanged(@Nullable Item item) {
                ImageView image = (ImageView) mInflatedView.findViewById(R.id.imageView_image);
                image.setImageBitmap(item.getImage());
                TextView text = (TextView) mInflatedView.findViewById(R.id.listItemTextView);
                text.setText(item.getDescription());
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getSelectedItem().observe(this, itemObserver);

        return mInflatedView;

    }

}
