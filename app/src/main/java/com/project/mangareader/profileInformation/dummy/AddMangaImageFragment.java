package com.project.mangareader.profileInformation.dummy;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.mangareader.DatabaseManagment.DataBaseControler;
import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AddMangaImageFragment extends Fragment {


    private Context context;
    private Manga manga;
    private Button load_images;
    private Button submit;
    private RecyclerView recyclerView;
    private View view;
    private List mArrayUri = new ArrayList();

    public AddMangaImageFragment(Context context, Manga manga) {
        this.context = context;
        this.manga = manga;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_manga_image, container, false);
        setView();
        getImages();
        submitimage();
        return view;
    }

    private void submitimage() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatainDB();
            }
        });
    }

    private void setDatainDB() {

        DataBaseControler dataBaseControler = new DataBaseControler(context);
        dataBaseControler.AddManga(manga);
        AppCompatActivity activity = (AppCompatActivity) getContext();
        AddMangaFragment addMangaFragment = new AddMangaFragment(context);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.addMangaFram, addMangaFragment, "Test Fragment").addToBackStack(null).commit();
    }

    private void getImages() {
        load_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && null != data) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {


                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();

                            Bitmap bitmap;

                            try {
                                bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                                final int COMPRESSION_QUALITY = 0;

                                ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                                        byteArrayBitmapStream);
                                byte[] b = byteArrayBitmapStream.toByteArray();
                                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                                mArrayUri.add(encodedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                        }
                        manga.setImages(mArrayUri);
                    }
                }
            };

            runnable.run();

            Runnable runnable1 = new Runnable() {
                @Override
                public void run() {
                    if (data.getData() != null) {

                        Bitmap bitmap;
                        Uri mImageUri = data.getData();
                        try {
                            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(mImageUri));
                            final int COMPRESSION_QUALITY = 0;
                            ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                                    byteArrayBitmapStream);
                            byte[] b = byteArrayBitmapStream.toByteArray();
                            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            manga.getImages().add(encodedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };
            runnable1.run();

        }


        setRecyclerview();
    }

    private void setRecyclerview() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setImagesRecyclerView setImagesRecyclerView = new setImagesRecyclerView(context, mArrayUri);
                recyclerView.setAdapter(setImagesRecyclerView);
            }
        };
        runnable.run();


    }

    private void pickImage() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }

    private void setView() {
        load_images = view.findViewById(R.id.load_slide_manga);
        submit = view.findViewById(R.id.submit_add_manga);
        recyclerView = view.findViewById(R.id.addimagerecyclerview);

    }


}