package com.ultimate.chatgroupmahasiswa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class ChatActivity extends AppCompatActivity {
    public static String TAG = "FirebaseUI.chat";
    private Firebase mRef;
    private Query mChatRef;
    private long userId;
    private String mName;
    private String mTime;
    private Button mSendButton;
    private EditText mMessageEdit;
    private int pos;
    private RecyclerView mMessages;
    private FirebaseRecyclerAdapter<ChatModel, ChatHolder> mRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("ChatServicesGroup",MODE_PRIVATE);
        String nama = sharedPreferences.getString("nama","");
        final String nim = sharedPreferences.getString("nim","");
        userId = Long.parseLong(nim);
        mName = nama;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Firebase.setAndroidContext(this);

        mSendButton = (Button) findViewById(R.id.sendButton);
        mMessageEdit = (EditText) findViewById(R.id.messageEdit);

      mSendButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
          }
      });

        String pilihan = getIntent().getExtras().getString("value");
        String link ="";
        ActionBar action = getSupportActionBar();
        switch(pilihan){
            case "job":
                link = "https://realtimechat-service.firebaseio.com/job";
                setTitle("Group JobVacancy");
                break;
            case "alumni":
                link = "https://realtimechat-service.firebaseio.com/alumni";
                setTitle("Group Alumni");
                break;
            case "kelas":
                link = "https://realtimechat-service.firebaseio.com/kelas";
                setTitle("Group Kelas");
                break;
            case "lounge":
                link = "https://realtimechat-service.firebaseio.com/lounge";
                setTitle("Group Lounge");
                break;
        }

        mRef = new Firebase(link);
        mChatRef = mRef.limitToLast(50);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel chat = new ChatModel(mMessageEdit.getText().toString(), mName, userId, System.currentTimeMillis(), mTime);
                mRef.push().setValue(chat, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Log.e(TAG, firebaseError.toString());
                        }
                    }
                });
                mMessageEdit.setText("");
            }
        });

        mMessages = (RecyclerView) findViewById(R.id.messagesList);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);

        mMessages.setHasFixedSize(false);
        mMessages.setLayoutManager(manager);

        mRecycleViewAdapter = new FirebaseRecyclerAdapter<ChatModel, ChatHolder>(ChatModel.class, R.layout.text_message, ChatHolder.class, mChatRef) {
            @Override
            public void populateViewHolder(ChatHolder chatView, ChatModel chat, int position) {
                chatView.setText(chat.getMessage());
                chatView.setName(chat.getName());
                chatView.setTime(chat.getFormattedTime());


                if (chat.getUserId() == Long.parseLong(nim)) {
                    chatView.setIsSender(true);
                } else {
                    chatView.setIsSender(false);
                }
            }
        };
        //SCROLL OTOMATIS
        mRecycleViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                    int friendlyMessageCount = mRecycleViewAdapter.getItemCount();
                int lastVisiblePosition = manager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessages.scrollToPosition(positionStart);
                }
            }
        });

        mMessages.setAdapter(mRecycleViewAdapter);
    }

    public static class ChatHolder extends RecyclerView.ViewHolder {
        View mView;

        public ChatHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setIsSender(Boolean isSender) {
            FrameLayout left_arrow = (FrameLayout) mView.findViewById(R.id.left_arrow);
            FrameLayout right_arrow = (FrameLayout) mView.findViewById(R.id.right_arrow);
            RelativeLayout messageContainer = (RelativeLayout) mView.findViewById(R.id.message_container);
            LinearLayout message = (LinearLayout) mView.findViewById(R.id.message);
            if (isSender) {
                left_arrow.setVisibility(View.GONE);
                right_arrow.setVisibility(View.VISIBLE);
                messageContainer.setGravity(Gravity.RIGHT);
            } else {
                left_arrow.setVisibility(View.VISIBLE);
                right_arrow.setVisibility(View.GONE);
                messageContainer.setGravity(Gravity.LEFT);
            }
        }

        public void setName(String name) {
            TextView field = (TextView) mView.findViewById(R.id.name_text);
            field.setText(name);
        }

        public void setText(String text) {
            TextView field = (TextView) mView.findViewById(R.id.message_text);
            field.setText(text);
        }

        public void setTime(String time){
            TextView field = (TextView) mView.findViewById(R.id.time_text);
            field.setText(time);
        }
    }
}
