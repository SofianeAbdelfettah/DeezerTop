package event.mycity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class details extends AppCompatActivity {


    public ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Intent myIntent = getIntent();
        final String firstKeyName = myIntent.getStringExtra("test1");
        spinner = (ProgressBar)findViewById(R.id.progressBar);

        TextView text = (TextView) findViewById(R.id.text);
        text.setText(firstKeyName);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.deezer.com/search?q=playlist&order=RATING_DESC";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,

                new Response.Listener<JSONObject>()
                {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override

                    public void onResponse(JSONObject response) {
                        System.out.println("System 2");

                        JSONArray array = null;
                        try {
                            System.out.println("System 3");

                            array = response.getJSONArray("data");

                            String[] title_array = new String[array.length()];

                            String preview = null;
                            TextView preview_text = (TextView) findViewById(R.id.preview);
                            for(int i = 0 ; i < array.length() ; i++)
                            {
                                System.out.println("System 4");

                                String title = array.getJSONObject(i).getString("title");

                                if(Objects.equals("Titre : " + title, firstKeyName)) {
                                    System.out.println("On a le bon truc : "+ title);

                                    String image_url = array.getJSONObject(i).getJSONObject("artist").getString("picture_medium");

                                    preview = array.getJSONObject(i).getString("preview");
                                    final URL previewTOUrl = new URL(preview);
                                    preview_text.setText(preview);
                                    preview_text.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent pass = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(previewTOUrl)));
                                            startActivity(pass);

                                        }
                                    });

                                    ImageView image = (ImageView) findViewById(R.id.image);
//                                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(150,160));
//                                    image.setMaxHeight(200);
//                                    image.setMaxWidth(200);
//                                    // Adds the view to the layout
//                                    layout2.addView(image);

                                    URL url = new URL(image_url);
                                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    image.setImageBitmap(bmp);


                                }
                                else
                                   System.out.println("Object not found");



                            }
                            spinner.setVisibility(View.GONE);
                        }

                        catch (JSONException e)
                        {
                            //  e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
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






//        TextView text = (TextView) findViewById(R.id.textView);
//        text.setText(firstKeyName);
//


}
