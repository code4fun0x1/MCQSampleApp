package com.ahdollars.crazyeights.mcqsampleapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahdollars.crazyeights.mcqsampleapp.model.Questions;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Shashank on 02-11-2016.
 */

public class ResultFragment extends Fragment {


    CircularProgressBar progressBar;
    TextView percentage,hideText;
    FloatingActionButton lock;
    public static final String TAG="CHECKING";
    SharedPreferences myPrefs;
    SwipeRefreshLayout refresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_result,container,false);
        myPrefs=getActivity().getSharedPreferences("ADMIN", Context.MODE_PRIVATE);

        lock=(FloatingActionButton)v.findViewById(R.id.lock);
        percentage=(TextView)v.findViewById(R.id.percent);
        hideText=(TextView)v.findViewById(R.id.hideText);
        refresh=(SwipeRefreshLayout)v.findViewById(R.id.swipeRefresh);
        progressBar=(CircularProgressBar) v.findViewById(R.id.progressBar);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myPrefs=getActivity().getSharedPreferences("ADMIN", Context.MODE_PRIVATE);
                SharedPreferences.Editor e=myPrefs.edit();
                e.clear();
                lock.setVisibility(View.VISIBLE);
                progressBar.setProgressWithAnimation(0f,700);
                percentage.setVisibility(View.INVISIBLE);
                hideText.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                refresh.setRefreshing(false);
            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: "+Questions.questionList.toString());
                
                view.setVisibility(View.GONE);
                hideText.setVisibility(View.GONE);
                int c=0;
              //  Toast.makeText(getActivity(), ""+Questions.getQuestionList().size(), Toast.LENGTH_SHORT).show();
                for(int i=0;i< Questions.questionList.size();i++){
                    Questions q=Questions.getQuestionList().get(i);
                    Log.d(TAG, "onClick: "+q.getQUESTION());
                    boolean correct=true;
                  for(int j=0;j<q.getOPTIONLIST().size();j++){
                      if(q.getOPTIONLIST().get(j).IS_TRUE()!=q.getOPTIONLIST().get(j).isMARKED()){
                          correct=false;
                          break;
                      }
                  }
                    
                    if(correct){
                        c++;
                  //      Toast.makeText(getActivity(), "Q no "+(i+1), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: CORRECT");
                    }
                        
                }

                Log.d(TAG, "onClick: END OF CHECKING");

                float p=0.0f;


                if(c!=0) {
              //      Toast.makeText(getActivity(), ""+c, Toast.LENGTH_SHORT).show();
                    p = ((float) c * 100 / Questions.getQuestionList().size());
                    Log.d(TAG, "onClick: PERCENTAGE " + p);
                    percentage.setVisibility(View.VISIBLE);
                    percentage.setText(p + "%");
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgressWithAnimation(p, 1000);
                }else{
               //     Toast.makeText(getActivity(), "else  "+c, Toast.LENGTH_SHORT).show();
                    percentage.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    percentage.setText(p + "%");
                    progressBar.setProgressWithAnimation(p, 1000);
                }
                SharedPreferences.Editor e=myPrefs.edit();
                e.putFloat("P",p);
                e.commit();
            }
        });
        v.setTag("TWO");
        float p=0.0f;
        if((p=myPrefs.getFloat("P",-1.0f))!=-1.0f){
            hideText.setVisibility(View.GONE);
            lock.setVisibility(View.GONE);
            percentage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            percentage.setText(p + "%");
            progressBar.setProgressWithAnimation(p, 1000);
        }
        return v;
    }





}
