package com.codepath.bestsellerlistapp;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.bestsellerlistapp.models.BestSellerBook;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BestSellerBook} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class BestSellerBooksRecyclerViewAdapter extends RecyclerView.Adapter<BestSellerBooksRecyclerViewAdapter.BookViewHolder> {

    private final List<BestSellerBook> books;
    private final OnListFragmentInteractionListener mListener;
    Context context;

    public BestSellerBooksRecyclerViewAdapter(List<BestSellerBook> items, OnListFragmentInteractionListener listener, Context context) {
        books = items;
        mListener = listener;
        this.context = context;

    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_best_seller_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, final int position) {
        holder.mItem = books.get(position);
        holder.mBookTitle.setText(books.get(position).title);
        holder.mBookAuthor.setText(books.get(position).author);
        holder.mDescription.setText(books.get(position).description);

        String ranking = String.format(Locale.US,"%d", books.get(position).rank);
        holder.mRanking.setText(ranking);

        BestSellerBook bestSellerBook = books.get(position);

        Glide.with(holder.mView).load(bestSellerBook.bookImageUrl).into(holder.mBookImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClick(holder.mItem);
                }
            }
        });

        holder.mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(books.get(position).amazonUrl));
                    context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mBookTitle;
        public final TextView mBookAuthor;
        public final TextView mRanking;
        public final ImageView mBookImage;
        public final TextView mDescription;
        public final Button mBuyButton;


        public BestSellerBook mItem;

        public BookViewHolder(View view) {
            super(view);
            mView = view;
            mBookTitle = (TextView) view.findViewById(R.id.book_title);
            mBookAuthor = (TextView) view.findViewById(R.id.book_author);
            mRanking = (TextView) view.findViewById(R.id.ranking);
            mBookImage = (ImageView) view.findViewById(R.id.book_image);
            mDescription = (TextView) view.findViewById(R.id.book_description);
            mBuyButton = (Button) view.findViewById(R.id.buy_button);
        }

        @Override
        public String toString() {
            return mBookTitle.toString() + " '" + mBookAuthor.getText() + "'";
        }
    }
}
