package leafenterprise.leafcollector.br.ui.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import leafenterprise.leafcollector.br.databinding.ActivityCartBinding;
import leafenterprise.leafcollector.br.domain.Product;
import leafenterprise.leafcollector.br.ui.home.adapter.CartItemsAdapter;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private ArrayList<Product> listCart;
    int totalLeafs = 0;
    private FirebaseAuth mAuth;
    private FirebaseDatabase userDb;
    private DatabaseReference dbReference;
    private CartItemsAdapter cartItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupCartList();
    }

    private void buyItems(String user, DatabaseReference dbReference) {

        dbReference.child(user).get().addOnCompleteListener(task -> {
            DataSnapshot dataUser = task.getResult();
            int leafsUser = Integer.parseInt(String.valueOf(dataUser.child("leafs").getValue()));
            if (leafsUser < totalLeafs) {
                binding.cartBtnBuy.setEnabled(false);
            } else {
                binding.cartBtnBuy.setOnClickListener(view -> {
                    confirmBuy(user, dbReference, totalLeafs, leafsUser);
                });
            }
        });

    }

    private void confirmBuy(String user, DatabaseReference dbReference, int totalLeafs, int userLeafs) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setTitle("Deseja confirmar a compra?")
                .setMessage("O pedido será enviado para o endereço já cadastrado!")
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {

                })
                .setPositiveButton("Confirmar", (dialogInterface, i) -> {

                    int leafTotal = userLeafs - totalLeafs;
                    dbReference.child(user).child("leafs").setValue(leafTotal);
                    for (i = 0; i < listCart.size(); i++)
                        listCart.remove(i);
                    cartItemsAdapter.notifyDataSetChanged();
                    binding.cartRvCartitems.setVisibility(View.GONE);
                    Toast.makeText(this, "Compra Realizada!", Toast.LENGTH_SHORT).show();
                    finish();
                });

        builder.show();
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
        binding.cartRvCartitems.setLayoutManager(layoutManager);
        binding.cartRvCartitems.setHasFixedSize(true);
        binding.cartRvCartitems.setAdapter(cartItemsAdapter);

        createItemsCart();
        for (int i = 0; i < listCart.size(); i++) {
            totalLeafs += listCart.get(i).getPrice();
        }
        binding.cartTotal.setText(String.valueOf(totalLeafs));

    }

    private void createItemsCart() {
        Product product1 = new Product("iPhone X", "Smartphone", 1, 5679, "");
        listCart.add(product1);
        Product product2 = new Product("Tenis Nike", "Sapato para caminhar", 3, 1401, "");
        listCart.add(product2);
        Product product3 = new Product("TV Smart", "Ideal para assistir", 2, 7040, "");
        listCart.add(product3);
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
            Toast.makeText(getApplicationContext(), "Usuário não encontrado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            buyItems(user, dbReference);
        }
    }
}