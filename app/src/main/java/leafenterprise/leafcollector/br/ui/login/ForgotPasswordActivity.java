package leafenterprise.leafcollector.br.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import leafenterprise.leafcollector.br.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        
        binding.fpasswordBtnChangepassword.setOnClickListener(view -> changePassword());

    }

    private void changePassword() {
        String email = binding.fpasswordEdtEmail.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            binding.fpasswordEdtEmail.setError("Email não pode ser vazio");
            binding.fpasswordEdtEmail.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.fpasswordEdtEmail.setError("Insira um e-mail válido");
            binding.fpasswordEdtEmail.requestFocus();
        } else{
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Verifique seu email para mudar a senha", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Tente denovo, algum erro aconteceu!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}