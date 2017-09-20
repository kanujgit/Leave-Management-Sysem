package sathya.com.leavemanagemantsysem;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewHod extends Activity {
    LeaveManagementDatabase ld;
    SQLiteDatabase sd;


    TextView tv1,tv2;
    ListView approvedlv,pendinglv,canclelv;
    ArrayList al,profile_name,profile_cno;
    ArrayList al_can,al_ap,al_pen;
    String profile_n[],profile_contect[];
    String keys[]={"name","contectno"};
    int ids[]={R.id.viewtv1,R.id.viewtv2};
    String pass1="dummy",pass2="dummy";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hod);

        final LeaveManagementDatabase ld = new LeaveManagementDatabase(this);
        sd = ld.getWritableDatabase();

        approvedlv = (ListView) findViewById(R.id.lvApprovedHod);
        pendinglv = (ListView) findViewById(R.id.lvPendingHod);
        canclelv = (ListView) findViewById(R.id.lvCancleHod);
        try
        {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            pass1 = b.getString("admin");
            pass2=b.getString("principal");
        }catch(Exception e){
            e.printStackTrace();
        }


        if(pass2==null)
            pass2="dummy";
        if(pass1==null)
            pass1="dummy";


        showPending();
        showApprove();
        showCancle();


        pendinglv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


                String f_name = "a", f_email = "a", f_gender = "a", f_quali = "a", f_contect = "a";
                //final long f_contect=0;

                HashMap hm1 = (HashMap) al_pen.get(position);
                final String text = (String) hm1.get(keys[0]);
                String cols[] = {LeaveManagementDatabase.NAME_COL, LeaveManagementDatabase.CONTECTNO_COL, LeaveManagementDatabase.EMAIL_COL, LeaveManagementDatabase.GENDER_COL, LeaveManagementDatabase.EXPERIENCE_COL};
                String sel = LeaveManagementDatabase.EMAIL_COL + " = ? ";
                String sel_args[] = {text};
                Cursor c = sd.query(LeaveManagementDatabase.HOD_TABLE, cols, sel, sel_args, null, null, null);
                if (c.moveToFirst()) {
                    f_name = c.getString(0);
                    f_contect = c.getString(1);
                    f_email = c.getString(2);
                    f_gender = c.getString(3);
                    f_quali = c.getString(4);
                }

                if (pass1.equals("adminAlert"))
                {

                    android.support.v7.app.AlertDialog.Builder ad = new android.support.v7.app.AlertDialog.Builder(ViewHod.this);
                    ad.setTitle("Delete");
                    ad.setMessage("Do You Want to Delete this Faculty");
                    ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String whr = LeaveManagementDatabase.EMAIL_COL + "= ?";
                            String whr_args[] = {text};

                            int i = sd.delete(LeaveManagementDatabase.HOD_TABLE, whr, whr_args);
                            if (i != 0) {
                                showPending();
                                Toast.makeText(ViewHod.this, "delete the Hod", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ad.show();

                }
                else if(pass2.equals("principalAlert"))
                {

                    final Dialog d = new Dialog(ViewHod.this);
                    d.setContentView(R.layout.view_profile_dialoge);
                    TextView tv_name = (TextView) d.findViewById(R.id.d_name);
                    TextView tv_cno = (TextView) d.findViewById(R.id.d_con);
                    TextView tv_email = (TextView) d.findViewById(R.id.d_email);
                    TextView tv_gender = (TextView) d.findViewById(R.id.d_gender);
                    TextView tv_quali = (TextView) d.findViewById(R.id.d_quali);
                    ImageButton iv_approve = (ImageButton) d.findViewById(R.id.btn_right);
                    ImageButton iv_cancle = (ImageButton) d.findViewById(R.id.btn_wrong);

                    tv_name.setText(f_name);
                    tv_cno.setText(f_contect);
                    tv_email.setText(f_email);
                    tv_gender.setText(f_gender);
                    tv_quali.setText(f_quali);
                    iv_approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(ViewHod.this, "Accept", Toast.LENGTH_SHORT).show();
                            ContentValues cv=new ContentValues();
                            cv.put(LeaveManagementDatabase.STATUS_COL,"approved");
                            String whr=LeaveManagementDatabase.EMAIL_COL+"= ?";
                            String whr_args[]={text};
                            int res=sd.update(LeaveManagementDatabase.HOD_TABLE,cv,whr,whr_args);

                          Toast.makeText(ViewHod.this, ""+res, Toast.LENGTH_SHORT).show();
                           if(res!=0)
                            {

                                showPending();
                               showApprove();
                                Toast.makeText(ViewHod.this, "Hod is approve", Toast.LENGTH_SHORT).show();
                            }
                            d.dismiss();
                        }
                    });
                    iv_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ViewHod.this, "Rejected", Toast.LENGTH_SHORT).show();
                            ContentValues cv=new ContentValues();
                            cv.put(ld.STATUS_COL,"cancle");
                            String where=LeaveManagementDatabase.EMAIL_COL +" = ?";
                            String where_args[]={text};
                            int res=sd.update(LeaveManagementDatabase.HOD_TABLE,cv,where,where_args);
                            if(res!=0){
                                Toast.makeText(ViewHod.this, "Hod is Cancle", Toast.LENGTH_SHORT).show();
                                  showCancle();
                                  showPending();
                                  showApprove();
                            }
                            d.dismiss();
                        }
                    });
                    d.show();
                }
            }
        });
    }



