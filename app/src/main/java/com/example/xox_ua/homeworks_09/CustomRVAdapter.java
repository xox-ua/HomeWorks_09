package com.example.xox_ua.homeworks_09;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

class CustomRVAdapter extends RecyclerView.Adapter<CustomRVAdapter.CountryViewHolder> {
    private List<Country> mCountries = new ArrayList<>();

    @Override public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new CustomRVAdapter.CountryViewHolder(v);
    }

    @Override public void onBindViewHolder(CountryViewHolder holder, int position) {
        holder.mImageViewFlag.setImageResource(mCountries.get(position).getFlagId());
        holder.mTextViewCountry.setText(mCountries.get(position).getCountryName());
        holder.mTextViewCity.setText(mCountries.get(position).getCapitalName());
        holder.mRatingBar.setRating(mCountries.get(position).getRatingBar());
    }

    public void setCountries(List<Country> countries) {
        mCountries.clear();
        mCountries.addAll(countries);
        notifyDataSetChanged();
    }

    public void addCountries(List<Country> countries) {
        mCountries.addAll(countries);
        notifyDataSetChanged();
    }

    @Override public int getItemCount() {
        return mCountries.size();
    }
    //@Override public int getItemViewType(int position) {
    //  return super.getItemViewType(position);
    //}

    static class CountryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivFlg) ImageView mImageViewFlag;
        @BindView(R.id.tvCountry) TextView mTextViewCountry;
        @BindView(R.id.tvCapital) TextView mTextViewCity;
        @BindView(R.id.ratingBar) RatingBar mRatingBar;

        CountryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
