package artyomd.noteapplication;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    public static List<Note> todel = new ArrayList<>();
    public MenuInflater minflator;
    private List<Note> mValues;
    public Note tempNote;
    private List<Note> filtered;
    private boolean showAll;


    public void switchDataset() {
        showAll = !showAll;
        todel.clear();
        notifyDataSetChanged();
    }

    public List<Note> getOriginalDataset(){
        return mValues;
    }

    public NoteRecyclerViewAdapter(List<String> items) {
        mValues = new ArrayList<>();
        for (String s : items) {
            mValues.add(new Note(s));
        }
        filtered = mValues;
        showAll = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = getDataset().get(position);
        holder.mTextView.setText(getDataset().get(position).getString());
        if(!getDataset().get(position).getState())
            holder.mTextView.setBackgroundColor(Color.GRAY);
        else
            holder.mTextView.setBackgroundColor(Color.WHITE);
        holder.mCheckBox.setChecked(false);

        holder.mCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            NoteRecyclerViewAdapter.todel.add(holder.mItem);
                        } else {
                            NoteRecyclerViewAdapter.todel.remove(holder.mItem);
                        }
                    }
                }
        );
    }

    public void filter() {
        List<Note> temp = new ArrayList<>();
        for (Note note : mValues) {
            if (note.getState()) {
                temp.add(note);
            }
        }
        this.filtered = temp;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return getDataset().size();
    }

    public List<Note> getDataset() {
        if (showAll) {
            return mValues;
        }
        return filtered;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public final View mView;
        public final TextView mTextView;
        public final CheckBox mCheckBox;
        public Note mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mView.setOnCreateContextMenuListener(this);
            mTextView = (TextView) view.findViewById(R.id.textView);
            mCheckBox = (CheckBox) view.findViewById(R.id.checkBox);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            minflator.inflate(R.menu.context_menu, menu);
            tempNote = mItem;
        }
    }
}
