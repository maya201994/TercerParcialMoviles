package com.example.gamr.tercerparcial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamr.tercerparcial.BaseDatos.ConstantesBD;
import com.example.gamr.tercerparcial.Conexion.SQLiteHelper;
import com.example.gamr.tercerparcial.Entidades.VolleySingleton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRegistrar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRegistrar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegistrar extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText campoNombre, campoDescripcion, campoRangomin, campoRangomax, campoLatitud, campoLongitud;
    Button registrarestacion;
    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;
    View vista;

    private OnFragmentInteractionListener mListener;

    public FragmentRegistrar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegistrar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegistrar newInstance(String param1, String param2) {
        FragmentRegistrar fragment = new FragmentRegistrar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista  = inflater.inflate(R.layout.fragment_fragment_registrar, container, false);
        registrarestacion = (Button) vista.findViewById(R.id.button_RegistrarEstacion);
        campoNombre = (EditText) vista.findViewById(R.id.editText_nombreestacion);
        campoDescripcion = (EditText) vista.findViewById(R.id.editText_descripcionestacion);
        campoRangomin = (EditText) vista.findViewById(R.id.editText_Ran_min);
        campoRangomax = (EditText) vista.findViewById(R.id.editText_Ran_max);
        campoLatitud = (EditText) vista.findViewById(R.id.editText_latitud);
        campoLongitud = (EditText) vista.findViewById(R.id.editText_longitud);
        registrarestacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        return vista;
    }

    private void cargarWebService() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String ip=getString(R.string.ip);
        String url = ip+"TercerParcial/RegistrarEstacion.php?nombre="+campoNombre.getText().toString()+"" +
                "&descripcion="+campoDescripcion.getText().toString()+"&latitud="+campoLatitud.getText().toString()+"" +
                "&longitud="+campoLongitud.getText().toString()+"&rangomin="+campoRangomin.getText().toString()+"" +
                "&rangomax="+campoRangomax.getText().toString()+"";
        url = url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(getContext(), "No se pudo resgitrar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "Se ha registrado la estacion correctamente.", Toast.LENGTH_SHORT).show();
        progressDialog.hide();
        campoRangomax.setText("");
        campoRangomin.setText("");
        campoLongitud.setText("");
        campoLatitud.setText("");
        campoDescripcion.setText("");
        campoNombre.setText("");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}