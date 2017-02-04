package org.strykeforce.scoutapp;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.widget.Button;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.SeekBar;
    import android.widget.TextView;
    import android.widget.Switch;
    import android.widget.ImageView;
    import android.graphics.Bitmap;
    import android.widget.Toast;

    import com.google.zxing.BarcodeFormat;
    import com.google.zxing.MultiFormatWriter;
    import com.google.zxing.WriterException;
    import com.google.zxing.common.BitMatrix;
    import com.journeyapps.barcodescanner.BarcodeEncoder;
    import java.io.IOException;
    import java.io.InputStream;
    import java.util.Scanner;

    public class MainActivity extends AppCompatActivity {
    /*THINGS THAT NEED TO IMPROVE:
    repeat onClickListener???
    field diagram?
    slide bar showing actual numbers
    add other elements -> high balls etc.
    lock screen orientation -> change manifest file and qr code layout

    Nika: textfile loop, disappearing

    ADDED:
    rotates through team numbers and match numbers from text file
    master app can handle all current data
    */

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
        String scoutid;
        String matchnumber;
        String Teamnumber;
        private ImageView QRImageView;
        private String QRStr;
        int lowgoalLoadsTele;
        int gearsDeliveredTele;
        TextView lowgoaldisplay;
        TextView geardisplay;
        private Button doneButton;
        String scoutName;
        String notes;
        private static SeekBar seek_bar;
        private static TextView text_view;
        int step = 1;
        int max = 30;
        int min = 5;
        int progressSeek;



        //Nika's Variables NB
        private int[][] allTeamNums;
        private boolean[] matchDone;
        private int numMatches;
        private final int SCOUT_ID = 1; //CONSTANT, in this case red 1
        private String teamText = "";
        private static int MATCH_NUMBER=0, TEAM_NUMBER; //current match and team num

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.start);

            allTeamNums = getTeamNums();
            matchDone = new boolean[numMatches];
            for(int j=0; j<numMatches; j++)
                matchDone[j] = false;

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText inputText = (EditText) findViewById(R.id.editText);
                    scoutid = inputText.getText().toString();
                    setContentView(R.layout.activity_main);
                    ResetMatch();
                    popupscouttScreen();
                };
            });
        }

        public void GenerateQRString()
        {
            EditText inputText1 = (EditText) findViewById(R.id.editText6);
            autohigh = inputText1.getText().toString();

            EditText inputText2 = (EditText) findViewById(R.id.editText3);
            scoutName = inputText2.getText().toString();

            EditText inputText3 = (EditText) findViewById(R.id.editText2);
            notes = inputText3.getText().toString();

            EditText inputText4 = (EditText) findViewById(R.id.highteledata);
            highgoals = inputText4.getText().toString();

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

            if(touchpad)
            {
                QRStr = "Scout ID: " + scoutid + System.lineSeparator() +
                    "Climb rope time: " + (progressSeek+min) + System.lineSeparator()
                    +"Tele High: " + highgoals + System.lineSeparator()
                    +"Scout Name: " + scoutName + System.lineSeparator()
                    +"Notes: " + notes + System.lineSeparator()
                    +"Auto High: " + autohigh + System.lineSeparator()
                    + "Low Goal Loads in Tele: " + lowgoalLoadsTele+ System.lineSeparator()
                    + "Gears Delivered in Tele: " + gearsDeliveredTele +System.lineSeparator()
                    + " Crosses base line: "+ CrossBaseLine+ System.lineSeparator()
                    + " Places Gear in Auto: " + PlaceGear + System.lineSeparator()
                    + " Scores low in auto: " + AutoLow + System.lineSeparator()
                    + " On defence: " + defense + System.lineSeparator()
                    + " Can pick gear off ground: " + gearoffground + System.lineSeparator()
                    + " Defended shooting high: " + getsdefended + System.lineSeparator()
                    + " Touchpad: " + touchpad;
            }
            else {
                QRStr = "Scout ID: " + scoutid + System.lineSeparator()
                    + "Climb rope time: 0" + System.lineSeparator()
                    + "Tele High: " + highgoals + System.lineSeparator()
                    + "Scout Name: " + scoutName + System.lineSeparator()
                    + "Notes: " + notes + System.lineSeparator()
                    + "Auto High: " + autohigh + System.lineSeparator()
                    + "Low Goal Loads in Tele: " + lowgoalLoadsTele + System.lineSeparator()
                    + " Crosses base line: " + CrossBaseLine + System.lineSeparator()
                    + "Gears Delivered in Tele: " + gearsDeliveredTele + System.lineSeparator()
                    + " Places Gear in Auto: " + PlaceGear + System.lineSeparator()
                    + " Scores low in auto: " + AutoLow + System.lineSeparator()
                    + " On defence: " + defense + System.lineSeparator()
                    + " Can pick gear off ground: " + gearoffground + System.lineSeparator()
                    + " Defended shooting high: " + getsdefended + System.lineSeparator()
                    + " Touchpad: " + touchpad;
            }
        }

        public void popupscouttScreen(){

            {
                doneButton = (Button) findViewById(R.id.sendbutton);

                SeekBar seekbar = (SeekBar) (findViewById(R.id.ropetimedata));
                seekbar.setMax((max - min) / step);

                seekbar.setOnSeekBarChangeListener(
                        new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress,
                                                          boolean fromUser) {
                                double value = min + (progress * step);
                                progressSeek = progress;
                            }
                        }
                );

                lowgoaldisplay = (TextView) findViewById(R.id.lowgoalloaddata);
                lowgoaldisplay.setText(Integer.toString(lowgoalLoadsTele));

                geardisplay = (TextView) findViewById(R.id.textView6);
                geardisplay.setText(Integer.toString(gearsDeliveredTele));

                findViewById(R.id.lowgoalsub).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lowgoalLoadsTele--;
                        lowgoaldisplay.setText(Integer.toString(lowgoalLoadsTele));
                    }
                });

                findViewById(R.id.lowgoaladd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lowgoalLoadsTele++;
                        lowgoaldisplay.setText(Integer.toString(lowgoalLoadsTele));
                    }
                });

                findViewById(R.id.gearsadd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gearsDeliveredTele++;
                        geardisplay.setText(Integer.toString(gearsDeliveredTele));
                    }
                });

                findViewById(R.id.gearssub).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gearsDeliveredTele--;
                        geardisplay.setText(Integer.toString(gearsDeliveredTele));
                    }
                });

                findViewById(R.id.sendbutton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GenerateQRString();
                        setContentView(R.layout.popup);
                        popupqrscreen();
                    }
                });
            }}

