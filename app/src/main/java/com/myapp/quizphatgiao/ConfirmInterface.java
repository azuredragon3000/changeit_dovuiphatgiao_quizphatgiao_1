package com.myapp.quizphatgiao;

public interface ConfirmInterface {
    void confirmCallback(String status, int ans);

    void win();

    void lose();

    void backTomain();

    void trongHoa(int index);

    void confirmtopic(int index);
}
