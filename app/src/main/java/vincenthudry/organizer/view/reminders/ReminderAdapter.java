package vincenthudry.organizer.view.reminders;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import vincenthudry.organizer.R;
import vincenthudry.organizer.utils.Tuple2;

class ReminderAdapter extends RecyclerView.Adapter {

    private List<Tuple2<Long,Long>> data;

    public ReminderAdapter(List<Tuple2<Long,Long>> data){
        this.data=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_reminder,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(data.get(position).t2);
        ((ViewHolder)holder).tv.setText(c.getTime().toString());
        Calendar now = Calendar.getInstance();

        if(c.getTimeInMillis()<now.getTimeInMillis()){
            ((ViewHolder)holder).tv.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv;


        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.text_list_item);
        }
    }

}
