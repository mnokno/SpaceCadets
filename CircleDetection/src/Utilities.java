import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public final class Utilities {

    public static Mat loadImg(String dir){
        return Imgcodecs.imread(dir);
    }

    public static Mat toGrayScale(Mat image){
        Mat res = new Mat();
        Imgproc.cvtColor(image, res, Imgproc.COLOR_RGB2GRAY);
        return res;
    }

    public static Mat blur(Mat image){
        Mat res = new Mat();
        Imgproc.GaussianBlur(image, res, new Size(5, 5), 0);
        return res;
    }

    public static Mat extractEdges(Mat image, float strength){
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

    public static Mat extractEdges(Mat image){
        return extractEdges(image, 1);
    }

    public static Mat detectCircles(Mat image, int minSize, int maxSize){
        float[][][] votes = new float[image.size(0)][image.size(1)][maxSize - minSize];

        double radFrac = Math.PI / 180f;
        for (int r = 0; r < maxSize - minSize; r++){
            System.out.println(r);
            for (int x = 0; x < image.size(0); x++){
                for (int y = 0; y < image.size(1); y++){
                    for (int theta = 0; theta < 360; theta++){
                        int a = (int)(x - (r + minSize) * Math.cos(theta * radFrac));
                        int b = (int)(y - (r + minSize) * Math.sin(theta * radFrac));
                        if (a < image.size(0) && b < image.size(1) && a >= 0 && b >= 0){
                            votes[x][y][r] += image.get(a, b)[0];
                        }
                    }
                }
            }
        }

        float max = Float.MIN_VALUE;
        int maxR = -1;
        int maxX = -1;
        int maxY = -1;
        for (int r = 0; r < maxSize - minSize; r++) {
            for (int x = 0; x < image.size(0); x++) {
                for (int y = 0; y < image.size(1); y++) {
                    if (votes[x][y][r] > max){
                        max = votes[x][y][r];
                        maxR = r;
                        maxX = x;
                        maxY = y;
                    }
                }
            }
        }

        Point center = new Point(maxX, maxY);
        System.out.println(maxX + " " + maxY + " " + maxR + " " + max);
        // circle centers
        Imgproc.circle(image, center, 1, new Scalar(255,255,255), 3, 8, 0 );
        // circle outline
        Imgproc.circle(image, center, maxR + minSize, new Scalar(255,0,255), 3, 8, 0 );
        return image;
    }
}