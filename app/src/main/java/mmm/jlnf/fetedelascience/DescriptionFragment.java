package mmm.jlnf.fetedelascience;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;

/**
 * Created by nicolas on 06/02/18.
 */

public class DescriptionFragment extends Fragment {

    @BindView(R.id.titleEvent) TextView title;
    @BindView(R.id.descriptionEvent) TextView desc;
    @BindView(R.id.villeEvent) TextView ville;
    public DescriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.eventlayoutdesc, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    public void update(EventPojo currentPojo) {
        title.setText(currentPojo.getTitre_fr());
        desc.setText(currentPojo.getDescription_fr());
        ville.setText(currentPojo.getVille());

    }
}
