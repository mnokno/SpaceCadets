import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {
    public static void main(String[] args) {
        //Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        System.out.println("Hello world!");
        Mat img = Utilities.blur(Utilities.ToGrayScale(Utilities.LoadImg("testdata\\img4.jpg")));
        HighGui.imshow("Image", img);
        HighGui.waitKey();
        System.exit(0);
    }
}