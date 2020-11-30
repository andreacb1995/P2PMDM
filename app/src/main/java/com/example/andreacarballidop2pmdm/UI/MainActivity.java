package com.example.andreacarballidop2pmdm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreacarballidop2pmdm.R;
import com.example.andreacarballidop2pmdm.core.Entrenamiento;
import com.example.andreacarballidop2pmdm.db.database.EntrenamientoLab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AccionesEntrenamiento {
    protected static final int CODIGO_EDICION_ITEM = 100;

    TextView tvFecha;
    Entrenamiento mEntrenamiento;
    Entrenamiento e;
    MyRecyclerViewAdapter adapter;

    private ArrayList<Entrenamiento> listaEntrenamientos;
    private ArrayList<Entrenamiento> entrenamientos;
    private Entrenamiento entrenamientoModifico;

    private EntrenamientoLab mEntrenamientoLab;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    EditText edNombre;
    EditText edEdad;
    EditText edAltura;
    EditText edPeso;
    ImageButton botonGuardar;
    ImageButton botonBorrar;

    public static final String Nombre = "nombre";
    public static final String Edad = "edad";
    public static final String Altura = "altura";
    public static final String Peso = "peso";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btAdd = findViewById(R.id.btAdd);

        listaEntrenamientos = new ArrayList<>();
        entrenamientos= new ArrayList<>();

        mEntrenamientoLab = EntrenamientoLab.get(this);
        entrenamientos =  mEntrenamientoLab.getEntrenamientos();
        for(int i = 0; i < entrenamientos.size(); i++){
            listaEntrenamientos.add(entrenamientos.get(i));
        }

        RecyclerView recyclerView = findViewById(R.id.rvEntrenamientos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(listaEntrenamientos, this);

        recyclerView.setLongClickable(true);
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onAdd(null);
            }
        });

        edNombre = (EditText) findViewById(R.id.edNombreUsuario);
        edEdad = (EditText) findViewById(R.id.edEdadUsuario);
        edAltura = (EditText) findViewById(R.id.edAlturaUsuario);
        edPeso = (EditText) findViewById(R.id.edPesoUsuario);

        botonGuardar = (ImageButton) findViewById(R.id.boton_guardar);
        botonBorrar = (ImageButton) findViewById(R.id.boton_borrar);
        sharedpreferences = getSharedPreferences("info", Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Nombre)) {
            edNombre.setText(sharedpreferences.getString(Nombre, ""));
        }
        if (sharedpreferences.contains(Edad)) {
            edEdad.setText(sharedpreferences.getString(Edad, ""));
        }
        if (sharedpreferences.contains(Altura)) {
            edAltura.setText(sharedpreferences.getString(Altura, ""));
        }
        if (sharedpreferences.contains(Peso)) {
            edPeso.setText(sharedpreferences.getString(Peso, ""));
        }

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar_datos(v);
                Toast.makeText(MainActivity.this, "Datos personales añadidos", Toast.LENGTH_LONG).show();
            }
        });

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Eliminar datos personales");
                builder.setMessage("Está seguro de que desea eliminar los datos personales ?");
                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor = sharedpreferences.edit();
                        editor.clear();
                        edNombre.setText(" ");
                        edEdad.setText(" ");
                        edAltura.setText(" ");
                        edPeso.setText(" ");
                        editor.commit();
                        Toast.makeText(MainActivity.this, "Datos personales borrados", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.create().show();

            }
        });
    }

    public void guardar_datos(View view) {
        String n = edNombre.getText().toString();
        String e = edEdad.getText().toString();
        String a = edAltura.getText().toString();
        String p = edPeso.getText().toString();

        editor = sharedpreferences.edit();
        editor.putString(Nombre, n);
        editor.putString(Edad, e);
        editor.putString(Altura, a);
        editor.putString(Peso, p);
        editor.commit();
    }

    public void mostrar_datos() {
        edNombre = (EditText) findViewById(R.id.edNombreUsuario);
        edEdad = (EditText) findViewById(R.id.edEdadUsuario);
        edAltura = (EditText) findViewById(R.id.edAlturaUsuario);
        edPeso = (EditText) findViewById(R.id.edPesoUsuario);

        sharedpreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Nombre)) {
            edNombre.setText(sharedpreferences.getString(Nombre, ""));
        }
        if (sharedpreferences.contains(Edad)) {
            edEdad.setText(sharedpreferences.getString(Edad, ""));
        }
        if (sharedpreferences.contains(Altura)) {
            edAltura.setText(sharedpreferences.getString(Altura, ""));
        }
        if (sharedpreferences.contains(Peso)) {
            edPeso.setText(sharedpreferences.getString(Peso, ""));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrar_datos();

        File internalStorageDir = getFilesDir();
        File estadisticas = new File(internalStorageDir, "EstadisticasGenerales.txt");
        String textToWrite = guardar_EstadisticasGenerales();
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(estadisticas);
            fos.write(textToWrite.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void onAdd(final Entrenamiento entrenamientoModifico) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View dialogLayout = getLayoutInflater().inflate(R.layout.add_entrenamiento, null);
        builder.setView(dialogLayout);
        tvFecha = dialogLayout.findViewById(R.id.edFecha);
        EditText editTextHoras = dialogLayout.findViewById(R.id.edHoras);
        final EditText editTextMinutos = dialogLayout.findViewById(R.id.edMinutos);
        final EditText editTextSegundos = dialogLayout.findViewById(R.id.edSegundos);
        final EditText editTextMetros = dialogLayout.findViewById(R.id.edMetros);

        final Calendar calendar = Calendar.getInstance();
        if (entrenamientoModifico != null) {
            String horas= String.valueOf(entrenamientoModifico.getHoras());
            String minutos= String.valueOf(entrenamientoModifico.getMinutos());
            String segundos= String.valueOf(entrenamientoModifico.getSegundos());
            String metros= String.valueOf(entrenamientoModifico.getMetros());
            String fecha= String.valueOf(entrenamientoModifico.getFormatoFecha());
            editTextHoras.setText(horas);
            editTextMinutos.setText(minutos);
            editTextSegundos.setText(segundos);
            editTextMetros.setText(metros);
            tvFecha.setText(fecha);
            calendar.setTime(entrenamientoModifico.getFecha());
        }

        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                final DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMMM 'de' yyyy", Locale.getDefault());
                        tvFecha.setText(formatoFecha.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePicker.show();
            }
        });

        builder.setPositiveButton("+", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String h = editTextHoras.getText().toString();
                String m = editTextMinutos.getText().toString();
                String s = editTextSegundos.getText().toString();
                String d = editTextMetros.getText().toString();

                if (h.isEmpty()) {
                    h = "0";
                }

                if (m.isEmpty()) {
                    m = "0";
                }
                if (s.isEmpty()) {
                    s = "0";
                }
                if (m.isEmpty()) {
                    m = "0";

                }
                if (h.isEmpty() && m.isEmpty() && s.isEmpty() && m.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No se permiten todos los campos vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (h.equals("0") && m.equals("0") && s.equals("0") && m.equals("0")) {
                    Toast.makeText(MainActivity.this, "No se permiten todos los datos a 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tvFecha == null) {

                    Toast.makeText(MainActivity.this, "El campo de la fecha está vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                int horas = Integer.parseInt(h);
                int minutos = Integer.parseInt(m);
                int segundos = Integer.parseInt(s);
                int metros = Integer.parseInt(d);

                if(horas > 24){

                    Toast.makeText(MainActivity.this, "El dato introducido en el campo de las horas es inválido ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(minutos > 59){

                    Toast.makeText(MainActivity.this, "El dato introducido en el campo de los minutos es inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(segundos > 59){

                    Toast.makeText(MainActivity.this, "El dato introducido en el campo de los segundos es inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (metros == 0) {
                    Toast.makeText(MainActivity.this, "No se permite el campo de la distancia a 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (entrenamientoModifico == null) {
                    e = new Entrenamiento();
                    e.setFecha(calendar.getTime());
                    e.setHoras(horas);
                    e.setMinutos(minutos);
                    e.setSegundos(segundos);
                    e.setMetros(metros);
                    float resultadominutosporkm = e.calcularMinKm(horas, minutos, segundos, metros);
                    float velocidadmedia = e.calcularVelocidadmedia(horas, minutos, segundos, metros);
                    e.setMinutosKm(resultadominutosporkm);
                    e.setVelocidadmediakmporhora(velocidadmedia);
                    mEntrenamientoLab.addEntrenamiento(e);
                    listaEntrenamientos.add(e);

                    Toast.makeText(MainActivity.this, "Entrenamiento creado", Toast.LENGTH_SHORT).show();
                } else {
                    float resultadominutosporkm = entrenamientoModifico.calcularMinKm(horas, minutos, segundos, metros);
                    float velocidadmedia = entrenamientoModifico.calcularVelocidadmedia(horas, minutos, segundos, metros);
                    entrenamientoModifico.modificarEntrenamiento(calendar.getTime(),horas,minutos,segundos,metros,resultadominutosporkm,velocidadmedia);
                    mEntrenamientoLab.updateEntrenamiento(entrenamientoModifico);

                    Toast.makeText(MainActivity.this,"Entrenamiento actualizado", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        this.getMenuInflater().inflate(R.menu.actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean toret = false;

        switch (menuItem.getItemId()) {
            case R.id.añadirEntrenamiento:
                onAdd(null);
                toret = true;
                break;
            case R.id.modificar:
                modificar();
                toret = true;
                break;
            case R.id.eliminar:
                eliminar();
                toret = true;
                break;
            case R.id.estadisticase:
                estadisticasGenerales();
                toret = true;
                break;
        }
        return toret;
    }

    private void modificar() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Modificar entrenamiento");

        final String[] arrayEntrenamientos = new String[listaEntrenamientos.size()];
        for (int i = 0; i < listaEntrenamientos.size(); i++) {
            arrayEntrenamientos[i] = listaEntrenamientos.get(i).getFormatoFecha() + ", " + listaEntrenamientos.get(i).getTiempo() + "," + listaEntrenamientos.get(i).getm();
        }

        builder.setSingleChoiceItems(arrayEntrenamientos, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                entrenamientoModifico = listaEntrenamientos.get(i);
            }
        });
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int i) {
                if (entrenamientoModifico == null) {
                    Toast.makeText(MainActivity.this, "No ha seleccionado un entrenamiento", Toast.LENGTH_SHORT).show();
                } else {
                    onAdd(entrenamientoModifico);
                }
            }

        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();

    }

    public void eliminar() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar entrenamientos");

        String[] stringtareas = new String[listaEntrenamientos.size()];
        final boolean[] entrenamientoseleccion = new boolean[listaEntrenamientos.size()];
        for (int i = 0; i < listaEntrenamientos.size(); i++) {
            stringtareas[i] = listaEntrenamientos.get(i).getFormatoFecha() + ", " + listaEntrenamientos.get(i).getTiempo() + ", " + listaEntrenamientos.get(i).getm();
        }
        builder.setMultiChoiceItems(stringtareas, entrenamientoseleccion, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                entrenamientoseleccion[i] = isChecked;
            }
        });
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                AlertDialog.Builder buildereliminar = new AlertDialog.Builder(MainActivity.this);
                buildereliminar.setMessage("¿Está seguro de que desea eliminar los elementos?");
                buildereliminar.setNegativeButton("No", null);
                buildereliminar.setPositiveButton("Sí", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                            for (int i = 0 ; i<listaEntrenamientos.size() ; i++)
                                if (entrenamientoseleccion[i])
                                    EntrenamientoLab.deleteEntrenamiento(listaEntrenamientos.get(i));
                                    adapter.notifyDataSetChanged();

                            for (int i = listaEntrenamientos.size()-1; i>=0; i--) {
                                if (entrenamientoseleccion[i]) {
                                    listaEntrenamientos.remove(i);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        Toast.makeText(MainActivity.this, "Entrenamientos eliminados correctamente", Toast.LENGTH_SHORT).show();
                        MainActivity.this.adapter.notifyDataSetChanged();
                    }
                });
                buildereliminar.create().show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    public void estadisticasGenerales() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.estadisticas_generales, null);
        builder.setView(dialogLayout);
        TextView tvKmNadados = dialogLayout.findViewById(R.id.tv_mostrarkm);
        TextView tvMediaMinutosKm = dialogLayout.findViewById(R.id.tv_mostrarmedia);

        float kmTotales = 0;
        float mediaMinutosKm = 0;
        float minPorKm = 0;

        for (Entrenamiento entrenamiento : listaEntrenamientos) {

            float metrosEntrenamiento = entrenamiento.getMetros();
            float conversion = metrosEntrenamiento / 1000;
            kmTotales = kmTotales + conversion;
            minPorKm = entrenamiento.getMinutosKm();
            mediaMinutosKm += minPorKm/listaEntrenamientos.size();

        }

        tvKmNadados.setText(Float.toString(kmTotales) + " km");

        tvMediaMinutosKm.setText(Float.toString(mediaMinutosKm) + " kms/h");

        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public String guardar_EstadisticasGenerales() {
//        listaEntrenamientos =  mEntrenamientoLab.getEntrenamientos();
        float kmTotales = 0;
        float mediaMinutosKm = 0;
        float minPorKm = 0;
        for (int i = 0; i < listaEntrenamientos.size(); i++) {
            float metrosEntrenamiento = listaEntrenamientos.get(i).getMetros();
            float conversion = metrosEntrenamiento / 1000;
            kmTotales = kmTotales + conversion;
            minPorKm = listaEntrenamientos.get(i).getMinutosKm();
            mediaMinutosKm = (mediaMinutosKm + minPorKm) / listaEntrenamientos.size();

        }
        String texto = "Estadísticas generales\n" +
                "Kilómetros totales nadados: " + kmTotales + " km\n" + "Minutos por kilómetro de media: " +
                mediaMinutosKm + " km/m";
        return texto;

    }

    @Override
    public void mostrar(Entrenamiento entrenamiento) {

        final TextView textview = new TextView(MainActivity.this);
        textview.setText(entrenamiento.toString());

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("           Entrenamiento ");
        builder.setView(textview);
        builder.setNegativeButton("Volver", null);
        builder.create().show();
    }

    @Override
    public void eliminar(Entrenamiento entrenamiento) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Borrar Elemento");
        builder.setMessage("Está seguro de que desea eliminar este elemento?\n\n" + entrenamiento.eliminarEntrenamiento());
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listaEntrenamientos.remove(entrenamiento);
                mEntrenamientoLab.deleteEntrenamiento(entrenamiento);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    @Override
    public void modificar(Entrenamiento entrenamientoModifico) {
        onAdd(entrenamientoModifico);
    }
}