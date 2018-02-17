package mmm.jlnf.fetedelascience;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.Pojos.CommentEvent;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;
import mmm.jlnf.fetedelascience.Pojos.NotationPojo;

/**
 * Created by nicolas on 21/01/18.
 */

public class NotationRecyclerAdapter extends RecyclerView.Adapter<NotationRecyclerAdapter.CommentHolder> {

    private Activity activity;
    private List<CommentEvent> commentEventList;

    public NotationRecyclerAdapter(List<CommentEvent> pojoList, Activity activity) {
        this.commentEventList = pojoList;
        this.activity = activity;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_event, parent, false);
        CommentHolder myViewHolder = new CommentHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        CommentEvent commentEvent = commentEventList.get(position);
        holder.name.setText(commentEvent.getName());
        holder.comment.setText(commentEvent.getComment());
        holder.stars.setText(String.valueOf(commentEvent.getStars()));

    }


    @Override
    public int getItemCount() {
        return commentEventList.size();
    }

    public void getCommentForEvent(String identifiant) {
        DatabaseReference databaseReference=  FirebaseDatabase.getInstance().getReference().child(identifiant);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Object> mapNotation = (Map<String,Object>) dataSnapshot.getValue();
                if(mapNotation != null) {
                    for (Map.Entry<String, Object> entry : mapNotation.entrySet()) {
                        CommentEvent commentEvent = new CommentEvent();
                        Map<String, Object> comment = new HashMap();
                        comment = (Map) entry.getValue();
                        commentEvent.setComment((String) comment.get("comment"));
                        commentEvent.setName((String) entry.getKey());
                        commentEvent.setStars((Long) comment.get("stars"));
                        commentEventList.add(commentEvent);
                        notifyItemInserted(commentEventList.size() - 1);


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static class CommentHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.name) TextView name;
        @BindView(R.id.comment) TextView comment;
        @BindView(R.id.stars) TextView stars;

        public CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }

    public List<CommentEvent> getCommentEventList() {
        return commentEventList;
    }

    public void setCommentEventList(List<CommentEvent> commentEventList) {
        this.commentEventList = commentEventList;
    }

    private Activity getActivity(){
        return activity;
    }


}
