package sathya.com.leavemanagemantsysem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FacultyProfileUpdate extends Activity
{
    String gender ,f_email,alert_f="";
    EditText d_name,d_pass, d_repass, d_phone,d_quali,d_exp;
    LeaveManagementDatabase ld;
    SQLiteDatabase sd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        ld=new LeaveManagementDatabase(this);
        sd=ld.getWritableDatabase();


        d_name = (EditText) findViewById(R.id.det_name);
        d_pass = (EditText) findViewById(R.id.det_pass);
        d_repass = (EditText) findViewById(R.id.det_repass);
        RadioGroup d_RadioGroup = (RadioGroup) findViewById(R.id.d_gender);
        RadioButton d_gender_male=(RadioButton)findViewById(R.id.d_gender_male);
        RadioButton d_gender_female=(RadioButton)findViewById(R.id.d_gender_female);

        d_phone = (EditText) findViewById(R.id.det_phone);
        d_quali = (EditText) findViewById(R.id.det_qualification);
        d_exp = (EditText) findViewById(R.id.det_exp);
        Button update_b1=(Button)(findViewById(R.id.update_b1));
        Button update_b2=(Button)(findViewById(R.id.update_b2));



        Intent i = getIntent();
        d_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                gender = checkedRadioButton.getText().toString();
            }
        });

        try {
            Bundle b = i.getExtras();
            f_email = b.getString("kemail");

        }catch(Exception e)
        {
            e.printStackTrace();
        }


        String name = "",repassword="",password="",gender="",contect="",quali="",exp="";

        String sel[]={ld.NAME_COL,ld.PASSWORD_COL,ld.GENDER_COL,ld.CONTECTNO_COL,ld.QUALIFICATION_COL,ld.EXPERIENCE_COL};
        String sel_args[]={f_email};
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
    }


    String u_name,u_password,u_repassword,u_contect_no,u_qualification,u_exp;

    public void update(View v)
    {
        u_name = d_name.getText().toString().trim();
        u_password = d_pass.getText().toString().trim();
        u_repassword = d_repass.getText().toString().trim();
        u_contect_no = d_phone.getText().toString().trim();
        u_qualification = d_quali.getText().toString().trim();
        u_exp = d_exp.getText().toString().trim();

        if (validateName(u_name)) {
            if (validatePassword(u_password)) {
                if (validateReassword(u_repassword)) {
                    if (validateGender(gender)) {
                        if (validateContectNo(u_contect_no)) {
                            if (validateQualification(u_qualification)) {
                                if (validateExp(u_exp))
                                {

                                    //   if(flag && bit!=null)
                                    //  {

                                    //  ByteArrayOutputStream bout=new ByteArrayOutputStream();
                                    //   boolean result=bit.compress(Bitmap.CompressFormat.JPEG,100,bout);
                                    //  if(result)
                                    //  {
                                    double cno = Double.parseDouble(u_contect_no);
                                    //   byte image[] = bout.toByteArray();
                                    ContentValues cv = new ContentValues();
                                    cv.put(ld.NAME_COL, u_name);
                                    cv.put(ld.PASSWORD_COL, u_password);
                                    cv.put(ld.GENDER_COL, gender);
                                    cv.put(ld.CONTECTNO_COL, cno);
                                    cv.put(ld.QUALIFICATION_COL, u_qualification);
                                    cv.put(ld.EXPERIENCE_COL, u_exp);

                                    String wherArgs[]={f_email};
                                    String whereClause=ld.EMAIL_COL+"= ?";

                                    int res=sd.update(ld.FACULTY_TABLE,cv,whereClause,wherArgs);
                                    if(res!=0)
                                    {
                                        Toast.makeText(this,"Profile is Update",Toast.LENGTH_SHORT).show();
                                        Intent i=new Intent(this,WelcomeFaculty.class);
                                        i.putExtra("RESULT",f_email);
                                        setResult(Activity.RESULT_OK,i);
                                        finish();

                                    }else{
                                        Toast.makeText(this,"Error! Not Update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void reset(View v)
    {
        d_name.setText("");
        d_pass.setText("");
        d_repass.setText("");
        d_phone.setText("");
        d_quali.setText("");
        d_exp.setText("");
        d_name.requestFocus();
    }

    public boolean validateName(String u_name)
    {
        if(u_name.isEmpty())
        {
            d_name.setError("Name should be filled");
            d_name.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    public boolean validatePassword(String u_password)
    {

        if(u_password.isEmpty())
        {
            d_pass.setError("Password should be filled");
            d_pass.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    public boolean validateReassword(String u_repassword)
    {

               if (!(u_repassword.equals(u_password)))
                {
                    d_repass.setError("Password and repassword not match");
                    d_repass.requestFocus();
                    return false;
                }
                else
                    {
                        return true;
                    }
    }

    public boolean validateContectNo(String u_contect_no)
    {
        //EditText et_ame,et_pass,et_email,et_contact,et_qualification,et_experience;
        if(u_contect_no.isEmpty())
        {
            d_phone.setError("Contect no should be filled");
            d_phone.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    public boolean validateQualification(String u_qualification)
    {
        //EditText et_ame,et_pass,et_email,et_contact,et_qualification,et_experience;
        if(u_qualification.isEmpty())
        {
            d_quali.setError("Qualification should be filled");
            d_quali.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    public boolean validateExp(String u_exp)
    {
        //EditText et_ame,et_pass,et_email,et_contact,et_qualification,et_experience;
        if(u_exp.isEmpty())
        {
            d_exp.setError("Experience should be filled");
            d_exp.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    private boolean validateGender(String gender)
    {
        if (gender.isEmpty())
        {
            Toast.makeText(this,"please select Gender",Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
