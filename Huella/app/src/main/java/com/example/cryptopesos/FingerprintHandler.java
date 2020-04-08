package com.example.cryptopesos;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler<overrideLibrary> extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler (Context context) {

        this.context = context;

    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("Hubo un error de autenticación. " + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Error de autenticación ", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("Transferensia Exitosa.", true);

    }



    private void update(String s, boolean b) {

       TextView Texto = (TextView) ((Activity)context).findViewById(R.id.Texto);
        ImageView ImgHuella = (ImageView) ((Activity)context).findViewById(R.id.ImgHuella);

       Texto.setText(s);

       if (b == false){

           Texto.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

       } else {

           Texto.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
           ImgHuella.setImageResource(R.mipmap.action_done);

       }
    }
}
