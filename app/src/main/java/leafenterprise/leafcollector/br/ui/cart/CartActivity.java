package leafenterprise.leafcollector.br.ui.cart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import leafenterprise.leafcollector.br.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}