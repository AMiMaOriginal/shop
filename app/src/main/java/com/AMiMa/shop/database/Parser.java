package com.AMiMa.shop.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    String link = "https://edostavka.by/category/5023?lc=3";
    List<Bitmap> bitmaps = new ArrayList<>();
    List<String> descriptions = new ArrayList<>();
    List<String> names = new ArrayList<>();

    public void connect(){
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(link).get();
                    Elements elements = document.getElementsByClass("card-image_image__a8P4T vertical_image__Dsd8F");
                    for (int i = 0; i < elements.size(); i++){
                        String imageURL = elements.get(i).attr("src");
                        URL url = new URL(imageURL);
                        InputStream input = url.openStream();
                        bitmaps.add(BitmapFactory.decodeStream(input));

                        String d = elements.get(i).attr("alt");
                        descriptions.add(d);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        try {
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getNames();
    }

    private void getNames(){
        for (int i = 0; i < descriptions.size(); i++){
            String[] description = descriptions.get(i).split(" ");
            String name = description[0];
            names.add(name);
        }
        for (int i = 0; i < names.size(); i++){
            Log.d("log", names.get(i));
        }
    }
}

