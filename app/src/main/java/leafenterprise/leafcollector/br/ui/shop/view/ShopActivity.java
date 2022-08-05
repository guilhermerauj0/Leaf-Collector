package leafenterprise.leafcollector.br.ui.shop.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import leafenterprise.leafcollector.br.databinding.ActivityShopBinding;
import leafenterprise.leafcollector.br.domain.Product;
import leafenterprise.leafcollector.br.ui.shop.adapter.ProductAdapter;

public class ShopActivity extends AppCompatActivity {

    private ActivityShopBinding binding;
    private List<Product> listProduct;
    private ProductAdapter productAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupShopInfo();
        setupProductList();

    }

    private void setupShopInfo() {
        String shopName = getIntent().getStringExtra("shop_name");
        String shopCategory = getIntent().getStringExtra("shop_category");

        binding.shopTxtShop.setText(shopName);
        binding.shopTxtCategory.setText(shopCategory);

    }

    private void setupProductList() {
        listProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(this, listProduct);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
        binding.rvitemProducts.setLayoutManager(gridLayoutManager);
        binding.rvitemProducts.setHasFixedSize(true);
        binding.rvitemProducts.setAdapter(productAdapter);
        createProductList();

    }

    private void createProductList() {
        Product product1 = new Product("iPhone X", "Smartphone", 1, 5679, "");
        listProduct.add(product1);
        Product product2 = new Product("Tenis Nike", "Sapato para caminhar", 3, 1401, "");
        listProduct.add(product2);
        Product product3 = new Product("TV Smart", "Ideal para assistir", 2, 7040, "");
        listProduct.add(product3);
    }
}