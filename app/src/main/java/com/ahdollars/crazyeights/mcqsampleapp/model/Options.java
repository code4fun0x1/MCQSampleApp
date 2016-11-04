package com.ahdollars.crazyeights.mcqsampleapp.model;

/**
 * Created by Shashank on 02-11-2016.
 */

public class Options{
    String OPTIONTEXT;
    boolean IS_TRUE;
    boolean MARKED;

    public Options(boolean IS_TRUE, String OPTIONTEXT) {
        this.IS_TRUE = IS_TRUE;
        this.OPTIONTEXT = OPTIONTEXT;
        MARKED=false;
    }

    public boolean isMARKED() {
        return MARKED;
    }

    public void setMARKED(boolean MARKED) {
        this.MARKED = MARKED;
    }

    public boolean IS_TRUE() {
        return IS_TRUE;
    }

    public void setIS_TRUE(boolean IS_TRUE) {
        this.IS_TRUE = IS_TRUE;
    }

    public String getOPTIONTEXT() {
        return OPTIONTEXT;
    }

    public void setOPTIONTEXT(String OPTIONTEXT) {
        this.OPTIONTEXT = OPTIONTEXT;
    }
}