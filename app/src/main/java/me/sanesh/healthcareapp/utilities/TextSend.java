package me.sanesh.healthcareapp.utilities;


import android.os.AsyncTask;
import android.util.Log;

import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;


public class TextSend extends AsyncTask<Void, Void, DetectIntentResponse> {

    private SessionName session;
    private SessionsClient sessionsClient;
    private QueryInput queryInput;
    private String TAG = "async";
    private ReplyInterface replyInterface;

    public TextSend(SessionName session, SessionsClient sessionsClient, QueryInput queryInput, ReplyInterface replyInterface) {
        this.session = session;
        this.sessionsClient = sessionsClient;
        this.queryInput = queryInput;
        this.replyInterface = replyInterface;
    }

    @Override
    protected DetectIntentResponse doInBackground(Void... voids) {
        try {
            DetectIntentRequest detectIntentRequest =
                    DetectIntentRequest.newBuilder()
                            .setSession(session.toString())
                            .setQueryInput(queryInput)
                            .build();
            return sessionsClient.detectIntent(detectIntentRequest);
        } catch (Exception e) {
            Log.d(TAG, "doInBackground: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(DetectIntentResponse response) {
        //handle return response here
        replyInterface.getChatBotReplyText(response);
    }

}
