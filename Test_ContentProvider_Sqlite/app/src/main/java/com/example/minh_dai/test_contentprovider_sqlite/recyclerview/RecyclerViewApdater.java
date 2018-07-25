package com.example.minh_dai.test_contentprovider_sqlite.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minh_dai.test_contentprovider_sqlite.R;
import com.example.minh_dai.test_contentprovider_sqlite.data.Book;

import java.util.List;

public class RecyclerViewApdater extends RecyclerView.Adapter<RecyclerViewApdater.ViewHodel> {

    private List<Book> mList;

    public RecyclerViewApdater(List<Book> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.mTxtAuthor.setText(mList.get(position).getAuthor());
        holder.mTxtTitle.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {

        private TextView mTxtTitle;
        private TextView mTxtAuthor;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            mTxtTitle = itemView.findViewById(R.id.txt_title);
            mTxtAuthor = itemView.findViewById(R.id.txt_author);
        }
    }
}
