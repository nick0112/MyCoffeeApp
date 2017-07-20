package ca.bcit.mycoffeeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrinkDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrinkDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private OnFragmentInteractionListener mListener;
    private String[] flavours = new String[] {
            "Caramel",
            "Cinnamon",
            "Hazelnut",
            "None",
            "Peppermint",
            "Vanilla",
    };
    private String[] milkType = new String[] {
            "Almond",
            "Double",
            "Milk",
            "Soy",
            "Whole"
    };


    public DrinkDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrinkDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkDetailsFragment newInstance(String param1, String param2) {
        DrinkDetailsFragment fragment = new DrinkDetailsFragment();
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
        final TextView testing;
        final Spinner flavourSpinner;
        final Spinner milkTypeSpinner;
        final ArrayAdapter<String> flavourAdapter;
        final ArrayAdapter<String>  milkAdapter;
        final Button button;
        view = inflater.inflate(R.layout.fragment_drink_details, container, false);
        button = (Button) view.findViewById(R.id.order);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (mListener != null)
                {
                    mListener.orderDrink();
                }
            }
        });
        final String testingString;
        testing = (TextView) view.findViewById(R.id.seeCoffeeNamePlease);
        flavourSpinner = (Spinner) view.findViewById(R.id.flavours);
        milkTypeSpinner = (Spinner) view.findViewById(R.id.milktype);
        testingString = getArguments().getString("COFFEE_NAME");
        testing.setText(testingString);
        flavourAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, flavours);
        milkAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, milkType);
        flavourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flavourSpinner.setAdapter(flavourAdapter);
        milkTypeSpinner.setAdapter(milkAdapter);
        flavourSpinner.setSelection(3);
        milkTypeSpinner.setSelection(2);
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
        void orderDrink();
    }
}
