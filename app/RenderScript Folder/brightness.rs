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
pixelf.g += BrightnessScale;
pixelf.b += BrightnessScale;
    return  rsPackColorTo8888(pixelf.r , pixelf.g , pixelf.b , pixelf.a);
}