package mmm.jlnf.fetedelascience.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by nicolas on 26/02/18.
 */

public class ItineraireAdapter extends RecyclerView.Adapter<ItineraireAdapter.ItineraireHolder>{

    private List<EventPojo> pojoList;
    public ItineraireAdapter(List<EventPojo> pojoList, Activity activity) {

        this.pojoList = pojoList;
    }

    @Override
    public ItineraireHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itineraire, parent, false);
        ItineraireHolder myViewHolder = new ItineraireHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ItineraireHolder holder, int position) {
        EventPojo eventPojo = pojoList.get(position);
        holder.textView.setText(eventPojo.getTitre_fr());

    }

    @Override
    public int getItemCount() {
        return pojoList.size();
    }

    public class ItineraireHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itineraire)
        TextView textView;

        public ItineraireHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

    }
}
