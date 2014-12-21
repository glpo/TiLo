package com.app.tilo.timelogger.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.app.tilo.timelogger.R;
import com.app.tilo.timelogger.model.CategoryElement;

import java.util.List;

public class CheckboxListAdapter extends ArrayAdapter<CategoryElement> {
    private final List<CategoryElement> list;
    private final Activity context;

    public CheckboxListAdapter(Activity context, List<CategoryElement> list) {
        super(context, R.layout.list_layout, list);
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder {
        public  TextView text;
        public CheckBox checkbox;
    }

    @Override
    public void add(CategoryElement object) {
        super.add(object);
    }

    @Override
    public void remove(CategoryElement object) {
        //super.remove(object);
        list.remove(object);
    }

    public void remove(List<CategoryElement> list) {
        this.list.removeAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.textView);
            /*viewHolder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TrackingActivity.class);
                    intent.putExtra("category", viewHolder.text.getText());
                    context.startActivity(intent);
                }
            });*/
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkBox);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            CategoryElement element = (CategoryElement) viewHolder.checkbox.getTag();
                            for(CategoryElement el : list) {
                                if(el.getName().equalsIgnoreCase(element.getName())) {
                                    el.setSelected(buttonView.isChecked());
                                }
                            }

                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.checkbox.setChecked(list.get(position).isSelected());
        if(!list.get(position).isVisible()) {
            holder.checkbox.setVisibility(View.INVISIBLE);
        }
        return view;
    }

}
