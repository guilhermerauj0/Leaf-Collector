package leafenterprise.leafcollector.br.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import leafenterprise.leafcollector.br.databinding.ActivityAdressBinding;
import leafenterprise.leafcollector.br.domain.Adress;

public class AdressActivity extends AppCompatActivity {

    private ActivityAdressBinding binding;
    private FirebaseAuth mAuth;
    private Adress adresses;
    FirebaseDatabase userDb;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.adressBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupViewObjects();
                insertAdressData();
                finish();
            }
        });
    }

    private void setupViewObjects() {

        String adress = binding.adressEdtAdress.getText().toString().trim();
        String num = binding.adressEdtNum.getText().toString().trim();
        String complement = binding.adressEdtComplement.getText().toString().trim();
        String district = binding.adressEdtDistrict.getText().toString().trim();
        String city = binding.adressEdtCity.getText().toString().trim();
        String state = binding.adressEdtState.getText().toString().trim();
        String cep = binding.adressEdtCep.getText().toString().trim();

        adresses = new Adress(adress, num, complement, district, city, state, cep);
    }

    private void insertAdressData() {
        mAuth = FirebaseAuth.getInstance();
        userDb = FirebaseDatabase.getInstance();
        dbReference = userDb.getReference("Users");

        dbReference.child(mAuth.getCurrentUser().getUid()).child("Endereço").setValue(adresses);

        Toast.makeText(this, "Endereço adicionado!", Toast.LENGTH_LONG).show();

    }
}