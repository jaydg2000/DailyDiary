package com.gdc.diary.ui;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdc.diary.ActivityEntryDetails;
import com.gdc.diary.R;
import com.gdc.diary.domain.DiaryEntry;
import com.gdc.diary.helpers.DateHelper;

import java.util.List;

/**
 * Created by jaydg on 11/10/2017.
 */

public class DiaryHistoryAdapter extends RecyclerView.Adapter<DiaryHistoryAdapter.ViewHolder> {

    private List<DiaryEntry> entries;

    public DiaryHistoryAdapter(List<DiaryEntry> entries) {
        // TODO: Make this unmodifable.
        this.entries = entries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_entry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        // TODO: Long lines
        int readingGoodColor = ResourcesCompat.getColor(viewHolder.parentView.getResources(), R.color.readingGood, null);
        int readingBadColor = ResourcesCompat.getColor(viewHolder.parentView.getResources(), R.color.readingBad, null);
        final DiaryEntry entry = entries.get(position);
        viewHolder.textViewMorningReading.setText(formatReading(entry.getMorningReading()));
        viewHolder.textViewWeight.setText(formatReading(entry.getWeight()));
        // TODO add Yes/No to resource file.
        viewHolder.textViewMorningMeds.setText(entry.hasTakenMorningMeds() ? "Yes" : "No");
        viewHolder.textViewEveningMeds.setText(entry.hasTakenEveningMeds() ? "Yes" : "No");
        viewHolder.textViewDate.setText(DateHelper.getDisplayableDateStr(entry.getReadingDate()));

        viewHolder.textViewMorningReading.setTextColor(entry.isSugarReadingGood()?readingGoodColor:readingBadColor);
        viewHolder.textViewMorningMeds.setTextColor(entry.hasTakenMorningMeds()?readingGoodColor:readingBadColor);
        viewHolder.textViewEveningMeds.setTextColor(entry.hasTakenEveningMeds()?readingGoodColor:readingBadColor);

        viewHolder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityEntryDetails.start(viewHolder.parentView.getContext(), entry.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    private String formatReading(int reading) {
        if (reading < 1) {
            return "";
        }
        return Integer.toString(reading);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final public View parentView;
        final public TextView textViewMorningReading;
        final public TextView textViewWeight;
        final public TextView textViewMorningMeds;
        final public TextView textViewEveningMeds;
        final public TextView textViewDate;

        public ViewHolder(View view) {
            super(view);
            parentView = view;
            textViewMorningReading = (TextView) view.findViewById(R.id.textViewMorningReading);
            textViewWeight = (TextView) view.findViewById(R.id.textViewWeight);
            textViewMorningMeds = (TextView) view.findViewById(R.id.textViewMorningMeds);
            textViewEveningMeds = (TextView) view.findViewById(R.id.textViewEveningMeds);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        }
    }
}
