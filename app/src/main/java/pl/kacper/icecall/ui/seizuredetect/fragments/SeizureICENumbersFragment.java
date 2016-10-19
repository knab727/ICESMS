package pl.kacper.icecall.ui.seizuredetect.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import pl.kacper.icecall.R;
import pl.kacper.icecall.configuration.DaggerFragment;
import pl.kacper.icecall.model.seizuredetect.SeizureICEPhoneNumber;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.ui.seizuredetect.adapters.SeizureICENumbersAdapter;


public class SeizureICENumbersFragment extends DaggerFragment {

    @Inject
    PersistenceUtil persistenceUtil;

    private ListView listView;
    private List<SeizureICEPhoneNumber> numbersList;
    private SeizureICENumbersAdapter adapter;

    public SeizureICENumbersFragment() {
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
        return inflater.inflate(R.layout.fragment_seizure_icenumbers, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initListView();
    }

    private void initListView() {
        listView = (ListView) getView().findViewById(R.id.seizuredetect_iceNumber_list);
        numbersList = persistenceUtil.getLoggedInUser().getSeizureDetectData().getSeizurePhoneNumbers();
        adapter = new SeizureICENumbersAdapter(getActivity(), numbersList);
        listView.setAdapter(adapter);
        listView.setEmptyView(getView().findViewById(R.id.seizuredetect_empty_icelist));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_seizuredetect_icenumbers, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.seizuredetect_add_iceNumber:
                showAddICEContactDialog();
                return true;

            case R.id.seizuredetect_removeNumber:
                int position = listView.getCheckedItemPosition();
                if(position != -1) {
                    numbersList.remove(position);
                    adapter.notifyDataSetChanged();
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void showAddICEContactDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sd_ice);


        final EditText contactName = (EditText) dialog.findViewById(R.id.seizuredetect_dialog_contactName);
        final EditText phoneNum = (EditText) dialog.findViewById(R.id.seizuredetect_dialog_phoneNumber);

        Button dismissButton = (Button) dialog.findViewById(R.id.seizuredetect_dialog_dismiss_button);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button addContactButton = (Button) dialog.findViewById(R.id.seizuredetect_dialog_addUser_button);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = contactName.getText().toString();
                String phone = phoneNum.getText().toString();
                if(!(contact.equals("")|phone.equals(""))) {
                    numbersList.add(new SeizureICEPhoneNumber(contact, phone));
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
