package leafenterprise.leafcollector.br.ui.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import leafenterprise.leafcollector.br.databinding.ActivityHomeBinding;
import leafenterprise.leafcollector.br.domain.Product;
import leafenterprise.leafcollector.br.domain.Shop;
import leafenterprise.leafcollector.br.ui.CartActivity;
import leafenterprise.leafcollector.br.ui.home.adapter.CartItemsAdapter;
import leafenterprise.leafcollector.br.ui.home.adapter.ShopsAdapter;
import leafenterprise.leafcollector.br.ui.login.LoginActivity;
import leafenterprise.leafcollector.br.ui.qrcode.QrCodeActivity;
import leafenterprise.leafcollector.br.ui.user.view.UserInfoActivity;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private ShopsAdapter shopsAdapter;
    private CartItemsAdapter cartItemsAdapter;
    private FirebaseDatabase userDb;
    private DatabaseReference dbReference;
    private List<Shop> listShops;
    public List<Product> listCart;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupShopList();
        setupCartList();

        binding.homeBtnChangebags.setOnClickListener(view -> readQrCode());

        binding.homeImgInfo.setOnClickListener(view -> inform());

        binding.homeImgCart.setOnClickListener(view -> openCart());

        binding.homeImgUser.setOnClickListener(view -> openUser());

        binding.homeTxtSignout.setOnClickListener(view -> signOutAccount());
    }


    private void setupShopList() {
        listShops = new ArrayList<>();
        shopsAdapter = new ShopsAdapter(this, listShops);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.homeRvShops.setLayoutManager(layoutManager);
        binding.homeRvShops.setHasFixedSize(true);
        binding.homeRvShops.setAdapter(shopsAdapter);

        createShops();
    }

    private void setupCartList() {
        listCart = new ArrayList<>();
        cartItemsAdapter = new CartItemsAdapter(this, listCart);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.homeRvCartitems.setLayoutManager(layoutManager);
        binding.homeRvCartitems.setHasFixedSize(true);
        binding.homeRvCartitems.setAdapter(cartItemsAdapter);

        createItemsCart();

    }

    private void createShops() {
        Shop shop1 = new Shop("Fulano Supermercado", "Alimentos");
        listShops.add(shop1);
        Shop shop2 = new Shop("Zé do Pé", "Calçados");
        listShops.add(shop2);
        Shop shop3 = new Shop("O Cara's Bugigangas", "Acessórios");
        listShops.add(shop3);
        Shop shop4 = new Shop("Casa dos Brindes", "Personalização");
        listShops.add(shop4);
        Shop shop5 = new Shop("Salão da Maria", "Cabelos");
        listShops.add(shop5);
        Shop shop6 = new Shop("VS Computadores", "Eletrônicos");
        listShops.add(shop6);
        Shop shop7 = new Shop("MH Instalações", "Elétricos");
        listShops.add(shop7);
    }

    private void createItemsCart() {
        Product product1 = new Product("iPhone X", "Smartphone", 1, 5679, "");
        listCart.add(product1);
        Product product2 = new Product("Tenis Nike", "Sapato para caminhar", 3, 1401, "");
        listCart.add(product2);
        Product product3 = new Product("TV Smart", "Ideal para assistir", 2, 7040, "");
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

    private void signOutAccount() {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance();
        dbReference = userDb.getReference("Users");
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(getApplicationContext(), "Usuário não encontrado, login novamente", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else {
            setupUserInfos(user,dbReference);
        }
    }

    private void setupUserInfos(String user, DatabaseReference dbReference) {

        dbReference.child(user).get().addOnCompleteListener(task -> {
            DataSnapshot dataUser = task.getResult();
            String userName = String.valueOf(dataUser.child("name").getValue());
            String userLeafs = String.valueOf(dataUser.child("leafs").getValue());
            binding.homeTxtUser.setText(userName);
            binding.homeTxtLeafs.setText(userLeafs);
        });

    }
}