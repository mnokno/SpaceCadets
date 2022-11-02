import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public final class Utilities {

    public static Mat LoadImg(String dir){
        return Imgcodecs.imread(dir);
    }

    public static Mat ToGrayScale(Mat image){
        Mat res = new Mat();
        Imgproc.cvtColor(image, res, Imgproc.COLOR_RGB2GRAY);
        return res;
    }

    public static Mat blur(Mat image){
        Mat res = new Mat();
        Imgproc.GaussianBlur(image, res, new Size(5, 5), 0);
        return res;
    }

    public static Mat ExtractEdges(Mat image){
        return image;
    }
}