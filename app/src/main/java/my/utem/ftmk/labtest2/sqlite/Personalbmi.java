package my.utem.ftmk.labtest2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Personalbmi extends SQLiteOpenHelper {
    private static final String dbName = "BMI.db";
    private static final String tblName = "BMITable";
    private static final String colName = "Name";
    private static final String colWeight = "Weight";
    private static final String colHeight = "Height";
    private static final String colStatus = "Status";

    public Personalbmi(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + tblName + " (" +
                colName + " TEXT, " +
                colWeight + " REAL, " +
                colHeight + " REAL, " +
                colStatus + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tblName);
        onCreate(db);
    }

    public boolean insertData(String name, float weight, float height, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colName, name);
        contentValues.put(colWeight, weight);
        contentValues.put(colHeight, height);
        contentValues.put(colStatus, status);

        long result = db.insert(tblName, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + tblName, null);
    }

    public boolean updateData(String name, float weight, float height, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colWeight, weight);
        contentValues.put(colHeight, height);
        contentValues.put(colStatus, status);

        int result = db.update(tblName, contentValues, colName + "=?", new String[]{name});
        return result > 0;
    }

    public boolean deleteData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(tblName, colName + "=?", new String[]{name});
        return result > 0;
    }
}
