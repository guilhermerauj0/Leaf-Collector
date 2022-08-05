package leafenterprise.leafcollector.br.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import leafenterprise.leafcollector.br.databinding.ActivityProductBinding;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.productBtnPutincart.setOnClickListener(view -> {

        });
        binding.productBtnConfirm.setOnClickListener(view -> {

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setupProductInfo();
    }

    private void setupProductInfo() {
        int price = getIntent().getIntExtra("product_price",0);

        binding.productName.setText(getIntent().getStringExtra("product_name"));
        binding.productDescription.setText(getIntent().getStringExtra("product_description"));
        binding.productTxtPrice.setText(Integer.toString(price));

    }
}