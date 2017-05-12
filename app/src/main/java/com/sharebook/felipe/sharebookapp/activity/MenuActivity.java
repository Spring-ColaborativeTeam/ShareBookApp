package com.sharebook.felipe.sharebookapp.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.LibroService;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Solicitud;
import com.sharebook.felipe.sharebookapp.security.model.Logout;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean intercambiarLista = false;
    PublicarFragment pubFra = new PublicarFragment();
    IntercambiarFragment intFra = new IntercambiarFragment();
    MisLibrosActivity misLibrosActivity = new MisLibrosActivity();
    LibrosDispActivity librosDispActivity = new LibrosDispActivity();
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    MapsActivity mapFra = new MapsActivity();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;
    ImageView imagen;
    Button botonImagen;
    EditText mensaje;
    Button guardarBoton;
    Libro libroSelec;
    Boolean tomoFoto = false;
    String uriImagen = null;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    Bitmap imagenCamara;
    private  RetrofiNetwork network;
    private ExecutorService executorService;
    private SharedPreferences sharedPreferences;
    Libro libroIntercambio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userDetails", 0);//Esta variable contiene el email que se obtiene con el token

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        botonImagen=(Button)findViewById(R.id.buttonImagen);
        imagen=(ImageView)findViewById(R.id.imagenCamara);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        //TextView currentUser = (TextView) header.findViewById(R.id.currentUser);
        //currentUser.setText(pref.getString("username", null));
        //
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mainFrame, mapFra);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.pub_libro) {
            tomoFoto = false;
            setTitle("Publicar Libro");
            fragment = pubFra;
        } else if (id == R.id.map_fragment) {
            setTitle("Ubicación");
            fragment = mapFra;
        } else if (id == R.id.dis_libro) {
            setTitle("Libros Disponibles");
            fragment = librosDispActivity;
        }
        else if (id == R.id.mis_libro) {
            setTitle("Mis Libros");
            fragment = misLibrosActivity;
        }
        else if(id == R.id.solicitud) {
            setTitle("Solicitudes");
            fragment = new SolicitudAcivity();
        }
        else if(id == R.id.solicitudpendiente) {
            setTitle("Solicitudes Pendientes");
            fragment = new SolicitudPendienteActivity();
        }
        else if(id == R.id.logout) {
            logout();
        }
        else if(id == R.id.buscar_libro) {
            setTitle("Buscar Libros");
            fragment = new BuscarActivity();
        }


        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void agregarFoto(View view){
        final CharSequence[] options = {"Tomar Foto", "Galeria de fotos", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Agregar Foto");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Tomar Foto")) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            tomoFoto = true;
                        }
                    } else if (options[item].equals("Galeria de fotos")) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);
                        tomoFoto = true;
                    } else if (options[item].equals("Cancelar")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imagen = (ImageView) findViewById(R.id.imagenCamara);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imagenCamara = (Bitmap) data.getExtras().get("data");
            imagen.setImageBitmap(imagenCamara);

        } else if (REQUEST_IMAGE_GALLERY == requestCode) {
            Uri uri = data.getData();

            try {
                imagenCamara = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imagen.setImageBitmap(imagenCamara);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getImagenCamara(){
        return imagenCamara;
    }

    public void publicarLibro(View view) throws InterruptedException {
        if(tomoFoto) {


            EditText nombreEdit = (EditText) findViewById(R.id.nombre);
            String nombre = nombreEdit.getText().toString();
            EditText editoEdit = (EditText) findViewById(R.id.editorial);
            String editorial = editoEdit.getText().toString();
            EditText autorEdit = (EditText) findViewById(R.id.autor);
            String autor = autorEdit.getText().toString();
            if(nombre != "" && autor != "" && editorial != ""){
                Libro l = new Libro();
                l.setId(nombre.trim()+autor.trim());
                l.setName(nombre);
                l.setDescription(editorial);
                l.setAutor(autor);
                l.setLatitude(new Float(4.7826755));
                l.setLongitude(new Float(-74.0447828));
                addLibroRetrofit(l);
                Thread.sleep(1000);
                Fragment fragment = null;

                fragment = misLibrosActivity;

                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainFrame, fragment);
                misLibrosActivity.adapter.imagen = imagenCamara;
                misLibrosActivity.adapter.idLibroPublicar = l.getName();
                transaction.addToBackStack(null);
                transaction.commit();

            }else{
                Toast.makeText(this, "Debe completar todos los campos del libro.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debe tener una imagen del libro.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addLibroRetrofit(final Libro libro){
        network = new RetrofiNetwork();
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                network.addLibro(libro);

            }
        });
    }

    public void addSolicitudRetrofit(final Solicitud solicitud){
        network = new RetrofiNetwork();
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                network.addSolicitud(solicitud);

            }
        });
    }

    private void logout(){
        LibroService service = RetrofiNetwork.createService(LibroService.class);
        Call<Logout> call = service.logout();
        call.enqueue(new Callback<Logout>() {
            @Override
            public void onResponse(Call<Logout> call, Response<Logout> response) {
                startActivity( new Intent(getBaseContext(), LoginActivity.class ) );
            }

            @Override
            public void onFailure(Call<Logout> call, Throwable t) {
                startActivity( new Intent(getBaseContext(), LoginActivity.class ) );
            }
        });

    }

    public Libro getLibroSelected(){
        return libroSelec;
    }

    public void intercambiarLibro(View view){
        intercambiarLista = true;
         libroSelec = librosDispActivity.adapter.libroSelected;
        libroIntercambio = libroSelec;
        Log.d("1234234", "Vealo --------> "+libroSelec.getName());
        Fragment fragment = null;
        intFra = new IntercambiarFragment();
        intFra.setLibroSelect(libroSelec);
        fragment = intFra;
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.mainFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }



    public void crearSolicitud(View view) throws InterruptedException {

        if(intercambiarLista){
             libroSelec = intFra.adapter.libroSelected;
        }else{
            libroSelec = mapFra.intFra.adapter.libroSelected;
            libroIntercambio = mapFra.libro;
        }
        if(libroSelec != null && libroIntercambio != null ) {
            Solicitud s = new Solicitud();
            s.setLibro1(libroIntercambio);
            s.setLibro2(libroSelec);
            addSolicitudRetrofit(s);
            Thread.sleep(1000);
            intercambiarLista = false;
            Fragment fragment = null;

            fragment = new SolicitudPendienteActivity();

            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainFrame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
           // Toast.makeText(this, "Seleciono " + libroSelec.getName()+ "--"+libroIntercambio.getName(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No ha seleccionado un libro para intercambiar", Toast.LENGTH_SHORT).show();
        }
    }

}
