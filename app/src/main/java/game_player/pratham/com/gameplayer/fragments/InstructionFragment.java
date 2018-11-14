package game_player.pratham.com.gameplayer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import game_player.pratham.com.gameplayer.R;

public class InstructionFragment extends Fragment{
    Context context;
    ImageButton imageButton;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.show_instruction_fragmnets,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // imageButton=view.findViewById(R.id.ok);
    }


}
