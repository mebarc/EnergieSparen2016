package luh.energiesparen;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mbarc on 14.09.2016.
 */
public class MyArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MyArrayAdapter(Context context, @LayoutRes int resource, String[]values) {
            super(context,resource, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           // convertView.setBackgroundColor(255);
            return super.getView(position,convertView, parent);
            /*LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText(values[position]);
            // change the icon for Windows and iPhone
            String s = values[position];
            if (s.startsWith("iPhone")) {
                imageView.setImageResource(R.drawable.no);
            } else {
                imageView.setImageResource(R.drawable.ok);
            }

            return rowView;*/
        }
    }
