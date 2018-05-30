package vincenthudry.organizer.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vincenthudry.organizer.R;
import vincenthudry.organizer.utils.Tuple2;
import vincenthudry.organizer.view.notes_module.NoteTakingActivity;

public class TextListAdapter extends RecyclerView.Adapter<TextListAdapter.ViewHolder> {

    private List<Tuple2<Integer, String>> data;

    private AppCompatActivity activity;
    public static final int UPDATE_RECYCLER = 2;

    public TextListAdapter(List<Tuple2<Integer, String>> data, AppCompatActivity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).t2);
        holder.id = data.get(position).t1;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, NoteTakingActivity.class);
                intent.putExtra("noteID",data.get(position).t1);
                activity.startActivityForResult(intent, UPDATE_RECYCLER);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public int id;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textListItem);
        }
    }
}
