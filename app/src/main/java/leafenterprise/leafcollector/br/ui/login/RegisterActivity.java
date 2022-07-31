package leafenterprise.leafcollector.br.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import leafenterprise.leafcollector.br.databinding.ActivityRegisterBinding;
import leafenterprise.leafcollector.br.domain.User;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.registerBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatAccount();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    private void creatAccount() {

        String email = binding.registerEdtEmail.getText().toString();
        String password = binding.registerEdtPassword.getText().toString();
        String name = binding.registerEdtName.getText().toString().trim();
        String cpf = binding.registerEdtCpf.getText().toString().trim();
        String phone = binding.registerEdtPhone.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.registerEdtEmail.setError("Email não pode ser vazio");
            binding.registerEdtEmail.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.registerEdtEmail.setError("Insira um e-mail válido");
            binding.registerEdtEmail.requestFocus();

        } else if (TextUtils.isEmpty(password)) {
            binding.registerEdtPassword.setError("Senha não pode ser vazia");
            binding.registerEdtPassword.requestFocus();

        } else if (TextUtils.isEmpty(name)) {
            binding.registerEdtName.setError("Nome não pode ser vazio");
            binding.registerEdtName.requestFocus();

        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(name, email, cpf, phone);

                                // ADD USER TO DATABASE
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(mAuth.getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "Usuário criado",
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Erro ao criar usuário",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Autenticação falhou",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        }

    private void reload() {
        Log.e("ERROR_CONNECTION","Usuario nao conectado");
    }
}