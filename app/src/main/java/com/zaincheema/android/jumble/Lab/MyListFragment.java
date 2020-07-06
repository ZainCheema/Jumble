package com.zaincheema.android.jumble.Lab;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyListFragment extends ListFragment {

    ItemViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);

        // Create the observer which updates the UI.
        final Observer<ArrayList<Item>> itemObserver = new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Item> items) {
                ItemAdapter itemAdapter = new ItemAdapter(getActivity(), mViewModel.getItems().getValue());
                setListAdapter(itemAdapter);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer
        mViewModel.getItems().observe(this, itemObserver);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                DummyData.DATA_HEADINGS));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mViewModel.selectItem(position);
        showContent(position);
    }


    void showContent(int index) {
        // Create an intent for starting the DetailsActivity
        Intent intent = new Intent();

        // explicitly set the activity context and class
        // associated with the intent (context, class)

        intent.setClass(getActivity(), ItemActivity.class);

        // pass the current position
        intent.putExtra("index", index);

        startActivity(intent);
    }


}
