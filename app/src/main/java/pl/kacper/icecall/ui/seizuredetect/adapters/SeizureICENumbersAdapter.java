package pl.kacper.icecall.ui.seizuredetect.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.kacper.icecall.R;
import pl.kacper.icecall.model.User;
import pl.kacper.icecall.model.seizuredetect.SeizureEvent;
import pl.kacper.icecall.model.seizuredetect.SeizureICEPhoneNumber;

/**
 * Created by kacper on 03.01.16.
 */
public class SeizureICENumbersAdapter extends ArrayAdapter<SeizureICEPhoneNumber> {

    private final Activity context;
    private List<SeizureICEPhoneNumber> list;

    public SeizureICENumbersAdapter(Activity context, List<SeizureICEPhoneNumber> objects) {
        super(context, R.layout.row_seizure_ice_numbers, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView == null) {
            LayoutInflater inflater =  context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_seizure_ice_numbers, null);

            viewHolder = new ViewHolder();
            viewHolder.contactTextView = (TextView)rowView.findViewById(R.id.seizuredetect_ICEcontactName);
            viewHolder.phoneNumberTextView = (TextView) rowView.findViewById(R.id.seizuredetect_iceNumber);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        final String contactName = list.get(position).getContactName();
        viewHolder.contactTextView.setText(contactName);

        final String phoneNumber = list.get(position).getPhoneNumber();
        viewHolder.phoneNumberTextView.setText(phoneNumber);

        return rowView;
    }

    class ViewHolder {
        TextView contactTextView;
        TextView phoneNumberTextView;
    }

    public void setList(List<SeizureICEPhoneNumber> list) {
        this.list = list;
    }
}
