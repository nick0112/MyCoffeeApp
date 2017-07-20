package ca.bcit.mycoffeeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CurrentOrdersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CurrentOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentOrdersFragment extends ListFragment {

    private boolean isUserPresent;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private int currentPosition;
    private String tempUser;
    private OnFragmentInteractionListener mListener;
    private String flavour;
    private String milkType;
    private String temperature;
    private String name;
    private String shots;
    private String coffeeName;
    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter adapter;

    public CurrentOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentOrdersFragment newInstance(String param1, String param2) {
        CurrentOrdersFragment fragment = new CurrentOrdersFragment();
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
        // Inflate the layout for this fragment
        final View view;
        view = inflater.inflate(R.layout.fragment_current_orders, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final TextView textView;
        currentUser = getUserName();
        isUserPresent = false;
        textView = (TextView) getView().findViewById(R.id.userPosition);
        list = new ArrayList<HashMap<String, String>>();
        adapter = new SimpleAdapter(getActivity(), list, R.layout.custom_row,
                new String[]{"Name", "Drink", "Details"},
                new int[]{R.id.displayUser, R.id.displayCoffee, R.id.displayDetails});
        DatabaseReference ref = firebaseDatabase.getReference("Orders");
        Log.wtf("PLEASE", "IS IT READING");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Order order = postSnapShot.getValue(Order.class);
                    tempUser = order.getUserName();
                    flavour = order.getDrink().getFlavour();
                    milkType = order.getDrink().getMilkType();
                    temperature = order.getDrink().getHotOrCold();
                    shots = Integer.toString(order.getDrink().getShots());
                    name = order.getUserName();
                    coffeeName = order.getDrink().getDrinkName();
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put("Name", name);
                    temp.put("Drink", coffeeName);
                    temp.put("Details", "Shots: " + shots + ", Temperature: " + temperature
                            + ", Flavour: " + flavour + ", Milk Type: " + milkType);
                    if (!list.contains(temp)) {
                        list.add(temp);
                    }
                    if (tempUser.equals(currentUser)) {
                        currentPosition = list.indexOf(temp) + 1;
                        textView.setText("Current Position: " + currentPosition);
                        isUserPresent = true;
                    }
                    setListAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (!isUserPresent) {
            textView.setText("Current Position: 0");
        }
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
        void onFragmentInteraction(Uri uri);
    }

    public String getUserName() {
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
    public void onListItemClick (ListView l, View v, final int position, long id) {
        DatabaseReference ref = firebaseDatabase.getReference("Orders");
        final TextView textView;
        textView = (TextView) getView().findViewById(R.id.userPosition);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int positionCount = 0;
                for (DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                    if (positionCount == position) {
                        postSnapShot.getRef().removeValue();
                        list.remove(position);
                        textView.setText("Current Position: " + position);
                    }
                    positionCount++;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
