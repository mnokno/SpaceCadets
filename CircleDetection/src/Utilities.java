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

    public static Mat ExtractEdges(Mat image, float strength){
        Mat gX = new Mat();
        Mat gY = new Mat();

        Mat filterX = new Mat(3, 3, CvType.CV_32F);
        Mat filterY = new Mat(3, 3, CvType.CV_32F);
        filterX.put(0, 0, new float[] {-1 * strength, 0 * strength, 1 * strength , -2 * strength , 0 * strength, 2 * strength, -1 * strength, 0 * strength, 1 * strength});
        filterY.put(0,0, new float[] {-1 * strength, -2 * strength, -1 * strength, 0 * strength, 0 * strength, 0 * strength, 1 * strength, 2 * strength, 1 * strength});
        Imgproc.filter2D(image, gX, -1, filterX, new Point(-1, -1), 0, Core.BORDER_DEFAULT);
        Imgproc.filter2D(image, gY, -1, filterY, new Point(-1, -1), 0, Core.BORDER_DEFAULT);

        Mat res = Mat.zeros(image.size(), CvType.CV_8U);
        for (int x = 0; x < res.size(0); x++){
            for (int y = 0; y < res.size(1); y++){
                int data = (int)Math.sqrt(gX.get(x, y)[0] * gX.get(x, y)[0] + gY.get(x, y)[0] * gY.get(x, y)[0]);
                res.put(x, y, data);
            }
        }

        return res;
    }

    public static Mat ExtractEdges(Mat image){
        return ExtractEdges(image, 1);
    }
}