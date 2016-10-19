package pl.kacper.icecall.ui.basic.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.kacper.icecall.R;
import pl.kacper.icecall.configuration.DaggerAppCompatActivity;
import pl.kacper.icecall.model.User;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.ui.basic.adapters.UsersListAdapter;

public class UserListActivity extends DaggerAppCompatActivity {

    @Inject
    PersistenceUtil persistenceUtil;

    ListView listView;
    UsersListAdapter adapter;
    List<String> userNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initListView();
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.user_list_view);
        List<User> users = persistenceUtil.getUserList();
        for(User u : users) {
            userNames.add(u.getLogin());
        }
        adapter = new UsersListAdapter(this, userNames);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "Clicked on " + userNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
