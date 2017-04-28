package filip.chessboard;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConversationActivity extends AppCompatActivity {
    static Map<String, String> conversations = new HashMap<>();
    String name;
    EditText conversationEditText;
    AutoCompleteTextView conversationSendText;
    Button sendButton;
    String htmlNewLine = "<br>";
    String userTextSetting = "<div align=\"left\"><font color='red'>";
    String otherTextSetting = "<div align=\"right\"><font color='green'>";
    String colorEnd = "</font></div>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        getName();
        checkName();
        setConversationStart();
        setStartingViews();
        setMessage("");
        startListening();
    }

    private void getName() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            name = extras.getString("name");
        } else {
            name = "";
        }
    }

    private void checkName() {
        if (Objects.equals(name, "")) this.finish();
    }

    private void setConversationStart() {
        if (!conversations.containsKey(name))
            conversations.put(name, "");
    }

    private void setStartingViews() {
        setNameTextView();
        setConversationEditText();
        setSendButton();
    }

    private void setNameTextView() {
        TextView nameTextView = (TextView) findViewById(R.id.NameTextView);
        nameTextView.setText(name);
    }

    private void setConversationEditText() {
        conversationEditText = (EditText) findViewById(R.id.ConversationEditText);
        conversationEditText.setMovementMethod(new ScrollingMovementMethod());
        conversationSendText = (AutoCompleteTextView) findViewById(
                R.id.ConversationAutoCompleteTextView);
    }

    private void setSendButton() {
        sendButton = (Button) findViewById(R.id.SendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { sendButtonClick(); }
        });
    }

    private void startListening() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setNewMessage();
                handler.postDelayed(this, 2000);
            }
        }, 2000);
    }

    private void setNewMessage() {
        final String newMessage = WebCommunication.WebAgent.getNewMessage(name);
        if (!Objects.equals(newMessage, "")) {
            setMessageAndScroll(newMessage);
        }
    }

    private void sendButtonClick() {
        String result = WebCommunication.WebAgent.sendMessage(
                name, createMessage(otherTextSetting));
        if (Objects.equals(result, "OK")) {
            setMessageAndScroll(createMessage(userTextSetting));
        } else {
            showAlert(result);
        }
    }

    private String createMessage(String setting) {
        return "\n" + setting + WebCommunication.WebAgent.mLogin + ": " +
                conversationSendText.getText() + colorEnd;
    }

    private void setMessageAndScroll(String message) {
        setMessage(message);
        scrollDown();
    }

    private void setMessage(String message) {
        conversations.put(name, conversations.get(name) + message);
        if (Objects.equals(conversations.get(name), ""))
            return;
        conversations.put(name, conversations.get(name).replace("\n", htmlNewLine));
        conversationEditText.setText(
                Html.fromHtml(conversations.get(name)), TextView.BufferType.SPANNABLE);
    }

    private void scrollDown() {
        conversationEditText.post(new Runnable() {
            @Override public void run() { conversationEditText.scrollBy(0, 0); }
        });
    }

    private void showAlert(String result) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Failed to create game");
        alert.setMessage(result);
        alert.setPositiveButton("OK", null);
        runOnUiThread(new Runnable() {
            @Override public void run() { alert.show(); }
        });
    }
}
