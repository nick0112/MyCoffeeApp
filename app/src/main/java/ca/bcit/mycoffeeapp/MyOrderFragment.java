package ca.bcit.mycoffeeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrderFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String currentUser;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrderFragment newInstance(String param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.fragment_my_order, container, false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void displayMyCoffeeInfo();
    }

    public String getMyUserName() {
        final String email;
        DatabaseReference ref = firebaseDatabase.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        email = firebaseAuth.getCurrentUser().getEmail();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (email != null) {
                    for (DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                        User user = postSnapShot.getValue(User.class);
                        if (user.getUserEmail().equals(email)) {
                            currentUser = user.getUserName();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        return currentUser;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final TextView textView;
        final TextView details;
        textView = (TextView) getView().findViewById(R.id.mycoffeename);
        details = (TextView) getView().findViewById(R.id.mycoffeedetails);
        DatabaseReference ref = firebaseDatabase.getReference("Orders");
        details.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    String currentUserName = getMyUserName();
                    Order order = postSnapShot.getValue(Order.class);
                    String coffeeName = order.getDrink().getDrinkName();
                    String flavour = order.getDrink().getFlavour();
                    String milkType = order.getDrink().getMilkType();
                    String temperature = order.getDrink().getHotOrCold();
                    String shots = Integer.toString(order.getDrink().getShots());
                    if (order.getUserName().equals(currentUserName)) {
                        details.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(coffeeName);
                        details.setText("Shots: " + shots + "\nFlavour: " + flavour +
                                "\nMilk Type: " + milkType + "\nTemperature: " + temperature);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
