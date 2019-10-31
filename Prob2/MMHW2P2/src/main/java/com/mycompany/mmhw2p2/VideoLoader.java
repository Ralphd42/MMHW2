/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mmhw2p2;

import org.bytedeco.ffmpeg.avutil.AVFrame;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_MPEG4;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
import org.bytedeco.opencv.global.opencv_core;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_COMP_CHISQR;
import static org.bytedeco.opencv.global.opencv_imgproc.compareHist;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

/**
 *
 * @author ralph
 */
public class VideoLoader {
    public Mat KeyFrame(String fname) throws FrameGrabber.Exception
    {
        Mat mret= new Mat();
        IplImage image;
        FFmpegFrameGrabber grb = new FFmpegFrameGrabber(fname);
        grb.start();
        Frame fr;
        while( ((fr=grb.grabKeyFrame())!=null)){
       // Frame fr =grb.grab();
        //fr =grb.grabKeyFrame();
        final CanvasFrame canvas = new CanvasFrame("AAAAAAAA", 1.0);
         ToMat mt = new ToMat();
          mret =mt.convert(fr);// ToMat.\\\(fr);//convertToMat(fr);//convert(fr);//    convertToMat(fr);//(Mat)fr.opaque;
        canvas.showImage(fr);
        AVFrame avfr;// = new AVFrame();
        }
        return mret;
    }
    public Mat KeyFrame(String fname, int threshehold) throws FrameGrabber.Exception, FrameRecorder.Exception
    {
        FFmpegFrameRecorder outRec = new FFmpegFrameRecorder("/home/ralph/development/fall2019Classes/mm/HW2/OUTPUT_TST.mp4",640,720);
        outRec.setFrameRate(10);
        outRec.setFormat("mp4");
        outRec.setVideoCodec(AV_CODEC_ID_MPEG4);
        int cnt =0;
        Mat mret  = new Mat();
        Mat morig = null;
        Mat histOrig = new Mat();
        IplImage image;
        FFmpegFrameGrabber grb = new FFmpegFrameGrabber(fname);
        grb.start();
        Frame fr;
         outRec.start();
        while( ((fr=grb.grab())!=null)){
        if (cnt==0)
        {
                ToMat mt = new ToMat();
                morig = mt.convert(fr);
                HistFunctions hs = new HistFunctions();
                histOrig = HistFunctions.getHistMatGrey(morig);
                 ++cnt;
                outRec.record(fr);
                //HW2P2.display(hist, "HIST");
        }else
        {
             ToMat mt = new ToMat();
             //System.out.printf("\nIS KEY FRAME %b",fr.keyFrame);
             if(fr.image!=null){
                Mat nxt = mt.convert(fr);
                Mat nxtHist =HistFunctions.getHistMatGrey(nxt);
                double histdiff =compareHist(histOrig,nxtHist,CV_COMP_CHISQR);
                if( histdiff>threshehold){
                    System.out.printf("\nIMGNUM%d diff %f", cnt,histdiff);
                    outRec.record(fr);
                    morig    = mt.convert(fr);
                    histOrig = HistFunctions.getHistMatGrey(morig);
                    ++cnt;
                }
             }
             else
             {
                // System.out.printf("\nNULL %b",fr.keyFrame );
             }
        }
       // Frame fr =grb.grab();
        //fr =grb.grabKeyFrame();
        //final CanvasFrame canvas = new CanvasFrame("AAAAAAAA", 1.0);
         //ToMat mt = new ToMat();
         // mret =mt.convert(fr);// ToMat.\\\(fr);//convertToMat(fr);//convert(fr);//    convertToMat(fr);//(Mat)fr.opaque;
        //canvas.showImage(fr);
        //AVFrame avfr;// = new AVFrame();
            
        }
        outRec.stop();
        return mret;
    }
    
    
}
