package com.myapp.quizphatgiao;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class SignupTabFragment extends Fragment {

    FirebaseAuth mAuth;
    Button register;
    EditText et_email,et_pass,et_mobile,et_passconfirm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.signup_tab_fragment,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        register = view.findViewById(R.id.signUp) ;
        mAuth = FirebaseAuth.getInstance();
        et_email = view.findViewById(R.id.email);
        et_pass = view.findViewById(R.id.password);
        et_mobile = view.findViewById(R.id.mobile);
        et_passconfirm = view.findViewById(R.id.confirm_password);
        register.setOnClickListener(v->{
            createUser();
        });

        ArrayList<View> views = new ArrayList<>();
        views.add(et_email);
        views.add(et_pass);
        views.add(et_mobile);
        views.add(et_passconfirm);

        new AnimationEditText(views).start();
    }

    private void createUser() {
        String email = et_email.getText().toString();
        String password = et_pass.getText().toString();

        if(TextUtils.isEmpty(email)){
            et_email.setError("Email cant be empty");
            et_email.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            et_pass.setError("Email cant be empty");
            et_pass.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(),"User register successfully", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(requireActivity(),MainActivity.class));
                    }else{
                        Toast.makeText(getContext(),"Register Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
