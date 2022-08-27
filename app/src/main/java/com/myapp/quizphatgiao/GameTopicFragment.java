package com.myapp.quizphatgiao;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.myapp.mylibrary.recycleview.GridSpacingItemDecoration;

import java.util.ArrayList;


public class GameTopicFragment extends Fragment implements InterfaceAds,ConfirmInterface{
    int spanCount;

    int value;
    View view;
    ConfirmInterface confirmInterface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.confirmInterface = this;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_topic, container, false);
    }
    PersistentData persistentData;
    TextView tp_gold,tp_level;
    ArrayList<Level> levelArrayList;
    AdapterLevel adapterTruyen;
    RecyclerView rc;
    ListenerItem listener;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spanCount = 2;

        //persistentData.gold = 100000;

        this.view = view;

        persistentData = ((MyApp)getActivity().getApplication()).getData();
         tp_gold = view.findViewById(R.id.topic_gold);
         tp_level  = view.findViewById(R.id.topic_level);
        tp_gold.setText("Vàng: "+persistentData.gold+"");
        tp_level.setText("Level: "+persistentData.level+"");

         listener = new ListenerItem() {
            @Override
            public void click(int index, Activity activity, int level) {
                if(persistentData.level == level) {
                    NavDirections action =
                            GameTopicFragmentDirections.actionGameTopicFragmentToGameContentFragment();
                    Navigation.findNavController(view).navigate(action);
                }else{
                    if(persistentData.level < level) {
                        // show dialog here
                        DiaLogTopic alert= new DiaLogTopic();
                        alert.showDialog(requireActivity(), confirmInterface,index,level);
                    }else{
                        Toast.makeText(activity, "màn này đả vượt qua", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        levelArrayList = ((MyApp)getActivity().getApplication()).getArrayLevel(persistentData);
        adapterTruyen = new AdapterLevel(levelArrayList,getContext(),listener,getActivity());
        rc = view.findViewById(R.id.rc);
        rc.setAdapter(adapterTruyen);
        rc.setLayoutManager(new GridLayoutManager(getContext(),2));

        if (value == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 2;
        }
        else if (value == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }
        int spacing_left = 10; // 50px
        int spacing_top=10;

        rc.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
    }

    @Override
    public void getResultAds(RewardItem rewardItem) {
        persistentData.level = persistentData.level+rewardItem.getAmount();
        ((MyApp)getActivity().getApplication()).updateData(persistentData);
        NavDirections action =
                GameTopicFragmentDirections.actionGameTopicFragmentToGameContentFragment();
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void confirmCallback(String status, int ans) {

    }

    @Override
    public void win() {

    }

    @Override
    public void lose() {

    }

    @Override
    public void backTomain() {

    }

    @Override
    public void trongHoa(int index) {

    }

    @Override
    public void confirmtopic(int index) {

       /* if(persistentData.gold >500) {
            persistentData.gold = persistentData.gold - 500;
            persistentData.level = persistentData.level + 1;

            ((MyApp) getActivity().getApplication()).updateData(persistentData);
            tp_gold.setText("Vàng: " + persistentData.gold + "");
            tp_level.setText("Level: " + persistentData.level + "");
            levelArrayList.get(index).src = "not_open";
            levelArrayList.get(index).txt = "Vào Game";
            //((MyApp)getActivity().getApplication()).getArrayLevel(persistentData);
            //adapterTruyen = new AdapterLevel(levelArrayList,getContext(),listener,getActivity());
            rc.setAdapter(adapterTruyen);
        }else{
            //Toast.makeText(getActivity(), "Không Đủ vàng", Toast.LENGTH_SHORT).show();
        }*/

    }
}