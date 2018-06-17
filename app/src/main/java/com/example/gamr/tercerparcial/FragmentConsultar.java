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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamr.tercerparcial.Entidades.Estacion;
import com.example.gamr.tercerparcial.Entidades.VolleySingleton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentConsultar.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link FragmentConsultar#newInstance} factory method to create an instance of this fragment.
 */
public class FragmentConsultar extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText campoName;
    TextView campoNombre, campoDescripcion, campoRangomin, campoRangomax, campoLatitud, campoLongitud;
    Button consultarestacion;
    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;
    View vista;
    private AdView mAdView;

    private OnFragmentInteractionListener mListener;

    public FragmentConsultar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentConsultar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConsultar newInstance(String param1, String param2) {
        FragmentConsultar fragment = new FragmentConsultar();
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
        vista = inflater.inflate(R.layout.fragment_fragment_consultar, container, false);
        campoName = (EditText) vista.findViewById(R.id.editText_nombreestacion);
        consultarestacion = (Button) vista.findViewById(R.id.bttn_BuscarEstacion);
        campoNombre = (TextView) vista.findViewById(R.id.textView_Nombre);
        campoDescripcion = (TextView) vista.findViewById(R.id.textView_Descripcion);
        campoLatitud = (TextView) vista.findViewById(R.id.textView_Latitud);
        campoLongitud = (TextView) vista.findViewById(R.id.textView_longitud);
        campoRangomax = (TextView) vista.findViewById(R.id.textView_Rangomax);
        campoRangomin = (TextView) vista.findViewById(R.id.textView_Rangomin);
        mAdView = vista.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        consultarestacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        return vista;
    }

    private void cargarWebService() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Consultando...");
        progressDialog.show();
        String ip=getString(R.string.ip);
        String url = ip+"TercerParcial/ConsultarEstacion.php?nombre="+campoName.getText().toString()+"";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(getContext(), "No se pudo consultar la estacion", Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

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
        campoNombre.setText("Nombre: "+estacion.getNombre());
        campoDescripcion.setText("Descripcion: "+estacion.getDescripcion());
        campoLatitud.setText("Latitud: "+estacion.getLatitud());
        campoLongitud.setText("Longitud: "+estacion.getLongitud());
        campoRangomin.setText("Rango Minimo: "+estacion.getRangomin());
        campoRangomax.setText("Rango Maximo: "+estacion.getRangomax());
        campoName.setText("");
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
     * See the Android Training lesson <a href="http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}