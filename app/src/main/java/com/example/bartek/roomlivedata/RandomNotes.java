package com.example.bartek.roomlivedata;

import android.content.Context;

import com.example.bartek.roomlivedata.database.NoteModel;

import java.util.Random;

public class RandomNotes {
    private String loremIpsum;
    private String[] titles = {"Apple", "Banana", "Orange", "Cheese", "Pepperoni", "Black Olives"};
    private Random random;

    public RandomNotes(Context context) {
        random = new Random();
        loremIpsum = context.getString(R.string.loremIpsum);

    }


    public NoteModel generate(){
        int randomDesc = random.nextInt((int)loremIpsum.length()/2) + 0;
        int randomTitles = random.nextInt(titles.length);
        return new NoteModel(titles[randomTitles], loremIpsum.substring(randomDesc, randomDesc + loremIpsum.length()/2));
    }
}
