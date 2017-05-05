package com.example.sidnei.appemail;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText txtNome, txtEmail;
    Button btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNome = (EditText)findViewById(R.id.txtNome);
        txtEmail = (EditText)findViewById(R.id.txtEmail);

        btnEmail = (Button)findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                enviarEmail();
            }
        });
    }

    private void enviarEmail(){

        final String nome = txtNome.getText().toString();
        final String email = txtEmail.getText().toString();

        if(isOnline()) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    Mail m = new Mail();

                    String[] toArr = {email};
                    m.setTo(toArr);

                    m.setFrom("bandeirasidnei02@gmail.com");
                    m.setSubject("Email de teste do seu app");
                    m.setBody(nome + " recebeu um email com sucesso!");

                    try {
                        //m.addAttachment("E:\\Imagens\\bomba.png");//anexo opcional
                        //m.addAttachment("data.data.com.example.sidnei.appemail/files/webservice.txt");//anexo opcional
                        m.send();
                    }
                    catch(RuntimeException rex){ }//erro ignorado
                    catch(Exception e) {
                        //tratar algum outro erro aqui
                        String erro = e.toString();
                    }

                    System.exit(0);
                }
            }).start();
        }
        else {
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
    }

    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        catch(Exception ex){
        Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online! (" + ex.getMessage() + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}