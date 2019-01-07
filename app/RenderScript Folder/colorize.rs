
#include "rs_math.rsh"
#include "rs_time.rsh"
#define min(X, Y) (((X) < (Y)) ? (X) : (Y))
#define max(X, Y) (((X) > (Y)) ? (X) : (Y))
#pragma version(1)
#pragma rs java_package_name(com.example.aaneas.afficherimagenew)

double h;
uchar4 RS_KERNEL colorize(uchar4 in){
float4  pixelf = rsUnpackColor8888(in);
double r = (double) pixelf.r/255.0;
double g = (double) pixelf.g/255.0;
double b = (double) pixelf.b/255.0;
double cmax = max(max(r,g),b);
double cmin = min(min(r,g),b);
cmin = cmax-cmin;
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
    return  rsPackColorTo8888(pixelf.r , pixelf.g , pixelf.b , pixelf.a);
}