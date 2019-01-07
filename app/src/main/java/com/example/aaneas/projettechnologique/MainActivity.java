package com.example.aaneas.projettechnologique;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.aaneas.afficherimagenew.ScriptC_brightness;
import com.example.aaneas.afficherimagenew.ScriptC_colorize;
import com.example.aaneas.afficherimagenew.ScriptC_colorkeep;
import com.example.aaneas.afficherimagenew.ScriptC_grey;
import com.example.aaneas.afficherimagenew.ScriptC_invert;
import com.example.aaneas.projettechnologique.R;


// plutôt que de commenter le code, j'ai décidé de l'expliquer dans le rapport pdf

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    long middle_time = 0;

    void toGray(Bitmap Bmp) {
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int c = Bmp.getPixel(i, j);
                int r = Color.red(c);
                int g = Color.green(c);
                int b = Color.blue(c);
                int avgcolor = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                Bmp.setPixel(i, j, (Color.rgb(avgcolor, avgcolor, avgcolor)));
            }
        }
    }

    void toGray2(Bitmap Bmp) {
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w * h];
        int c = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        int avgcolor = 0;
        Bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            c = pixels[i];
            r = Color.red(c);
            g = Color.green(c);
            b = Color.blue(c);
            avgcolor = (int) (0.3 * r + 0.59 * g + 0.11 * b);
            pixels[i] = Color.rgb(avgcolor, avgcolor, avgcolor);
        }
        Bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    void colorkeep(Bitmap Bmp, double rand) {
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w * h];
        int c = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        int avgcolor = 0;
        Bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            c = pixels[i];
            r = Color.red(c);
            g = Color.green(c);
            b = Color.blue(c);
            if ((rand < 0.33 && (r < 80 || g > 80 || b > 100)) || ((rand >= 0.33 && rand < 0.66) && (r > 120 || g < 80 || b > 120)) || (rand >= 0.66 && (r > 120 || g > 170 || b < 100))) {
                avgcolor = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                pixels[i] = Color.rgb(avgcolor, avgcolor, avgcolor);
            }

        }
        Bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    void colorize(Bitmap Bmp, double rand) {
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w * h];
        int c = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        //int avgcolor = 0;
        rand = rand * (double) 360;
        float[] hsv = new float[3];
        Bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            c = pixels[i];
            r = Color.red(c);
            g = Color.green(c);
            b = Color.blue(c);
            Color.RGBToHSV(r, g, b, hsv);
            hsv[0] = (float) rand;
            pixels[i] = Color.HSVToColor(hsv);
        }
        Bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    private void changeBrightnessRS(Bitmap bmp) {
//1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
//2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());
//3)  Creer le  script
        ScriptC_brightness brightnessScript = new ScriptC_brightness(rs);
//4)  Copier  les  donnees  dans  les  Allocations
// ...
//5)  Initialiser  les  variables  globales  potentielles
// ...
//6)  Lancer  le noyau

        brightnessScript.forEach_changeBrightness(input, output);
//7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
//8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy();
        output.destroy();
        brightnessScript.destroy();
        rs.destroy();
    }

    private void toGreyRS(Bitmap bmp) {
//1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
//2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());
//3)  Creer le  script
        ScriptC_grey greyScript = new ScriptC_grey(rs);
//4)  Copier  les  donnees  dans  les  Allocations
// ...
//5)  Initialiser  les  variables  globales  potentielles
// ...
//6)  Lancer  le noyau

        greyScript.forEach_toGrey(input, output);
//7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
//8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy();
        output.destroy();
        greyScript.destroy();
        rs.destroy();
    }

    private void colorizeRS(Bitmap bmp, double rand) {
//1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
//2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());
//3)  Creer le  script
        ScriptC_colorize colorizeScript = new ScriptC_colorize(rs);
//4)  Copier  les  donnees  dans  les  Allocations
// ...
//5)  Initialiser  les  variables  globales  potentielles
        colorizeScript.set_h(rand * (double) 360);
//6)  Lancer  le noyau
        colorizeScript.forEach_colorize(input, output);
//7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
//8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy();
        output.destroy();
        colorizeScript.destroy();
        rs.destroy();
    }

    private void invertRS(Bitmap bmp) {
//1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
//2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());
//3)  Creer le  script
        ScriptC_invert invertScript = new ScriptC_invert(rs);
//4)  Copier  les  donnees  dans  les  Allocations
// ...
//5)  Initialiser  les  variables  globales  potentielles
// ...
//6)  Lancer  le noyau
        invertScript.forEach_invert(input, output);
//7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
//8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy();
        output.destroy();
        invertScript.destroy();
        rs.destroy();
    }

    private void colorkeepRS(Bitmap bmp, double rand) {
//1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
//2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());
//3)  Creer le  script
        ScriptC_colorkeep colorkeepScript = new ScriptC_colorkeep(rs);
//4)  Copier  les  donnees  dans  les  Allocations
// ...
//5)  Initialiser  les  variables  globales  potentielles
        colorkeepScript.set_rand(rand);
//6)  Lancer  le noyau
        colorkeepScript.forEach_colorkeep(input, output);
//7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
//8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy();
        output.destroy();
        colorkeepScript.destroy();
        rs.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long start_time = System.nanoTime();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imViewColor = (ImageView) findViewById(R.id.imageViewWhite);
        ImageView imViewBlack = (ImageView) findViewById(R.id.imageViewBlack);
        ImageView imViewWhite = (ImageView) findViewById(R.id.imageViewColor);
        ImageView imViewMononoke = (ImageView) findViewById(R.id.imageViewMononoke);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;
        o.inMutable = true;
        final Bitmap imageMononoke = BitmapFactory.decodeResource(getResources(), R.drawable.mononokehime, o);
        imViewMononoke.setImageBitmap(imageMononoke);
        final Bitmap imageColor = BitmapFactory.decodeResource(getResources(), R.drawable.color, o);
        imViewColor.setImageBitmap(imageColor);
        final Bitmap imageBlack = BitmapFactory.decodeResource(getResources(), R.drawable.colorblack, o);
        imViewBlack.setImageBitmap(imageBlack);
        final Bitmap imageWhite = BitmapFactory.decodeResource(getResources(), R.drawable.colorwhite, o);
        imViewWhite.setImageBitmap(imageWhite);
        middle_time = System.nanoTime();
        System.out.println("middle_time : " + (middle_time - start_time));
        Spinner monSpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> monadaptater = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Monspinner));
        monadaptater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monSpinner.setAdapter(monadaptater);
        monSpinner.setOnItemSelectedListener(this);
    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        ImageView imViewColor = (ImageView) findViewById(R.id.imageViewWhite);
        ImageView imViewBlack = (ImageView) findViewById(R.id.imageViewBlack);
        ImageView imViewWhite = (ImageView) findViewById(R.id.imageViewColor);
        ImageView imViewMononoke = (ImageView) findViewById(R.id.imageViewMononoke);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;
        o.inMutable = true;
        final Bitmap imageMononoke = BitmapFactory.decodeResource(getResources(), R.drawable.mononokehime, o);
        imViewMononoke.setImageBitmap(imageMononoke);
        final Bitmap imageColor = BitmapFactory.decodeResource(getResources(), R.drawable.color, o);
        imViewColor.setImageBitmap(imageColor);
        final Bitmap imageBlack = BitmapFactory.decodeResource(getResources(), R.drawable.colorblack, o);
        imViewBlack.setImageBitmap(imageBlack);
        final Bitmap imageWhite = BitmapFactory.decodeResource(getResources(), R.drawable.colorwhite, o);
        imViewWhite.setImageBitmap(imageWhite);
        double rand = Math.random();
        switch (pos) {

            case 0:
                break;
            case 1:
                toGray(imageColor);
                toGray(imageBlack);
                toGray(imageWhite);
                toGray(imageMononoke);
                break;
            case 2:
                toGray2(imageColor);
                toGray2(imageBlack);
                toGray2(imageWhite);
                toGray2(imageMononoke);
                break;
            case 3:
                toGreyRS(imageColor);
                toGreyRS(imageBlack);
                toGreyRS(imageWhite);
                toGreyRS(imageMononoke);
                break;
            case 4:
                colorize(imageColor, rand);
                colorize(imageBlack, rand);
                colorize(imageWhite, rand);
                colorize(imageMononoke, rand);
                break;
            case 5:
                colorizeRS(imageColor, rand);
                colorizeRS(imageBlack, rand);
                colorizeRS(imageWhite, rand);
                colorizeRS(imageMononoke, rand);
                break;
            case 6:
/*
                        invert(imageColor);
                        invert(imageBlack);
                        invert(imageWhite);
                        invert(imageMononoke);
*/
                break;
            case 7:
                invertRS(imageColor);
                invertRS(imageBlack);
                invertRS(imageWhite);
                invertRS(imageMononoke);
                break;
            case 8:
                    /*
                    changeBrightness(imageColor);
                    changeBrightness(imageBlack);
                    changeBrightness(imageWhite);
                    changeBrightness(imageMononoke);
                    */
                break;
            case 9:
                changeBrightnessRS(imageColor);
                changeBrightnessRS(imageBlack);
                changeBrightnessRS(imageWhite);
                changeBrightnessRS(imageMononoke);
                break;
            case 10:
                colorkeep(imageColor, rand);
                colorkeep(imageBlack, rand);
                colorkeep(imageWhite, rand);
                colorkeep(imageMononoke, rand);
                break;
            case 11:
                colorkeepRS(imageColor, rand);
                colorkeepRS(imageBlack, rand);
                colorkeepRS(imageWhite, rand);
                colorkeepRS(imageMononoke, rand);
                break;

        }
        System.out.println("final time : " + (System.nanoTime() - middle_time));

    }


    public void onNothingSelected(AdapterView<?> parent) {

    }

}