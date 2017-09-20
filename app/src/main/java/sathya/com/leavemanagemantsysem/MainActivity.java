package sathya.com.leavemanagemantsysem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText userName,userpass;
    Spinner spinType;
    String uname,upass,type;
    LeaveManagementDatabase ld;
    SQLiteDatabase sd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName=(EditText)findViewById(R.id.EditTextEmail);
        userpass=(EditText)findViewById(R.id.PEdittextPassword);
        spinType=(Spinner)findViewById(R.id.spinnerType);
        ld=new LeaveManagementDatabase(this);
        sd=ld.getWritableDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void submit(View v)
    {
            uname=userName.getText().toString().trim();
            upass=userpass.getText().toString().trim();
            type=spinType.getSelectedItem().toString();

        if(uname.isEmpty()){
            userName.setError("User Name must be enterned");
            userName.requestFocus();
        }else{
            if(upass.isEmpty()){
                userpass.setError("Password must be enterned");
                userpass.requestFocus();
            }else{
                if(type.equals("Select"))
                {
                    Snackbar.make(v,"Select type of the login",Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    if(type.equals("Admin"))
                    {
                        if(upass.equals("admin") && upass.equals("admin"))
                        {
                            finish();
                            Intent i=new Intent(this,AdminActivity.class);
                            i.putExtra("admin",uname);
                            startActivity(i);
                        }
                        else
                        {
                            Snackbar.make(v,"invelid crediantial for admin",Snackbar.LENGTH_LONG).show();
                            userName.requestFocus();
                        }
                    }
                    else if(type.equals("Principal"))
                    {
                        String cols[]={ld.PASSWORD_COL,ld.STATUS_COL};
                        String sel=ld.EMAIL_COL+" = ?";
                        String sel_ags[]={uname};
                        Cursor c=sd.query(ld.PRINCIPAL_TABLE,cols,sel,sel_ags,null,null,null);
                        if(c.moveToFirst())
                        {
                            String password=c.getString(0);
                            String status=c.getString(1);
                            if(upass.equals(password))
                            {
                                Intent i = new Intent(this, WelcomePrincipal.class);
                                i.putExtra("kname", uname);
                                startActivity(i);
                            }
                        }else{
                        Snackbar.make(v,"invelid crediantial for Principal",Snackbar.LENGTH_LONG).show();
                        userName.requestFocus();
                        }
                    }

                    else if(type.equals("Head Of Department"))
                    {
                        String cols[]={ld.PASSWORD_COL,ld.STATUS_COL};
                        String sel=ld.EMAIL_COL+" = ?";
                        String sel_ags[]={uname};
                        Cursor c=sd.query(ld.HOD_TABLE,cols,sel,sel_ags,null,null,null);
                        if(c.moveToFirst())
                        {
                            String password=c.getString(0);
                            String status=c.getString(1);

                            if(upass.equals(password))
                            {
                                if(status.equals("pending"))
                                {
                                    //Toast.makeText(this, "status is pending", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder ad=new AlertDialog.Builder(this);
                                    ad.setTitle("Status");


                                    ad.setMessage("Sorry !\n\n Your  Registration is Pending.");
                                    ad.show();

                                }else{

                                    Intent i = new Intent(this, WelcomeHod.class);
                                    i.putExtra("kname", uname);

                                    startActivity(i);
                                }
                            }
                        }else
                            {
                            Snackbar.make(v,"invelid crediantial for Head Of Department",Snackbar.LENGTH_LONG).show();
                            userName.requestFocus();
                        }
                    }

                    else if(type.equals("Faculty"))
                    {
                        String cols[]={ld.PASSWORD_COL};
                        String sel=ld.EMAIL_COL+" = ?";
                        String sel_ags[]={uname};
                        Cursor c=sd.query(ld.FACULTY_TABLE,cols,sel,sel_ags,null,null,null);
                        if(c.moveToFirst())

                        {   String password=c.getString(0);

                            if(upass.equals(password))
                            {
                                finish();
                                Intent i = new Intent(this, WelcomeFaculty.class);
                                i.putExtra("kname", uname);
                                startActivity(i);
                            }
                        }
                        Snackbar.make(v,"invelid crediantial for Faculty",Snackbar.LENGTH_LONG).show();
                        userName.requestFocus();
                    }

                }
            }
        }

    }



    public void newUser(View v)
    {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.newuserdialoge);
        Button b = (Button) d.findViewById(R.id.b1_dailog);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(i);
                d.dismiss();
            }
        });

        Button b1 = (Button) d.findViewById(R.id.b2_dailog);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(i);
                d.dismiss();
            }
        });
        d.show();
    }

}
