package manu.com.iocsample;

import androidx.appcompat.app.AppCompatActivity;
import manu.com.api.Bind;
import manu.com.bind.BindKnife;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.tvData)
    TextView tvData;
    @Bind(R.id.tvMessage)
    TextView tvMessage;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindKnife.bind(this);
        Log.d(TAG, "onCreate:" + (tvData == null));
        Log.d(TAG, "onCreate:" + (tvMessage == null));
        tvData.setText("data");
        tvMessage.setText("message");
    }


}
