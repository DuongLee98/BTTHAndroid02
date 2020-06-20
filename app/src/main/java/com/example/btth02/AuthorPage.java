package com.example.btth02;

import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthorPage extends Fragment {

    private AuthorPageViewModel mViewModel;

    public EditText authorName;
    public Button addAuthor;

    public static AuthorPage newInstance() {
        return new AuthorPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.author_page_fragment, container, false);
        authorName = v.findViewById(R.id.authorName);
        addAuthor = v.findViewById(R.id.addAuthor);
        addAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = authorName.getText().toString();
                ContentValues cont = new ContentValues();
                cont.put("name", name);
                long lastIndex = MainActivity.db.insert("author", null, cont);
                String data = lastIndex + " - " + name;
                if (lastIndex > 0)
                {
                    Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();
                }
                mViewModel.setNewAuthor(data);
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(AuthorPageViewModel.class);
        // TODO: Use the ViewModel
    }

}
