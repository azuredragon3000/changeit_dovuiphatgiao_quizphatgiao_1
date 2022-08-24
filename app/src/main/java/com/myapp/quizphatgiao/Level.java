package com.myapp.quizphatgiao;

public class Level {
    String src;
    String txt;
    int level;

    public Level(int curlevel, String txt,int level) {
        if(curlevel == level){
            this.src = "not_open";
        }else if(curlevel > level){
            this.src = "open";
        }else{
            this.src = "lock";
        }
        if(curlevel > level){
            this.txt = "Đả Mở";
        }else if(curlevel == level){
            this.txt = "Vào Game";
        }else{
            this.txt = "Mở Khóa";
        }

        this.level = level;
    }
}
