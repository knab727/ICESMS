package pl.kacper.icecall.ui.seizuredetect.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import pl.kacper.icecall.R;
import pl.kacper.icecall.configuration.DaggerFragment;
import pl.kacper.icecall.model.seizuredetect.SeizureEvent;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.ui.seizuredetect.adapters.SeizureAttacksAdapter;


public class SeizureAttacksFragment extends DaggerFragment {

    @Inject
    PersistenceUtil persistenceUtil;

    private ListView listView;

    public SeizureAttacksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seizure_attacks, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initListView();
    }

    private void initListView() {
        listView = (ListView) getView().findViewById(R.id.seizuredetect_seizureattack_list);
        List<SeizureEvent> list = persistenceUtil.getLoggedInUser().getSeizureDetectData().getSeizureEventList();
        SeizureAttacksAdapter adapter = new SeizureAttacksAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setEmptyView(getView().findViewById(R.id.seizuredetect_empty_seizureslist));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_seizuredetect_attacks, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
