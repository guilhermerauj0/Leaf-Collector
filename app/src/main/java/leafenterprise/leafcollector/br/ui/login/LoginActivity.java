package leafenterprise.leafcollector.br.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import leafenterprise.leafcollector.br.databinding.ActivityLoginBinding;
import leafenterprise.leafcollector.br.ui.home.view.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.loginBtnConfirm.setOnClickListener(view -> signInAccount());

        binding.loginTxtRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        binding.loginTxtForgotpassword.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    private void signInAccount(){
        String email = binding.loginEdtEmail.getText().toString();
        String password = binding.loginEdtPassword.getText().toString();


        if(TextUtils.isEmpty(email)){
            binding.loginEdtEmail.setError("Email não pode ser vazio");
            binding.loginEdtEmail.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            binding.loginEdtPassword.setError("Senha não pode ser vazia");
            binding.loginEdtPassword.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            toHomeActivity();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void toHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}