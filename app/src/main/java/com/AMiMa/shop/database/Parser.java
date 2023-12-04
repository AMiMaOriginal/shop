package com.AMiMa.shop.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.AMiMa.shop.database.dataClasses.Product;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    String link = "https://edostavka.by/category/5023?lc=3";
    List<String> linksImage = new ArrayList<>();
    List<String> descriptions = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<String> prices = new ArrayList<>();
    Elements elements = null;

    public void connect(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    Document document = Jsoup.connect(link).get();

                    elements = document.getElementsByClass("price_main__5jwcE");
                    for (int i = 0; i < 6; i++){
                        String[] string = elements.get(i).text().split("р");
                        String price = string[0].replace(',', '.');
                        prices.add(price);
                    }

                    elements = document.getElementsByClass("card-image_image__a8P4T vertical_image__Dsd8F");
                    for (int i = 0; i < elements.size(); i++){
                        linksImage.add(elements.get(i).attr("src"));

                        String description = elements.get(i).attr("alt");
                        descriptions.add(description);

                        count++;
                        if (count==5){
                            break;
                        }
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
    }

    public List<Product> getProduct() {
        connect();
        for (int i = 0; i < linksImage.size(); i++){
            products.add(new Product(descriptions.get(i), Double.parseDouble(prices.get(i)), "бульба", linksImage.get(i), 1, 0));
        }
        return products;
    }
}