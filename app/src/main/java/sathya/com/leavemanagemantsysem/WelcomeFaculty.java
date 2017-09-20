package sathya.com.leavemanagemantsysem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class WelcomeFaculty extends AppCompatActivity
{
    TextView profile;
    String profileName="";
    String gender = "";
    String to_date="";
    String from_date="";
    String department="",leave_reason="";

    final int REQUEST_CODE=123;

    LeaveManagementDatabase ld;
    SQLiteDatabase sd;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_faculty);
        profile = (TextView) findViewById(R.id.textviewUser);
        Intent i = getIntent();
        Bundle b = i.getExtras();
    try {
        profileName = b.getString("kname");
        profile.setText("Welcome " + profileName);
    }   catch (Exception e){
        e.printStackTrace();
    }

        ld=new LeaveManagementDatabase(this);
        sd=ld.getWritableDatabase();
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
        Intent i=new Intent(this,FacultyProfileUpdate.class);
        i.putExtra("kemail",profileName);
        startActivity(i);

       /*final Dialog d=new Dialog(this);
        d.setContentView(R.layout.update_profile);

        final EditText d_name = (EditText) d.findViewById(R.id.det_name);
        final EditText d_pass = (EditText) d.findViewById(R.id.det_pass);
        final EditText d_repass = (EditText) d.findViewById(R.id.det_repass);
        RadioGroup d_RadioGroup = (RadioGroup) d.findViewById(R.id.d_gender);
        RadioButton d_gender_male=(RadioButton)d.findViewById(R.id.d_gender_male);
        RadioButton d_gender_female=(RadioButton)d.findViewById(R.id.d_gender_female);
        final EditText d_phone = (EditText) d.findViewById(R.id.det_phone);
        final EditText d_quali = (EditText) d.findViewById(R.id.det_qualification);
        final EditText d_exp = (EditText) d.findViewById(R.id.det_exp);
        Button update_b1=(Button)d.findViewById(R.id.update_b1);
        Button update_b2=(Button)d.findViewById(R.id.update_b2);

        d_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton) d.findViewById(checkedId);
                gender = checkedRadioButton.getText().toString();
            }
        });
         String name = "",repassword="",password="",gender="",contect="",quali="",exp="";


        String sel[]={ld.NAME_COL,ld.PASSWORD_COL,ld.GENDER_COL,ld.CONTECTNO_COL,ld.QUALIFICATION_COL,ld.EXPERIENCE_COL};
        String sel_args[]={profileName};
        String where=ld.EMAIL_COL+" = ? ";
        Cursor c=sd.query(LeaveManagementDatabase.FACULTY_TABLE,sel,where,sel_args,null,null,null);
        if(c.moveToFirst())
        {
            name=c.getString(0);
            password=c.getString(1);
            gender=c.getString(2);
            contect=c.getString(3);
            quali=c.getString(4);
            exp=c.getString(5);
        }
        d_name.setText(name);
        d_pass.setText(password);
        d_repass.setText(password);
        d_phone.setText(contect);
        d_quali.setText(quali);
        d_exp.setText(exp);

        if(gender.equalsIgnoreCase("Male"))
        {
            d_gender_male.setChecked(true);
        }
        else if(gender.equalsIgnoreCase("Fe-Male"))
        {
            d_gender_female.setChecked(true);
        }



        update_b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name_b, repassword_b, password_b, gender_b, contect_b, quali_b, exp_b;
                name_b = d_name.getText().toString().trim();
                password_b = d_pass.getText().toString().trim();
                repassword_b = d_repass.getText().toString().trim();
                contect_b = d_phone.getText().toString().trim();
                quali_b = d_quali.getText().toString().trim();
                exp_b = d_exp.getText().toString().trim();

                if (validateName(name_b))
                {
                   if (validatePassword(password_b))
                    {
                        if (validateGender(gender))
                        {
                            if (validateContectNo(contect_no))
                            {
                                if (validateQualification(qualification))
                                {
                                    if (validateExp(exp))
                                    {

                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        d.show();*/

    }

    public void updateProfile(View v)
    {
        Intent i=new Intent(this,FacultyProfileUpdate.class);
        i.putExtra("kemail",profileName);
        startActivity(i);

       /*final Dialog d=new Dialog(this);
        d.setContentView(R.layout.update_profile);

        final EditText d_name = (EditText) d.findViewById(R.id.det_name);
        final EditText d_pass = (EditText) d.findViewById(R.id.det_pass);
        final EditText d_repass = (EditText) d.findViewById(R.id.det_repass);
        RadioGroup d_RadioGroup = (RadioGroup) d.findViewById(R.id.d_gender);
        RadioButton d_gender_male=(RadioButton)d.findViewById(R.id.d_gender_male);
        RadioButton d_gender_female=(RadioButton)d.findViewById(R.id.d_gender_female);
        final EditText d_phone = (EditText) d.findViewById(R.id.det_phone);
        final EditText d_quali = (EditText) d.findViewById(R.id.det_qualification);
        final EditText d_exp = (EditText) d.findViewById(R.id.det_exp);
        Button update_b1=(Button)d.findViewById(R.id.update_b1);
        Button update_b2=(Button)d.findViewById(R.id.update_b2);

        d_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton) d.findViewById(checkedId);
                gender = checkedRadioButton.getText().toString();
            }
        });
         String name = "",repassword="",password="",gender="",contect="",quali="",exp="";


        String sel[]={ld.NAME_COL,ld.PASSWORD_COL,ld.GENDER_COL,ld.CONTECTNO_COL,ld.QUALIFICATION_COL,ld.EXPERIENCE_COL};
        String sel_args[]={profileName};
        String where=ld.EMAIL_COL+" = ? ";
        Cursor c=sd.query(LeaveManagementDatabase.FACULTY_TABLE,sel,where,sel_args,null,null,null);
        if(c.moveToFirst())
        {
            name=c.getString(0);
            password=c.getString(1);
            gender=c.getString(2);
            contect=c.getString(3);
            quali=c.getString(4);
            exp=c.getString(5);
        }
        d_name.setText(name);
        d_pass.setText(password);
        d_repass.setText(password);
        d_phone.setText(contect);
        d_quali.setText(quali);
        d_exp.setText(exp);

        if(gender.equalsIgnoreCase("Male"))
        {
            d_gender_male.setChecked(true);
        }
        else if(gender.equalsIgnoreCase("Fe-Male"))
        {
            d_gender_female.setChecked(true);
        }



        update_b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name_b, repassword_b, password_b, gender_b, contect_b, quali_b, exp_b;
                name_b = d_name.getText().toString().trim();
                password_b = d_pass.getText().toString().trim();
                repassword_b = d_repass.getText().toString().trim();
                contect_b = d_phone.getText().toString().trim();
                quali_b = d_quali.getText().toString().trim();
                exp_b = d_exp.getText().toString().trim();

                if (validateName(name_b))
                {
                   if (validatePassword(password_b))
                    {
                        if (validateGender(gender))
                        {
                            if (validateContectNo(contect_no))
                            {
                                if (validateQualification(qualification))
                                {
                                    if (validateExp(exp))
                                    {

                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        d.show();*/

    }

    public void leaveStatus(View v)
    {

        String hod_pending="dummy",principal_pending="dummy";
        String columns[]={ld.STATUS_HOD_COL,ld.STATUS_PRINCIPAL_COL};
        String selection=ld.EMAIL_COL+" = ? ";
        String where[]={profileName};
        Cursor c=sd.query(ld.LEAVE_TABLE,columns,selection,where,null,null,null);
        if(c.moveToFirst())
        {
            hod_pending=c.getString(0);
            principal_pending=c.getString(1);
        }

        if(hod_pending.equals("pending"))
        {
            android.support.v7.app.AlertDialog.Builder ad = new android.support.v7.app.AlertDialog.Builder(this);
            ad.setTitle("Leave Status");
            ad.setMessage("Sorry !\n\nYour Leave application is Pending from Head Of Department.");
            ad.show();

        }
        else if(hod_pending.equals("cancel"))
        {
            android.support.v7.app.AlertDialog.Builder ad = new android.support.v7.app.AlertDialog.Builder(this);
            ad.setTitle("Leave Status");
            ad.setMessage("Sorry !\n\nYour Leave application is cancel from Head Of Department.");
            ad.show();
        }
        else if(principal_pending.equals("pending"))
        {
            AlertDialog.Builder ad=new AlertDialog.Builder(this);
            ad.setTitle("Leave Status");
            ad.setMessage("Sorry !\n\n Your  Leave application  is Pending from Principal Office.");
            ad.show();
        }
         else if(hod_pending.equals("cancel"))
        {
            AlertDialog.Builder ad=new AlertDialog.Builder(this);
            ad.setTitle("Leave Status");
            ad.setMessage("Sorry !\n\n Your  Leave application  is Cancel from Principal Office.");
            ad.show();
        }
        else if(hod_pending.equals("approved") && principal_pending.equals("approved"))
        {
            AlertDialog.Builder ad=new AlertDialog.Builder(this);
            ad.setTitle("Leave Status");
            ad.setMessage("Your  Leave application  is Approved.");
            ad.show();
        }
        else{
            Toast.makeText(this, "Wait ", Toast.LENGTH_SHORT).show();
        }


    }

    public  void takeLeave(View v)
    {
        Toast.makeText(this, "take Leave ", Toast.LENGTH_SHORT).show();
        final Dialog d=new Dialog(this);
        d.setContentView(R.layout.apply_for_leave);

        TextView tv_profile_name =(TextView)d.findViewById(R.id.tv_profile_name);
        final   Spinner sp_deprt =(Spinner)d.findViewById(R.id.sp1leave_reason);
        final   Spinner sp_reason=(Spinner)d.findViewById(R.id.sp2leave_reason);
                EditText leave_details=(EditText)d.findViewById(R.id.et_leave_reason);
        final   Button fromDate=(Button) d.findViewById(R.id.b1_leavefrom);
        final   Button toDate=(Button) d.findViewById(R.id.b2_leaveto);
        final   TextView tv1_date_from=(TextView)d.findViewById(R.id.tv1_leavefrom);
        final   TextView tv2_date_to=(TextView)d.findViewById(R.id.tv2_leaveto);
                Button submit=(Button)d.findViewById(R.id.leave_button);




        tv_profile_name.setText("Welcome "+profileName);
        Calendar mcurrentDate= Calendar.getInstance();

        final int   c_year=mcurrentDate.get(Calendar.YEAR);
        final int    c_month=mcurrentDate.get(Calendar.MONTH);
        final int  c_date=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        fromDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp=new DatePickerDialog(WelcomeFaculty.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        from_date=dayOfMonth+"/"+month+"/"+year;

                        tv1_date_from.setText(from_date);
                    }
                },c_year,c_month,c_date);
                dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dp.show();
            }
        });

        toDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp=new DatePickerDialog(WelcomeFaculty.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                         to_date=dayOfMonth+"/"+month+"/"+year;

                        tv2_date_to.setText(to_date);
                    }
                },c_year,c_month,c_date);
                dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dp.show();
            }
        });


        d.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                department=sp_deprt.getSelectedItem().toString();
                leave_reason=sp_reason.getSelectedItem().toString();
                String status_principal="pending";
                String status_hod="pending";
                ContentValues cv=new ContentValues();
                ContentValues cv1=new ContentValues();

                cv.put(ld.EMAIL_COL,profileName);
                cv.put(ld.DEPARTMENT_COL,department);
                cv.put(ld.LEAVE_REASON_COL,leave_reason);
                cv.put(ld.DATE_FROM_COL,to_date);
                cv.put(ld.DATE_TO_COL,from_date);
                cv.put(ld.STATUS_HOD_COL,status_hod);
                cv.put(ld.STATUS_PRINCIPAL_COL,status_principal);

                cv1.put(ld.DEPARTMENT_COL,department);
                cv1.put(ld.LEAVE_REASON_COL,leave_reason);
                cv1.put(ld.DATE_FROM_COL,to_date);
                cv1.put(ld.DATE_TO_COL,from_date);
                cv1.put(ld.STATUS_HOD_COL,status_hod);
                cv1.put(ld.STATUS_PRINCIPAL_COL,status_principal);

                String where_args[] = {profileName};
                String where = ld.EMAIL_COL + " = ?";
                long res=sd.insert(ld.LEAVE_TABLE,null,cv);
                if(res!=-1)
                {
                    Toast.makeText(WelcomeFaculty.this,"Leave Successfully submit",Toast.LENGTH_SHORT).show();
                    d.dismiss();
                }else{

                    long res_=sd.update(ld.LEAVE_TABLE,cv1,where,where_args);
                    if(res_!=0)
                    {
                        Toast.makeText(WelcomeFaculty.this,"Leave Application Successfully submit",Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }else{
                        Toast.makeText(WelcomeFaculty.this,"Leave Application Not submit",Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }

                    d.dismiss();
                }
            }
        });
    }


    public void totalDays(String date_from,String date_to)
    {


    }


    @Override
    public void onBackPressed()
    {
        return ;
    }

}