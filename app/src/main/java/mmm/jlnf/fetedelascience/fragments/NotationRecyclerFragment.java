package mmm.jlnf.fetedelascience.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.adapters.NotationRecyclerAdapter;
import mmm.jlnf.fetedelascience.pojos.CommentEvent;
import mmm.jlnf.fetedelascience.R;

/**
 * Created by nicolas on 17/02/18.
 */

public class NotationRecyclerFragment extends Fragment {
    @BindView(R.id.notationfragment)
    RecyclerView recyclerView;
    private NotationRecyclerAdapter notationRecyclerAdapter;
    private List<CommentEvent> notationPojoList = new ArrayList<>();

    public NotationRecyclerFragment(){
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclernotationfragment, container, false);
        ButterKnife.bind(this, view);
        notationRecyclerAdapter = new NotationRecyclerAdapter(notationPojoList, this.getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity().getApplication().getBaseContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(notationRecyclerAdapter);

        return view;
    }

    public NotationRecyclerAdapter getNotationRecyclerAdapter() {
        return notationRecyclerAdapter;
    }
}
