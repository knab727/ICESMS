package pl.kacper.icecall.ui.settings.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import pl.kacper.icecall.model.ModuleData;
import pl.kacper.icecall.modules.ModuleManager;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.ui.basic.activities.LoginActivity;
import pl.kacper.icecall.ui.settings.adapters.SettingsAdapter;

/**
 * Created by kacper on 02.01.16.
 */
public class SettingsFragment extends DaggerFragment {

    @Inject
    PersistenceUtil persistenceUtil;

    @Inject
    ModuleManager moduleManager;


    private ListView listView;

    public SettingsFragment() {
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initListView();
        getView().findViewById(R.id.settings_logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persistenceUtil.getLoggedInUser().setLoggedIn(false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
                startActivity(intent);
            }
        });
    }

    private void initListView() {
        listView = (ListView) getView().findViewById(R.id.settings_modules_list);
        List<ModuleData> objects = persistenceUtil.getLoggedInUser().getModuleSettings();
        SettingsAdapter adapter = new SettingsAdapter(getActivity(), objects, persistenceUtil.getLoggedInUser());
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



}
