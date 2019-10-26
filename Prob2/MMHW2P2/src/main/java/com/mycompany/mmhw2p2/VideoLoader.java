/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mmhw2p2;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

/**
 *
 * @author ralph
 */
public class VideoLoader {
    public Mat KeyFrame(String fname) throws FrameGrabber.Exception
    {
        IplImage image;
        FFmpegFrameGrabber grb = new FFmpegFrameGrabber(fname);
        grb.start();
        Frame fr =grb.grab();
        final CanvasFrame canvas = new CanvasFrame("AAAAAAAA", 1.0);
         ToMat mt = new ToMat();
        Mat mret =mt.convert(fr);// ToMat.\\\(fr);//convertToMat(fr);//convert(fr);//    convertToMat(fr);//(Mat)fr.opaque;
        canvas.showImage(fr);
    
        return mret;
    }
    
}
