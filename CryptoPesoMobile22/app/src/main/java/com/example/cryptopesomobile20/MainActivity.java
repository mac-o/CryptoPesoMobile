package com.example.cryptopesomobile20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView myScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnEscanear(View v){

        myScannerView = new ZXingScannerView(this);
        setContentView(myScannerView);
        myScannerView.setResultHandler(this);
        myScannerView.startCamera();

    }

    @Override
    public void handleResult(Result rawResult) {

        Log.v("HandleResult", rawResult.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado del Scan");
        builder.setMessage(rawResult.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        myScannerView.resumeCameraPreview(this);
    
    }


}
