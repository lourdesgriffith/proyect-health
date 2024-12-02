package com.example.health;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.modelo.odontologoDB;

public class MainActivity extends AppCompatActivity {
    private EditText correo; // creo variable de tipo edittext
    private EditText clave;
    String cont, user; // declaramos 2 variables tipo String
    odontologoDB odb; // declaramos la variable para la clase odontologiadb
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        correo=findViewById(R.id.usuariotxt); // aqui llamamos al id.usuariotxt
        clave=findViewById(R.id.contrasenatxt); // esta variable hace lo mismo pero llama a la contasena
        odb = new odontologoDB(this); // crea un objeto odb=  a la clase odontologodb
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void menu_principal (View view) { // declaramos un metodo menu_principal
        user=correo.getText().toString(); // la variable de tipo string user= es igual a correo, el gettext trae el contenido del cuadro de texto
        cont=clave.getText().toString();
        boolean val=true; // declarara una variable true
        if(TextUtils.isEmpty(user)){ // preguntamos si la variable user, esta vacia
            correo.setError("Cuadro de texto vacio"); //validacion, si esta vacia la variable user,
            val=false; //  si no hay nada escrito la val. va ser falso
        }

        if(TextUtils.isEmpty(cont)){
            clave.setError("Cuadro de texto vacio");
            val=false;
        }
        if(val){ // preguntamos si val es igual a true, va ser veradero cuando los cuadros de texto tengan contenido
            boolean v = odb.buscar(user,cont); // llamamos al metodo buscar, de mi clase odontologodb y le eviamos parametros. usario y contrasena
            if(v){ // preguntamos si v= true
                Intent mp = new Intent(this,menu_principal.class); // declaramos en la variable de tipo intent a que activity queremos abrir o llamar
                startActivity(mp); // se abre la activity
                finish();
            }
            else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show(); // si escribimos algo que no esta en la bd entra al else, y sale error
            }
        }

    }


   public void registrar(View view){
        Intent r = new Intent(this,registrar.class);
        startActivity(r);
   }

    public boolean login(String username, String password) {
        String usuario = username;
        String clave = password;
        if(usuario.equals("usuario") && clave.equals("contraseña123")){
            Toast.makeText(this, "Inicio exitosos", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(this, "Los datos no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
