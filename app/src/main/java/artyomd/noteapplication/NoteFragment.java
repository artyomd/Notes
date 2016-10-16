package artyomd.noteapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NoteFragment extends Fragment {

    List<String> values;
    NoteRecyclerViewAdapter adap;

    public NoteFragment() {
        values = new ArrayList<>();
        values.add("Item1");
        values.add("Item2");
        values.add("Item3");
        values.add("Item4");
        values.add("Item5");

    }

    public void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                adap.getOriginalDataset().add(new Note(input.getText().toString()));
                adap.filter();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adap = new NoteRecyclerViewAdapter(this.values);
            adap.minflator = getActivity().getMenuInflater();
            recyclerView.setAdapter(adap);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Iterator<Note> itr = NoteRecyclerViewAdapter.todel.iterator();
                while (itr.hasNext()) {
                    adap.getOriginalDataset().remove(itr.next());
                }
                adap.notifyDataSetChanged();
                return true;
            case R.id.shn:
                adap.switchDataset();
                return true;
            case R.id.add:
                this.showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show:
                adap.tempNote.setState(true);
                adap.filter();
                return true;
            case R.id.hide:
                adap.tempNote.setState(false);
                adap.filter();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
