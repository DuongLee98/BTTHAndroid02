package com.example.btth02;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class BookPage extends Fragment {

    private BookPageViewModel mViewModel;
    private AuthorPageViewModel aViewModel;

    public static BookPage newInstance() {
        return new BookPage();
    }

    public Spinner spinAuthor;
    public ArrayList<String> arrAuthor;
    public String[] authorP = {"id", "name"};
    public String[] bookP = {"isbn", "title", "aid", "language", "price", "quantity", "daterelease"};
    public ArrayAdapter<String> arrayAdapAuthor;

    public EditText editISBN, editTitle, editPrice, editQuantity, editDate;
    public RadioButton radioButtonVi, radioButtonFo;
    public Button addBook, displayall, remove, update;

    public ArrayList<Book> arrBook;
    public RecyclerView recyclerView;
    public RecyleAdapter recyleAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_page_fragment, container, false);

        spinAuthor = v.findViewById(R.id.spinner_author);
        arrAuthor = new ArrayList<>();
        getAllAuthor();
        arrayAdapAuthor = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrAuthor);
        arrayAdapAuthor.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinAuthor.setAdapter(arrayAdapAuthor);
        aViewModel.getNewAuhor().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                getAllAuthor();
                arrayAdapAuthor.notifyDataSetChanged();
            }
        });

        recyclerView = v.findViewById(R.id.recylerBook);
        arrBook = new ArrayList<>();
        getAllBook();
        recyleAdapter = new RecyleAdapter(arrBook, v.getContext());
        recyclerView.setAdapter(recyleAdapter);
        linearLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        editISBN = v.findViewById(R.id.editISBN);
        editTitle = v.findViewById(R.id.editTitle);
        editPrice = v.findViewById(R.id.editPrice);
        editQuantity = v.findViewById(R.id.editQuantity);
        editDate = v.findViewById(R.id.editDate);

        radioButtonVi = v.findViewById(R.id.radioVietnamese);
        radioButtonFo = v.findViewById(R.id.radioForegin);
        addBook = v.findViewById(R.id.buttonAddBook);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cont = new ContentValues();
                cont.put("isbn", editISBN.getText().toString());
                cont.put("title", editTitle.getText().toString());
                cont.put("price", Float.parseFloat(editPrice.getText().toString()));
                cont.put("quantity", Integer.parseInt(editQuantity.getText().toString()));
                cont.put("daterelease", editDate.getText().toString());

                if (radioButtonVi.isSelected())
                {
                    cont.put("language", 0);
                }
                else
                {
                    cont.put("language", 1);
                }
                int author = Integer.parseInt(spinAuthor.getSelectedItem().toString().split("-")[0].trim());
                cont.put("aid", author);
                int lastInsertID = (int) MainActivity.db.insert("book", null, cont);
                getAllBook();
                recyleAdapter.notifyDataSetChanged();
            }
        });

        displayall = v.findViewById(R.id.buttonDisplayAll);
        displayall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllBook();
                recyleAdapter.notifyDataSetChanged();
            }
        });

        remove = v.findViewById(R.id.buttonRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = editISBN.getText().toString();
                String[] select = {isbn};
                int rs = MainActivity.db.delete("book", "isbn = ?", select);
                getAllBook();
                recyleAdapter.notifyDataSetChanged();
            }
        });

        update = v.findViewById(R.id.buttonUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cont = new ContentValues();
                //cont.put("isbn", editISBN.getText().toString());
                cont.put("title", editTitle.getText().toString());
                cont.put("price", Float.parseFloat(editPrice.getText().toString()));
                cont.put("quantity", Integer.parseInt(editQuantity.getText().toString()));
                cont.put("daterelease", editDate.getText().toString());

                if (radioButtonVi.isSelected())
                {
                    cont.put("language", 0);
                }
                else
                {
                    cont.put("language", 1);
                }
                int author = Integer.parseInt(spinAuthor.getSelectedItem().toString().split("-")[0].trim());
                cont.put("aid", author);
                String qr = "isbn = ?";
                String isbn = editISBN.getText().toString();
                String[] select = {isbn};
                MainActivity.db.update("book", cont, qr, select);
                getAllBook();
                recyleAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    public Author getAuthorById(int id)
    {
        String[] select = {id+""};
        Cursor cs = MainActivity.db.query("author", authorP, "id = ?", select, null, null, null);
        if (cs.moveToFirst())
        {
            Author rs = new Author(cs.getInt(0), cs.getString(1));
            //Log.e("GET", rs.getName()+" "+rs.getId());
            cs.close();
            return rs;
        }
        cs.close();
        return null;
    }

    public void getAllAuthor()
    {
        arrAuthor.clear();
        Cursor cursor = MainActivity.db.query("author", authorP, null, null, null, null, null);
        while (cursor.moveToNext())
        {
            arrAuthor.add(cursor.getInt(0) + " - " + cursor.getString(1));
        }
        cursor.close();
    }

    public void getAllBook()
    {
        arrBook.clear();
        Cursor cursor = MainActivity.db.query("book", bookP, null, null, null, null, null);
        while (cursor.moveToNext())
        {
            Book b = new Book();
            String isbn = cursor.getString(0);
            String title = cursor.getString(1);
            int aid = cursor.getInt(2);
            int language = cursor.getInt(3);
            float price = cursor.getFloat(4);
            int quantity = cursor.getInt(5);
            String daterelease = cursor.getString(6);

            b.setIsbn(isbn);
            b.setTitle(title);
            b.setAid(aid);
            b.setLanguage(language);
            b.setPrice(price);
            b.setQuantity(quantity);
            b.setDate(daterelease);
            Author tmp = getAuthorById(aid);
            b.setAuthor(tmp.getName());
            arrBook.add(b);
        }
        cursor.close();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(BookPageViewModel.class);
        aViewModel = ViewModelProviders.of(requireActivity()).get(AuthorPageViewModel.class);
        // TODO: Use the ViewModel
    }

}
