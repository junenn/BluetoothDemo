package com.jash.bluetoothdemo;

import android.bluetooth.BluetoothDevice;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private BluetoothDevice device;
    private ChatClient client;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        device = getIntent().getParcelableExtra("device");
        if (TextUtils.isEmpty(device.getName())) {
            setTitle("没有名字");
        } else {
            setTitle(device.getName());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        client = SocketThread.getClient(device);
        edit = ((EditText) findViewById(R.id.chat_edit));
        findViewById(R.id.chat_send).setOnClickListener(this);
        if (client == null){
            Toast.makeText(this, "此设备不支持聊天", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat_send:
                String s = edit.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    client.send(s);
                    edit.setText("");
                }
                break;
        }
    }
}
