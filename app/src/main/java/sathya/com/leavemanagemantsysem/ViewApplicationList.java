package sathya.com.leavemanagemantsysem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewApplicationList extends Activity {

    ListView pendingAppList;
    String keys[]={"name","contectno"};
    int ids[]={R.id.viewtv1,R.id.viewtv2};
    ArrayList pendingAppArrayList;
    LeaveManagementDatabase ld;
    SQLiteDatabase sd;
    String principal_profile="dummy";
    String principal_alert="dummy";
    String hod_profie="dummy";
    String hod_alert="dummy";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_application_list);
        pendingAppList = (ListView) findViewById(R.id.leaveAppList);
        pendingAppArrayList = new ArrayList();

        ld = new LeaveManagementDatabase(this);
        sd = ld.getWritableDatabase();

        try {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            hod_profie = b.getString("hodAlert1");
            hod_alert=b.getString("hodAlert2");
            principal_profile = b.getString("principalAlert1");
            principal_alert = b.getString("principalAlert2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(principal_alert==null)
        {
             principal_alert="dummy";
        }

        if(principal_profile==null)
        {
            principal_profile="dummy";
        }
        if(hod_alert==null)
        {
            hod_alert="dummy";
        }

        if(hod_profie==null)
        {
            hod_profie="dummy";
        }

        if (principal_alert.equals("pAlert"))
        {

            final ArrayList parrayList = new ArrayList();
            String cols[] = {ld.EMAIL_COL, ld.DEPARTMENT_COL};
            final String where = ld.STATUS_PRINCIPAL_COL + " = ? ";
            String where_args[] = {"pending"};
            Cursor c = sd.query(ld.LEAVE_TABLE, cols, where, where_args, null, null, null);
            int count = 0;
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(0);
                    String department = c.getString(1);

                    HashMap hm = new HashMap();
                    hm.put(keys[0], name);
                    hm.put(keys[1], department);
                    parrayList.add(hm);

                } while (c.moveToNext());
            }
            SimpleAdapter sa = new SimpleAdapter(this, parrayList, R.layout.view_list_style, keys, ids);
            pendingAppList.setAdapter(sa);

            pendingAppList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    HashMap hm1 = (HashMap) parrayList.get(position);
                    final String text = (String) hm1.get(keys[0]);
                    //Toast.makeText(ViewApplicationList.this, "" + text, Toast.LENGTH_SHORT).show();

                    android.support.v7.app.AlertDialog.Builder ad = new android.support.v7.app.AlertDialog.Builder(ViewApplicationList.this);
                    ad.setTitle("! Confirm Leave Application ");
                    ad.setMessage("Do You want to confirm this Leave Application");
                    ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentValues cv = new ContentValues();
                            cv.put(ld.STATUS_PRINCIPAL_COL, "approved");
                            String where = ld.EMAIL_COL + " = ?";
                            String where_args[] = {text};
                            int res = sd.update(ld.LEAVE_TABLE, cv, where, where_args);

                            if (res != 0)
                            {
                                Toast.makeText(ViewApplicationList.this, "Leave Application is approved", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ViewApplicationList.this, WelcomePrincipal.class);
                                i.putExtra("RESULT", principal_profile );
                                setResult(Activity.RESULT_OK, i);
                                finish();
                                dialog.dismiss();
                            }
                        }
                    });

                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentValues cv = new ContentValues();
                            cv.put(ld.STATUS_PRINCIPAL_COL, "cancel");
                            String where = ld.EMAIL_COL + " = ?";
                            String where_args[] = {text};
                            int res = sd.update(ld.LEAVE_TABLE, cv, where, where_args);
                            if (res != 0)
                            {
                                Toast.makeText(ViewApplicationList.this, "Leave Application is Cancel", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ViewApplicationList.this, WelcomePrincipal.class);
                                i.putExtra("RESULT", principal_profile);
                                setResult(Activity.RESULT_OK, i);
                                finish();
                                dialog.dismiss();
                            }
                        }
                    });
                    ad.show();
                    ad.setCancelable(false);
                }
            });







        } else if (hod_alert.equals("hAlert"))
        {

            String cols[] = {ld.EMAIL_COL, ld.DEPARTMENT_COL};
            final String where = ld.STATUS_HOD_COL + " = ? ";
            String where_args[] = {"pending"};
            Cursor c = sd.query(ld.LEAVE_TABLE, cols, where, where_args, null, null, null);
            int count = 0;
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(0);
                    String department = c.getString(1);

                    HashMap hm = new HashMap();
                    hm.put(keys[0], name);
                    hm.put(keys[1], department);
                    pendingAppArrayList.add(hm);

                } while (c.moveToNext());
            }
            SimpleAdapter sa = new SimpleAdapter(this, pendingAppArrayList, R.layout.view_list_style, keys, ids);
            pendingAppList.setAdapter(sa);

            pendingAppList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    HashMap hm1 = (HashMap) pendingAppArrayList.get(position);
                    final String text = (String) hm1.get(keys[0]);
                    Toast.makeText(ViewApplicationList.this, "" + text, Toast.LENGTH_SHORT).show();

                    android.support.v7.app.AlertDialog.Builder ad = new android.support.v7.app.AlertDialog.Builder(ViewApplicationList.this);
                    ad.setTitle("! Confirm Leave Application ");
                    ad.setMessage("Do You want to confirm this Leave Application");
                    ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentValues cv = new ContentValues();
                            cv.put(ld.STATUS_HOD_COL, "approved");
                            String where = ld.EMAIL_COL + " = ?";
                            String where_args[] = {text};
                            int res = sd.update(ld.LEAVE_TABLE, cv, where, where_args);
                            if (res != 0) {
                                Toast.makeText(ViewApplicationList.this, "Leave Application is approved", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ViewApplicationList.this, WelcomeHod.class);
                                i.putExtra("RESULT", hod_profie);
                                setResult(Activity.RESULT_OK, i);
                                finish();
                                dialog.dismiss();
                            }
                        }
                    });

                    ad.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentValues cv = new ContentValues();
                            cv.put(ld.STATUS_HOD_COL, "cancel");
                            String where = ld.EMAIL_COL + " = ?";
                            String where_args[] = {text};
                            int res = sd.update(ld.LEAVE_TABLE, cv, where, where_args);
                            if (res != 0) {
                                Toast.makeText(ViewApplicationList.this, "Leave Application is Cancle", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ViewApplicationList.this, WelcomeHod.class);
                                i.putExtra("RESULT", hod_profie);
                                setResult(Activity.RESULT_OK, i);
                                finish();
                                dialog.dismiss();
                            }
                        }
                    });
                    ad.show();
                    ad.setCancelable(false);
                      }
            });
        }
    }
}