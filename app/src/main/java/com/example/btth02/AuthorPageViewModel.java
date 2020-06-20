package com.example.btth02;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AuthorPageViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public MutableLiveData<String> newAuthor = new MutableLiveData<>();

    public void setNewAuthor(String d)
    {
        newAuthor.setValue(d);
    }

    public LiveData<String> getNewAuhor()
    {
        return newAuthor;
    }
}
