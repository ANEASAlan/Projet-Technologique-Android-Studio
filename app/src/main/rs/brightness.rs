#include "rs_math.rsh"
#include "rs_time.rsh"
#define min(X, Y) (((X) < (Y)) ? (X) : (Y))
#define max(X, Y) (((X) > (Y)) ? (X) : (Y))
#pragma version(1)
#pragma rs java_package_name(com.example.aaneas.afficherimagenew)

float BrightnessScale = 75.0/255.0;
uchar4 RS_KERNEL changeBrightness(uchar4 in){

float4  pixelf = rsUnpackColor8888(in);
pixelf.r += BrightnessScale;
if(pixelf.r > 1.0) pixelf.r = 1.0;
pixelf.g += BrightnessScale;
if(pixelf.g > 1.0) pixelf.g = 1.0;
pixelf.b += BrightnessScale;
if(pixelf.b > 1.0) pixelf.b = 1.0;
    return  rsPackColorTo8888(pixelf.r , pixelf.g , pixelf.b , pixelf.a);
}