package vincenthudry.organizer.view.reminders_module;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import vincenthudry.organizer.R;
import vincenthudry.organizer.controller.AlarmManager;
import vincenthudry.organizer.model.Database;
import vincenthudry.organizer.utils.Tuple2;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Tuple2<Long,Long>> data;
    private Context context;
    private RecyclerView rw;

    public ListAdapter(List<Tuple2<Long,Long>> data, Context context, RecyclerView rw){
        this.data=data;
        this.context=context;
        this.rw=rw;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_reminder,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(data.get(position).t2);
        holder.tv.setText(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db=new Database(context);
                AlarmManager am=new AlarmManager(context,db);
                am.deleteAlarm(data.get(holder.getAdapterPosition()).t1);
                data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });
        holder.id=data.get(position).t1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public long id;
        public TextView tv;
        public Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.recycler_item_reminder_textview);
            btn=itemView.findViewById(R.id.delete_reminder_button);
        }
    }
}
