package org.strykeforce.scoutapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.ImageView;
import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class MainActivity extends AppCompatActivity {

    boolean CrossBaseLine;
    boolean PlaceGear;
    boolean AutoLow;
    String autohigh;
    int gearsdelivered;
    int lowgoalsteleload;
    boolean gearoffground;
    String highgoals;
    boolean getsdefended;
    boolean touchpad;
    int timetakentotouchpad;
    boolean defense;
    String notes;
    String matchnumber;
    String Teamnumber;
    int scoutid;
    private ImageView QRImageView;
    private String QRStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NextMatch();
        findViewById(R.id.sendbutton).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                GenerateQRString();
                setContentView(R.layout.popup);
                QRImageView = (ImageView) findViewById(R.id.imageView2);
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(QRStr,BarcodeFormat.QR_CODE,400,400);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    QRImageView.setImageBitmap(bitmap);
                }
                catch (WriterException e) {
                    e.printStackTrace();

                }

                findViewById(R.id.button_No).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_main);
                    }});

                findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setContentView(R.layout.activity_main);
                        NextMatch();
                    }});

            }

        });


    }
    public void GenerateQRString()
    {
        Switch baselineswitch  = (Switch) findViewById(R.id.baseline);
        CrossBaseLine = baselineswitch.isChecked();

        Switch placegearSwitch = (Switch) findViewById(R.id.placegearautodata);
        PlaceGear = placegearSwitch.isChecked();

        Switch lowgoalAutoSwitch = (Switch) findViewById(R.id.lowgoaldataauto);
        AutoLow = lowgoalAutoSwitch.isChecked();

        Switch touchpadSwitch = (Switch) findViewById(R.id.touchpad);
        touchpad = touchpadSwitch.isChecked();

        Switch gearoffgroundSwitch = (Switch) findViewById(R.id.gearoffground);
        gearoffground = gearoffgroundSwitch.isChecked();


        Switch OnDefenseSwitch = (Switch) findViewById(R.id.ondefence);
        defense = OnDefenseSwitch.isChecked();


        Switch GetsDefendedSwitch = (Switch) findViewById(R.id.highgoaldefence);
        getsdefended = GetsDefendedSwitch.isChecked();

        QRStr = " Crosses base line: "+ CrossBaseLine+ System.lineSeparator()
                + " Places Gear in Auton: " + PlaceGear + System.lineSeparator()
                + " Scores the low goal in auton: " + AutoLow + System.lineSeparator()
                + " On defence: " + defense + System.lineSeparator()
                + " Can pick a gear off the ground: " + gearoffground + System.lineSeparator()
                + " Defended while shooting high goal: " + getsdefended + System.lineSeparator()
                + " Touchpad: " + touchpad;

    }
    public void NextMatch(){
        //import excel
        Switch baselineswitch  = (Switch) findViewById(R.id.baseline);
        baselineswitch.setChecked(false);

        Switch placegearSwitch = (Switch) findViewById(R.id.placegearautodata);
        placegearSwitch.setChecked(false);

        Switch lowgoalAutoSwitch = (Switch) findViewById(R.id.lowgoaldataauto);
        lowgoalAutoSwitch.setChecked(false);

        Switch touchpadSwitch = (Switch) findViewById(R.id.touchpad);
        touchpadSwitch.setChecked(false);;

        Switch gearoffgroundSwitch = (Switch) findViewById(R.id.gearoffground);
        gearoffgroundSwitch.setChecked(false);


        Switch OnDefenseSwitch = (Switch) findViewById(R.id.ondefence);
        OnDefenseSwitch.setChecked(false);


        Switch GetsDefendedSwitch = (Switch) findViewById(R.id.highgoaldefence);
        GetsDefendedSwitch.setChecked(false);


    }
}


