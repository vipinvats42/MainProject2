package com.example.tapesh.mainproject2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by TAPESH on 4/16/2016.
 */
public class OneFragment extends Fragment {

    private String[] arraySpinner2;
    private String[] arraySpinner1;

    String textspinner1;
    String textspinner;
    View view;
    Spinner spinner2,spinner1;
    Button button1;
    EditText editText1price;
    String price;
    public OneFragment()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
           view=inflater.inflate(R.layout.fragment_one,container,false);

        spinner2=(Spinner)view.findViewById(R.id.spinner2);
        spinner1=(Spinner)view.findViewById(R.id.spinner1);
        button1=(Button)view.findViewById(R.id.button1);
        editText1price=(EditText)view.findViewById(R.id.editText1);

        this.arraySpinner2=new String[]{"","Bag","Cards","Purse","Trouser"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, arraySpinner2);
        spinner2.setAdapter(adapter2);



        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_one, container, false);




        this.arraySpinner1=new String[]{"","10","15","20","25"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, arraySpinner1);
        spinner1.setAdapter(adapter1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncTask().execute();
                Log.i("in on click","method");
            }
        });




        return view;
    }

    private class HttpAsyncTask extends AsyncTask<Void ,Void,Void>
    {
       @Override
        protected void onPreExecute()
       {
           price=editText1price.getText().toString();
            textspinner=spinner1.getSelectedItem().toString();
            textspinner1=spinner2.getSelectedItem().toString();
           Log.i("qty",textspinner);
           Log.i("item",textspinner1);

       }

       @Override
        protected  Void doInBackground(Void...argo)
       {


           JSONObject jo = new JSONObject();
          try {





              jo.put("itemName", textspinner1);
              jo.put("unitPrice", price);
              jo.put("quantity", textspinner);
              jo.put("brand","");

              URL url = new URL("http://192.168.0.13:8080/Buddy/a/purchase");

              HttpClient httpClient=new DefaultHttpClient();
              HttpPost httpPost=new HttpPost(url.toURI());

              httpPost.setEntity(new StringEntity(jo.toString(), "UTF-8"));
              httpPost.setHeader("Content-Type", "application/json");
              httpPost.setHeader("Accept-Encoding", "application/json");
              httpPost.setHeader("Accept-Language", "en-US");
              ResponseHandler<String> responseHandler = new BasicResponseHandler();
             // HttpResponse response=httpClient.execute(httpPost,responseHandler);
              String response=httpClient.execute(httpPost,responseHandler);

              Log.i("hit the json","to url");
              Log.i("response",response);


          }catch (Exception e)
          {
              Log.i("error",e.toString());

          }

           return null;
       }


    }


}
