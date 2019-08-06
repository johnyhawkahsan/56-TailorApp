package com.johnyhawkdesigns.a56_tailorapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.johnyhawkdesigns.a56_tailorapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SizeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SizeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SizeFragment extends Fragment {

    private static final String TAG = SizeFragment.class.getSimpleName();

    private boolean addingNewRecord = true; // adding (true) or editing (false)
    FloatingActionButton floatingActionButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SizeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SizeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SizeFragment newInstance(String param1, String param2) {
        SizeFragment fragment = new SizeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_size, container, false);

        //When Floating button is clicked, we need to be redirected to AddEditPersonFragment
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addNewSize);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch AddEditPersonFragment
                Log.d(TAG, "onClick: ");
                Toast.makeText(getActivity(), "Add New Person", Toast.LENGTH_SHORT).show();

                AddEditPersonFragment addEditPersonFragment = new AddEditPersonFragment();
                //Bundle args = new Bundle();
                //args.putInt("id", 12345);
                //addEditPersonFragment.setArguments(args);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, addEditPersonFragment);
                transaction.commit();
            }
        });

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
