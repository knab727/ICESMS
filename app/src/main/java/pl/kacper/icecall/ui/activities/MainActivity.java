package pl.kacper.icecall.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import pl.kacper.icecall.R;
import pl.kacper.icecall.configuration.DaggerAppCompatActivity;
import pl.kacper.icecall.model.User;
import pl.kacper.icecall.modules.ModuleManager;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.ui.adapters.ModulesListAdapter;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    PersistenceUtil persistenceUtil;

    @Inject
    ModuleManager moduleManager;

    private ListView listView;
    private ModulesListAdapter adapter;
    private List<String> moduleNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        User user = persistenceUtil.getUser(getIntent().getStringExtra("USER_LOGIN"));
        initListView();

    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.main_module_list);
        moduleNames = moduleManager.getModuleNames();
        adapter = new ModulesListAdapter(this, moduleNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "Clicked on "+ moduleNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
