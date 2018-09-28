package com.example.ktabe.beautifulbulldog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import io.realm.Realm;

public class BulldogActivity extends AppCompatActivity {

    private TextView textName;
    private TextView textAge;
    private Spinner voteSpinner;
    private Button voteButton;
    private Realm realm;
    private TextView voteText;
    private ImageView voteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        textName = (TextView) findViewById(R.id.text_name);
        textAge = (TextView) findViewById(R.id.text_age);
        voteSpinner = (Spinner) findViewById(R.id.vote_spinner);
        voteButton = (Button) findViewById(R.id.vote_button);
        voteImage = (ImageView) findViewById(R.id.vote_image);

        Integer[] items = new Integer[]{1,2,3,4,5};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        voteSpinner.setAdapter(adapter);

        realm = Realm.getDefaultInstance();
        String id = (String) getIntent().getStringExtra("bulldog");
        final Bulldog bulldog = realm.where(Bulldog.class).equalTo("id",id).findFirst();

        String username = (String) getIntent().getStringExtra("username");
        final User owner = realm.where(User.class).equalTo("username",username).findFirst();

        textName.setText(bulldog.getName());
        textAge.setText(bulldog.getAge());

        if(bulldog.getImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bulldog.getImage(), 0, bulldog.getImage().length);
            voteImage.setImageBitmap(bmp);
        }


        voteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Vote vote = new Vote();
                        vote.setRating(voteSpinner.getSelectedItemPosition());
                        vote.setBulldog(bulldog);
                        vote.setOwner(owner);
                        realm.copyToRealm(vote);

                        finish();
                    }
                });
            }
        });


    }
}
