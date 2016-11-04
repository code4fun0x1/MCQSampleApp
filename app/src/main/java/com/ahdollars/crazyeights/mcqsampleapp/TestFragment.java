package com.ahdollars.crazyeights.mcqsampleapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahdollars.crazyeights.mcqsampleapp.databases.MyDBHandler;
import com.ahdollars.crazyeights.mcqsampleapp.databases.MyDBHelper;
import com.ahdollars.crazyeights.mcqsampleapp.model.Options;
import com.ahdollars.crazyeights.mcqsampleapp.model.Questions;

import java.util.ArrayList;

/**
 * Created by Shashank on 02-11-2016.
 */

public class TestFragment extends Fragment {

    RecyclerView rv;
    SwipeRefreshLayout refresh;
  //  Button b;
    public MyAdapter adapter;
  //  MyDBHelper dbHelper;
    MyDBHandler dbHelper;
    SQLiteDatabase db;
    public static final String TAG="CHECKED";




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_test,container,false);
        rv=(RecyclerView)v.findViewById(R.id.test_container);
        refresh=(SwipeRefreshLayout)v.findViewById(R.id.swipeRefresh);
      //  dbHelper=new MyDBHelper(getActivity());
        dbHelper=MyDBHandler.getInstance(getActivity());
    //    db=dbHelper.getReadableDatabase();



       dbHelper.setOnDataChangeListener(new MyDBHandler.OnDataChangeListener() {
           @Override
           public void onUpdate() {



               AsyncTask<Context,Void,Void> loadDBtask=new AsyncTask<Context, Void, Void>() {
                   @Override
                   protected Void doInBackground(Context... contexts) {
                       Questions.questionList.clear();
                       Questions.questionList=new ArrayList<Questions>();

                       Cursor questionCursor=dbHelper.query(MyDBHelper.TABLE_QUESTION_NAME,new String[]{MyDBHelper.UID,MyDBHelper.Q_TEXT},null,null,null,null,null);

                       int qid=questionCursor.getColumnIndex(MyDBHelper.UID);
                       int qtext=questionCursor.getColumnIndex(MyDBHelper.Q_TEXT);
                       int otext;
                       int ovalid;
                       while(questionCursor.moveToNext()) {

                           int id=questionCursor.getInt(qid);
                           String q=questionCursor.getString(qtext);

                           Cursor optionCursor = dbHelper.query(MyDBHelper.TABLE_OPTIONS, new String[]{MyDBHelper.QID, MyDBHelper.OPTION_TEXT, MyDBHelper.OPTION_VALID}, " " + MyDBHelper.QID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
                           ArrayList<Options> op=new ArrayList<Options>();
                           otext=optionCursor.getColumnIndex(MyDBHelper.OPTION_TEXT);
                           ovalid=optionCursor.getColumnIndex(MyDBHelper.OPTION_VALID);
                           while(optionCursor.moveToNext()){

                               String optionText=optionCursor.getString(otext);
                               boolean v=Boolean.valueOf(optionCursor.getString(ovalid));
                               op.add(new Options(v,optionText));

                           }

                           Questions newQuestion=new Questions(op,q);
                           Questions.getQuestionList().add(newQuestion);

                       }


                       return null;
                   }

                   @Override
                   protected void onPostExecute(Void aVoid) {
                       super.onPostExecute(aVoid);
                      adapter.notifyDataSetChanged();
                   }
               }.execute(getActivity());

           }
       });

//        b=(Button)v.findViewById(R.id.clickme);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),Questions.getQuestionList().size()+"",Toast.LENGTH_LONG).show();
//            }
//        });



        AsyncTask<Context,Void,Void> loadDBtask=new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {

                Questions.questionList.clear();
                Questions.questionList=new ArrayList<Questions>();
               // Cursor questionCursor=db.query(MyDBHelper.TABLE_QUESTION_NAME,new String[]{MyDBHelper.UID,MyDBHelper.Q_TEXT},null,null,null,null,null);
                Cursor questionCursor=dbHelper.query(MyDBHelper.TABLE_QUESTION_NAME,new String[]{MyDBHelper.UID,MyDBHelper.Q_TEXT},null,null,null,null,null);

                int qid=questionCursor.getColumnIndex(MyDBHelper.UID);
                int qtext=questionCursor.getColumnIndex(MyDBHelper.Q_TEXT);
                int otext;
                int ovalid;
                while(questionCursor.moveToNext()) {

                    int id=questionCursor.getInt(qid);
                    String q=questionCursor.getString(qtext);

                 //   Cursor optionCursor = db.query(MyDBHelper.TABLE_OPTIONS, new String[]{MyDBHelper.QID, MyDBHelper.OPTION_TEXT, MyDBHelper.OPTION_VALID}, " " + MyDBHelper.QID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
                    Cursor optionCursor = dbHelper.query(MyDBHelper.TABLE_OPTIONS, new String[]{MyDBHelper.QID, MyDBHelper.OPTION_TEXT, MyDBHelper.OPTION_VALID}, " " + MyDBHelper.QID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

                    ArrayList<Options> op=new ArrayList<Options>();
                    otext=optionCursor.getColumnIndex(MyDBHelper.OPTION_TEXT);
                    ovalid=optionCursor.getColumnIndex(MyDBHelper.OPTION_VALID);
                    while(optionCursor.moveToNext()){

                        String optionText=optionCursor.getString(otext);
                        boolean v=Boolean.valueOf(optionCursor.getString(ovalid));
                        op.add(new Options(v,optionText));

                    }

                    Questions newQuestion=new Questions(op,q);
                    Questions.getQuestionList().add(newQuestion);

                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter=new MyAdapter();
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setAdapter(adapter);
            }
        }.execute(getActivity());



        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                AsyncTask<Context,Void,Void> loadDBtask=new AsyncTask<Context, Void, Void>() {
                    @Override
                    protected Void doInBackground(Context... contexts) {
                        Questions.questionList.clear();
                        Questions.questionList=new ArrayList<Questions>();

                        Cursor questionCursor=dbHelper.query(MyDBHelper.TABLE_QUESTION_NAME,new String[]{MyDBHelper.UID,MyDBHelper.Q_TEXT},null,null,null,null,null);

                        int qid=questionCursor.getColumnIndex(MyDBHelper.UID);
                        int qtext=questionCursor.getColumnIndex(MyDBHelper.Q_TEXT);
                        int otext;
                        int ovalid;
                        while(questionCursor.moveToNext()) {

                            int id=questionCursor.getInt(qid);
                            String q=questionCursor.getString(qtext);

                            Cursor optionCursor = dbHelper.query(MyDBHelper.TABLE_OPTIONS, new String[]{MyDBHelper.QID, MyDBHelper.OPTION_TEXT, MyDBHelper.OPTION_VALID}, " " + MyDBHelper.QID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
                            ArrayList<Options> op=new ArrayList<Options>();
                            otext=optionCursor.getColumnIndex(MyDBHelper.OPTION_TEXT);
                            ovalid=optionCursor.getColumnIndex(MyDBHelper.OPTION_VALID);
                            while(optionCursor.moveToNext()){

                                String optionText=optionCursor.getString(otext);
                                boolean v=Boolean.valueOf(optionCursor.getString(ovalid));
                                op.add(new Options(v,optionText));

                            }

                            Questions newQuestion=new Questions(op,q);
                            Questions.getQuestionList().add(newQuestion);

                        }


                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        adapter=new MyAdapter();
                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv.setAdapter(adapter);
                        refresh.setRefreshing(false);
                        Toast.makeText(getActivity(), ""+Questions.getQuestionList().size(), Toast.LENGTH_SHORT).show();

                    }
                }.execute(getActivity());




            }
        });


        return v;

    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView question;
        ArrayList<CheckBox> options=new ArrayList<>();
        View v;
        LinearLayout ll;

        public MyHolder(View itemView) {
            super(itemView);
            v=itemView;
            ll=(LinearLayout)v.findViewById(R.id.body);
            question=(TextView) v.findViewById(R.id.question);
        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyHolder>{


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=getActivity().getLayoutInflater().inflate(R.layout.each_question_card,parent,false);
            MyHolder holder=new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            final Questions q=Questions.getQuestionList().get(position);
            holder.question.setText((position+1)+". "+q.getQUESTION());
            int l=q.getOPTIONLIST().size();
            holder.ll.removeAllViews();
            if(holder.options.size()==0){
            for( int i=0;i<l;i++){
                CheckBox c=new CheckBox(getActivity());
                c.setChecked(false);
                c.setText(q.getOPTIONLIST().get(i).getOPTIONTEXT());
                final int t=i;
                c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        q.getOPTIONLIST().get(t).setMARKED(b);
                        Log.d(TAG, "onCheckedChanged: "+b);

                    }
                });
                holder.options.add(c);
                holder.ll.addView(c);
            }}else{
                for(int i=0;i<holder.options.size();i++){
                    holder.ll.addView(holder.options.get(i));
                    final int t=i;
                    holder.options.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            q.getOPTIONLIST().get(t).setMARKED(b);
                            Log.d(TAG, "onCheckedChanged: CHECKED ONE "+b);
                        }
                    });
                }
            }
        }

        @Override
        public int getItemCount() {
            return Questions.getQuestionList().size();
        }



    }




}
