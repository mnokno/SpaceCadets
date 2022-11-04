import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {
    public static void main(String[] args) {
        //Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        System.out.println("Hello world!");

        Mat original = Utilities.loadImg("testdata\\img12.jpg");
        Mat img = Utilities.toGrayScale(original);
        img = Utilities.blur(img, 3);
        img = Utilities.extractEdges(img);
        img = Utilities.threshold(img);
        img = Utilities.detectCircles(img, original, 10, 50);

        HighGui.imshow("Image", img);
        HighGui.waitKey();
        System.exit(0);
    }
}