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


// commenter format javadoc
//penser à faire des tableaux pour la présentation

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

    void invert(Bitmap Bmp) {
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w * h];
        int c = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        Bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            c = pixels[i];
            pixels[i] = Color.rgb(255-Color.red(c), 255-Color.green(c), 255-Color.blue(c));
        }
        Bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    void changeBrightness(Bitmap Bmp) {
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w * h];
        int c = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        int BrightnessScale = 75;
        Bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            c = pixels[i];
            r = BrightnessScale+Color.red(c);
            if(r > 255) r = 255;
            g = BrightnessScale+Color.green(c);
            if(g > 255) g = 255;
            b = BrightnessScale+Color.blue(c);
            if(b > 255) b = 255;
            pixels[i] = Color.rgb(r,g,b);
        }
        Bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    void contrast(Bitmap Bmp) {
        int h = Bmp.getHeight();
        int w = Bmp.getWidth();
        int[] pixels = new int[w * h];
        int c = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        Bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        double max = 0.0;
        double min = 255.0;
        for (int i = 0; i < pixels.length; i++) {
            //System.out.println(Color.green(c));
            if(Color.green(pixels[i]) < min) min = Color.green(pixels[i]);
            if(Color.green(pixels[i]) > max) max = Color.green(pixels[i]);
        }
        if(min == 255.0) min--;
        if(max == 0.0) max++;
        for (int i = 0; i < pixels.length; i++) {
            c = pixels[i];
            r = (int) (255.0 * ((float) (Color.red(c) - min)) / (max - min));
            //System.out.println(min);
            //System.out.println(max);
            //System.out.println(r);
            if(r > 255) r = 255;
            else if (r < 0) r = 0;
            g = (int) (255.0 * ((float) (Color.green(c) - min)) / (max - min));
            if(g > 255) g = 255;
            else if (g < 0) g = 0;
            b = (int) (255.0 * ((float) (Color.blue(c) - min)) / (max - min));
            if(b > 255) b = 255;
            else if (b < 0) b = 0;
            pixels[i] = Color.rgb(r,g,b);
        }
        Bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    /*private void contrastRS(Bitmap bmp, double rand) {
//1)  Creer un  contexte  RenderScript
        RenderScript rs = RenderScript.create(this);
//2)  Creer  des  Allocations  pour  passer  les  donnees
        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());
//3)  Creer le  script
        ScriptC_contrast contrastScript = new ScriptC_contrast(rs);
//4)  Copier  les  donnees  dans  les  Allocations
// ...
//5)  Initialiser  les  variables  globales  potentielles
        double max = 0.0;
        double min = 255.0;
        int h = bmp.getHeight();
        int w = bmp.getWidth();
        int[] pixels = new int[w * h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            //System.out.println(Color.green(c));
            if(Color.green(pixels[i]) < min) min = Color.green(pixels[i]);
            if(Color.green(pixels[i]) > max) max = Color.green(pixels[i]);
        }
        contrastScript.set_mini(min/255.0);
        contrastScript.set_maxi(max/255.0);
//6)  Lancer  le noyau
        contrastScript.forEach_contrast(input, output);
//7)  Recuperer  les  donnees  des  Allocation(s)
        output.copyTo(bmp);
//8)  Detruire  le context , les  Allocation(s) et le  script
        input.destroy();
        output.destroy();
        coontrastScript.destroy();
        rs.destroy();
    }*/

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
        ImageView imViewWoman = (ImageView) findViewById(R.id.imageViewWoman);
        ImageView imViewGrayscale = (ImageView) findViewById(R.id.imageViewGrayscale);
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
        final Bitmap imageWoman = BitmapFactory.decodeResource(getResources(), R.drawable.woman, o);
        imViewWoman.setImageBitmap(imageWoman);
        final Bitmap imageGrayscale = BitmapFactory.decodeResource(getResources(), R.drawable.grayscale2, o);
        imViewGrayscale.setImageBitmap(imageGrayscale);
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
        ImageView imViewWoman = (ImageView) findViewById(R.id.imageViewWoman);
        ImageView imViewGrayscale = (ImageView) findViewById(R.id.imageViewGrayscale);
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
        final Bitmap imageWoman = BitmapFactory.decodeResource(getResources(), R.drawable.woman, o);
        imViewWoman.setImageBitmap(imageWoman);
        final Bitmap imageGrayscale = BitmapFactory.decodeResource(getResources(), R.drawable.grayscale2, o);
        imViewGrayscale.setImageBitmap(imageGrayscale);
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
                invert(imageColor);
                invert(imageBlack);
                invert(imageWhite);
                invert(imageMononoke);
                invert(imageWoman);
                invert(imageGrayscale);
                break;
            case 7:
                invertRS(imageColor);
                invertRS(imageBlack);
                invertRS(imageWhite);
                invertRS(imageMononoke);
                invertRS(imageWoman);
                invertRS(imageGrayscale);
                break;
            case 8:
                changeBrightness(imageColor);
                changeBrightness(imageBlack);
                changeBrightness(imageWhite);
                changeBrightness(imageMononoke);
                changeBrightness(imageWoman);
                changeBrightness(imageGrayscale);
                break;
            case 9:
                changeBrightnessRS(imageColor);
                changeBrightnessRS(imageBlack);
                changeBrightnessRS(imageWhite);
                changeBrightnessRS(imageMononoke);
                changeBrightnessRS(imageWoman);
                changeBrightnessRS(imageGrayscale);
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
            case 12:
                contrast(imageWoman);
                contrast(imageGrayscale);
                break;
            case 13:
                /*contrastRS(imageWoman);
                contrastRS(imageGrayscale);
                break;*/
        }
        System.out.println("final time : " + (System.nanoTime() - middle_time));

    }


    public void onNothingSelected(AdapterView<?> parent) {

    }

}