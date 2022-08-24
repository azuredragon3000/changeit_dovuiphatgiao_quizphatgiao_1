package com.myapp.quizphatgiao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.myapp.mylibrary.editext.AnimationEditText;

import java.util.ArrayList;

public class LoginTabFragment extends Fragment {

    EditText et_email,et_pass;
    TextView forgetpass;
    Button login;
    Float v = 0f;
    FirebaseAuth mAuth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(v->{
            loginUser();
        });

    }

    private void loginUser() {
        String email = et_email.getText().toString();
        String password = et_pass.getText().toString();

        if(TextUtils.isEmpty(email)){
            et_email.setError("Email cant be empty");
            et_email.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            et_pass.setError("Email cant be empty");
            et_pass.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(),"User logged successfully",Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(requireActivity(),MainActivity.class));
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        intent.putExtra("KEY",true);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(),"Register Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        et_email = root.findViewById(R.id.email);
        et_pass = root.findViewById(R.id.password);
        forgetpass = root.findViewById(R.id.forget_pass);
        login = root.findViewById(R.id.button_login);

        ArrayList<View> views = new ArrayList<>();
        views.add(et_email);
        views.add(et_pass);
        views.add(forgetpass);
        views.add(login);

        new AnimationEditText(views).start();

        return root;
    }
}
