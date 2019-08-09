package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.repository.PersonRepository;

import java.util.List;

public class PersonViewModel extends AndroidViewModel{


    private PersonRepository personRepository; //Add a private member variable to hold a reference to the repository
    private LiveData<List<Person>> mAllPersons;
    private MutableLiveData<Person> searchResults;


    // Constructor of ViewModel
    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRepository = new PersonRepository(application);
        mAllPersons = personRepository.getAllPersons();
        searchResults = personRepository.getSearchResults();
    }

    //Getter method for getting all record
    public LiveData<List<Person>> getAllPersons() {
        return mAllPersons;
    }

    //Insert method
    public void insert(Person person){
        personRepository.insert(person);
    }

    // find method - this method will begin searching in personRepository, and then returned results are stored within search result returned by AsyncTask
    public void findPersonWithPersonID(int personID){
        personRepository.findPersonWithPersonID(personID);
    }

    public MutableLiveData<Person> getSearchResults() {
        return searchResults;
    }

    // Delete specific record
    public void deletePersonWithPersonID(int personID){
        personRepository.deletePersonWithPersonID(personID);
    }

    // delete all records
    public void deleteAllPersons(){
        personRepository.deleteAllPersons();
    }

    // update record
    public void update(Person person){
        personRepository.updateTask(person);
    }
}
