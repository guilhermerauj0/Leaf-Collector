package leafenterprise.leafcollector.br.ui.qrcode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import leafenterprise.leafcollector.br.databinding.ActivityQrCodeBinding;

public class QrCodeActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 101;
    private ActivityQrCodeBinding binding;
    private CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupPermissions();
        codeScanner();
    }

    private void codeScanner() {
        codeScanner = new CodeScanner(this, binding.qrcodeCodescanner);

        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(true);
        codeScanner.setScanMode(ScanMode.SINGLE);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Exibição da resposta de leitura bem sucedida
                        Toast.makeText(QrCodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Throwable thrown) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Main", "Camera error");
                    }
                });
            }
        });

        binding.qrcodeCodescanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });

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