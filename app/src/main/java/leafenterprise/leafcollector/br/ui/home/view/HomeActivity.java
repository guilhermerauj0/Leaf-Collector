package leafenterprise.leafcollector.br.ui.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import leafenterprise.leafcollector.br.databinding.ActivityHomeBinding;
import leafenterprise.leafcollector.br.domain.CartItem;
import leafenterprise.leafcollector.br.domain.Shop;
import leafenterprise.leafcollector.br.ui.CartActivity;
import leafenterprise.leafcollector.br.ui.QrCodeActivity;
import leafenterprise.leafcollector.br.ui.UserInfoActivity;
import leafenterprise.leafcollector.br.ui.home.adapter.CartItemsAdapter;
import leafenterprise.leafcollector.br.ui.home.adapter.ShopsAdapter;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private ShopsAdapter shopsAdapter;
    private CartItemsAdapter cartItemsAdapter;
    private List<Shop> listShops;
    private List<CartItem> listCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupShopList();
        setupCartList();
        createShops();
        createItemsCart();

        // ABRIR LEITOR QRCODE
        binding.homeBtnChangebags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readQrCode();
            }
        });

        // INFO BUTTON
        binding.homeImgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inform();
            }
        });

        // ABRIR CARRINHO
        binding.homeImgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCart();
            }
        });

        // ABRIR TELA DE USUARIO
        binding.homeImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUser();
            }
        });
    }

    private void setupShopList() {
        listShops = new ArrayList<>();
        shopsAdapter = new ShopsAdapter(this, listShops);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.homeRvShops.setLayoutManager(layoutManager);
        binding.homeRvShops.setHasFixedSize(true);
        binding.homeRvShops.setAdapter(shopsAdapter);
    }

    private void setupCartList() {
        listCart = new ArrayList<>();
        cartItemsAdapter = new CartItemsAdapter(this, listCart);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.homeRvCartitems.setLayoutManager(layoutManager);
        binding.homeRvCartitems.setHasFixedSize(true);
        binding.homeRvCartitems.setAdapter(cartItemsAdapter);

    }

    private void createShops() {
        Shop shop1 = new Shop("Nilza Supermercado", "Alimentos");
        listShops.add(shop1);
        Shop shop2 = new Shop("Zé do Pé", "Calçados");
        listShops.add(shop2);
        Shop shop3 = new Shop("Univasf Acessórios", "Mimos");
        listShops.add(shop3);
        Shop shop4 = new Shop("Folhas Perso", "Personalização");
        listShops.add(shop4);
        Shop shop5 = new Shop("Pantovin", "Cabelos");
        listShops.add(shop5);
        Shop shop6 = new Shop("VS Computadores", "Eletrônicos");
        listShops.add(shop6);
        Shop shop7 = new Shop("MH Instalações", "Elétricos");
        listShops.add(shop7);
    }

    private void createItemsCart() {
        CartItem product1 = new CartItem("iPhone X", "Smartphone", 1, 5679, "");
        listCart.add(product1);
        CartItem product2 = new CartItem("Tenis Nike", "Sapato para caminhar", 3, 1401, "");
        listCart.add(product2);
        CartItem product3 = new CartItem("TV Smart", "Ideal para assistir", 2, 7040, "");
        listCart.add(product3);
    }

    private void readQrCode() {
        Intent intent = new Intent(getApplicationContext(), QrCodeActivity.class);
        startActivity(intent);
    }

    private void inform() {
        // TODO "Implementar Alert dialog"
    }

    private void openCart() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    private void openUser() {
        Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
        startActivity(intent);
    }

}