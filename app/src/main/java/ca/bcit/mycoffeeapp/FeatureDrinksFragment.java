package ca.bcit.mycoffeeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeatureDrinksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeatureDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeatureDrinksFragment extends ListFragment {


    private OnFragmentInteractionListener mListener;
    private String[] coffeeList = new String[] {
            "Americano",
            "Cappuccino",
            "Chailatte",
            "Espresso",
            "Flatwhite",
            "Latte",
            "London Fog",
            "Mocha",
            "Macchiato"
    };

    public FeatureDrinksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeatureDrinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeatureDrinksFragment newInstance(String param1, String param2) {
        FeatureDrinksFragment fragment = new FeatureDrinksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.fragment_feature_drinks, container, false);
        return view;
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
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, coffeeList);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        String coffeeSelected = coffeeList[position];
        DrinkDetailsFragment df = new DrinkDetailsFragment();
        Bundle args = new Bundle();
        args.putString("COFFEE_NAME", coffeeSelected);
        df.setArguments(args);

        getFragmentManager().beginTransaction().add(R.id.fragments, df).commit();
    }

}
