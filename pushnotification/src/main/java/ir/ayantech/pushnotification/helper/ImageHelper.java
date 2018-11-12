package ir.ayantech.pushnotification.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ir.ayantech.pushnotification.R;

public class ImageHelper {

    public static void downloadImage(String url, OnBitmapDownloaded onBitmapDownloaded) {
        new DownloadImage(onBitmapDownloaded).execute(url);
    }

    private static Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        private OnBitmapDownloaded onBitmapDownloaded;

        public DownloadImage(OnBitmapDownloaded onBitmapDownloaded) {
            this.onBitmapDownloaded = onBitmapDownloaded;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return getBitmapFromURL(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            onBitmapDownloaded.onBitmapDownloaded(bitmap);
        }
    }

    public interface OnBitmapDownloaded {
        void onBitmapDownloaded(Bitmap bitmap);
    }
}
