package pl.kacper.icecall.ui.basic.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.kacper.icecall.R;

/**
 * Created by kacper on 30.12.15.
 */
public class ModulesListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private List<String> list;

    public ModulesListAdapter(Activity context, List<String> objects) {
        super(context, R.layout.row_module_list, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView == null) {
            LayoutInflater inflater =  context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_module_list, null);

            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView)rowView.findViewById(R.id.module_name);


            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        final String moduleName =  list.get(position);
        viewHolder.nameTextView.setText(moduleName);
        return rowView;
    }

    class ViewHolder {
        TextView nameTextView;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
