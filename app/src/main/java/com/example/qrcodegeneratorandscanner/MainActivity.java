package com.example.qrcodegeneratorandscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare views for scanning QR codes
    private TextView tvScanResult;
    private Button btnScanQR;

    // Declare views for generating QR codes
    private EditText etQRCodeData;
    private Button btnGenerateQR;
    private ImageView ivGeneratedQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize scanning UI components
        tvScanResult = findViewById(R.id.tvScanResult);
        btnScanQR = findViewById(R.id.btnScanQR);

        // Initialize generating UI components
        etQRCodeData = findViewById(R.id.etQRCodeData);
        btnGenerateQR = findViewById(R.id.btnGenerateQR);
        ivGeneratedQR = findViewById(R.id.ivGeneratedQR);

        // Start QR code scanner when the "Scan QR Code" button is clicked
        btnScanQR.setOnClickListener(v -> new IntentIntegrator(this).initiateScan());

        // Generate QR code when the "Generate QR Code" button is clicked
        btnGenerateQR.setOnClickListener(v -> {
            String data = etQRCodeData.getText().toString();
            if (!data.isEmpty()) {
                generateQRCode(data);
            }
        });
    }

    // Handle the result from the QR code scanner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                tvScanResult.setText("Scanned: " + result.getContents());
            } else {
                tvScanResult.setText("Scan canceled");
            }
        }
    }

    // Generate a QR code from the entered text and display it in the ImageView
    private void generateQRCode(String data) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix = new com.google.zxing.qrcode.QRCodeWriter()
                    .encode(data, BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivGeneratedQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
