package fandradetecinfo.com.meusgastos.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class _BaseModel {

    protected String table;


    public static final String DATABASE_NAME = "meusgastosdb";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_CREATE_1 = "CREATE TABLE IF NOT EXISTS abastecimento "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, data DATETIME, id_veiculo INTEGER, "
            + " id_posto INTEGER, combustivel INTEGER, preco_litro DOUBLE, valor_pago DOUBLE, "
            + "odometro INTEGER)";


    public static final String TABLE_CREATE_2 = "CREATE TABLE IF NOT EXISTS consumo "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, km_percorridos INTEGER, litros_gastos DOUBLE, "
            + "id_abastecimento INTEGER, km_hora DOUBLE)";

    public static final String TABLE_CREATE_3 = "CREATE TABLE IF NOT EXISTS veiculo "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, modelo TEXT, fabricante TEXT, ano TEXT, "
            + " apelido TEXT)";

    public static final String TABLE_CREATE_4 = "CREATE TABLE IF NOT EXISTS posto "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT)";


    public static final String TABLE_ALTER_2 = "ALTER TABLE consumo ADD COLUMN id_abastecimento INTEGER";

    protected DataBaseHelper dbhelper;
    protected Context context;
    protected SQLiteDatabase db;

    public _BaseModel(Context ctx) {
        this.context = ctx;
        dbhelper = new DataBaseHelper(context);
        }

    private static class DataBaseHelper extends SQLiteOpenHelper {
        //Create a helper object to create, open, and/or manage a database.
        public DataBaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        //Called when the database is created for the first time.
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE_1);
            db.execSQL(TABLE_CREATE_2);
        	db.execSQL(TABLE_CREATE_3);
        	db.execSQL(TABLE_CREATE_4);
        }

        @Override
        //Called when the database needs to be upgraded.
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_1);
            //b.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_2);
            onCreate(db);
        }
    }

    public _BaseModel open() {
        //Create and/or open a database that will be used for reading and writing.
        db = dbhelper.getWritableDatabase();
//        try
//        {
//            db.rawQuery("SELECT id_abastecimento from consumo", null);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//
//            db.execSQL(TABLE_ALTER_2);
//        }
        return this;
    }

    public void close() {
        //Close any open database object.
        dbhelper.close();
    }

    //Insert record in the database

    public long insert(ContentValues content)
    {
        try {
            return db.insertOrThrow(table, null, content);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean exists(String sql, String[] args)
    {
        boolean ret = false;
        try {
            Cursor c = buscarCursor(sql, args);
            c.moveToFirst();
            if (!c.getString(0).equals("0")) {
                ret = true;
            }
            c.close();
        }
        catch (Exception e) {
            throw e;
        }
        return ret;
    }

    protected Cursor buscarCursor(String sql, String[] args)
    {
        try {
            //String sqltemp = "update abastecimento set data = '2017-07-24' where data = '24/07/2017'";
            //db.execSQL(sqltemp);



//            String sqltemp = "delete from abastecimento where id = (select max(id) from abastecimento)";
//            db.execSQL(sqltemp);
//            sqltemp = "delete from consumo where id = (select max(id) from consumo)";
//            db.execSQL(sqltemp);

//            String sqltemp = "update consumo set data = '2017-08-18' where data = '18/08/2017'";
//            db.execSQL(sqltemp);
//
//            String sqltemp = "update consumo "
//            + " set id_abastecimento = 42"
//            + " where id_abastecimento = 43 ";
//           db.execSQL(sqltemp);

            return db.rawQuery(sql, args, null);
        } catch (Exception e) {
            throw e;
        }
    }

    protected Cursor buscarCursor(String sql)
    {
        try {
            return db.rawQuery(sql, null);
        } catch (Exception e) {
            throw e;
        }
    }

    protected String tratarData(String data)
    {
        return data.substring(6, 10) + "-" + data.substring(3, 5) + "-" + data.substring(0, 2);
    }

}
