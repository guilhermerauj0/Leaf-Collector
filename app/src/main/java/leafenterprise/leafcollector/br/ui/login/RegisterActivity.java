package leafenterprise.leafcollector.br.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        String name = binding.registerEdtFirstname.getText().toString().trim();
        String lastName = binding.registerEdtLastname.getText().toString().trim();
        Integer cpf = Integer.parseInt(String.valueOf(binding.registerEdtCpf.getText()));
        Integer phone = Integer.parseInt(String.valueOf(binding.registerEdtPhone.getText()));

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
            binding.registerEdtFirstname.setError("Nome não pode ser vazio");
            binding.registerEdtFirstname.requestFocus();

        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            User user = new User(name, lastName ,email, cpf, phone, 0);

                            // ADD USER TO DATABASE
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Usuário criado",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Erro ao criar usuário",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Autenticação falhou",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        }

    private void reload() {
        Log.e("ERROR_CONNECTION","Usuario nao conectado");
    }
}