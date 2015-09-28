package luh.energiesparen;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Fragment Containing the Holder for the Tabs
public class TabsFragment extends Fragment {

    private static String TAB_TITLES = "tab_titles";
    private static String TAB_LISTE1 = "tab_liste1";
    private static String TAB_LISTE2 = "tab_liste2";
    private static String TAB_LISTE3 = "tab_liste3";
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    public static String[] tabstitles;
    public static String[] liste1;
    public static String[] liste2;
    public static String[] liste3;


    public TabsFragment() {
        // Required empty public constructor
    }

    // Constructor which gets the TItles and the StringArrays with the Tips passed
    public static TabsFragment newInstance(String[] titles,
                                           String[] liste1,
                                           String[] liste2,
                                           String[] liste3) {
        Bundle args = new Bundle();
        args.putStringArray(TAB_TITLES, titles);
        args.putStringArray(TAB_LISTE1, liste1);
        args.putStringArray(TAB_LISTE2, liste2);
        args.putStringArray(TAB_LISTE3, liste3);
        TabsFragment fragment = new TabsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabstitles = getArguments().getStringArray(TAB_TITLES);
        liste1 = getArguments().getStringArray(TAB_LISTE1);
        liste2 = getArguments().getStringArray(TAB_LISTE2);
        liste3 = getArguments().getStringArray(TAB_LISTE3);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate Layout
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        tabstitles = getActivity().getResources().getStringArray(R.array.sTabtitles);
        int_items = tabstitles.length;

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

    // Adapter to handle Data and Current Tab Position
    private static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0:
                    return TabListenFragment.newInstance(liste1);
                case 1:
                    return TabListenFragment.newInstance(liste2);
                case 2:
                    return TabListenFragment.newInstance(liste3);

            }
            return new TabListenFragment();
        }


        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabstitles[position];
        }
    }

}
