package me.sanesh.healthcareapp.utilities;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;

public interface ReplyInterface {

    void getChatBotReplyText(DetectIntentResponse returnResponse);
}
