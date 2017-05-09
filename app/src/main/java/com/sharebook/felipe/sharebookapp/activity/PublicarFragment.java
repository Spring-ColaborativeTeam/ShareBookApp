package com.sharebook.felipe.sharebookapp.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sharebook.felipe.sharebookapp.R;

/**
 * Created by Diego on 25/04/2017.
 */

public class PublicarFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int CAPTURE_IMAGE = 1;
    private static final int PICK_IMAGE = 2;
    ImageView imagen;
    Button botonImagen;
    EditText mensaje;
    Button guardarBoton;
    Uri uriImagen = null;
    String imagePath;
    Boolean tomoFoto = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        botonImagen=(Button)getActivity().findViewById(R.id.buttonImagen);
        imagen=(ImageView)getActivity().findViewById(R.id.imageView);

        return inflater.inflate(R.layout.activity_publicar, null);
    }

    public void agregarFoto(View view){
        final CharSequence[] options = {"Tomar Foto", "Galeria de fotos", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Agregar Foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Tomar Foto")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        tomoFoto = true;
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                    }
                } else if (options[item].equals("Galeria de fotos")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    tomoFoto = true;
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PublicarFragment.class);
    }
}