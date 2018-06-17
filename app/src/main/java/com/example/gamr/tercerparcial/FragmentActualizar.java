package com.example.gamr.tercerparcial;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamr.tercerparcial.Entidades.Estacion;
import com.example.gamr.tercerparcial.Entidades.VolleySingleton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the{@link FragmentActualizar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentActualizar#newInstance} factory method to create an instance of this fragment.
 */
public class FragmentActualizar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText campoName;
    EditText campoDescripcion, campoRangomin, campoRangomax, campoLatitud, campoLongitud;
    Button consultarestacion, actualizarestacion;
    ProgressDialog progressDialog;
    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;
    View vista;

    private AdView mAdView;

    private OnFragmentInteractionListener mListener;

    public FragmentActualizar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentActualizar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentActualizar newInstance(String param1, String param2) {
        FragmentActualizar fragment = new FragmentActualizar();
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
        vista = inflater.inflate(R.layout.fragment_fragment_actualizar, container, false);
        campoName = (EditText) vista.findViewById(R.id.editText_nombreestacion);
        consultarestacion = (Button) vista.findViewById(R.id.bttn_BuscarEstacion);
        actualizarestacion = (Button) vista.findViewById(R.id.bttn_ActualizarEstacion);
        campoDescripcion = (EditText) vista.findViewById(R.id.textView_Descripcion);
        campoLatitud = (EditText) vista.findViewById(R.id.textView_Latitud);
        campoLongitud = (EditText) vista.findViewById(R.id.textView_longitud);
        campoRangomax = (EditText) vista.findViewById(R.id.textView_Rangomax);
        campoRangomin = (EditText) vista.findViewById(R.id.textView_Rangomin);
        mAdView = vista.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        consultarestacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        actualizarestacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceActualizar();
            }
        });
        return vista;
    }

    private void cargarWebService() {
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String ip=getString(R.string.ip);
        String url = ip+"TercerParcial/ConsultarEstacion.php?nombre="+campoName.getText().toString();
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.hide();
                Estacion estacion = new Estacion();
                JSONArray jsonArray = response.optJSONArray("estacion");
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(0);
                    estacion.setNombre(jsonObject.optString("nombre"));
                    estacion.setDescripcion(jsonObject.optString("descripcion"));
                    estacion.setLatitud((float) jsonObject.optDouble("latitud"));
                    estacion.setLongitud((float)jsonObject.optDouble("longitud"));
                    estacion.setRangomin((float)jsonObject.optDouble("rangomin"));
                    estacion.setRangomax((float)jsonObject.optDouble("rangomax"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                campoDescripcion.setText(estacion.getDescripcion());
                campoLatitud.setText(String.valueOf(estacion.getLatitud()));
                campoLongitud.setText(String.valueOf(estacion.getLongitud()));
                campoRangomin.setText(String.valueOf(estacion.getRangomin()));
                campoRangomax.setText(String.valueOf(estacion.getRangomax()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                progressDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void webServiceActualizar() {
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String ip=getString(R.string.ip);
        String url = ip+"TercerParcial/ActualizarEstacion.php?";
        stringRequest=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    Toast.makeText(getContext(),"Se ha Actualizado con exito.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No se ha Actualizado.",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                String nombre=campoName.getText().toString();
                String descripcion=campoDescripcion.getText().toString();
                String latitud=campoLatitud.getText().toString();
                String longitud=campoLongitud.getText().toString();
                String rangomin=campoRangomin.getText().toString();
                String rangomax=campoRangomax.getText().toString();

                Map<String,String> parametros=new HashMap<>();
                parametros.put("nombre",nombre);
                parametros.put("descripcion",descripcion);
                parametros.put("latitud",latitud);
                parametros.put("longitud",longitud);
                parametros.put("rangomin",rangomin);
                parametros.put("rangomax",rangomax);
                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
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
     * This interface must be implemented by activities that contain this fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that activity.
     * <p>
     * See the Android Training lesson <a href="http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}