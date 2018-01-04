package ca.javajeff.zedkz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static android.R.attr.width;

/**
 * Created by Саддам on 04.01.2018.
 */

public class InfoActivity extends AppCompatActivity {
    TextView nameView;
    TextView surnameView;
    TextView idView;
    TextView emailView;
    TextView genderView;
    TextView ipView;
    ImageView photoView;
    TextView jobView;

    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");

        nameView = (TextView) findViewById(R.id.name);
        surnameView = (TextView) findViewById(R.id.surname);
        //idView = (TextView) findViewById(R.id.id);
        emailView = (TextView) findViewById(R.id.email);
        genderView = (TextView) findViewById(R.id.gender);
        ipView = (TextView) findViewById(R.id.ip);
        jobView = (TextView) findViewById(R.id.job);
        photoView = (ImageView) findViewById(R.id.photo);

        JSONObject object;
        try {
            object = OpenAPIJsonUtils.Arr.getJSONObject(id);
            String name = object.getString("first_name");
            nameView.setText(name);
            String surname = object.getString("last_name");
            surnameView.setText(surname);
            String email = object.getString("email");
            emailView.setText(email);
            String gender = object.getString("gender");
            genderView.setText(gender);
            String ip = object.getString("ip_address");
            ipView.setText(ip);
            String photo = object.getString("photo");
            Log.v("photo", photo);
            //Picasso.with(this).load(photo).into(photoView);
            new DownloadImageTask((ImageView) findViewById(R.id.photo))
            .execute(photo);
            JSONObject empl = object.getJSONObject("employment");
            String job = empl.getString("name") + ", Position: " + empl.getString("position");
            jobView.setText(job);
            int idd = id+1;
            String iddd = String.valueOf(idd);
            //idView.setText(iddd);

            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            setSupportActionBar(mActionBarToolbar);
            mActionBarToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.White));
            getSupportActionBar().setTitle(name + " " + surname);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("errorr", "errorr");
        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
