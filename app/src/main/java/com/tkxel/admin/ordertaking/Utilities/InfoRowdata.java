package com.tkxel.admin.ordertaking.Utilities;

/**
 * Created by admin on 11/7/2017.
 */
public class InfoRowdata {

    public boolean isclicked=false;
    public int index;
    /*public String fanId;
    public String strAmount;*/

    public InfoRowdata(boolean isclicked,int index/*,String fanId,String strAmount*/)
    {
        this.index=index;
        this.isclicked=isclicked;
        /*this.fanId=fanId;
        this.strAmount=strAmount;*/
    }

}