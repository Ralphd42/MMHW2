/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hw2q1;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.bytedeco.javacpp.indexer.UByteIndexer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

/**
 *
 * @author ralph
 */
public class HW2Functs {
    
    public double MAD( int i, int j, int x, int y, Mat target, Mat ref, int N)
    {
        double retval =0;    
        double NrecpSq = (double)1/(double)(N*N);
        UByteIndexer iTarget =target.createIndexer();
        UByteIndexer iRef =ref.createIndexer();
        double sumo =0.0;
        for( int k=0; k<N;k++)
        {   
            double sumi =0.0;
            for(int l=0;l<N;l++)
            {
                sumi+=Math.abs(  iTarget.get(x+k,y+l)-iRef.get(x+i+k,y+j+l));
            }
            sumo+=sumi;
        }
        retval =NrecpSq *sumo;
        return retval;
    }
    public class MotionVector
    {
        public int u;
        public int v;
    
    }
    public MotionVector SeqSearch( int x, int y, Mat target, Mat ref, int N, int p)
    {
        MotionVector mv = new MotionVector();
        double min_MAD = Double.MAX_VALUE;   // cant get bigger than that
        double cur_MAD =0;
        for(int i =-p;i<=p;i++ )
        {
            for(int j =-p;j<=p;j++ )
            {   
                cur_MAD = MAD(   i,   j,   x,   y,   target,   ref,   N );
                if( cur_MAD<min_MAD)
                {
                    min_MAD =cur_MAD;
                    mv.u=i;
                    mv.v=j;
                    System.out.println("Md " + min_MAD     +" mv.u " + mv.u + "mv.v" + mv.v);
                }
                System.out.println(".");
            }
        }
        System.out.println("DONE");
        return mv;
    }
    
    public static BufferedImage MatToBuff(Mat m) {
    Java2DFrameConverter paintConverter = new Java2DFrameConverter();
    final OpenCVFrameConverter converter = new OpenCVFrameConverter.ToMat();
        // Show image on window.
    Frame f =converter.convert(m);
    return paintConverter.getBufferedImage(f);
    
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
