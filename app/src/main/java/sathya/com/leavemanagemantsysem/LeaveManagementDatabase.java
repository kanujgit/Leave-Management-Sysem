package sathya.com.leavemanagemantsysem;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Anuj on 22-Aug-17.
 */

public class LeaveManagementDatabase extends SQLiteOpenHelper
{
    Context context;
    public  static final  String DATABASE_NAME="LMS";
    public  static final  int  VERSION=1;

    public  static final  String PRINCIPAL_TABLE="PRINCIPAL_DETAILS";
    public  static final  String HOD_TABLE="HOD_DETAILS";
    public  static final  String FACULTY_TABLE="FACULTY_DETAILS";
    public  static final  String LEAVE_TABLE="LEAVE_DETAILS";

    public  static final  String NAME_COL="NAME";
    public  static final  String EMAIL_COL="EMAIL";
    public  static final  String PASSWORD_COL="PASSWORD";
    public  static final  String GENDER_COL="GENDER";
    public  static final  String CONTECTNO_COL="CONTECT_NO";
    public  static final  String QUALIFICATION_COL="QULIFICATION";
    public  static final  String EXPERIENCE_COL="EXPERIENCE";
    public  static final  String STATUS_COL="STATUS";
    public  static final  String PHOTO_COL="PHOTO";

    public  static final  String DEPARTMENT_COL="DEPARTMENT";
    public  static final  String LEAVE_REASON_COL="LEAVE_REASON";
    public  static final  String DATE_FROM_COL="DATE_FROM";
    public  static final  String DATE_TO_COL="DATE_TO";
    public  static final  String STATUS_HOD_COL="HOD_LEAVE_STATUS";
    public  static final  String STATUS_PRINCIPAL_COL="PRINCIPAL_LEAVE_STATUS";




    public LeaveManagementDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query="create table "+PRINCIPAL_TABLE+" ( "+NAME_COL+" text,"+EMAIL_COL+" text Primary Key,"+PASSWORD_COL+" text,"+GENDER_COL+" text,"+CONTECTNO_COL+" integer,"+QUALIFICATION_COL+" text,"+EXPERIENCE_COL+" text,"+STATUS_COL+" text,"+PHOTO_COL+" BLOB)";
        db.execSQL(query);

        String query1="create table "+HOD_TABLE+" ( "+NAME_COL+" text,"+EMAIL_COL+" text Primary Key,"+PASSWORD_COL+" text,"+GENDER_COL+" text,"+CONTECTNO_COL+" integer,"+QUALIFICATION_COL+" text,"+EXPERIENCE_COL+" text,"+STATUS_COL+" text,"+PHOTO_COL+" BLOB)";
        db.execSQL(query1);

        String query2="create table "+FACULTY_TABLE+" ( "+NAME_COL+" text,"+EMAIL_COL+" text Primary Key,"+PASSWORD_COL+" text,"+GENDER_COL+" text,"+CONTECTNO_COL+" integer,"+QUALIFICATION_COL+" text,"+EXPERIENCE_COL+" text,"+STATUS_COL+" text,"+PHOTO_COL+" BLOB)";
        db.execSQL(query2);

        String query3="create table "+LEAVE_TABLE+" ( "+EMAIL_COL+" text Primary Key  ,"+DEPARTMENT_COL+" text,"+LEAVE_REASON_COL+" text,"+DATE_FROM_COL+" text,"+DATE_TO_COL+" text,"+STATUS_HOD_COL+" text,"+STATUS_PRINCIPAL_COL+" text)";
        db.execSQL(query3);
/*

        String department="";
        String leave_reason="";
        String status_principal="pending";
        String status_hod="pending";
        String to_date="";
        String from_date="";
        ContentValues cv=new ContentValues();
        cv.put(EMAIL_COL,"");
        cv.put(DEPARTMENT_COL,department);
        cv.put(LEAVE_REASON_COL,leave_reason);
        cv.put(DATE_FROM_COL,to_date);
        cv.put(DATE_TO_COL,from_date);
        cv.put(STATUS_HOD_COL,status_hod);
        cv.put(STATUS_PRINCIPAL_COL,status_principal);
        long res=db.insert(LEAVE_TABLE,null,cv);
        if(res!=-1)
        {
            Toast.makeText(context, "Table Create Leave ", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cancel ", Toast.LENGTH_SHORT).show();
        }
*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + PRINCIPAL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FACULTY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HOD_TABLE);

        // create new table
        onCreate(db);
    }
}
