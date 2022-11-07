package circledetector.app.detector;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Main {
    public static void main(String[] args) {
        // loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // extracts circles
        Mat original = Utilities.loadImg("testdata\\img4.jpg");
        Mat img = Utilities.toGrayScale(original);


        img = Utilities.blur(img, 3);
        img = Utilities.extractEdges(img);
        img = Utilities.threshold(img, .5f);
        img = Utilities.resize(img, 10000);
        System.out.println(img.size(0) + " " + img.size(1));
        Circle[] circles = Utilities.detectCircles(img, 0.05f, 0.175f, 0.0727f, 0.60f, 100);
        Utilities.drawCirclesOnImage(original, circles, Utilities.getResizeFactor(original, 10000));//

    }
}