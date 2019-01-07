//#include <stdlib.h>
//#include <time.h>
//#include <math.h>
#include "rs_math.rsh"
#include "rs_time.rsh"
#define min(X, Y) (((X) < (Y)) ? (X) : (Y))
#define max(X, Y) (((X) > (Y)) ? (X) : (Y))
#pragma version(1)
#pragma rs java_package_name(com.example.aaneas.afficherimagenew)

float BrightnessScale = 0.0;
uchar4 RS_KERNEL changeBrightness(uchar4 in){
//rsTime(NULL);
//double h = rsRand(255);
double h = 0;
float4  pixelf = rsUnpackColor8888(in);
double r = (double) pixelf.r/255.0;
double g = (double) pixelf.g/255.0;
double b = (double) pixelf.b/255.0;
double cmax = max(max(r,g),b);
double cmin = min(min(r,g),b);
cmin = cmax-cmin;
/*
if(cmin == 0){
h = 0;
}else{
switch(cmax){
 case r :
h = 60*(((g-b)/cmin)%6);
 break;

 case g :
h = 60*(((b-r)/cmin)+2);
 break;

 case b :
h = 60*(((r-g)/cmin)+4);
}
}*/

double s;
if(cmax == 0) s = 0;
else s = cmin / cmax;

double c = cmax * s;
double x = c * (1 - abs(((int)(h/60))%2-1));
double m = cmax - c;
if(h < 60){
pixelf.r = 255*(m+c);
pixelf.g = 255*(m+x);
pixelf.b = 255*(m);
}else if(h < 120){
pixelf.r = 255*(m+x);
pixelf.g = 255*(m+c);
pixelf.b = 255*(m);
}else if(h < 180){
pixelf.r = 255*(m);
pixelf.g = 255*(m+c);
pixelf.b = 255*(m+x);
}else if(h < 240){
pixelf.r = 255*(m);
pixelf.g = 255*(m+x);
pixelf.b = 255*(m+c);
}else if(h < 300){
pixelf.r = 255*(m+x);
pixelf.g = 255*(m);
pixelf.b = 255*(m+c);
}else{
pixelf.r = 255*(m+c);
pixelf.g = 255*(m);
pixelf.b = 255*(m+x);
}
//rsDebug("%d %d %d\n",pixelf.r,pixelf.g , pixelf.b);
    return  rsPackColorTo8888(pixelf.r , pixelf.g , pixelf.b , pixelf.a);
}