package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.onemobilekidz.mobilekidz.model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class SendMessage extends Activity {


    private static final String LOG = "SendMessage";
    private String friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Firebase.setAndroidContext(this);

        Intent intent = getIntent();
        friendId = intent.getStringExtra("friendId");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(final View view) {
        EditText messageView = (EditText) findViewById(R.id.messageText);
        String messageText = messageView.getText().toString();
        String userId = UserModel.getCurrentUser().getUserId();

        Firebase messageRef = new Firebase(Constants.FIREBASE_URL).child("messages").child(friendId);
        Map<String, String> message = new HashMap<String, String>();
        message.put("sender", userId);
        message.put("message", messageText);
        Firebase ref = messageRef.push();
        ref.setValue(message,
                new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Message could not be saved. " + firebaseError.getMessage());
                        } else {
                            Toast.makeText(view.getContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Intent intent = new Intent(this, Friends.class);
        this.startActivity(intent);

    }
}
