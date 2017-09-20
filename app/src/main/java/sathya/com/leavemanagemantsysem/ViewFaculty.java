package sathya.com.leavemanagemantsysem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewFaculty extends AppCompatActivity {

    ListView approvedlv,pendinglv,canclelv;
    ArrayList al_can,al_ap,al_pen;
    LeaveManagementDatabase ld;
    SQLiteDatabase sd;
    String d_email,d_name,d_contect,d_gender,d_exp;
    String keys[]={"name","contectno"};
    int ids[]={R.id.viewtv1,R.id.viewtv2};
    String pass1="dummy",pass2="dummy",pass_hod="dummy";
    String text="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);
        approvedlv=(ListView)findViewById(R.id.lvApprovedFaculty);
        pendinglv=(ListView)findViewById(R.id.lvPendingFaculty);
        canclelv=(ListView)findViewById(R.id.lvCancleFaculty);


        try
        {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            pass1=b.getString("admin");
            pass2=b.getString("principal");
            pass_hod=b.getString("hod");

        }catch(Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        if(pass2==null)
            pass2="dummy";
        if(pass1==null)
            pass1="dummy";
        if(pass_hod==null)
            pass_hod="dummy";


        ld=new LeaveManagementDatabase(this);
        sd=ld.getWritableDatabase();


        showPending();
        showApprove();
        showCancle();


        pendinglv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                HashMap hm = (HashMap) al_pen.get(position);
                text = (String) hm.get(keys[0]);

                String cols[] = {LeaveManagementDatabase.NAME_COL, LeaveManagementDatabase.CONTECTNO_COL, LeaveManagementDatabase.EMAIL_COL, LeaveManagementDatabase.GENDER_COL, LeaveManagementDatabase.EXPERIENCE_COL};
                String sel_args[] = {text};
                String sel = LeaveManagementDatabase.EMAIL_COL + " = ? ";
                Cursor c = sd.query(LeaveManagementDatabase.FACULTY_TABLE, cols, sel, sel_args, null, null, null);
                if (c.moveToFirst()) {
                    d_name = c.getString(0);
                    d_contect = c.getString(1);
                    d_email = c.getString(2);
                    d_gender = c.getString(3);
                    d_exp = c.getString(4);
                }


                if ((pass1.equals("adminAlert")))
                {

                    android.support.v7.app.AlertDialog.Builder ad = new android.support.v7.app.AlertDialog.Builder(ViewFaculty.this);
                    ad.setTitle("Delete");
                    ad.setMessage("Do You Want to Delete this Faculty");
                    ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                     String whr = LeaveManagementDatabase.EMAIL_COL + "= ?";
                            String whr_args[] = {d_email};

                            int i = sd.delete(LeaveManagementDatabase.FACULTY_TABLE, whr, whr_args);
                            if (i != 0) {
                               // show();
                                Toast.makeText(ViewFaculty.this, "delete the faculty", Toast.LENGTH_SHORT).show();


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
                else if (pass2.equals("principalAlert"))
                {
                    final Dialog d = new Dialog(ViewFaculty.this);
                    d.setContentView(R.layout.view_profile_dialoge);
                    TextView tv_name = (TextView) d.findViewById(R.id.d_name);
                    TextView tv_cno = (TextView) d.findViewById(R.id.d_con);
                    TextView tv_email = (TextView) d.findViewById(R.id.d_email);
                    TextView tv_gender = (TextView) d.findViewById(R.id.d_gender);
                    TextView tv_quali = (TextView) d.findViewById(R.id.d_quali);
                    ImageButton iv_approve = (ImageButton) d.findViewById(R.id.btn_right);
                    ImageButton iv_cancle = (ImageButton) d.findViewById(R.id.btn_wrong);

                    tv_name.setText(d_name);
                    tv_cno.setText(d_contect);
                    tv_email.setText(d_email);
                    tv_gender.setText(d_gender);
                    tv_quali.setText(d_exp);
                    iv_approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            Toast.makeText(ViewFaculty.this, "Approve ", Toast.LENGTH_SHORT).show();
                            ContentValues cv=new ContentValues();
                            cv.put(LeaveManagementDatabase.STATUS_COL,"approved");
                            String whr=LeaveManagementDatabase.EMAIL_COL+"= ?";
                            String whr_args[]={text};

                            int res=sd.update(LeaveManagementDatabase.FACULTY_TABLE,cv,whr,whr_args);
                            Toast.makeText(ViewFaculty.this, ""+res, Toast.LENGTH_SHORT).show();
                            if(res!=0)
                            {

                                showPending();
                                showApprove();
                                Toast.makeText(ViewFaculty.this, "Faculty is approve", Toast.LENGTH_SHORT).show();
                            }
                            d.dismiss();

                        }
                    });

                    iv_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ViewFaculty.this, "Rejected", Toast.LENGTH_SHORT).show();
                            d.dismiss();
                        }
                    });
                    d.show();
                }
                else if(pass_hod.equals("HodAlert"))
                {
                    final Dialog d = new Dialog(ViewFaculty.this);
                    d.setContentView(R.layout.view_profile_dialoge);
                    TextView tv_name = (TextView) d.findViewById(R.id.d_name);
                    TextView tv_cno = (TextView) d.findViewById(R.id.d_con);
                    TextView tv_email = (TextView) d.findViewById(R.id.d_email);
                    TextView tv_gender = (TextView) d.findViewById(R.id.d_gender);
                    TextView tv_quali = (TextView) d.findViewById(R.id.d_quali);
                    ImageButton iv_approve = (ImageButton) d.findViewById(R.id.btn_right);
                    ImageButton iv_cancle = (ImageButton) d.findViewById(R.id.btn_wrong);

                    tv_name.setText(d_name);
                    tv_cno.setText(d_contect);
                    tv_email.setText(d_email);
                    tv_gender.setText(d_gender);
                    tv_quali.setText(d_exp);
                    iv_approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            Toast.makeText(ViewFaculty.this, "Approve ", Toast.LENGTH_SHORT).show();
                            ContentValues cv=new ContentValues();
                            cv.put(LeaveManagementDatabase.STATUS_COL,"approved");
                            String whr=LeaveManagementDatabase.EMAIL_COL+"= ?";
                            String whr_args[]={text};

                            int res=sd.update(LeaveManagementDatabase.FACULTY_TABLE,cv,whr,whr_args);
                            Toast.makeText(ViewFaculty.this, ""+res, Toast.LENGTH_SHORT).show();
                            if(res!=0)
                            {

                                showPending();
                                showApprove();
                                Toast.makeText(ViewFaculty.this, "Faculty is approve", Toast.LENGTH_SHORT).show();
                            }
                            d.dismiss();
                        }

                    });
                    d.show();


                    iv_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {

                            Toast.makeText(ViewFaculty.this, "Rejected", Toast.LENGTH_SHORT).show();
                            ContentValues cv=new ContentValues();
                            cv.put(ld.STATUS_COL,"cancle");
                            String where=LeaveManagementDatabase.EMAIL_COL +" = ?";
                            String where_args[]={text};
                            int res=sd.update(LeaveManagementDatabase.HOD_TABLE,cv,where,where_args);
                            if(res!=0){
                                Toast.makeText(ViewFaculty.this, "Hod is Cancle", Toast.LENGTH_SHORT).show();
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
    {
        al_pen = new ArrayList();
        String cols[] = {LeaveManagementDatabase.EMAIL_COL, LeaveManagementDatabase.CONTECTNO_COL};
        String sel = ld.STATUS_COL + " = ?";
        String sel_ags[] = {"pending"};
        Cursor c = sd.query(ld.FACULTY_TABLE, cols, sel, sel_ags, null, null, null);

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
                al_pen.add(hm);
            } while (c.moveToNext());
        }
        SimpleAdapter sa=new SimpleAdapter(ViewFaculty.this,al_pen,R.layout.view_list_style,keys,ids);
        pendinglv.setAdapter(sa);
    }

    public void showApprove()
    {
        al_ap = new ArrayList();
        String cols[] = {LeaveManagementDatabase.EMAIL_COL, LeaveManagementDatabase.CONTECTNO_COL};
        String sel = ld.STATUS_COL + " = ?";
        String sel_ags[] = {"approved"};
        Cursor c = sd.query(ld.FACULTY_TABLE, cols, sel, sel_ags, null, null, null);

        boolean res = c.moveToFirst();
        if (res)
        {
            do {
                String name   =c.getString(0);

                String  contectno=c.getString(1);

                HashMap hm= new HashMap();
                hm.put(keys[0],name);
                hm.put(keys[1],contectno);
                al_ap.add(hm);

            } while (c.moveToNext());
        }

        SimpleAdapter sa=new SimpleAdapter(ViewFaculty.this,al_ap,R.layout.view_list_style,keys,ids);
        approvedlv.setAdapter(sa);
    }

    public void showCancle()
    {

        al_can = new ArrayList();
        String cols[] = {LeaveManagementDatabase.EMAIL_COL, LeaveManagementDatabase.CONTECTNO_COL};
        String sel = ld.STATUS_COL + " = ?";
        String sel_ags[] = {"cancle"};
        Cursor c = sd.query(ld.FACULTY_TABLE, cols, sel, sel_ags, null, null, null);
        al_can.clear();

        boolean res = c.moveToFirst();
        if (res) {
            do {
                String name   =c.getString(0);
                String  contectno=c.getString(1);
                HashMap hm= new HashMap();
                hm.put(keys[0],name);
                hm.put(keys[1],contectno);
                al_can.add(hm);
            } while (c.moveToNext());
        }
        SimpleAdapter sa=new SimpleAdapter(ViewFaculty.this,al_can,R.layout.view_list_style,keys,ids);
        canclelv.setAdapter(sa);
    }
}
