package leafenterprise.leafcollector.br.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import leafenterprise.leafcollector.br.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        String email = binding.registerEdtEmail.getText().toString();
        String password = binding.registerEdtPassword.getText().toString();
        String name = binding.registerEdtName.getText().toString();

        binding.registerBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.isEmpty() || password.isEmpty() || name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Por favor, preencha os campos!", Toast.LENGTH_SHORT).show();
                }else{
                    creatAccount(email,password);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            reload();
        }
    }
    private void creatAccount(String email, String password){

        if(TextUtils.isEmpty(email)){
            binding.registerEdtEmail.setError("Email não pode ser vazio");
            binding.registerEdtEmail.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            binding.registerEdtPassword.setError("Senha não pode ser vazia");
            binding.registerEdtPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(RegisterActivity.this, "Usuário criado",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                finish();
                            }else{
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Autenticação falhou",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user) {
    }

    private void reload() {
        Log.e("ERROR_CONNECTION","Usuario nao conectado");
    }
}