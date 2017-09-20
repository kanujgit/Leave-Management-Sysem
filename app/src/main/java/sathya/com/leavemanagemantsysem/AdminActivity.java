package sathya.com.leavemanagemantsysem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    TextView profile;
    String profileName;
    String flag;
    SQLiteDatabase sd;
    LeaveManagementDatabase ld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        profile = (TextView) findViewById(R.id.textviewUser);

        ld=new LeaveManagementDatabase(this);
         sd= ld.getWritableDatabase();

        try {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            profileName = b.getString("admin");
            profile.setText("Welcome " + profileName);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menuoption,menu);
        return true;
    }
    public  void viewProfile(MenuItem mi)
    {
        Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
    }

    public void logout(MenuItem mi)
    {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void addPrincipal(View v)
    {

        int count=getProfilesCount();
        if(count==0) {
            flag = "ok";
            Intent i = new Intent(this, RegistrationActivity.class);
            i.putExtra("k1", flag);
            startActivity(i);
        }else{
            Snackbar.make(v, "Not allowed ! Principal  have registered ", Snackbar.LENGTH_LONG).show();
        }
    }

    public void viewHod(View v)
    {


        Intent i = new Intent(this,ViewHod.class);
        i.putExtra("admin","adminAlert");
        startActivity(i);
    }

    public void viewFaculty(View v)
    {
     Intent i=new Intent(this ,ViewFaculty.class);
        i.putExtra("admin","adminAlert");
        startActivity(i);
    }



    public int getProfilesCount()
    {

        String countQuery = "SELECT  * FROM " + LeaveManagementDatabase.PRINCIPAL_TABLE;
//        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = sd.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public void viewLeaveReportAdmin(View v)
    {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        return ;
    }
}