public void showPending()
    {  al_pen = new ArrayList();
        String cols[] = {LeaveManagementDatabase.EMAIL_COL, LeaveManagementDatabase.CONTECTNO_COL};
        String sel = ld.STATUS_COL + " = ?";
        String sel_ags[] = {"pending"};
        Cursor c = sd.query(ld.HOD_TABLE, cols, sel, sel_ags, null, null, null);


        boolean res = c.moveToFirst();
        if (res) {
            do {
                String name   =c.getString(0);
                //profile_name.add(name);
                String  contectno=c.getString(1);
                //profile_cno.add(contectno);
                HashMap hm= new HashMap();
                hm.put(keys[0],name);
                hm.put(keys[1],contectno);
                al_pen.add(hm);
            } while (c.moveToNext());


        }
        SimpleAdapter sa=new SimpleAdapter(ViewHod.this,al_pen,R.layout.view_list_style,keys,ids);
        pendinglv.setAdapter(sa);




    }

public void showApprove()
    {


         al_ap = new ArrayList();
        String cols[] = {LeaveManagementDatabase.EMAIL_COL, LeaveManagementDatabase.CONTECTNO_COL};
        String sel = ld.STATUS_COL + " = ?";
        String sel_ags[] = {"approved"};
        Cursor c = sd.query(ld.HOD_TABLE, cols, sel, sel_ags, null, null, null);

        boolean res = c.moveToFirst();
        if (res)
        {
            do {
                String name   =c.getString(0);
                //profile_name.add(name);
                String  contectno=c.getString(1);
                //profile_cno.add(contectno);
                HashMap hm= new HashMap();
                hm.put(keys[0],name);
                hm.put(keys[1],contectno);
                al_ap.add(hm);

            } while (c.moveToNext());
        }

        SimpleAdapter sa=new SimpleAdapter(ViewHod.this,al_ap,R.layout.view_list_style,keys,ids);
        approvedlv.setAdapter(sa);
    }

public void showCancle()
    {

        al_can = new ArrayList();
        String cols[] = {LeaveManagementDatabase.EMAIL_COL, LeaveManagementDatabase.CONTECTNO_COL};
        String sel = ld.STATUS_COL + " = ?";
        String sel_ags[] = {"cancle"};
        Cursor c = sd.query(ld.HOD_TABLE, cols, sel, sel_ags, null, null, null);
        al_can.clear();

        boolean res = c.moveToFirst();
        if (res) {
            do {
                String name   =c.getString(0);
                //profile_name.add(name);
                String  contectno=c.getString(1);
                //profile_cno.add(contectno);
                HashMap hm= new HashMap();
                hm.put(keys[0],name);
                hm.put(keys[1],contectno);
                al_can.add(hm);
            } while (c.moveToNext());
        }
        SimpleAdapter sa=new SimpleAdapter(ViewHod.this,al_can,R.layout.view_list_style,keys,ids);
        canclelv.setAdapter(sa);
    }

/*public class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            int i=al.size();
            return i;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater li=getLayoutInflater();
            View v=li.inflate(R.layout.view_list_style,parent,false);
            ImageView iv=(ImageView)v.findViewById(R.id.view_iv);
            tv1=(TextView)v.findViewById(R.id.viewtv1);
            tv2=(TextView)v.findViewById(R.id.viewtv2);

            HashMap hm=(HashMap) al.get(position);
            String H=  (String)hm.get(keys[0]);
            String S=  (String)hm.get(keys[1]);
//            byte sb[]=(byte[])hm.get(keys[2]);
  //          bit= BitmapFactory.decodeByteArray(sb,0,sb.length);
            tv1.setText(H);
            tv2.setText(S);
    //        iv.setImageBitmap(bit);
            iv.setImageResource(R.drawable.login);
          //  Toast.makeText(ViewHod.this, "ok google", Toast.LENGTH_SHORT).show();
            return v;
        }
    }*/


}

