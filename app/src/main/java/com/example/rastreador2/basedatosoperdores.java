package com.example.rastreador2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.repositories.usuarioRepo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class basedatosoperdores extends AppCompatActivity {

    ListView listviewOperadores;
    ImageView imageViewOperador;
    EditText nombreOperador, telefonoOperador;
    Button btnActualizar, btnEliminar, btnCambiarFoto, btnBuscarOperador;
    ArrayList<Usuario> operadores = new ArrayList<>();
    ArrayList<String> operadoresList = new ArrayList<>();
    Usuario actualUser = null;
    String newImagePath = "";
    Boolean isNewImage = false;
    final String NOT_OPERATORS = "No existen operadores registrados";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basedatosoperdores);

        listviewOperadores = findViewById(R.id.listViewOperadores);
        imageViewOperador = findViewById(R.id.imageOperatorDB);
        nombreOperador = findViewById(R.id.nameOperadorDB);
        telefonoOperador = findViewById(R.id.phoneOperadorDB);
        btnActualizar = findViewById(R.id.BtnActualizar);
        btnEliminar = findViewById(R.id.eliminar);
        btnCambiarFoto = findViewById(R.id.cambiarfoto);
        btnBuscarOperador = findViewById(R.id.buscarOperadorDB);

        usuarioRepo repo = new usuarioRepo(this);
        operadores = repo.getAll();
        this.getList(operadores);

        final ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.layoutlist,
                operadoresList
        );

        listviewOperadores.setAdapter(adapter);

        if(operadores.size() != 1 || !operadores.get(0).equals(NOT_OPERATORS)) {
            listviewOperadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Usuario operador = operadores.get(position);
                    nombreOperador.setText(operador.getNombre());
                    telefonoOperador.setText(operador.getPhone_number());
                    File file = new File(operador.getImage_path());
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                        imageViewOperador.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Confirmar");
        builder.setMessage("¿Estás seguro de eliminar a este usuario?");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUser();
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        btnBuscarOperador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searching = nombreOperador.getText().toString();
                if (searching.equals("")) {
                    Toast.makeText(getApplicationContext(), "Ingresa el nombre del operador", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        usuarioRepo repo = new usuarioRepo(getApplicationContext());
                        Usuario usuario = repo.getOneByName(searching);
                        if(usuario != null) {
                            actualUser = usuario;
                            telefonoOperador.setText(usuario.getPhone_number());
                            File file = new File(usuario.getImage_path());
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                                imageViewOperador.setImageBitmap(bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();
                        throw e;
                    }
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actualUser != null) {
                    usuarioRepo repo = new usuarioRepo(getApplicationContext());
                    if(isNewImage) {
                        Bitmap image = ((BitmapDrawable) imageViewOperador.getDrawable()).getBitmap();
                        newImagePath = saveImageInExternaStorage(image, actualUser.getNombre());
                    } else {
                        newImagePath = actualUser.getImage_path();
                    }
                    int updated = repo.update(
                            actualUser.getId(),
                            telefonoOperador.getText().toString(),
                            nombreOperador.getText().toString(),
                            newImagePath);

                    if(updated > 0) {
                        Toast.makeText(getApplicationContext(), "Datos actualizados", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Algo falló al actualizar", Toast.LENGTH_LONG).show();
                    }
                    limpiar();
                    operadores.clear();
                    operadores = repo.getAll();
                    operadoresList.clear();
                    getList(operadores);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay ningún usuario seleccionado", Toast.LENGTH_LONG).show();
                }

            }

        });

        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actualUser != null) {
                    Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                            "content://media/internal/images/media"
                    ));
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(getApplicationContext(), "No hay ningún usuario seleccionado", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actualUser != null) {
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay ningún usuario seleccionado", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private void getList(ArrayList<Usuario> usuarios) {
        if(!usuarios.isEmpty()) {
            for(int i = 0; i < usuarios.size(); i++) {
                operadoresList.add(String.format("%s", usuarios.get(i).getNombre()));
            }
        } else {
            operadoresList.add(NOT_OPERATORS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==1){
            Uri uri=data.getData();
            try {
                imageViewOperador.setImageBitmap(convertViewToBitmap(uri));
                isNewImage = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap convertViewToBitmap(Uri data) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data);
        return bitmap;
    }

    private String saveImageInExternaStorage(Bitmap image, String userName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, String.format("%s-%s.jpg", userName, Calendar.getInstance().getTime().toString()));
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    private void deleteUser() {
        usuarioRepo repo = new usuarioRepo(getApplicationContext());
        int deleted = repo.delete(actualUser.getId());
        if(deleted > 0) {
            Toast.makeText(getApplicationContext(), "Usuario eliminado", Toast.LENGTH_LONG).show();
            limpiar();
            operadores.clear();
            operadoresList.clear();
            operadores = repo.getAll();
            getList(operadores);
        } else {
            Toast.makeText(getApplicationContext(), "Algo falló al eliminar", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiar() {
        nombreOperador.setText("");
        telefonoOperador.setText("");
        imageViewOperador.setImageBitmap(null);
    }
}
