package leafenterprise.leafcollector.br.ui.user.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import leafenterprise.leafcollector.br.domain.Adress;
import leafenterprise.leafcollector.br.ui.user.adapter.AdressAdapter;

public class UserInfoActivity extends AppCompatActivity {

    private leafenterprise.leafcollector.br.databinding.ActivityUserInfoBinding binding;
    private List<Adress> listAdress;
    private FirebaseAuth mAuth;
    FirebaseDatabase userDb;
    DatabaseReference dbReference;
    private AdressAdapter adressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = leafenterprise.leafcollector.br.databinding.ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.userinfoBtnAddadress.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AdressActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance();
        dbReference = userDb.getReference("Users");
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser != null){
            setupUserInfos(user, dbReference);
            setupAdresses(user, dbReference);
        }

    }

    private void setupUserInfos(String user, DatabaseReference dbReference) {


        dbReference.child(user).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    DataSnapshot dataUser = task.getResult();
                    String userName = String.valueOf(dataUser.child("name").getValue());
                    String userCpf = String.valueOf(dataUser.child("cpf").getValue());
                    String userPhone = String.valueOf(dataUser.child("phone").getValue());
                    String userEmail = String.valueOf(dataUser.child("email").getValue());
                    String userLastName = String.valueOf(dataUser.child("lastName").getValue());

                    binding.userinfoTxtUser.setText(userName);
                    binding.userinfoEdtName.setText(userName);
                    binding.userinfoEdtCellphone.setText(userPhone);
                    binding.userinfoEdtEmail.setText(userEmail);
                    binding.userinfoEdtCpf.setText(userCpf);
                    binding.userinfoEdtLastname.setText(userLastName);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário não existe", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Não foi possível acessar os dados", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupAdresses(String user, DatabaseReference dbReference) {
        listAdress = new ArrayList<>();
        adressAdapter = new AdressAdapter(this, listAdress);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.userinfoRvAdress.setLayoutManager(layoutManager);
        binding.userinfoRvAdress.setHasFixedSize(true);
        binding.userinfoRvAdress.setAdapter(adressAdapter);

        dbReference.child(user).child("Endereço").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    DataSnapshot dataUser = task.getResult();
                    String adress = String.valueOf(dataUser.child("adress").getValue());
                    String num = String.valueOf(dataUser.child("num").getValue());
                    String complement = String.valueOf(dataUser.child("complement").getValue());
                    String district = String.valueOf(dataUser.child("district").getValue());
                    String city = String.valueOf(dataUser.child("city").getValue());
                    String state = String.valueOf(dataUser.child("state").getValue());
                    String cep = String.valueOf(dataUser.child("cep").getValue());

                    Adress list = new Adress(adress, num, complement, district, city, state, cep);

                    listAdress.add(list);
                    adressAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getApplicationContext(), "Sem endereços", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Não foi possível acessar os endereços", Toast.LENGTH_SHORT).show();
            }
        });
    }
}