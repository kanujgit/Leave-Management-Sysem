package sathya.com.leavemanagemantsysem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class WelcomeHod extends AppCompatActivity
{
    TextView profile;
    String profileName;
    final int REQUEST_CODE=123;
    LeaveManagementDatabase ld;
    SQLiteDatabase  sd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_hod);


        ld=new LeaveManagementDatabase(this);
        sd=ld.getWritableDatabase();

        profile = (TextView) findViewById(R.id.textviewUser);
        try{
            Intent i = getIntent();
            Bundle b = i.getExtras();
            profileName = b.getString("kname");
            profile.setText("Welcome " + profileName);
            
        }catch (Exception e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menuoption,menu);
        return true;
    }

    public void logout(MenuItem mi)
    {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void viewProfile(MenuItem mi)
    {
        finish();
        Intent i=new Intent(this,HodProfileUpdate.class);
        i.putExtra("hemail",profileName);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            if(requestCode==REQUEST_CODE)
            {
                if(resultCode==RESULT_OK)
                {
                    profileName=data.getExtras().getString("RESULT");
                }
            }
    }

    public void viewFacultyH(View v)
    {
        Intent i=new Intent(this,ViewFaculty.class);
        i.putExtra("hod","HodAlert");
        startActivity(i);
    }


    public void viewLeaveApplication(View v)
    {

        int count =getProfilesCount();
        if(!(count==0))
        {
            Intent intent=new Intent(this,ViewApplicationList.class);
            intent.putExtra("hodAlert1",profileName);
            intent.putExtra("hodAlert2","hAlert");
            startActivity(intent);

        }else{
            android.support.v7.app.AlertDialog.Builder ad = new android.support.v7.app.AlertDialog.Builder(this);
            ad.setTitle("Leave Application Status ");
            ad.setMessage("No Leave Application");
            ad.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            ad.setCancelable(false);
        }

        /*
        final Dialog d=new Dialog(this);
        setContentView(R.layout.leave_application_list);
        ListView lv=(ListView)d.findViewById(R.id.leaveAppList);

        String keys[]={"name","contectno"};
        int ids[]={R.id.viewtv1,R.id.viewtv2};
        ArrayList al=new ArrayList();
        String cols[]={ld.EMAIL_COL,ld.DEPARTMENT_COL};
        String where=ld.STATUS_HOD_COL+" = ? ";
        String where_args[]={"pending"};
        Cursor c=sd.query(ld.LEAVE_TABLE,cols,where,where_args,null,null,null);
        int count=0;
        if(c.moveToFirst())
        {
            do{
                Toast.makeText(this, ""+count++, Toast.LENGTH_SHORT).show();
                String name=c.getString(0);
                String department=c.getString(1);

                HashMap hm=new HashMap();
                hm.put(keys[0],name);
                hm.put(keys[1],department);
                al.add(hm);
                
            }while (c.moveToNext());
        }
        SimpleAdapter sa=new SimpleAdapter(this,al,R.layout.view_list_style,keys,ids);
        //lv.setAdapter(sa);
        d.show();*/
    }

    public void takeLeave_hod(View v)
    {
        Toast.makeText(this, "Take Leave", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        return ;
    }


    public int getProfilesCount()
    {

        String countQuery = "SELECT  * FROM " + LeaveManagementDatabase.LEAVE_TABLE;
//        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = sd.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
}
