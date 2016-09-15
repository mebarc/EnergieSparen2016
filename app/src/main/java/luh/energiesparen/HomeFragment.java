package luh.energiesparen;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

// Startbildschirm
public class HomeFragment extends Fragment {

    private TextView tv_hello;
    private TextView tv_homeTitle;
    private Button btn_logout;
    //private FragmentManager mFragmentManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Load Layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv_hello = (TextView) view.findViewById(R.id.textView_home);
        tv_homeTitle = (TextView) view.findViewById(R.id.textView_homeTitle);
        String welcome="";
        String username = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString("username", "Nutzer");
        if(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getBoolean("first_startup", true)){
            tv_hello.setText(getString(R.string.WelcomeFirst));
        }
        else{
            tv_hello.setText(getString(R.string.Welcome));
        }
        welcome = "Hallo " + username +"!";
        tv_homeTitle.setText(welcome);

        //Ausloggen
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.commit();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent,new LoginFragment()).commit();
                //startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return view;
//        return inflater.inflate(R.layout.fragment_home,null);
    }

}
