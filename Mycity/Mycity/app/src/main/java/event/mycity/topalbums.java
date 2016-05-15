package event.mycity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static event.mycity.R.id.listView2;

public class topalbums extends AppCompatActivity {
    public ProgressBar spinner;
    public String[] title_array;

    public String[] getList(){
        return this.title_array;
    }

    public void setTitle(String[] title_array){
        this.title_array = title_array;
    }

    public String[] album_name_array;

    public String[] getAlbum_name(){
        return this.album_name_array;
    }

    public void setAlbum(String[] album_name_array){
        this.album_name_array = album_name_array;
    }

    public int arraylength;

    public int getLength(){
        return this.arraylength;
    }

    public void setLength(int arraylength){
        this.arraylength = arraylength;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apilist);

        getAlbum();
    }


    public void getAlbum() {

        spinner = (ProgressBar)findViewById(R.id.progress);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        java.util.Date date= new java.util.Date();
        long timeStemp = System.currentTimeMillis();

        final TextView mTextView = (TextView) findViewById(R.id.text);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.deezer.com/search?q=album/top";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>()
                {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override

                    public void onResponse(JSONObject response) {

//                        spinner.setVisibility(View.VISIBLE);
                        JSONArray array = null;
                        try {

                            array = response.getJSONArray("data");
                            String id = null;
                            String title = null;
                            String duration = null;

                            String[] title_array = new String[array.length()];
                            String[] duration_merge = new String[array.length()];
                            String[] album_name_array = new String[array.length()];
                            int arrayLength=  array.length();

                            for(int i = 0 ; i < array.length() ; i++)
                            {


                                String image_url = array.getJSONObject(i).getJSONObject("artist").getString("picture_medium");

                                String album_name = array.getJSONObject(i).getJSONObject("album").getString("title");
                                album_name_array [i] =  album_name;


                                duration = array.getJSONObject(i).getString("duration");

                                title = array.getJSONObject(i).getString("title");
                                title_array [i] = title;



                                int j = i+1;
                                int durationToInt = Integer.parseInt(duration);

                                int mins = durationToInt / 60;

                                // Minutes String
                                String minsToString = String.valueOf(mins);
                                // Minutes String
                                durationToInt = durationToInt - mins * 60;

                                int secs = durationToInt;

                                String secsToString = String.valueOf(secs);

                                if (secs < 10)
                                {
                                    String lowSecs = "0"+secs;
                                    secsToString = lowSecs;

                                }
                                String minsAndSeconds = minsToString+":"+secsToString;
                                duration_merge [i] = minsAndSeconds;

                              //  System.out.println(duration_merge[i]);
                               // System.out.println(album_name_array[i]);
                               // System.out.println(title_array[i]);



                                //Log.d("Resultat Of titles =>", title.toString());
                                //Log.d("Resultat Of Durations =>", "int "+mins+":"+secsToString);
                                //Log.d("Pictures => ", image_url);


                        /*
                            ImageView image = new ImageView(getBaseContext());
                            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(150,160));
                            image.setMaxHeight(200);
                            image.setMaxWidth(200);
                            // Adds the view to the layout
                            layout2.addView(image);
                            URL url = new URL(image_url);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            image.setImageBitmap(bmp);
                        */

                            }
                                    setLength(arrayLength);
                                    setTitle(title_array);
                                    setAlbum(album_name_array);
                                    SimpleAdapt();
                            spinner.setVisibility(View.GONE);

                        }

                        catch (JSONException e)
                        {
                            //  e.printStackTrace();
                        }


                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }

        );



        getRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(getRequest);



    }

    public void SimpleAdapt()
    {
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < getLength(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("cur", "Top rated  : " + getList()[i]);
            hm.put("txt", "Album : " + getAlbum_name()[i]);
            aList.add(hm);
        }
        String[] from = {"txt", "cur"};
        int[] to = {R.id.txt, R.id.cur};

        SimpleAdapter adapter = new SimpleAdapter(this, aList, R.layout.activity_test_2, from, to);

        ListView listView = (ListView) findViewById(listView2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //@Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                HashMap listItem = (HashMap) adapter.getItemAtPosition(position);
                String ok = (String) listItem.get("txt");

                System.out.println(ok);

                Intent intent = new Intent(topalbums.this , albumsdetails.class);
                intent.putExtra("test1",ok);
                //based on item add info to intent
                startActivity(intent);
            }
        });


        listView.setAdapter(adapter);

    }
}
