package game_player.pratham.com.gameplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.modalclass.GameName;

public class CustomArrayAdapter extends RecyclerView.Adapter<CustomArrayAdapter.ViewHolder> {
    List<GameName> gameList;
    Context context;

    public CustomArrayAdapter(List gameList, Context context) {
        this.gameList = gameList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GameName gameName= gameList.get(position);
        holder.textView.setText(gameName.getName());
      //  holder.spinner.setAdapter();
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Spinner spinner;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.gameName);
            spinner=itemView.findViewById(R.id.studentSpinner);
        }
    }
}
