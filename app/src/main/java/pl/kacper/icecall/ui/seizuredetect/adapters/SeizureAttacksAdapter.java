package pl.kacper.icecall.ui.seizuredetect.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.kacper.icecall.R;
import pl.kacper.icecall.model.ModuleData;
import pl.kacper.icecall.model.User;
import pl.kacper.icecall.model.seizuredetect.SeizureEvent;

/**
 * Created by kacper on 03.01.16.
 */
public class SeizureAttacksAdapter extends ArrayAdapter<SeizureEvent> {
    private final Activity context;
    private List<SeizureEvent> list;

    private final static String CALL_MADE = "Call Made";
    private final static String CALL_NOT_MADE = "Didn't Call";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SeizureAttacksAdapter(Activity context, List<SeizureEvent> objects) {
        super(context, R.layout.row_seizure_attacks, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView == null) {
            LayoutInflater inflater =  context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_seizure_attacks, null);

            viewHolder = new ViewHolder();
            viewHolder.dateTextView = (TextView)rowView.findViewById(R.id.seizuredetect_seizureDate);
            viewHolder.callMadeTextView = (TextView) rowView.findViewById(R.id.seizuredetect_iceNumber);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        Date date =  list.get(position).getTimestamp();
        String timestamp = dateFormat.format(date);
        viewHolder.dateTextView.setText(timestamp);

        boolean callMade = list.get(position).isCallMade();
        if(callMade)
            viewHolder.callMadeTextView.setText(CALL_MADE);
        else
            viewHolder.callMadeTextView.setText(CALL_NOT_MADE);

        return rowView;
    }

    class ViewHolder {
        TextView dateTextView;
        TextView callMadeTextView;
    }

    public void setList(List<SeizureEvent> list) {
        this.list = list;
    }
}
