package luh.energiesparen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

// Startbildschirm
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Load Layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Button Click Action
        Button button = (Button) view.findViewById(R.id.button_verbrauch);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Opens new VerbrauchsActivity
                Intent intent = new Intent(getActivity(), VerbrauchActivity.class);
                startActivity(intent);
            }
        });

        return view;
//        return inflater.inflate(R.layout.fragment_home,null);
    }

}
