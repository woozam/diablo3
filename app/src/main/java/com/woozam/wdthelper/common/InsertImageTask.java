package com.woozam.wdthelper.common;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by woozam on 2016-02-07.
 */
public class InsertImageTask extends AsyncTask<Void, Void, Void> {

    private String mTitle;
    private String mDescription;
    private Bitmap mBitmap;

    public InsertImageTask(String title, String description, Bitmap bitmap) {
        mTitle = title;
        mDescription = description;
        mBitmap = bitmap;
    }

    @Override
    protected Void doInBackground(Void... params) {
        insertImage(mBitmap, mTitle, mDescription);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(D3Application.getContext(), R.string.save_to_gallery_done, Toast.LENGTH_LONG).show();
    }

    public static void insertImage(Bitmap source, String title, String description) {
        title += ".jpg";
        String path = Environment.getExternalStorageDirectory().toString() + "/" + D3Application.getContext().getString(R.string.app_name) + "/";
        OutputStream fOut;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path, title);
        try {
            file.createNewFile();
            fOut = new FileOutputStream(file);
            source.compress(Bitmap.CompressFormat.JPEG, 95, fOut);
            fOut.flush();
            fOut.close();
            addImageToGallery(D3Application.getContext(), path + title, title, description);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Uri addImageToGallery(Context context, String filepath, String title, String description) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filepath);
        return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}