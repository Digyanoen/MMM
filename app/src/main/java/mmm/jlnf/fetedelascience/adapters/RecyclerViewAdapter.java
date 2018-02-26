package mmm.jlnf.fetedelascience.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.fragments.DescriptionFragment;
import mmm.jlnf.fetedelascience.fragments.RecyclerFragment;
import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by nicolas on 21/01/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerFragment fragment;
    private List<EventPojo> pojoList;

    public RecyclerViewAdapter(List<EventPojo> pojoList, RecyclerFragment fragment) {
        this.pojoList = pojoList;
        this.fragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventPojo eventPojo = pojoList.get(position);
        holder.title.setText(eventPojo.getTitre_fr());
        holder.description.setText(eventPojo.getDescription_fr());
        holder.currentPojo = eventPojo;
        holder.fragment = fragment;
        Picasso.with(holder.imageView.getContext()).load(eventPojo.getImage()).centerCrop().fit().into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return pojoList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.titre_fr)
        protected TextView title;
        @BindView(R.id.description_fr)
        protected TextView description;
        @BindView(R.id.imageView)
        protected ImageView imageView;

        protected EventPojo currentPojo;
        private RecyclerFragment fragment;

        /**
         * Lors d'un clic sur un événement, chargemenet d'une description détaillée de celui-ci
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(v -> {
                fragment.onClick(currentPojo);
            });
        }
    }

    public List<EventPojo> getPojoList() {
        return pojoList;
    }

    public void setPojoList(List<EventPojo> pojoList) {
        this.pojoList = pojoList;
    }

}
