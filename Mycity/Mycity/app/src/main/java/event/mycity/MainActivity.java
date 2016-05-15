package event.mycity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playlistnext(View view)
    {
        Intent intent = new Intent(MainActivity.this, Apilist.class);
        startActivity(intent);
    }

    public void albumsnext(View view)
    {
        Intent intent = new Intent(MainActivity.this, topalbums.class);
        startActivity(intent);
    }

    public void artistesnext(View view)
    {
        Intent intent = new Intent(MainActivity.this, topartistes1.class);
        startActivity(intent);
    }


}
