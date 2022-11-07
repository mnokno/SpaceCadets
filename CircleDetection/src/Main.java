import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class Main {
    public static void main(String[] args) {
        // loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // extracts circles
        Mat original = Utilities.loadImg("testdata\\img5.jpg");
        Mat img = Utilities.toGrayScale(original);
        img = Utilities.resize(img, 20000);
        img = Utilities.blur(img, 3);
        img = Utilities.extractEdges(img);
        img = Utilities.threshold(img, .5f);
        System.out.println(img.size(1) + " " + img.size(0));
        Circle[] circles = Utilities.detectCircles(img, 10, 40, 20, 0.60f, 100);
        //Circle[] circles = Utilities.detectCircles(img, 20, 80, 20, 0.60f, 0);
        //Circle[] circles = Utilities.detectCircles(img, 50, 80, 10, 0.60f, 0);
        original = Utilities.resize(original, 20000);
        Utilities.drawCirclesOnImage(original, circles);

        // shows predicted circles cast onto the original images
        HighGui.imshow("Image", original);
        HighGui.waitKey();
        System.exit(0);
    }
}