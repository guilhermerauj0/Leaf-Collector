package leafenterprise.leafcollector.br.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import leafenterprise.leafcollector.br.R;
import leafenterprise.leafcollector.br.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // TODO "Implementar botão de confirmar login"
        binding.loginBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // TODO "Implementar botão de atualizar a senha (esqueci a senha)"
        // TODO "Implementar botão de cadastrar conta"
        // TODO "Implementar Objetos com viewBinding"
    }
}