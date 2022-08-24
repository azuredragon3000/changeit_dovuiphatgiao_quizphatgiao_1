package com.myapp.quizphatgiao;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.myapp.mylibrary.boitinhyeu.FunctionCommon;


public class GameContentFragment extends Fragment implements ConfirmInterface{

    // global variable
    //int level;
    //int gold;
    PersistentData persistentData;

    // local variables
    private int point;
    private int currentQuestion;

    // layout variable
    private Button bt1,bt2,bt3,bt4,bt5;
    private TextView level_tv,ans1_tv,ans2_tv,ans3_tv,ans4_tv,curques_tv,point_tv,gold_tv,time_tv,question_tv;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get global variable
        persistentData = ((MyApp)getActivity().getApplication()).getData();
        // initialize variable
        point = 0;
        currentQuestion = 1;
        //level = 0;
        //gold = 100;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_content, container, false);
    }
    DatabasePhatGiao databasePhatGiao;
    Question randomQuestion;
    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        // initialize variable
        bt1 = view.findViewById(R.id.bt1);
        bt2 = view.findViewById(R.id.bt2);
        bt3 = view.findViewById(R.id.bt3);
        bt4 = view.findViewById(R.id.bt4);
        bt5 = view.findViewById(R.id.bt5);
        ans1_tv = view.findViewById(R.id.ans1);
        ans2_tv = view.findViewById(R.id.ans2);
        ans3_tv = view.findViewById(R.id.ans3);
        ans4_tv = view.findViewById(R.id.ans4);
        question_tv = view.findViewById(R.id.question);

        databasePhatGiao = ((MyApp) getActivity().getApplication()).getDatabasePhatGiao();
        randomQuestion = getRandomQuestion();
        showQuestion(randomQuestion);

        point_tv = view.findViewById(R.id.point);
        curques_tv = view.findViewById(R.id.curques);
        gold_tv = view.findViewById(R.id.game_gold);
        time_tv = view.findViewById(R.id.time);

        level_tv = view.findViewById(R.id.game_level);

        gold_tv.setText("Vàng: "+persistentData.gold+"");
        level_tv.setText("Level: "+persistentData.level+"");

        curques_tv.setText("Câu Hỏi: "+currentQuestion);
        setColorForCurrentQuestion(currentQuestion,R.color.Yellow);

        ans1_tv.setOnClickListener(v->{
            if(currentQuestion <= 5 ) {
                showDialog("ongoing",1);
            }else{
                showDialogBack();
            }
        });

        ans2_tv.setOnClickListener(v->{
            if(currentQuestion <= 5 ) {
                showDialog("ongoing",2);
            }else{
                showDialogBack();
            }
        });

        ans3_tv.setOnClickListener(v->{
            if(currentQuestion <= 5 ) {
                showDialog("ongoing",3);
            }else{
                showDialogBack();
            }

        });

        ans4_tv.setOnClickListener(v->{
            if(currentQuestion <= 5 ) {
                showDialog("ongoing",4);
            }else{
                showDialogBack();
            }
        });
    }

    private void showQuestion(Question randomQuestion) {
        ans1_tv.setText(randomQuestion.ans1);
        ans2_tv.setText(randomQuestion.ans2);
        ans3_tv.setText(randomQuestion.ans3);
        ans4_tv.setText(randomQuestion.ans4);
        question_tv.setText(randomQuestion.question);

    }

    private Question getRandomQuestion() {
        int random = FunctionCommon.getRandom(619,0);
        Question question = databasePhatGiao.getQuestion(random);
        return  question;
    }

    private void showDialog(String status, int ans) {
        DiaLogConfirm alert= new DiaLogConfirm(status,ans);
        alert.showDialog(requireActivity(),this);
    }

    private void showDialogBack() {
        DiaLogBack alert= new DiaLogBack();
        String t = "";
        if(point >= 3) {
            t = "Chúc Mừng Bạn Đả Lên Level";
        }else{
            t = "Cố gắng lại nhé";
        }
        alert.showDialog(requireActivity(),this,t);
        // backto Topic game
    }


    private void showDialogLose(String rs) {
        DiaLogLose alert= new DiaLogLose();
        alert.showDialog(requireActivity(), this,rs);
    }

    private void showDialogWin(String rs) {
        DiaLogWin alert= new DiaLogWin();
        alert.showDialog(requireActivity(),this,rs);
    }



    private boolean checkAns(Question mQuestion, int i) {
        return (mQuestion.result == i);
    }

    private void setColorForCurrentQuestion(int currentQuestion,int color) {
        switch (currentQuestion){
            case 1:
                bt1.setBackgroundColor(getResources().getColor(color));
                break;
            case 2:
                bt2.setBackgroundColor(getResources().getColor(color));
                break;
            case 3:
                bt3.setBackgroundColor(getResources().getColor(color));
                break;
            case 4:
                bt4.setBackgroundColor(getResources().getColor(color));
                break;
            case 5:
                bt5.setBackgroundColor(getResources().getColor(color));
                break;
            default:
                break;
        }

    }

    public void lose(){
        setColorForCurrentQuestion(currentQuestion,R.color.Red);
        currentQuestion++;
        if(currentQuestion <= 5) {
            curques_tv.setText("Câu Hỏi: " + currentQuestion);
            setColorForCurrentQuestion(currentQuestion, R.color.Yellow);
            randomQuestion = getRandomQuestion();
            showQuestion(randomQuestion);
        }else{
            showDialogBack();
        }
    }

    @Override
    public void backTomain() {
        if(point >= 3){
            persistentData.level = persistentData.level + 1;
        }
        //level_tv.setText("Level: "+level);
        ((MyApp)getActivity().getApplication()).updateData(persistentData);
        // back to main
        NavDirections action =GameContentFragmentDirections.actionGameContentFragmentToGameTopicFragment();
        //      GameTopicFragmentDirections.actionGameTopicFragmentToGameContentFragment();
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void trongHoa(int index) {

    }

    @Override
    public void confirmtopic(int index) {

    }

    public void win(){
        // increase gold
        persistentData.gold = persistentData.gold + 50;
        gold_tv.setText("Vàng: "+persistentData.gold );
        // update here
        ((MyApp)getActivity().getApplication()).updateData(persistentData);
        // increase point
        point++;
        point_tv.setText("Point: " + point);
        // change color status
        setColorForCurrentQuestion(currentQuestion,R.color.Green);
        // increate cur question
        currentQuestion++;
        if(currentQuestion <= 5) {
            curques_tv.setText("Câu Hỏi: " + currentQuestion);
            // change color status
            setColorForCurrentQuestion(currentQuestion, R.color.Yellow);
            randomQuestion = getRandomQuestion();
            showQuestion(randomQuestion);
        }else{
            showDialogBack();
        }
    }


    @Override
    public void confirmCallback(String status, int ans) {
        switch (status){
            case "ongoing":
                if(checkAns(randomQuestion,ans)){
                    //correct ans
                    showDialogWin(randomQuestion.getAns());
                }else{
                    //wrong ans
                    showDialogLose(randomQuestion.getAns());
                }

                break;
            case "finish":
                showDialogBack();

                break;
            default:
                break;
        }
    }
}