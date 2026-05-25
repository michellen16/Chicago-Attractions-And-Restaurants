package edu.uic.cs478.s2026.project3app2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView adapter for the list of Chicago places
 * (attractions or restaurants). Highlights the currently
 * selected item.
 */
public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {

    // ---------------------------------------------------------------
    // Callback interface — notifies the fragment when an item is tapped
    // ---------------------------------------------------------------
    public interface OnPlaceSelectedListener {
        void onPlaceSelected(ChicagoPlace place, int position);
    }

    private final Context                context;
    private final List<ChicagoPlace>     places;
    private       int                    selectedPosition = (int) RecyclerView.NO_ID;
    private final OnPlaceSelectedListener listener;

    public PlacesAdapter(Context context,
                         List<ChicagoPlace> places,
                         OnPlaceSelectedListener listener) {
        this.context  = context;
        this.places   = places;
        this.listener = listener;
    }

    // ---------------------------------------------------------------
    // RecyclerView.Adapter overrides
    // ---------------------------------------------------------------

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        ChicagoPlace place = places.get(position);

        holder.tvName.setText(place.getName());
        holder.tvDescription.setText(place.getDescription());

        // Highlight selected row
        holder.itemView.setSelected(position == selectedPosition);
        holder.itemView.setActivated(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            // Refresh old and new rows only (efficient)
            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);

            if (listener != null) {
                listener.onPlaceSelected(place, selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return places == null ? 0 : places.size();
    }

    // ---------------------------------------------------------------
    // Public helpers
    // ---------------------------------------------------------------

    /** Restore selection after a configuration change. */
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    // ---------------------------------------------------------------
    // ViewHolder
    // ---------------------------------------------------------------
    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDescription;

        PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName        = itemView.findViewById(R.id.tvPlaceName);
            tvDescription = itemView.findViewById(R.id.tvPlaceDescription);
        }
    }
}
