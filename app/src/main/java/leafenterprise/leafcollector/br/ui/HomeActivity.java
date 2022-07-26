package leafenterprise.leafcollector.br.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

import leafenterprise.leafcollector.br.databinding.ActivityHomeBinding;
import leafenterprise.leafcollector.br.domain.Shop;
import leafenterprise.leafcollector.br.ui.adapter.ShopsAdapter;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private ShopsAdapter shopsAdapter;
    private List<Shop> listShops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupShopList();
        createShops();

        // TODO "Implementar os recyclerviews presentes"
        // TODO "Implementar os botões"
        // TODO "Implementar ViewBinding"
    }

    private void setupShopList() {
        listShops = new ArrayList<>();
        shopsAdapter = new ShopsAdapter(this, listShops);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.homeRvShops.setLayoutManager(layoutManager);
        binding.homeRvShops.setHasFixedSize(true);
        binding.homeRvShops.setAdapter(shopsAdapter);
    }

    private void createShops(){
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

}