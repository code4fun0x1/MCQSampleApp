package com.ahdollars.crazyeights.mcqsampleapp.model;

import java.util.ArrayList;

/**
 * Created by Shashank on 29-10-2016.
 */

public class Questions {

    String QUESTION;
    ArrayList<Options> OPTIONLIST;

    public Questions(ArrayList<Options> OPTIONLIST, String QUESTION) {
        this.OPTIONLIST = OPTIONLIST;
        this.QUESTION = QUESTION;
    }


    public ArrayList<Options> getOPTIONLIST() {
        return OPTIONLIST;
    }

    public void setOPTIONLIST(ArrayList<Options> OPTIONLIST) {
        this.OPTIONLIST = OPTIONLIST;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public static ArrayList<Questions> getQuestionList() {
        return questionList;
    }

    public static void setQuestionList(ArrayList<Questions> questionList) {
        Questions.questionList = questionList;
    }

    public  static ArrayList<Questions> questionList=new ArrayList<>();

}
