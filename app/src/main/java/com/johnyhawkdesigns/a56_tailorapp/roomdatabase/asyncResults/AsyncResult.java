package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.asyncResults;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;

public interface AsyncResult {
    void asyncFinished(Person foundPerson);
}
