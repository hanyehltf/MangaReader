package com.project.mangareader.profileInformation.dummy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.R;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


public class AddMangaFragment extends Fragment {
    private EditText m_name;
    private EditText m_writer;
    private Spinner genera_spinner;
    private String genera;
    private Button next_level;
    private Button load_cover;
    private Context context;
    private Manga manga;
    private ImageView cover;
    private Uri ImageUri;
    private String encodedImage;

    public AddMangaFragment(Context context) {
        // Required empty public constructor
        this.context = context;
        this.manga = new Manga();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_add_manga, container, false);
        m_name = view.findViewById(R.id.manga_name);
        m_writer = view.findViewById(R.id.manga_writer);
        next_level = view.findViewById(R.id.next_level_submit);
        load_cover = view.findViewById(R.id.add_manga_load_pic);
        genera_spinner = view.findViewById(R.id.manga_ganra);
        cover = view.findViewById(R.id.manga_cover);
        String[] genera1 = {"عاشقانه ", "جنایی", "معمایی ", "ترسناک "};
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, genera1);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        genera_spinner.setAdapter(arrayAdapter);
    load_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCover();
            }
        });

        next_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextlevel();
            }
        });


        return view;

    }

    private void nextlevel() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                genera = String.valueOf(genera_spinner.getSelectedItem());
                manga.setCover(encodedImage);
                manga.setName(m_name.getText().toString());
                manga.setWriter(m_writer.getText().toString());
                manga.setGenera(genera);
                AppCompatActivity activity = (AppCompatActivity) getContext();
                AddMangaImageFragment addMangaImageFragment = new AddMangaImageFragment(context, manga);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.addMangaFram, addMangaImageFragment, "Test Fragment").addToBackStack(null).commit();
            }
        };
        runnable.run();

    }

    private void setCover() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        };
        runnable.run();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        ImageUri = data.getData();
                        Bitmap bitmap;

                        bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(ImageUri));
                        cover.setImageBitmap(bitmap);
                        final int COMPRESSION_QUALITY = 0;
                        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                                byteArrayBitmapStream);
                        byte[] b = byteArrayBitmapStream.toByteArray();
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


                        Toast.makeText(context, "     بارگزاری موفقیعت امیز بود    " + ImageUri, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(context, "در بارگزاری عکس مشکلی پیش امده است ", Toast.LENGTH_LONG).show();
                    }
                }
            };
            r.run();

        }

    }
}