//Segregated popup from scout screen
public void popupqrscreen()
{
    QRImageView = (ImageView) findViewById(R.id.imageView2);
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    try {
        BitMatrix bitMatrix = multiFormatWriter.encode(QRStr, BarcodeFormat.QR_CODE, 400, 400);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        QRImageView.setImageBitmap(bitmap);
    } catch (WriterException e) {
        e.printStackTrace();
    }

    findViewById(R.id.button_No).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_main);
            popupscouttScreen();
        }
    });

    findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_main);
            ResetMatch();
            popupscouttScreen();
        }
    });
}
        public void ResetMatch() {
            //gets next match number and team number NB
            MATCH_NUMBER++;
            TextView matchDisplay = (TextView) findViewById(R.id.matchdata);
            matchDisplay.setText(Integer.toString(MATCH_NUMBER));
            
            TEAM_NUMBER = allTeamNums[MATCH_NUMBER][SCOUT_ID]; //gets team from list generated from text file
            TextView teamDisplay = (TextView) findViewById(R.id.teamdata);
            teamDisplay.setText(Integer.toString(TEAM_NUMBER));
            
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

            //completed remaining fields reset
            gearsDeliveredTele = 0;
            lowgoalLoadsTele = 0;

            EditText highgoalsauto = (EditText) findViewById(R.id.editText6);
            highgoalsauto.setText("");

            EditText highgoalsdel = (EditText) findViewById(R.id.highteledata);
            highgoalsdel.setText("");

            EditText notes = (EditText) findViewById(R.id.editText2);
            notes.setText("");

            EditText scoutname = (EditText) findViewById(R.id.editText3);
            scoutname.setText("");
        }



        //gets all the team numbers from text file and
        public int[][] getTeamNums()
        {
            try{
                InputStream stream = getAssets().open("teams_matches");
                int size = stream.available();
                byte[] buffer = new byte[size];
                stream.read(buffer);
                stream.close();
                teamText = new String(buffer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            Scanner scan = new Scanner(teamText);
            int numLines = countLines(teamText);
            numMatches = numLines;
            int[][] allTeams = new int[numLines][6];
            for(int j=0; j<numLines; j++)
            {
                for(int k=0; k<6; k++)
                {
                    allTeams[j][k] = scan.nextInt();
                }
            }
            return allTeams;
        }

        private static int countLines(String str){
            String[] lines = str.split("\r\n|\r|\n");
            return  lines.length;
        }
            };
