package com.ahdollars.crazyeights.mcqsampleapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ahdollars.crazyeights.mcqsampleapp.databases.MyDBHandler;
import com.ahdollars.crazyeights.mcqsampleapp.databases.MyDBHelper;
import com.ahdollars.crazyeights.mcqsampleapp.model.Options;
import com.ahdollars.crazyeights.mcqsampleapp.model.Questions;

import java.util.ArrayList;

/**
 * Created by Shashank on 02-11-2016.
 */

public class AddQuestionFragment extends Fragment {


    RadioGroup optionContainer;
    EditText enteredQuestion,optionText;
    CheckBox isCorrect;
    FloatingActionButton add;
    Button addToBank,reset;
    ArrayList<Options> optionList;
    Options currentOption;
    Questions currentQuestion;
    ScrollView s;
    SharedPreferences myPrefs;
   // MyDBHelper dbHelper;
    MyDBHandler dbHelper;
    SQLiteDatabase db;
    boolean qValid=true;
    boolean oValid=true;
    boolean correctGiven=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_addques,container,false);
        s=(ScrollView)v.findViewById(R.id.scroll_view);
        optionContainer=(RadioGroup)v.findViewById(R.id.option_container);
        enteredQuestion=(EditText)v.findViewById(R.id.question);
        optionText=(EditText)v.findViewById(R.id.option_text);
        isCorrect=(CheckBox)v.findViewById(R.id.is_correct);
        add=(FloatingActionButton)v.findViewById(R.id.add_choise);
        addToBank=(Button) v.findViewById(R.id.add_to_bank);

      //  dbHelper=new MyDBHelper(getActivity());
        dbHelper=MyDBHandler.getInstance(getActivity());
     //   db=dbHelper.getWritableDatabase();

        reset=(Button) v.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Questions.questionList.clear();
                myPrefs=getActivity().getSharedPreferences("ADMIN", Context.MODE_PRIVATE);
                SharedPreferences.Editor e=myPrefs.edit();
                e.clear();
                e.commit();
                oValid=qValid=true;
                correctGiven=false;

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(optionText.getText().toString().trim().length()==0){
                    oValid=false;
                }

                if(isCorrect.isChecked()){
                    correctGiven=true;
                }

                if(oValid){

                    currentOption=new Options(isCorrect.isChecked(),optionText.getText().toString());
                    if(optionList==null){
                        optionList=new ArrayList<Options>();
                    }
                    RadioButton r=new RadioButton(getActivity());
                    r.setChecked(isCorrect.isChecked());
                    r.setText(optionText.getText().toString());
                    r.setClickable(false);
                    optionContainer.addView(r);
                    s.scrollTo(s.getMaxScrollAmount(),s.getMaxScrollAmount());
                    optionList.add(currentOption);
                    Toast.makeText(getActivity(),"VALID OPTION ADDED",Toast.LENGTH_SHORT).show();

                }else{

                }
                currentOption=null;
                isCorrect.setChecked(false);
                optionText.setText("");

           //     Toast.makeText(getActivity(),isCorrect.isChecked()+"",Toast.LENGTH_LONG).show();



            }
        });
        addToBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(enteredQuestion.getText().toString().trim().length()==0){
                    qValid=false;
                }

                if(qValid && oValid && correctGiven){
                    long uid=-1;
                    ContentValues cv=new ContentValues();
                    cv.put(MyDBHelper.Q_TEXT,enteredQuestion.getText().toString());
                   // uid=db.insert(MyDBHelper.TABLE_QUESTION_NAME,null,cv);
                    uid=dbHelper.insert(MyDBHelper.TABLE_QUESTION_NAME,cv);
                    if(uid!=-1){

                        for(int i=0;i<optionList.size();i++){
                            cv=null;
                            cv=new ContentValues();
                            cv.put(MyDBHelper.QID,uid);
                            cv.put(MyDBHelper.OPTION_TEXT,optionList.get(i).getOPTIONTEXT());
                            cv.put(MyDBHelper.OPTION_VALID,String.valueOf(optionList.get(i).IS_TRUE()));
                           // db.insert(MyDBHelper.TABLE_OPTIONS,null,cv);
                            dbHelper.insert(MyDBHelper.TABLE_OPTIONS,cv);
                        }
                        Toast.makeText(getActivity(),"VALID QUESTION ADDED",Toast.LENGTH_SHORT).show();
                        correctGiven=false;
                        qValid=true;
                        oValid=true;
                    }
                }else{

                }
         //       currentQuestion=new Questions(optionList,enteredQuestion.getText().toString());
        //        Questions.questionList.add(currentQuestion);
                currentQuestion=null;
                optionList=null;
                enteredQuestion.setText("");
                optionContainer.removeAllViews();
            }
        });
        v.setTag("ZERO");
        return v;
    }
}
