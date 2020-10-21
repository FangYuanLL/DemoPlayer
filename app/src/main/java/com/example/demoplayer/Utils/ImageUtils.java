package com.example.demoplayer.Utils;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.example.demoplayer.dummy.DummyContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageUtils {

    private static String TAG = "MainActivity";

    public static HashMap<Integer,ArrayList<Bitmap>> map = new HashMap<>();
    public static HashMap<Integer,Bitmap> firstmap = new HashMap<>();
    public static String videoPath = Environment.getExternalStorageDirectory().getPath()+"/videodemo.mp4";
    public static Bitmap getImage(int times){
        /*MediaMetadataRetriever retriever = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(videoPath).getAbsolutePath());
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(inputStream.getFD());
            //AssetFileDescriptor afd = getAssets().openFd(fileName);
            //retriever.setDataSource(inputStream.getFD(),);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = retriever.getFrameAtTime();
        retriever.release();
        */

        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoPath,
                MediaStore.Images.Thumbnails.MINI_KIND);
        return thumbnail;
    }

    public static void setHashMap(){
        Log.d(TAG,"enter setHashMap ");
        getFirstFrame();
    }

    public static void getFirstFrame(){
        for (int i=0;i< DummyContent.COUNT;i++){
            firstmap.put(i,getImage(1000));
        }
    }

    static class ImageTask implements Runnable{
        @Override
        public void run() {
            int id = Integer.valueOf(Thread.currentThread().getName());
            ArrayList<Bitmap> list= new ArrayList<>();
            list.add(getImage(2000));
            list.add(getImage(3000));
            list.add(getImage(4000));
            map.put(id,list);
        }
    }

}
