package me.sanesh.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import me.sanesh.healthcareapp.models.Text;
import me.sanesh.healthcareapp.utilities.ReplyInterface;
import me.sanesh.healthcareapp.utilities.ChatAdapter;
import me.sanesh.healthcareapp.utilities.TextSend;

public class ChatbotActivity extends AppCompatActivity implements ReplyInterface {

    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    List<Text> messageList = new ArrayList<>();
    EditText txtText;
    ImageButton sendButton;
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private String uuid = UUID.randomUUID().toString();
    private String TAG = "ChatbotActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);


        recyclerView = findViewById(R.id.chatView);
        txtText = findViewById(R.id.editMessage);
        sendButton = findViewById(R.id.btnSend);

        chatAdapter = new ChatAdapter(messageList, this);
        recyclerView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = txtText.getText().toString();
                if (!message.isEmpty()) {
                    messageList.add(new Text(message, false));
                    txtText.setText("");
                    sendTextToChatBot(message);
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                    Objects.requireNonNull(recyclerView.getLayoutManager())
                            .scrollToPosition(messageList.size() - 1);
                } else {
                    Toast.makeText(ChatbotActivity.this, "Please enter text!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chatBotSetUp();
    }


    private void chatBotSetUp() {
        try {
            InputStream stream = this.getResources().openRawResource(R.raw.credential);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);

            Log.d(TAG, "projectId : " + projectId);
        } catch (Exception e) {
            Log.d(TAG, "setUpBot: " + e.getMessage());
        }
    }

    private void sendTextToChatBot(String message) {
        QueryInput input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
        new TextSend(sessionName, sessionsClient, input,this).execute();
    }

    @Override
    public void getChatBotReplyText(DetectIntentResponse returnResponse) {
        if (returnResponse != null) {
            String botReply = returnResponse.getQueryResult().getFulfillmentText();
            if (!botReply.isEmpty()) {
                messageList.add(new Text(botReply, true));
                chatAdapter.notifyDataSetChanged();
                Objects.requireNonNull(recyclerView.getLayoutManager()).scrollToPosition(messageList.size() - 1);
            } else {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "failed to connect!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}