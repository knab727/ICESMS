package pl.kacper.icecall.ui.basic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import pl.kacper.icecall.R;
import pl.kacper.icecall.configuration.DaggerAppCompatActivity;
import pl.kacper.icecall.model.User;
import pl.kacper.icecall.modules.ModuleManager;
import pl.kacper.icecall.modules.SensorsService;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.ui.basic.adapters.ModulesListAdapter;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    PersistenceUtil persistenceUtil;

    @Inject
    ModuleManager moduleManager;

    private ListView listView;
    private ModulesListAdapter adapter;
    private List<String> moduleNames;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        user = persistenceUtil.getUser(getIntent().getStringExtra("USER_LOGIN"));
        if(user == null)
            user = persistenceUtil.getLoggedInUser();
        persistenceUtil.setLoggedInUser(user);
        moduleManager.setData();
        initListView();
        Intent i = new Intent(this, SensorsService.class);
        startService(i);
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.main_module_list);
        moduleNames = moduleManager.getEnabledModuleNames();
        adapter = new ModulesListAdapter(this, moduleNames);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.empty_main));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), DisplayActivity.class);
                intent.putExtra("MODULE_NAME", moduleNames.get(position));
                startActivity(intent);
            }
        });
    }
}
