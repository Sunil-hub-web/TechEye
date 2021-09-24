package com.example.techeye.subscriber.geofence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techeye.R;
import com.example.techeye.subscriber.SharedPrefManager;

public class VideoCalling extends AppCompatActivity {

    String name, mobile, email;

    TextView text_FullName, text_MobileNo, text_Name, text_email;
    ImageView image_click, image_wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_video_calling);

        text_FullName = findViewById (R.id.fullname);
        text_MobileNo = findViewById (R.id.mobile);
        text_Name = findViewById (R.id.name);
        image_click = findViewById (R.id.click);
        image_wrong = findViewById (R.id.wrong);

       /* //text_email = findViewById (R.id.email);

        // Initialize default options for Jitsi Meet conferences.
        URL serverURL;
        try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            serverURL = new URL ("https://meet.jit.si");
        } catch (MalformedURLException e) {
            e.printStackTrace ( );
            throw new RuntimeException ("Invalid server URL!");
        }
        JitsiMeetConferenceOptions defaultOptions
                = new JitsiMeetConferenceOptions.Builder ( )
                .setServerURL (serverURL)
                // When using JaaS, set the obtained JWT here
                //.setToken("MyJWT")
                .setWelcomePageEnabled (false)
                .build ( );
        JitsiMeet.setDefaultConferenceOptions (defaultOptions);

        name = SharedPrefManager.getInstance (VideoCalling.this).getUser ( ).getName ( );
        mobile = SharedPrefManager.getInstance (VideoCalling.this).getUser ( ).getMobileno ( );
        //email = SharedPrefManager.getInstance (VideoCalling.this).getUser ( ).getEmail ( );
        text_FullName.setText (name);
        text_MobileNo.setText (mobile);
        //text_email.setText (email);

        char str = name.charAt (0);
        text_Name.setText (String.valueOf (str));


        image_click.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                String text = text_MobileNo.getText ( ).toString ( );

                if (text.length ( ) > 0) {
                    // Build options object for joining the conference. The SDK will merge the default
                    // one we set earlier and this one when joining.
                    JitsiMeetConferenceOptions options
                            = new JitsiMeetConferenceOptions.Builder ( )
                            .setRoom (text)
                            .build ( );
                    // Launch the new activity with the given options. The launch() method takes care
                    // of creating the required Intent and passing the options.
                    JitsiMeetActivity.launch (VideoCalling.this, options);
                }
            }

        });

        image_wrong.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (VideoCalling.this, DirectionMapForGeofence.class);
                startActivity (intent);
            }
        });

*/
    }
}