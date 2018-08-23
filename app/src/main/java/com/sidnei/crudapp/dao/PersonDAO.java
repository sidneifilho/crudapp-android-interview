package com.sidnei.crudapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sidnei.crudapp.model.Person;
import com.sidnei.crudapp.persistent.DBSqliteGateway;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class PersonDAO implements IDAO<Person> {

    private final String TABLE_PERSON = "Persons";
    private DBSqliteGateway dbGateway;

    public PersonDAO(Context cx){
        dbGateway = DBSqliteGateway.getInstance(cx);
    }

    public boolean saveOrUpdate(Person p){

        SQLiteDatabase db = dbGateway.getDatabase();
        long rowsSavedOrUpdated;

        try{
            db.beginTransaction();

            ContentValues cv = new ContentValues();

            cv.put("Name", p.getName());
            cv.put("CPF", p.getCpf());
            cv.put("CEP", p.getCep());
            cv.put("UF", p.getUf());
            cv.put("Address", p.getAddress());

            switch (p.getSex()){
                case FEMALE:
                    cv.put("Sex", "f");
                    break;
                case MALE:
                    cv.put("Sex", "m");
                    break;
                case OTHER:
                    cv.put("Sex", "o");
                    break;
            }

            /// verifying if the object already exist in the database, if doens't we will insert a new record
            /// if exist, we will update the record
            if(p.getId() == 0){
                rowsSavedOrUpdated = db.insert(TABLE_PERSON, null, cv);
                db.setTransactionSuccessful();

                /// if the insert was succeed than we will get the ID value generated by the database to associate with the person object
                if(rowsSavedOrUpdated > 0){

                    /// verifying if is a valid record
                    Person newP = selectLastRecordInserted();
                    if(newP != null){
                        p.setId(newP.getId());
                    }

                }

            }else{
                rowsSavedOrUpdated = db.update(TABLE_PERSON, cv, "ID=?", new String[]{Integer.toString(p.getId())});
                db.setTransactionSuccessful();
            }

        }catch (Exception ex){
            Log.d("PersonDAO saveOrUpdate", "Erro when trying to save or update person, ERROR: " + ex.getMessage());
            return false;
        }finally {
            db.endTransaction();
        }

        return rowsSavedOrUpdated > 0;
    }

    public boolean deleteAll(){

        SQLiteDatabase db = dbGateway.getDatabase();
        long rowsDeleted = 0;

        try{
            db.beginTransaction();
            rowsDeleted = db.delete(TABLE_PERSON, "", new String[]{});
            db.setTransactionSuccessful();
        }catch (Exception ex){
            Log.d("PersonDAO deleteAll", "Erro when trying to delete all the persons, ERROR: " + ex.getMessage());
        }finally {
            db.endTransaction();
        }

        return rowsDeleted > 0;
    }

    public boolean delete(Person p){

        SQLiteDatabase db = dbGateway.getDatabase();
        long rowsDeleted = 0;

        try{
            db.beginTransaction();

            /// deleting by Person's ID
            String whereClause = "CPF=?";
            String[] args = new String[]{p.getCpf()};
            rowsDeleted = db.delete(TABLE_PERSON, whereClause, args);
            db.setTransactionSuccessful();
        }catch (Exception ex){
            Log.d("PersonDAO delete", "Erro when trying to delete a person, ERROR: " + ex.getMessage());
        }finally {
            db.endTransaction();
        }

        return rowsDeleted > 0;
    }

    public ArrayList<Person> selectAll(String whereClause){
        ArrayList<Person> listPerson = new ArrayList<>();
        SQLiteDatabase db = dbGateway.getDatabase();

        try{
            Cursor cursor;

            if(whereClause == null){
                cursor = db.rawQuery("SELECT * FROM Persons", null);
            }else{
                if(whereClause.equals("")){
                    cursor = db.rawQuery("SELECT * FROM Persons", null);
                }else{
                    cursor = db.rawQuery("SELECT * FROM Persons Where " + whereClause, null);
                }
            }

            while(cursor.moveToNext()){
                Person p = convertCursorToPerson(cursor);
                listPerson.add(p);
            }
            cursor.close();
        }catch (Exception ex){
            Log.d("PersonDAO selectAll", "Error when trying to select all persons from a database, ERROR: " + ex.getMessage());
        }

        return listPerson;
    }

    public Person selectByID(int id){
        Person person = null;
        SQLiteDatabase db = dbGateway.getDatabase();

        try{
            Cursor cursor = db.rawQuery("SELECT * FROM Persons Where ID=" + Integer.toString(id), null);

            if(cursor.moveToFirst()){
                person = convertCursorToPerson(cursor);
            }

            cursor.close();
        }catch (Exception ex){
            Log.d("PersonDAO selectById", "Error when trying to select by id a person, ERROR: " + ex.getMessage());
        }

        return person;
    }

    public Person selectLastRecordInserted(){
        Person person = null;
        SQLiteDatabase db = dbGateway.getDatabase();

        try{
            Cursor cursor = db.rawQuery("SELECT * FROM Persons", null);

            /// moving to the last register inserted to return
            if(cursor.moveToLast()){
                person = convertCursorToPerson(cursor);
            }

            cursor.close();
        }catch (Exception ex){
            Log.d("PersonDAO selLastRecAdd", "Error when trying to select the last person inserted, ERROR: " + ex.getMessage());
        }

        return person;
    }

    /**
     * Function used to convert a Cursor from the database to a Person object
     * @param cursor Cursor retrieved from a sql query
     * @return Person object with the values from the query
     * */
    private Person convertCursorToPerson(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String name = cursor.getString(cursor.getColumnIndex("Name"));
        String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
        String cep = cursor.getString(cursor.getColumnIndex("CEP"));
        String uf = cursor.getString(cursor.getColumnIndex("UF"));
        String address = cursor.getString(cursor.getColumnIndex("Address"));
        String sexStr = cursor.getString(cursor.getColumnIndex("Sex"));

        Person.SEX sex = Person.SEX.FEMALE;
        switch (sexStr){
            case "f":
                sex = Person.SEX.FEMALE;
                break;
            case "m":
                sex = Person.SEX.MALE;
                break;
            case "o":
                sex = Person.SEX.OTHER;
                break;
        }

        return new Person(id, name, cpf, cep, uf, address, sex);
    }
}
