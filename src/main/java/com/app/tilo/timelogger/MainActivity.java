package com.app.tilo.timelogger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.app.tilo.timelogger.adapter.CheckboxListAdapter;
import com.app.tilo.timelogger.db.DBTemplates;
import com.app.tilo.timelogger.model.CategoryElement;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private static final String LOG_TAG = "myLog";
    private boolean isEditMode = false;

    private MenuItem editMenuItem;
    private DBTemplates db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        db = new DBTemplates(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            ListView listView = (ListView) findViewById(R.id.categoriesListView);

            int listSize = listView.getChildCount();

            for (int i = 0; i < listSize; i++) {
                View lisItem = listView.getChildAt(i);
                CheckboxListAdapter.ViewHolder holder = (CheckboxListAdapter.ViewHolder) lisItem.getTag();
                holder.checkbox.setVisibility(View.INVISIBLE);
            }
            for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                CategoryElement element = (CategoryElement) listView.getAdapter().getItem(i);
                element.setVisible(false);
            }
            if(editMenuItem != null) {
                editMenuItem.setTitle("Edit");
            }
        } else if (id == R.id.action_new) {
            final EditText input = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("New Category")
                    .setMessage("Please, specify new category name: ")
                    .setView(input)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Editable value = input.getText();
                            ListView listView = (ListView) findViewById(R.id.categoriesListView);
                            CheckboxListAdapter adapter = (CheckboxListAdapter) listView.getAdapter();
                            adapter.add(new CategoryElement(value.toString(), isEditMode));

                            db.addNewCategory(value.toString());
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            }).show();
        } else if (id == R.id.action_edit) {
            isEditMode = true;
            ListView listView = (ListView) findViewById(R.id.categoriesListView);

            if ("Edit".equalsIgnoreCase(item.getTitle().toString())) {
                editMenuItem = item;
                item.setTitle("Delete");

                int listSize = listView.getChildCount();

                for (int i = 0; i < listSize; i++) {
                    View lisItem = listView.getChildAt(i);
                    CheckboxListAdapter.ViewHolder holder = (CheckboxListAdapter.ViewHolder) lisItem.getTag();
                    holder.checkbox.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                    CategoryElement element = (CategoryElement) listView.getAdapter().getItem(i);
                    element.setVisible(true);
                }
            } else if ("Delete".equalsIgnoreCase(item.getTitle().toString())) {
                CheckboxListAdapter adapter = (CheckboxListAdapter) listView.getAdapter();
                List<CategoryElement> list = new ArrayList<>();

                for (int i = 0; i < listView.getAdapter().getCount();  i++) {
                    CategoryElement element = adapter.getItem(i);
                    if (element.isSelected()) {
                        list.add(element);
                        db.deleteCategory(element.getName());
                    }
                }
                adapter.remove(list);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        private final String LOG_TAG = "myLogs";
        private ListView categoriesListView;

        private DBTemplates db;

        public PlaceholderFragment() {
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

            ListView listView = (ListView) getActivity().findViewById(R.id.categoriesListView);

            int listSize = listView.getAdapter().getCount();

            View item = listView.getChildAt(0);
            CheckboxListAdapter.ViewHolder h = (CheckboxListAdapter.ViewHolder) item.getTag();
            if (h.checkbox.getVisibility() == View.VISIBLE) {
                outState.putString("mode", "edit");

                boolean[] checkedArr = new boolean[listSize];

                for (int i = 0; i < listSize; i++) {
                    CategoryElement category = (CategoryElement) listView.getAdapter().getItem(i);
                    if (category.isSelected()) {
                        checkedArr[i] = true;
                    }
                }
                outState.putBooleanArray("checkedArr", checkedArr);
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            db = new DBTemplates(getActivity());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.category_fragment, container, false);

            categoriesListView = (ListView) rootView.findViewById(R.id.categoriesListView);

            List<String> categoryNames = db.getAllCategories();

            final List<CategoryElement> list = new ArrayList<CategoryElement>();
            for(String name : categoryNames) {
                list.add(new CategoryElement(name));
            }
            /*list.add(new CategoryElement("Work"));
            list.add(new CategoryElement("Shopping"));
            list.add(new CategoryElement("Education"));
            list.add(new CategoryElement("Sport"));
            list.add(new CategoryElement("Rest"));
            list.add(new CategoryElement("Entertainment"));
            list.add(new CategoryElement("Reading"));
            list.add(new CategoryElement("Cooking"));*/

            if (savedInstanceState != null) {
                String mode = savedInstanceState.getString("mode");
                if ("edit".equalsIgnoreCase(mode)) {
                    boolean[] arr = savedInstanceState.getBooleanArray("checkedArr");

                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setVisible(true);
                        if(i < arr.length) {
                            list.get(i).setSelected(arr[i]);
                        }
                    }
                }
            }

            ArrayAdapter<CategoryElement> adapter = new CheckboxListAdapter(getActivity(), list);

            categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ModeActivity.class);
                    intent.putExtra("category", list.get((int) id).getName());
                    Log.d("wtf", "start new ativity");
                    startActivity(intent);
                }
            });
            categoriesListView.setAdapter(adapter);

            return rootView;
        }
    }
}
