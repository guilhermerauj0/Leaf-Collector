package leafenterprise.leafcollector.br.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import leafenterprise.leafcollector.br.databinding.ActivityAdressBinding;

public class AdressActivity extends AppCompatActivity {

    private ActivityAdressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}