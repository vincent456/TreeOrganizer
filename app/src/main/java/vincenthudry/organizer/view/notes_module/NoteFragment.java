package vincenthudry.organizer.view.notes_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vincenthudry.organizer.R;
import vincenthudry.organizer.model.NotesDatabase;
import vincenthudry.organizer.utils.Tuple2;

public class NoteFragment extends Fragment {

    private Context context;

    public static final int NEW_NOTE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context=getContext();

        Intent intent=getActivity().getIntent();

        final long nodeID=intent.getLongExtra("nodeID",-1);

        if(nodeID==-1){
            throw new IllegalStateException();
        }

        NotesDatabase db = new NotesDatabase(context);

        View v=inflater.inflate(R.layout.fragment_note,container,false);

        RecyclerView rw=v.findViewById(R.id.notes_list);
        List<Tuple2<Long,String>> data=db.getAllTitles(nodeID);
        TextListAdapter adapter=new TextListAdapter(data,getActivity());
        rw.setLayoutManager(new LinearLayoutManager(context));
        rw.setAdapter(adapter);

        //region add listeners
        View fab=v.findViewById(R.id.add_note_fab);
                fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,NoteTakingActivity.class);
                intent.putExtra("noteID",-1L);
                intent.putExtra("nodeID",nodeID);
                getActivity().startActivityForResult(intent,NEW_NOTE_REQUEST);
            }
        });
        //endregion

        return v;
    }
}