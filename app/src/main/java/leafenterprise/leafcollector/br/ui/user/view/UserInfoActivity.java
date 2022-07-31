package leafenterprise.leafcollector.br.ui.user.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import leafenterprise.leafcollector.br.databinding.ActivityUserInfoBinding;
import leafenterprise.leafcollector.br.domain.Adress;
import leafenterprise.leafcollector.br.ui.AdressActivity;
import leafenterprise.leafcollector.br.ui.user.adapter.AdressAdapter;

public class UserInfoActivity extends AppCompatActivity {

    private ActivityUserInfoBinding binding;
    private List<Adress> listAdress;
    private AdressAdapter adressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupAdresses();
        createAdresses();

        binding.userinfoBtnAddadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdressActivity.class));
            }
        });
    }

    private void setupAdresses() {
        listAdress = new ArrayList<>();
        adressAdapter = new AdressAdapter(this, listAdress);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.userinfoRvAdress.setLayoutManager(layoutManager);
        binding.userinfoRvAdress.setHasFixedSize(true);
        binding.userinfoRvAdress.setAdapter(adressAdapter);
    }


    private void createAdresses() {
        Adress adress1 = new Adress(
                "Rua senhor do bonfim",
                "134",
                "",
                "Centro",
                "Senhor do Bonfim",
                "Bahia",
                "48970000");
        listAdress.add(adress1);

        Adress adress2 = new Adress(
                "Rua juazeiro",
                "67",
                "",
                "Centro",
                "Juazeiro",
                "Bahia",
                "48903000");
        listAdress.add(adress2);
    }

}