package leafenterprise.leafcollector.br.ui.qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.ScanMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import leafenterprise.leafcollector.br.databinding.ActivityQrCodeBinding;
import leafenterprise.leafcollector.br.ui.proofs.ChangeSuccessActivity;

public class QrCodeActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 101;
    private ActivityQrCodeBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference dbReference;
    private FirebaseDatabase userDb;
    private CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupPermissions();
        codeScanner();
    }

    private void setupPermissions() {
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    private void codeScanner() {
        codeScanner = new CodeScanner(this, binding.qrcodeCodescanner);

        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(true);
        codeScanner.setScanMode(ScanMode.SINGLE);

        codeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            // Exibição da resposta de leitura bem sucedida
            catchOldLeafFromDatabase(result);
            Toast.makeText(QrCodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
        }));

        codeScanner.setErrorCallback(thrown -> runOnUiThread(() -> Log.e("Main", "Camera error")));

        binding.qrcodeCodescanner.setOnClickListener(view -> codeScanner.startPreview());

    }

    private void catchOldLeafFromDatabase(Result result) {
        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance();
        dbReference = userDb.getReference("Users");

        dbReference.child(user).get().addOnCompleteListener(task -> {
            DataSnapshot dataUser = task.getResult();
            String oldLeaf = String.valueOf(dataUser.child("leafs").getValue());
            sendDataToChangeSuccessActivity(Integer.parseInt(oldLeaf), result);
        });
    }

    private void sendDataToChangeSuccessActivity(Integer oldLeaf, Result result) {
        Intent intent = new Intent(getApplicationContext(), ChangeSuccessActivity.class);
        intent.putExtra("qrcode_value", result.getText());
        intent.putExtra("oldLeaf", oldLeaf);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de câmera sucedida", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}