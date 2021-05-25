package com.appsereno.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appsereno.R;
import com.appsereno.model.base.BaseApplication;
import com.appsereno.model.entities.ClasificacionIncidencia;
import com.appsereno.model.entities.ClasificacionWraper;
import com.appsereno.model.entities.IncidenciaWraper;
import com.appsereno.model.entities.SubtipoIncidencia;
import com.appsereno.model.entities.SubtipoIncidenciaWraper;
import com.appsereno.model.entities.TipoIncidencia;
import com.appsereno.model.entities.TipoIncidenciaWraper;
import com.appsereno.model.webservice.IncidenciaService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * ReportarIncidenciaFragment is the class that is bound to the fragment_reportar_incidencia.xml view
 */
public class ReportarIncidenciaFragment extends Fragment {

    private final String ruta_evidencia= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/evidencia/";
    private File file = new File(ruta_evidencia);
    private File imgCapturada;
    private File videoCapturado;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton btnImage;
    private ImageButton btnVideo;
    private Button btnSaveIncidencia;
    private LinearLayout linOpacity;
    private Disposable disposable;
    @Inject
    IncidenciaService incidenciaService;
    private List<ClasificacionIncidencia> clasificacionIncidenciaList;
    private List<TipoIncidencia> tipoIncidenciaList;
    private List<SubtipoIncidencia> subtipoIncidenciaList;
    private List<String> clasificacionList;
    private List<String> tipoList;
    private List<String>  subtipoList;
    private Integer idClasificacion;
    private Integer idTipo;
    private Integer idSubtipo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_reportar_incidencia,container,false);
        clasificacionIncidenciaList= new ArrayList<>();
        btnImage=view.findViewById(R.id.img_btn_image_file);
        btnVideo=view.findViewById(R.id.img_btn_video_file);
        btnSaveIncidencia=view.findViewById(R.id.btn_reportar_incidencia);
        btnImage.setOnClickListener(this::selecionarImagen);
        btnVideo.setOnClickListener(this::capturarVideo);
        btnSaveIncidencia.setOnClickListener(this::guardarInicencia);
        linOpacity=view.findViewById(R.id.opacity_reportar_incidencia);
        setUpDagger();
        fillDataClasificacion();
        file.mkdirs();
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private String getCodeImage() {
        return "img_"+ new Date().getTime();
    }
    private String getCodeVideo() {
        return "video_"+ new Date().getTime();
    }

    public void capturarVideo(View view){
        String fileName = ruta_evidencia + getCodeVideo() + ".mp4";
        File video = new File( fileName );
        try {
            video.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile( video );
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(cameraIntent, 1);
            videoCapturado=video;
        }
    }
    public void selecionarImagen(View v){
        String fileName = ruta_evidencia + getCodeImage() + ".jpg";
        File foto = new File( fileName );
        try {
            foto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile( foto );
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //startActivityForResult(cameraIntent, 0);

        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);*/
        if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(cameraIntent, 0);
            imgCapturada=foto;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == getActivity().RESULT_OK) {
            Toast.makeText(getContext(),"imagen lista para enviar",Toast.LENGTH_LONG).show();
        }else if(requestCode==1&&resultCode==getActivity().RESULT_OK) {
            Toast.makeText(getContext(),"video listo para enviar",Toast.LENGTH_LONG).show();
        }else if(requestCode==0&&resultCode!=getActivity().RESULT_OK) {
            try {
                if(imgCapturada.delete()){
                    Toast.makeText(getContext(),"Ha cancelo la captura de imagen",Toast.LENGTH_LONG).show();
                    imgCapturada=null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if(requestCode==1&&resultCode!=getActivity().RESULT_OK) {
            try {
                if(videoCapturado.delete()){
                    Toast.makeText(getContext(),"Ha cancelo la captura de video",Toast.LENGTH_LONG).show();
                    videoCapturado=null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{/*
            try {
                imgCapturada.delete();
            }catch (Exception e){
                e.printStackTrace();
            }*/
        }
    }

    public void fillDataClasificacion(){
        linOpacity.setVisibility(View.VISIBLE);
        incidenciaService
                .getClasificacionIncidencia()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<ClasificacionWraper>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }
                            @Override
                            public void onNext(ClasificacionWraper clasificacionWraper) {

                               clasificacionIncidenciaList=clasificacionWraper.getData();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("TAG1", "Error: " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("TAG1", "OnComplete: ");
                                clasificacionList= new ArrayList<>();
                                for(ClasificacionIncidencia ci:clasificacionIncidenciaList){
                                    clasificacionList.add(ci.getValor());
                                }
                                AutoCompleteTextView autoCompleteTextView=getActivity().findViewById(R.id.ac_clasificacion_incidencia);
                                Log.i("CLASIFICACION",clasificacionIncidenciaList.toString());
                                ArrayAdapter adapter= new ArrayAdapter(getActivity().getApplicationContext(),R.layout.ac_simple_item,clasificacionList);
                                autoCompleteTextView.setAdapter(adapter);
                                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        idClasificacion=clasificacionIncidenciaList.get(position).getMultitablaId();
                                        AutoCompleteTextView acSubtipo=getActivity().findViewById(R.id.ac_subtipo_incidencia);
                                        acSubtipo.setText("");
                                        AutoCompleteTextView acTipo=getActivity().findViewById(R.id.ac_tipo_incidencia);
                                        acTipo.setText("");
                                        idTipo=null;
                                        idSubtipo=null;
                                        fillDataTipoIncidencia(idClasificacion);
                                    }
                                });
                                autoCompleteTextView.setThreshold(1);
                                linOpacity.setVisibility(View.GONE);
                            }
                        }
                );

    }

    private void setUpDagger(){
        ((BaseApplication)getActivity().getApplication()).getRetrofitComponent().inject(this);
    }


    public void fillDataTipoIncidencia(Integer id){
        linOpacity.setVisibility(View.VISIBLE);
        incidenciaService
                .getTipoIncidenciaByIdClas(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<TipoIncidenciaWraper>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }
                            @Override
                            public void onNext(TipoIncidenciaWraper tipoIncidenciaWraper) {

                                tipoIncidenciaList=tipoIncidenciaWraper.getData();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("TAG1", "Error: " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("TAG1", "OnComplete: ");
                                tipoList= new ArrayList<>();
                                for(TipoIncidencia ti:tipoIncidenciaList){
                                    tipoList.add(ti.getValor());
                                }
                                AutoCompleteTextView autoCompleteTextView=getActivity().findViewById(R.id.ac_tipo_incidencia);
                                Log.i("CLASIFICACION",clasificacionIncidenciaList.toString());
                                ArrayAdapter adapter= new ArrayAdapter(getActivity().getApplicationContext(),R.layout.ac_simple_item,tipoList);
                                autoCompleteTextView.setAdapter(adapter);
                                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        idTipo=tipoIncidenciaList.get(position).getMultitablaId();
                                        fillDataSubtipoIncidencia(idTipo);
                                    }
                                });
                                autoCompleteTextView.setThreshold(1);
                                AutoCompleteTextView scSubtipo=getActivity().findViewById(R.id.ac_subtipo_incidencia);
                                scSubtipo.setText("");
                                idSubtipo=null;
                                linOpacity.setVisibility(View.GONE);
                            }
                        }
                );
    }

    public void fillDataSubtipoIncidencia(Integer id){
        linOpacity.setVisibility(View.VISIBLE);
        incidenciaService
                .getSubtitpoIncidencia(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<SubtipoIncidenciaWraper>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }
                            @Override
                            public void onNext(SubtipoIncidenciaWraper subtipoIncidenciaWraper) {

                                subtipoIncidenciaList=subtipoIncidenciaWraper.getData();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("TAG1", "Error: " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("TAG1", "OnComplete: ");
                                subtipoList= new ArrayList<>();
                                for(SubtipoIncidencia si:subtipoIncidenciaList){
                                    subtipoList.add(si.getValor());
                                }
                                AutoCompleteTextView autoCompleteTextView=getActivity().findViewById(R.id.ac_subtipo_incidencia);
                                ArrayAdapter adapter= new ArrayAdapter(getActivity().getApplicationContext(),R.layout.ac_simple_item,subtipoList);
                                autoCompleteTextView.setAdapter(adapter);
                                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        idSubtipo=subtipoIncidenciaList.get(position).getMultitablaId();
                                    }
                                });
                                autoCompleteTextView.setThreshold(1);
                                linOpacity.setVisibility(View.GONE);
                            }
                        }
                );
    }

    public void guardarInicencia(View view){
        TextInputEditText tiDescripcion = getView().findViewById(R.id.ta_descripcion_incidencia);
        if(idClasificacion!=null&& idTipo!=null&&idSubtipo!=null&&tiDescripcion.getText().toString().length()>1&&tiDescripcion.getText().toString().length()<=250) {
            SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            Integer idPersonal = preferences.getInt("id_personal", 0);
            String ciudadanoInfo = preferences.getString("user_info", "-");
            String telefonoCiudadano = preferences.getString("celular", "-");

            Date fecha = new Date();
            SimpleDateFormat sdFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdHora = new SimpleDateFormat("hh:mm:ss");

            MultipartBody.Part videoPart = (videoCapturado!=null)?MultipartBody.Part.createFormData("video", videoCapturado.getName(), RequestBody.create(MediaType.parse("video/*"), videoCapturado)):null;
            MultipartBody.Part imagePart =(imgCapturada!=null)? MultipartBody.Part.createFormData("image", imgCapturada.getName(), RequestBody.create(MediaType.parse("image/*"), imgCapturada)):null;
            linOpacity.setVisibility(View.VISIBLE);
            incidenciaService
                    .reportarIncidencia(
                            videoPart, imagePart, null,
                            RequestBody.create(MediaType.parse("text/plain"), sdFecha.format(fecha)),
                            RequestBody.create(MediaType.parse("text/plain"), sdHora.format(fecha)),
                            RequestBody.create(MediaType.parse("text/plain"), idPersonal.toString()),
                            RequestBody.create(MediaType.parse("text/plain"), ciudadanoInfo),
                            RequestBody.create(MediaType.parse("text/plain"), idClasificacion.toString()),
                            RequestBody.create(MediaType.parse("text/plain"), idTipo.toString()),
                            RequestBody.create(MediaType.parse("text/plain"), idSubtipo.toString()),
                            null, null, null,
                            RequestBody.create(MediaType.parse("text/plain"), tiDescripcion.getText().toString()),
                            null,
                            RequestBody.create(MediaType.parse("text/plain"), telefonoCiudadano),
                            RequestBody.create(MediaType.parse("text/plain"), "-"),
                            RequestBody.create(MediaType.parse("text/plain"), idPersonal.toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Observer<IncidenciaWraper>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    disposable = d;
                                }

                                @Override
                                public void onNext(IncidenciaWraper subtipoIncidenciaWraper) {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("TAG1", "Error: " + e.getMessage());
                                    Toast.makeText(getContext(), "Ocurrio un error intente denuevo", Toast.LENGTH_LONG);
                                    linOpacity.setVisibility(View.GONE);
                                }

                                @Override
                                public void onComplete() {
                                    Log.d("TAG1", "OnComplete: ");
                                    AutoCompleteTextView acClasificacion = getActivity().findViewById(R.id.ac_clasificacion_incidencia);
                                    acClasificacion.setText("");
                                    AutoCompleteTextView acTipo = getActivity().findViewById(R.id.ac_tipo_incidencia);
                                    acTipo.setText("");
                                    AutoCompleteTextView acSubtipo = getActivity().findViewById(R.id.ac_subtipo_incidencia);
                                    acSubtipo.setText("");
                                    tiDescripcion.setText("");
                                    imgCapturada=null;
                                    videoCapturado=null;
                                    linOpacity.setVisibility(View.GONE);
                                }
                            }
                    );
        }else{
            Toast.makeText(getContext(),"Rellene todos los campos requeridos",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
