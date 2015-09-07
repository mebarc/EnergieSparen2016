package luh.energiesparen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabListenFragment extends Fragment {

    public static final String ARG_LISTE = "ARG_LISTE";

    private String[] inhalte;


    public TabListenFragment() {
        // Required empty public constructor
    }

    public static TabListenFragment newInstance(String[] liste) {
        Bundle args = new Bundle();
        args.putStringArray(ARG_LISTE, liste);
        TabListenFragment fragment = new TabListenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inhalte = getArguments().getStringArray(ARG_LISTE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ListView lv = new ListView(getActivity());
                ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, inhalte);
        lv.setAdapter(itemsAdapter);
        return lv;
    }


}
