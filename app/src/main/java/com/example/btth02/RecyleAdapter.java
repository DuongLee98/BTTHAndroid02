package com.example.btth02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyleAdapter extends RecyclerView.Adapter<RecyleAdapter.RecyclerViewHolder>
{
    public ArrayList<Book> arrBook;
    public Context context;
    public Book b;

    public RecyleAdapter(ArrayList<Book> b, Context c) {
        this.arrBook = b;
        this.context = c;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.book_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        b = this.arrBook.get(position);
        holder.cvISBN.setText("ISBN: "+b.getIsbn());
        holder.cvTitle.setText("Title: " +b.getTitle());
        holder.cvAuthor.setText("Author: " +b.getAuthor());
    }

    @Override
    public int getItemCount() {
        return this.arrBook.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView cvISBN, cvTitle, cvAuthor;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            cvISBN = itemView.findViewById(R.id.cv_isbn);
            cvTitle = itemView.findViewById(R.id.cv_title);
            cvAuthor = itemView.findViewById(R.id.cv_author);
        }
    }
}
