/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mmhw2p2;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.WindowConstants;
import org.bytedeco.javacpp.indexer.UByteIndexer;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.calcHist;
import org.bytedeco.opencv.opencv_core.Mat;

/**
 https://github.com/bytedeco/javacv-examples/blob/master/OpenCV_Cookbook/src/test/java/opencv_cookbook/CalcHistTest.java
 https://www.programcreek.com/java-api-examples/?class=org.opencv.imgproc.Imgproc&method=calcHist
 * @author ralph
 */
public class HW2P2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String imgLocation ="/home/ralph/development/fall2019Classes/mm/HW2/test.jpg";
        VideoLoader vl = new VideoLoader();
try{        vl.KeyFrame("/home/ralph/development/fall2019Classes/mm/HW2/PKA.mp4");
}catch(Exception Exp){}    
        
        if(true){
        Mat infile =imread(imgLocation);
        HistFunctions hs = new HistFunctions();
        Mat hist = hs.getHistMat3(infile);
        display(hist, "CHIST");
        }
        
       // calcHist(infile  );
         
    }
    static void display(Mat image, String caption) {
        // Create image window named "My Image".
        final CanvasFrame canvas = new CanvasFrame(caption, 1.0);

        // Request closing of the application when the image window is closed.
        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Convert from OpenCV Mat to Java Buffered image for display
        final OpenCVFrameConverter converter = new OpenCVFrameConverter.ToMat();
        // Show image on window.
        Frame f =converter.convert(image);
         
        canvas.showImage(converter.convert(image));
    }
    
    
    
    
    
}
