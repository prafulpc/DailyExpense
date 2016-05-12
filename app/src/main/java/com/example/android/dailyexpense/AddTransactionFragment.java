package com.example.android.dailyexpense;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTransactionFragment extends Fragment {

    public AddTransactionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("TransactionFragment", "Fragment");
        return inflater.inflate(R.layout.fragment_add_transaction, container, false);

    }
}
