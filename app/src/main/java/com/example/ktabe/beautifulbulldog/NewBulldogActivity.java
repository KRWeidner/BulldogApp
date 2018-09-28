package com.example.ktabe.beautifulbulldog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

public class NewBulldogActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Button saveButton;
    private EditText saveName;
    private EditText saveAge;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bulldog);

        imageButton = (ImageButton) findViewById(R.id.image_button);
        saveButton = (Button) findViewById(R.id.save_button);
        saveName = (EditText) findViewById(R.id.save_name);
        saveAge = (EditText) findViewById(R.id.save_age);

        realm = Realm.getDefaultInstance();
        //String id = (String) getIntent().getStringExtra("bulldog");
        //final Bulldog bulldog = realm.where(Bulldog.class).equalTo("id",id).findFirst();


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePicIntent,1);
                }

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveName.getText().toString().matches("")
                        && !saveAge.getText().toString().matches("")
                        && imageButton.getDrawable() != null){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm){
                            Bulldog bulldog = new Bulldog();
                            bulldog.setAge(saveAge.getText().toString());
                            bulldog.setName(saveName.getText().toString());
                            bulldog.setId(realm.where(Bulldog.class).findAllSorted("id").last().getId()+1);
                            BitmapDrawable image = (BitmapDrawable) imageButton.getDrawable();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.getBitmap().compress(Bitmap.CompressFormat.JPEG,100,baos);
                            byte[] imageInByte = baos.toByteArray();
                            bulldog.setImage(imageInByte);

                            realm.copyToRealm(bulldog);
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==1 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }
}
