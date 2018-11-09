package zm.gov.moh.core.repository.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import zm.gov.moh.core.repository.database.dao.derived.ClientDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAddressDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonNameDao;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttribute;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

@android.arch.persistence.room.Database(
        entities = {

                Person.class,
                PersonAddress.class,
                PersonAttribute.class,
                PersonAttributeType.class,
                PersonName.class,
        }, version = 1)
@TypeConverters(Converter.class)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "mcare";

    private static volatile Database dbInstance;

    //Dao
    public abstract PersonDao personDao();
    public abstract PersonAddressDao personAddressDao();
    public abstract PersonAttributeDao personAttributeDao();
    public abstract PersonAttributeTypeDao personAttributeTypeDao();
    public abstract PersonNameDao personNameDao();
    public abstract ClientDao clientDao();

    //database getter
    public static Database getDatabase(final Context context){

        if (dbInstance == null)
              synchronized (Database.class){
                  if (dbInstance == null)
                     dbInstance = Room.databaseBuilder(context.getApplicationContext(),Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
              }

         return dbInstance;
    }
}

/**
 *
 *package com.example.zita.architecturecomponent.repository.database;
 *
 * import android.arch.persistence.db.SupportSQLiteDatabase;
 * import android.arch.persistence.room.Database;
 * import android.arch.persistence.room.Room;
 * import android.arch.persistence.room.RoomDatabase;
 * import android.content.Context;
 * import android.os.AsyncTask;
 * import android.support.annotation.NonNull;
 *
 * import com.example.zita.architecturecomponent.repository.database.doa.PersonDoa;
 * import com.example.zita.architecturecomponent.repository.database.entity.Person;
 *
 * @Database(entities = {Person.class}, version = 1)
 * public abstract class AppDB extends RoomDatabase {
 *
 *     static volatile AppDB dbInstance;
 *
 *     public abstract PersonDoa personDoa();
 *
 *     private static RoomDatabase.Callback dbCallback = new Callback() {
 *         @Override
 *         public void onOpen(@NonNull SupportSQLiteDatabase db) {
 *             super.onOpen(db);
 *
 *             new PopulateDb(dbInstance).execute();
 *
 *         }
 *     };
 *
 *     public static AppDB getDB(final Context context){
 *         if (dbInstance == null)
 *             synchronized (AppDB.class){
 *                 if (dbInstance == null){
 *                     dbInstance = Room.databaseBuilder(context.getApplicationContext(),AppDB.class,"appdb").build();
 *                 }
 *             }
 *         return dbInstance;
 *     }
 *
 *
 *
 *     private static class PopulateDb extends AsyncTask<Void,Void,Void>{
 *
 *         PersonDoa mPersonDoa;
 *         PopulateDb(AppDB database){
 *             mPersonDoa = database.personDoa();
 *         }
 *
 *         @Override
 *         protected Void doInBackground(Void... voids) {
 *
 *             Person person1 = new Person("Zita","Tembo","Dev");
 *             Person person2 = new Person("Tony","Tembo","DevOps");
 *             mPersonDoa.insert(person1);
 *             mPersonDoa.insert(person2);
 *
 *             return null;
 *         }
 *     }
 * }
 */
