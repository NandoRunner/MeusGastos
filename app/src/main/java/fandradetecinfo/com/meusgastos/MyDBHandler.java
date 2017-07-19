package fandradetecinfo.com.meusgastos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "meusgastosdb";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME_1 = "abastecimento";
    public static final String TABLE_NAME_2 = "consumo";
    public static final String TABLE_NAME_3 = "veiculo";
    public static final String TABLE_NAME_4 = "posto";



    //Table query
    public static final String TABLE_CREATE_1 = "CREATE TABLE IF NOT EXISTS abastecimento "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, data DATETIME, id_veiculo INTEGER, "
            + " id_posto INTEGER, combustivel INTEGER, preco_litro DOUBLE, valor_pago DOUBLE, "
            + "odometro INTEGER)";


    public static final String TABLE_CREATE_2 = "CREATE TABLE IF NOT EXISTS consumo "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, data DATETIME, id_veiculo INTEGER, "
            + "combustivel INTEGER, km_percorridos INTEGER, litros_gastos DOUBLE)";

    public static final String TABLE_CREATE_3 = "CREATE TABLE IF NOT EXISTS veiculo "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, modelo TEXT, fabricante TEXT, ano TEXT, "
            + " apelido TEXT)";

    public static final String TABLE_CREATE_4 = "CREATE TABLE IF NOT EXISTS posto "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT)";


    private static MyDBHandler sInstance;

    public static synchronized MyDBHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MyDBHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    //protected Context context;
    //protected SQLiteDatabase db;
    protected String sql;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_1);
        db.execSQL(TABLE_CREATE_2);
        db.execSQL(TABLE_CREATE_3);
        db.execSQL(TABLE_CREATE_4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_4);
            onCreate(db);
        }
    }

    protected String buscarValorEscalar(String pSQL, String[] pArgs)
    {
        try {
            SQLiteDatabase db = getWritableDatabase();
            Cursor c = db.rawQuery(pSQL, pArgs, null);
            String ret = "";
            if (c.moveToFirst()) {
                ret = c.getString(0);
            }
            c.close();
            return ret;

        } catch (Exception ex) {
            throw ex;
        }
    }

    protected Cursor buscarCursor(String sql) {
        try {
            SQLiteDatabase db = getWritableDatabase();

            //db.execSQL("update abastecimento set data = (select data from consumo where abastecimento.id = consumo.id + 5) where id > 14");

            //db.execSQL("update consumo set data = '2017-04-02' where id = 21");
            //db.execSQL("update abastecimento set data = '2017-04-02' where id = 26");

            return db.rawQuery(sql, null);
        } catch (Exception ex) {
            throw ex;
        }
    }

    protected long inserir(String pTabela, ContentValues pContent)
    {
        try {
            SQLiteDatabase db = getWritableDatabase();
            return db.insertOrThrow(pTabela, null, pContent);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
