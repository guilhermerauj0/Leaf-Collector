package leafenterprise.leafcollector.br.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import leafenterprise.leafcollector.br.databinding.ActivityUserInfoBinding;

public class UserInfoActivity extends AppCompatActivity {

    private ActivityUserInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}