//#include "rs_debug.rsh"
#pragma version(1)
#pragma rs java_package_name(com.example.aaneas.afficherimagenew)
double rand;
uchar4 RS_KERNEL colorkeep(uchar4 in){
float4  pixelf = rsUnpackColor8888(in);
//rsDebug("in %d %d %d\n",in.r,in.g,in.b);
//pixelf.r = pixelf.r * 255.0;
//pixelf.g = pixelf.g * 255.0;
//pixelf.b = pixelf.b * 255.0;
//pixelf.r = (int) in.r;
//pixelf.g = (int) in.g;
//pixelf.b = (int) in.b;
//rsDebug("salut\n",0);
//rsDebug("pixelf %d %d %d\n",pixelf.r,pixelf.g,pixelf.b);
int r = pixelf.r * 255;
int g = pixelf.g * 255;
int b = pixelf.b * 255;
if((rand < 0.33 && (r < 80 || g > 80 || b > 100)) || ((rand >= 0.33 && rand < 0.66) && (r > 120 || g < 80 || b > 120)) || (rand >= 0.66 && (r > 120 || g > 170 || b < 100)))
//if(pixelf.r > 120.0/255.0 || pixelf.g < 80.0/255.0 || pixelf.b > 120.0/255.0)
//if(pixelf.r > 80.0/255.0 || pixelf.g < 80.0/255.0 || pixelf.b > 100.0/255.0)
            {
                float avgcolor = ((0.3 * pixelf.r) + (0.59 * pixelf.g) + (0.11 * pixelf.b));
                //rsDebug("pixelf\n",pixelf.r,in.r);
                //rsDebug("pixelf %d %d %d\n",pixelf.r,pixelf.g,pixelf.b);
                //rsDebug("avg %d\n",avgcolor);
                pixelf.r = avgcolor;
                pixelf.g = avgcolor;
                pixelf.b = avgcolor;
            }
    return  rsPackColorTo8888(pixelf.r , pixelf.g , pixelf.b , pixelf.a);
}