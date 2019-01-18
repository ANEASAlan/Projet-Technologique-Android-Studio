#include "rs_math.rsh"
#include "rs_time.rsh"
#pragma version(1)
#pragma rs java_package_name(com.example.aaneas.afficherimagenew)


double mini = 0.0;
double maxi = 1.0;
uchar4 RS_KERNEL contrast(uchar4 in){

float4  pixelf = rsUnpackColor8888(in);
pixelf.r = (pixelf.r - mini) / (maxi - mini);
if(pixelf.r > 1.0) pixelf.r = 1.0;
pixelf.g = (pixelf.g - mini) / (maxi - mini);
if(pixelf.g > 1.0) pixelf.g = 1.0;
pixelf.b = (pixelf.b - mini) / (maxi - mini);
if(pixelf.b > 1.0) pixelf.b = 1.0;
    return  rsPackColorTo8888(pixelf.r , pixelf.g , pixelf.b , pixelf.a);
}