package com.nascenia.biyeta.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nascenia.biyeta.Manifest;
import com.nascenia.biyeta.R;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class ImageChoose extends Activity {
    GridView images;
    ArrayList<String> imagesArray;
    private static final int CAMERA_REQUEST = 1888;
    public Bitmap[] bitmapArray;
    FrameLayout progressBarHolder;
    ProgressBar progressBar;

    ImageView back;

    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_choose);
        images = (GridView) findViewById(R.id.gridview_android_example);

        progressBarHolder = (FrameLayout) findViewById(R.id.frame_layout);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new MyTask().execute();

        images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                if(position==0){
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = getFile();
                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                    startActivityForResult(camera_intent,CAMERA_REQUEST);
                }
                else{
                    Intent i = new Intent(getApplicationContext(), SelectedImageActivity.class);
                    i.putExtra("image_url", imagesArray.get(position - 1));
                    startActivity(i);
                }
                //Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected", Toast.LENGTH_LONG).show();
            }
        });


    }



    private class MyTask extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            images.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Boolean status) {
            super.onPostExecute(status);
            progressBar.setVisibility(View.GONE);
            images.setVisibility(View.VISIBLE);
            images.setAdapter(new ImageAdapterGridView(ImageChoose.this));
        }

        protected Boolean doInBackground(Void... params) {
            imagesArray = getFilePaths();
            bitmapArray = new Bitmap[imagesArray.size()+1];
            Bitmap icon= BitmapFactory.decodeResource(ImageChoose.this.getResources(),
                    R.drawable.camera_icon);
            bitmapArray[0] = icon;
            for(int position=0;position<imagesArray.size();position++)
            {
                File imgFile = new  File(imagesArray.get(position));
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    Bitmap bitmap;
                    int width = myBitmap.getWidth();
                    int height = myBitmap.getHeight();

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int screenWidth = metrics.widthPixels;
                    if(width >= height)
                    {
                        bitmap = Bitmap.createScaledBitmap(myBitmap,(screenWidth*5)/16,((height*((screenWidth*5)/16))/width),true);
                    }
                    else{
                        bitmap = Bitmap.createScaledBitmap(myBitmap,((width*((screenWidth*5)/16))/height),(screenWidth*5)/16,true);
                    }

                    bitmapArray[position+1] = bitmap;
                }
            }
            return true;
        }
    }


    private File getFile()
    {
        File folder = new File("sdcard/camera_app");
        if(!folder.exists())
        {
            folder.mkdir();
        }
        File image_file = new File(folder,"cam_image.jpg");
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/camera_app/cam_image.jpg";

        if(resultCode == RESULT_OK){
            Intent i = new Intent(getApplicationContext(), SelectedImageActivity.class);
            i.putExtra("image_url", path);
            startActivity(i);
        }
    }

    public ArrayList<String> getFilePaths()
    {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;
        if(u != null)
        {
            c = managedQuery(u, projection, null, null, null);
        }

        if ((c != null) && (c.moveToFirst()))
        {
            do
            {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try{
                    dirList.add(tempDir);
                }
                catch(Exception e)
                {

                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);

        }

        for(int i=0;i<dirList.size();i++)
        {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if(imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if(imagePath.isDirectory())
                    {
                        imageList = imagePath.listFiles();

                    }
                    if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                            )
                    {



                        String path= imagePath.getAbsolutePath();
                        resultIAV.add(path);

                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return resultIAV;


    }


    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;




        }

        public int getCount() {
            return imagesArray.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null){
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int width = metrics.widthPixels;
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams((width*5)/16, (width*5)/16));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                mImageView.setBackgroundColor(Color.rgb(0, 0, 0));
            } else{
                mImageView = (ImageView) convertView;
            }

            for(int i=0; i < bitmapArray.length;i++)
                mImageView.setImageBitmap(bitmapArray[position]);
            return mImageView;
        }
    }
}
