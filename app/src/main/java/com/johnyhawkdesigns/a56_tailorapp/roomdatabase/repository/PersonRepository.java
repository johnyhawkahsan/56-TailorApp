package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.TailorRoomDatabase;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.asyncResults.AsyncResult;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.dao.PersonDao;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;

import java.util.List;


/*
 * A Repository is a class that abstracts access to multiple data sources.
 * The Repository is not part of the Architecture Components libraries, but is a suggested best practice for code separation and architecture.
 * A Repository class handles data operations. It provides a clean API to the rest of the app for app data
 */
public class PersonRepository implements AsyncResult {

    private static final String TAG = PersonRepository.class.getSimpleName();

    private PersonDao personDao; // PersonDao object instance
    private LiveData<List<Person>> mAllPersons; // list of all persons in liveData

    public PersonRepository(Application application){
        TailorRoomDatabase tailorRoomDatabase = TailorRoomDatabase.getDBINSTANCE(application); // Get instance of DB
        personDao = tailorRoomDatabase.personDao(); // we receive personDao from tailorRoomDatabase
        mAllPersons = personDao.getAllPersons(); // We are receiving all persons data in constructor
    }

    // Room executes all queries on a separate thread. Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Person>> getAllPersons() {
        return mAllPersons;
    }



    // ===================================================Insert Method==============================================================//
    public void insert(Person person){
        new PersonRepository.insertAsyncTask(personDao).execute(person); // add this person object using asyncTask to PersonDao
    }

    // Inner Class asyncTask to insert Person data
    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> { // Nothing needs to be reuturned from this class, because we
        private static final String TAG = PersonRepository.insertAsyncTask.class.getSimpleName();
        private PersonDao mAsyncTaskDao;

        //constructor
        insertAsyncTask(PersonDao personDao){
            mAsyncTaskDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... params) {
            mAsyncTaskDao.insert(params[0]);//Insert
            return null;
        }

    }
// =================================================== //Insert Method==============================================================//





// =====================================================Query/Find Method==========================================================//
    // We declare a MutableLiveData variable named personSearchResult into which the results of a search operation are stored whenever a asynchronous search task completes
    private MutableLiveData<Person> personSearchResult = new MutableLiveData<>();

    // methods that ViewModel can call to obtain a references to the searchResults live data objects
    public MutableLiveData<Person> getSearchResults() {
        return personSearchResult;
    }

    // Note that this method also returns void, but we capture returned "person" via delegation pattern (Using an interface "AsyncResult")
    public void findPersonWithMobileNo(int mobileNo) {
        PersonRepository.queryAsyncTask task = new PersonRepository.queryAsyncTask(personDao);
        task.delegate = this; // PersonRepository delegate inside queryAsyncTask class is assigned a value which is this exact parent class "PersonRepository"
        task.execute(mobileNo); // We provide "mobileNo" to this asyncTask as input
    }

    // We pass this "Integer" primitive type (MobileNo) and in return, we receive Person object
    private static class queryAsyncTask extends AsyncTask<Integer, Void, Person> {

        private static final String TAG = PersonRepository.queryAsyncTask.class.getSimpleName();
        private PersonDao asyncTaskDao;
        private PersonRepository delegate = null; // An instance of Base/Parent class, this will be instantiated when we create "task" object from queryAsyncTask in constructor

        // constructor method needs to be be passed a reference to the DAO object
        queryAsyncTask(PersonDao dao) {
            asyncTaskDao = dao;
        }

        // passed an Integer (MobileNo) for which the search is to be performed against Persons, passes it to the getPersonWithMobileNo() method of the DAO and returns a list of matching Person entity objects         @Override
        protected Person doInBackground(Integer... params) {
            Log.d(TAG, "doInBackground: mobileNo in params = " + params[0]);
            return asyncTaskDao.getPersonWithMobileNo(params[0]); // call our dao sqliteQuery inside doInBackground
        }

        @Override
        protected void onPostExecute(Person person) {
            // delegate is an instance of parentClass "PersonRepository", we created this instance to call interface method "asyncFinished"
            delegate.asyncFinished(person); // returned person result is "interfaced" /returned through asyncFinished method (We cannot directly return an object from Class, only methods have return types, that's why we used an interface)
        }
    }

    // NOTE == We cannot return directly from a class, that's why we used an Interface "asyncResult" to hold our returned person object
    // This is our interface's implemented method. It takes person object from onPostExecute and help us set value for childSearchResult.
    @Override
    public void asyncFinished(Person foundPerson) {
        Log.d(TAG, "asyncFinished: foundPerson.getMobileNo() = " + foundPerson.getMobileNo());
        personSearchResult.setValue(foundPerson); // setValue is a special method for "MutableLive Data". We set the found person value to personSearchResult object
    }
// ===============================================================//Query/Find Method==========================================================//






// ===============================================Delete single person Method==========================================================//
    public void deletePersonWithMobileNo(int mobileNo) {
        new deleteAsyncTask(personDao).execute(mobileNo);
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private static final String TAG = deleteAsyncTask.class.getSimpleName();
        private PersonDao asyncTaskDao;

        deleteAsyncTask(PersonDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            Log.d(TAG, "doInBackground: delete person with mobileNo = " + params[0]);
            asyncTaskDao.deleteChildWithID(params[0]);
            return null;
        }
    }
// ===============================================//Delete single person Method==========================================================//




// =====================================================Delete all persons Method==========================================================//
    public void deleteAllPersons() {
        new deleteAllAsyncTask(personDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = deleteAsyncTask.class.getSimpleName();
        private PersonDao asyncTaskDao;

        deleteAllAsyncTask(PersonDao dao) {
            asyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: delete all persons");
            asyncTaskDao.deleteAll();
            return null;
        }
    }
// =====================================================//Delete all persons Method==========================================================//




// =====================================================Update person Method==========================================================//
    // I intentionally left this task as static from (anita-1990) sample to check this functionality as well
    public void updateTask(final Person person) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                personDao.update(person);
                Log.d("updateTask", "doInBackground: updating person with mobileNo = " + person.getMobileNo());
                return null;
            }
        }.execute();
    }
// =====================================================//Update person Method==========================================================//



}
