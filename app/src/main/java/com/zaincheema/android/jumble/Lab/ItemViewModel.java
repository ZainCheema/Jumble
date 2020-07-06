package com.zaincheema.android.jumble.Lab;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.zaincheema.android.jumble.Lab.Item;
import com.zaincheema.android.jumble.Lab.ItemsRepository;

import java.util.ArrayList;

public class ItemViewModel extends AndroidViewModel {
    private LiveData<ArrayList<Item>> mItems;
    private LiveData<Item> mSelectedItem;
    private ItemsRepository mItemRepository;
    private int mSelectedIndex;

    public ItemViewModel(@NonNull Application pApplication) {
        super(pApplication);
        mItemRepository = ItemsRepository.getInstance(getApplication());
    }



    public LiveData<ArrayList<Item>> getItems() {
        if (mItems == null) {
            mItems = mItemRepository.getItems();
        }
        return mItems;
    }

    public LiveData<Item> getItem(int pItemIndex) {
        return mItemRepository.getItem(pItemIndex);
    }

    public void selectItem(int pIndex) {
        if(pIndex != mSelectedIndex || mSelectedItem == null) {
            mSelectedIndex = pIndex;
            mSelectedItem = getItem(mSelectedIndex);
        }
    }

    public LiveData<Item> getSelectedItem() {
        return mSelectedItem;
    }

}
