package pl.kacper.icecall.ui.settings.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import pl.kacper.icecall.R;
import pl.kacper.icecall.model.ModuleData;
import pl.kacper.icecall.model.User;

/**
 * Created by kacper on 02.01.16.
 */
public class SettingsAdapter extends ArrayAdapter<ModuleData> {
    private final Activity context;
    private List<ModuleData> list;
    private User user;

    public SettingsAdapter(Activity context, List<ModuleData> objects, User user) {
        super(context, R.layout.row_settings, objects);
        this.context = context;
        this.list = objects;
        this.user = user;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView == null) {
            LayoutInflater inflater =  context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_settings, null);

            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView)rowView.findViewById(R.id.settings_module_name);
            viewHolder.stateSwitch = (Switch) rowView.findViewById(R.id.settings_row_switch);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        final String moduleName =  list.get(position).getModuleName();
        viewHolder.nameTextView.setText(moduleName);

        boolean moduleState = list.get(position).getEnabled();
        viewHolder.stateSwitch.setChecked(moduleState);
        viewHolder.stateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setEnabled(isChecked);
                user.setModuleSettings(list);
            }
        });
        return rowView;
    }

    class ViewHolder {
        TextView nameTextView;
        Switch stateSwitch;
    }

    public void setList(List<ModuleData> list) {
        this.list = list;
    }
}
