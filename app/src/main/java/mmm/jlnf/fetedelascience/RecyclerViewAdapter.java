package mmm.jlnf.fetedelascience;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;

/**
 * Created by nicolas on 21/01/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<EventPojo> pojoList;

    public RecyclerViewAdapter(List<EventPojo> pojoList) {
        this.pojoList = pojoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventPojo eventPojo = pojoList.get(position);
        holder.title.setText(eventPojo.getTitre_fr());
        holder.description.setText(eventPojo.getDescription_fr());

    }



    @Override
    public int getItemCount() {
        return pojoList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.titre_fr) protected TextView title;
        @BindView(R.id.description_fr) protected TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<EventPojo> getPojoList() {
        return pojoList;
    }

    public void setPojoList(List<EventPojo> pojoList) {
        this.pojoList = pojoList;
    }
}
