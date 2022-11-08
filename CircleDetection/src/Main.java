import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class Main {
    public static void main(String[] args) {
        // loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // extracts circles
        Mat original = Utilities.loadImg("testdata\\img1.jpg");
        Mat img = Utilities.toGrayScale(original);
        img = Utilities.resize(img, 10000);
        //img = Utilities.blur(img, 1);
        img = Utilities.extractEdges(img);
        img = Utilities.threshold(img, .3f);
        System.out.println(img.size(0) + " " + img.size(1));
        Circle[] circles = Utilities.detectCircles(img, 0.05f, 0.175f, 0.0727f, 0.90f, 100);
        Utilities.drawCirclesOnImage(original, circles, Utilities.getResizeFactor(original, 10000));//

        // shows predicted circles cast onto the original images
        HighGui.imshow("Image", original);
        HighGui.waitKey();
        System.exit(0);
    }
}