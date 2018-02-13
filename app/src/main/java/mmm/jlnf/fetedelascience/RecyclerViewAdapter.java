package mmm.jlnf.fetedelascience;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;

/**
 * Created by nicolas on 21/01/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Activity activity;
    private List<EventPojo> pojoList;

    public RecyclerViewAdapter(List<EventPojo> pojoList, Activity activity) {
        this.pojoList = pojoList;
        this.activity = activity;
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
        holder.activity = activity;

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

        protected EventPojo currentPojo;
        private Activity activity;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(activity.findViewById(R.id.eventlarge) == null){
                        FragmentManager fragmentManager = activity.getFragmentManager();
                        DescriptionFragment descriptionFragment = new DescriptionFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.eventlarge, descriptionFragment)
                                .addToBackStack(null)
                                .commit();
                        fragmentManager.executePendingTransactions();
                        descriptionFragment.update(currentPojo);
                        Log.e("tag", "toto");
 /*                   }
                    else{
                        DescriptionFragment descriptionFragment = (DescriptionFragment) activity.getFragmentManager().findFragmentById(R.id.eventlarge);
                        descriptionFragment.update(currentPojo);

                        Log.e("tag", "tata");
                    }
*/



                }
            });
        }


    }



    public List<EventPojo> getPojoList() {
        return pojoList;
    }

    public void setPojoList(List<EventPojo> pojoList) {
        this.pojoList = pojoList;
    }

    private Activity getActivity(){
        return activity;
    }


}
