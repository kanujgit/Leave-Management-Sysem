package sathya.com.leavemanagemantsysem;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class WelcomePrincipal extends AppCompatActivity {

    TextView profile;
    String profileName;
    final  int REQUEST_CODE=123;
    LeaveManagementDatabase ld;
    SQLiteDatabase sd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_principal);
        profile = (TextView) findViewById(R.id.textviewUser);


        ld=new LeaveManagementDatabase(this);
        sd=ld.getWritableDatabase();
        try
        {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            profileName = b.getString("kname");
            profile.setText("Welcome " + profileName);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==REQUEST_CODE)
        {
            if(requestCode==RESULT_OK){
                profileName=data.getExtras().getString("RESULT");
            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menuoption,menu);
        return true;
    }

    public void viewProfile(MenuItem mi)
    {
        Intent i=new Intent(this,PrincipalProfileUpdate.class);
        i.putExtra("pemail",profileName);
        startActivity(i);
    }

    public void logout(MenuItem mi)
    {
        finish();
       /* Intent i = new Intent(this, MainActivity.class);
        startActivity(i);*/
    }


    public void viewHodP(View v)
    {
        Intent i=new Intent(this,ViewHod.class);
        i.putExtra("principal","principalAlert");
        startActivity(i);
    }


    public void  viewFacultyP(View v)
    {
        Intent i=new Intent(this,ViewFaculty.class);
        i.putExtra("principal","principalAlert");
        startActivity(i);
    }


    public  void viewLeaveApp(View v)
    {
        int count =getProfilesCount();
        if(!(count==0))
        {
            Intent intent=new Intent(this,ViewApplicationList.class);
            intent.putExtra("principalAlert1",profileName);
            intent.putExtra("principalAlert2","pAlert");
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
    }


    @Override
    public void onBackPressed()

    {
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
