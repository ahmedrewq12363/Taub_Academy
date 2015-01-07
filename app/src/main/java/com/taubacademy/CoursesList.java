package com.taubacademy;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.widget.SearchView;

import static com.taubacademy.R.drawable.*;

/**
 * Created by ziad on 12/25/2014.
 */
public class CoursesList  extends Fragment implements AdapterView.OnItemClickListener {
    ListView coursesList;
    communicator c;

   String[] orig_courses_nums = {"234114","234123","234112","234122","236360","236363","234118"};
   String[] orig_courses_names = {"Introduction To Computers Science","Operating Systems","Introduction To Computer C Language","Introduction to Systems Programming","Theory Of Compilation","Database Systems","Computer Organization And Programming"};
   int global_items;
    public void search(String query)
    {
        if(query == "" || query==null)
        {
            coursesList.setAdapter(new mylistAdapter(getActivity().getBaseContext(),orig_courses_names,orig_courses_nums));
            return;
        }
        ArrayList<String> query_result_names = new ArrayList<String>();
        ArrayList<String> query_result_numbers = new ArrayList<String>();
        int k = 0;
        for(int i = 0 ; i < orig_courses_nums.length ; ++i)
        {
            if(orig_courses_nums[i].toLowerCase().contains(query.toLowerCase()) || orig_courses_names[i].toLowerCase().contains(query.toLowerCase()) )
            {
                query_result_names.add(k,orig_courses_names[i]);
                query_result_numbers.add(k,orig_courses_nums[i]);
                k++;
            }
        }

        String[] names  = new String[k] ;
        String[] numbers = new String[k] ;
        for(int i = 0 ; i < k ; ++i)
        {
            names[i] = query_result_names.get(i);
            numbers[i] = query_result_numbers.get(i);
        }
        coursesList.setAdapter(new mylistAdapter(getActivity().getBaseContext(),names,numbers));
        global_items = k;
    }

    public void restoreListView()
    {
        coursesList.setAdapter(new mylistAdapter(getActivity().getBaseContext(),orig_courses_names,orig_courses_nums));
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstances)
    {
        return inflater.inflate(R.layout.courses_fragment_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        coursesList = (ListView)getActivity().findViewById(R.id.listView);
        coursesList.setAdapter(new mylistAdapter(getActivity().getBaseContext(),orig_courses_names,orig_courses_nums));
        coursesList.setOnItemClickListener(this);
        c= (communicator) getActivity();

        SearchBar SearchStrings = new SearchBar(getActivity().getBaseContext());
        SearchView searchItem = (SearchView) getActivity().findViewById(R.id.searchView);
        searchItem.setSubmitButtonEnabled(true);
        searchItem.setOnQueryTextListener(SearchStrings);
        SearchStrings.setActivity(this);
        global_items = orig_courses_names.length;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView text = (TextView) view.findViewById(R.id.secondLine);
        c.respond(text.getText().toString());
    }

     class mylistAdapter extends BaseAdapter  {
        String[] Courses;
        String[] Names;
         WeakReference<Context> contextWeakReference;
        mylistAdapter(Context c,String[] courses_names,String[] courses_nums){
            contextWeakReference = new WeakReference<Context>(c);
            Courses=courses_nums;
            Names=courses_names;
        }
        @Override
        public int getCount() {
            return global_items;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewH = null;
            if(view == null){
//                view is null
                LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.course_item,viewGroup,false);
                viewH = new ViewHolder();
                viewH.firstLine = (TextView)view.findViewById(R.id.firstline);
                viewH.secondLine=(TextView)view.findViewById(R.id.secondLine);
                viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.icon = (ImageView) view.findViewById(R.id.icon11);
                view.setTag(viewH);
            }else {
                //view != null
                viewH = (ViewHolder) view.getTag();
            }
            //set curr view (the suitable indexes in the arrays).
            viewH.firstLine.setText(Names[i%Names.length]);
            viewH.secondLine.setText(Courses[i%(Courses.length)]);
            viewH.icon.setImageResource(technion1);
            return view;
        }


        class ViewHolder {
            TextView firstLine;
            TextView secondLine;
            ImageView icon;
            int position;
        }
    }
}
