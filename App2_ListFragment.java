package edu.uic.cs478.s2026.project3app2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Fragment 1 — displays the scrollable list of places.
 * Communicates back to the host Activity via the
 * OnPlaceSelectedListener interface.
 */
public class ListFragment extends Fragment {

    // ---------------------------------------------------------------
    // Keys for saving / restoring state
    // ---------------------------------------------------------------
    private static final String ARG_PLACES           = "arg_places";
    private static final String STATE_SELECTED_INDEX = "state_selected_index";

    // ---------------------------------------------------------------
    // Callback interface implemented by the host Activity
    // ---------------------------------------------------------------
    public interface OnPlaceSelectedListener {
        void onPlaceSelected(ChicagoPlace place, int position);
    }

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------
    private List<ChicagoPlace>       places;
    private PlacesAdapter            adapter;
    private RecyclerView             recyclerView;
    private OnPlaceSelectedListener  hostListener;
    private int                      restoredPosition = (int) RecyclerView.NO_ID;

    // ---------------------------------------------------------------
    // Factory method
    // ---------------------------------------------------------------
    public static ListFragment newInstance(List<ChicagoPlace> places) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();

        // ArrayList is Serializable
        args.putSerializable(ARG_PLACES, (java.io.Serializable) places);
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // The host Activity must implement OnPlaceSelectedListener
        if (context instanceof OnPlaceSelectedListener) {
            hostListener = (OnPlaceSelectedListener) context;
        } else {
            throw new ClassCastException(context
                    + " must implement ListFragment.OnPlaceSelectedListener");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain fragment across config changes
        setRetainInstance(true);

        if (getArguments() != null) {
            places = (List<ChicagoPlace>) getArguments()
                    .getSerializable(ARG_PLACES);
        }

        if (savedInstanceState != null) {
            restoredPosition = savedInstanceState
                    .getInt(STATE_SELECTED_INDEX, (int) RecyclerView.NO_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewPlaces);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext()));

        adapter = new PlacesAdapter(
                requireContext(),
                places,
                (place, position) -> {
                    restoredPosition = position;
                    if (hostListener != null) {
                        hostListener.onPlaceSelected(place, position);
                    }
                }
        );

        recyclerView.setAdapter(adapter);

        // Restore the previously selected item (after config change)
        if (restoredPosition != RecyclerView.NO_ID) {
            adapter.setSelectedPosition(restoredPosition);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_INDEX,
                adapter != null ? adapter.getSelectedPosition() : (int) RecyclerView.NO_ID);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostListener = null;
    }

    // ---------------------------------------------------------------
    // Public helpers (called by the host Activity)
    // ---------------------------------------------------------------

    /** Programmatically highlight an item (e.g., after config restore). */
    public void setSelectedPosition(int position) {
        restoredPosition = position;
        if (adapter != null) {
            adapter.setSelectedPosition(position);
        }
    }

    public int getSelectedPosition() {
        return adapter != null ? adapter.getSelectedPosition() : (int) RecyclerView.NO_ID;
    }
}
