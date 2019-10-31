/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mmhw2p2;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_BGR2HSV;
import static org.bytedeco.opencv.global.opencv_imgproc.calcHist;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author ralph
 */
public class HistFunctions {
    public Mat ColorHistGrm( Mat inputImage)
    {   
        Mat dest = new Mat();
        cvtColor(inputImage, dest, Imgproc.COLOR_BGR2HSV); 
        final int[] channels = new int[]{0};
        final Mat mask = new Mat();
        final Mat rv = new Mat();
        final int[] histSize = new int[]{255};
        final float[] histRange = new float[]{0f, 256f};
        IntPointer intPtrChannels = new IntPointer(channels);
        IntPointer intPtrHistSize = new IntPointer(histSize);
        final PointerPointer<FloatPointer> ptrPtrHistRange = new PointerPointer<>(histRange);
        calcHist(inputImage, 1, intPtrChannels, mask, rv, 1, intPtrHistSize, ptrPtrHistRange, true, false);
        return rv;
    }
    
     

     
    public Mat calcHist3D(Mat inputImage) {
        //ColorHistogram m = new COLOR_BGR2HSV();
         Mat dest = new Mat();
        cvtColor(inputImage, dest, Imgproc.COLOR_BGR2HSV); //COLOR_BGR2HSV COLOR_RGB2GRAY
        Mat rv = new Mat();
        // Compute histogram
        final int[] channels = new int[]{0, 1, 2};
        final Mat mask = new Mat();
         
        final int[] histSize = new int[]{8, 8, 8};
        final float[] histRange = new float[]{0f, 255f};
        IntPointer intPtrChannels = new IntPointer(channels);
        IntPointer intPtrHistSize = new IntPointer(histSize);
        final PointerPointer<FloatPointer> ptrPtrHistRange = new PointerPointer<>(histRange, histRange, histRange);
        calcHist(dest, 1, intPtrChannels, mask, rv, 3, intPtrHistSize, ptrPtrHistRange, true, false);
        return rv;
    }
    public Mat ch3(Mat inputImage)
    {
         Mat dest = new Mat();
         Mat r_hist = new Mat();
        int channels[] = {0 , 1};
				int h_bins = 50; int s_bins = 60; int v_bins = 60;     
				int histSize[] = { h_bins, s_bins, v_bins};
				float range[] = {  0,255,0, 180 } ;
				//
				cvtColor(inputImage, dest, CV_BGR2HSV  );
				 
				calcHist(dest, 1, channels, new Mat(), r_hist, 1, histSize, range, true, false);
    
    
                                return r_hist;
    }
    public static Mat getHistMat(Mat image){ 
        //int[] hist_size = {50,60};
        int[] hist_size = {150,160};
        int[] channels = {0,1}; 
        IntPointer channels_pointer=new IntPointer(channels); 
        IntPointer hist_size_pointer=new IntPointer(hist_size); 
        final float[][] ranges = {{0f,180f},{0f,256f}}; 
        PointerPointer<FloatPointer> range_pointer=new PointerPointer<>(ranges); 
        Mat hist=new Mat(); 
        hist.rows(image.rows()).cols(image.cols()); 
        Mat mask=new Mat(); 
        mask.rows(image.rows()).cols(image.cols()); 
        opencv_imgproc.calcHist(image, 1, channels_pointer, mask, hist, 2, hist_size_pointer, range_pointer,true,false); 
        opencv_core.normalize(hist, hist, 0, 1, opencv_core.NORM_MINMAX, -1, new Mat()); 
        return hist; }
    
    public static Mat getHistMat3(Mat image){ 
        //int[] hist_size = {50,60};
        //cvtColor(image, image, Imgproc.COLOR_RGB2GRAY); //COLOR_BGR2HSV COLOR_RGB2GRAY
        int[] hist_size = {256,1000};
        int[] channels = {0,1,2}; 
        IntPointer channels_pointer=new IntPointer(channels); 
        IntPointer hist_size_pointer=new IntPointer(hist_size); 
        final float[][] ranges = {{0f,256f},{0f,256f}}; 
        PointerPointer<FloatPointer> range_pointer=new PointerPointer<>(ranges); 
        Mat hist=new Mat(); 
        hist.rows(image.rows()).cols(image.cols()); 
        Mat mask=new Mat(); 
        mask.rows(image.rows()).cols(image.cols()); 
        opencv_imgproc.calcHist(image, 1, channels_pointer, mask, hist, 2, hist_size_pointer, range_pointer,true,false); 
        opencv_core.normalize(hist, hist, 0, 1, opencv_core.NORM_MINMAX, -1, new Mat()); 
        return hist; }
    
    
    public static Mat getHistMatGrey(Mat image){ 
        //int[] hist_size = {50,60};
        cvtColor(image, image, Imgproc.COLOR_RGB2GRAY); //COLOR_BGR2HSV COLOR_RGB2GRAY
        IntPointer sz=new IntPointer(new int[]{256});
        IntPointer chn=new IntPointer(new int[]{0});// greyscale only need one channel
         
         
         
        final float[] ranges = {0f,256f}; 
        PointerPointer<FloatPointer> range_pointer=new PointerPointer<>(ranges); 
        Mat hist=new Mat(); 
        hist.rows(image.rows()).cols(image.cols()); 
        Mat mask=new Mat(); 
        mask.rows(image.rows()).cols(image.cols()); 
        opencv_imgproc.calcHist(image, 1, chn, mask, hist, 1, sz, range_pointer,true,false); 
        opencv_core.normalize(hist, hist, 0, 1, opencv_core.NORM_MINMAX, -1, new Mat()); 
        return hist; }
    
    
    
    
   /**
    * calculates intersection of two greyscale normalized histograms
    * @param h1 normalized greyscale histogram 1
    * @param h2 normalized greyscale histogram 2
    * @return the intersection
    */
   public static double HistIntersectDistance(Mat h1, Mat h2)
    {
        double retval =0.0;
        FloatIndexer h1Indexer = h1.createIndexer();
        FloatIndexer h2Indexer = h2.createIndexer();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(h1.rows());
        System.out.println(h1.cols());
        double sum =0.0;
        for( int cnt =0; cnt <h1.rows();cnt++)
        {
            sum = min(h1Indexer.get(cnt),h2Indexer.get(cnt));
        }
        return retval;
    } 
    public static double min(double m1, double m2)
    {
        double retval =m2;
        if(m1<m2)
        {
            retval =m1;
        }    
        return retval;
    }
    public static double EuclidHistDist( Mat h1, Mat h2)
    {
        double retval =0.0;
        FloatIndexer h1Indexer = h1.createIndexer();
        FloatIndexer h2Indexer = h2.createIndexer();
        double sum =0.0;
        for( int cnt =0; cnt <h1.rows();cnt++)
        {
            sum += Math.pow((h1Indexer.get(cnt)-h2Indexer.get(cnt)),2.0);
        }
        
        retval =Math.sqrt(sum);
        return retval;
    }
    
    
    
    
}





    
    
 
