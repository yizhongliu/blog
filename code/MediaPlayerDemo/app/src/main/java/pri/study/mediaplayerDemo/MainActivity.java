package pri.study.mediaplayerDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;

    private OnCompletionListener onCompletionListener;
    private OnErrorListener onErrorListener;
    private OnPreparedListener onPreparedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initMediaPlayer();
        initSurfaceView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mediaPlayer.reset();
        mediaPlayer.release();
    }

    private void initView() {
        surfaceView = findViewById(R.id.surfaceView);
    }

    private void initMediaPlayer() {
        onCompletionListener = new OnCompletionListener();
        onErrorListener = new OnErrorListener();
        onPreparedListener = new OnPreparedListener();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        mediaPlayer.setOnErrorListener(onErrorListener);
        mediaPlayer.setOnPreparedListener(onPreparedListener);
    }

    private void initSurfaceView() {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(holder);
                preparePlayer();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    public void preparePlayer() {
        File file = new File(getExternalFilesDir(null), "test.mp4");
        String path = file.getAbsolutePath();
        Log.e("test", "Path:" + path);

        try {
            mediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.prepareAsync();
    }


    public class OnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();
        }
    }

    public class OnErrorListener implements MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }
    }

    public class OnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    }
}
