import org.opencv.core.*;
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

    public static Mat blur(Mat image, int strength){
        Mat res = new Mat();
        Imgproc.GaussianBlur(image, res, new Size(strength, strength), 0);
        return res;
    }

    public static Mat blur(Mat image){
        return blur(image, 5);
    }

    public static Mat extractEdges(Mat image, float strength, Polarity polarity){

        // positive polarity of edges
        Mat gXp = new Mat();
        Mat gYp = new Mat();
        Mat filterXp = new Mat(3, 3, CvType.CV_32F);
        Mat filterYp = new Mat(3, 3, CvType.CV_32F);
        filterXp.put(0,0, new float[] {-1, 0, 1, -2 ,0, 2, -1, 0, 1});
        filterYp.put(0,0, new float[] {-1, -2, -1, 0, 0, 0, 1, 2, 1});
        Imgproc.filter2D(image, gXp, -1, filterXp, new Point(-1, -1), 0, Core.BORDER_DEFAULT);
        Imgproc.filter2D(image, gYp, -1, filterYp, new Point(-1, -1), 0, Core.BORDER_DEFAULT);

        // negative polarity of edges
        Mat gXn = new Mat();
        Mat gYn = new Mat();
        Mat filterXn = new Mat(3, 3, CvType.CV_32F);
        Mat filterYn = new Mat(3, 3, CvType.CV_32F);
        filterXn.put(0,0, new float[] {1, 0, -1, 2, 0, -2, 1, 0, -1});
        filterYn.put(0,0, new float[] {1, 2, 1, 0, 0, 0, -1, -2, -1});
        Imgproc.filter2D(image, gXn, -1, filterXn, new Point(-1, -1), 0, Core.BORDER_DEFAULT);
        Imgproc.filter2D(image, gYn, -1, filterYn, new Point(-1, -1), 0, Core.BORDER_DEFAULT);

        // applies scale
        // it should not make any difference to the finale effect, but its there for validation
        if (strength != 1){
            for (int x = 0; x < 3; x++){
                for (int y = 0; y < 3; y++){
                    filterXn.put(x, y, filterXn.get(x, y)[0] * strength);
                    filterYn.put(x, y, filterYn.get(x, y)[0] * strength);
                    filterXp.put(x, y, filterXp.get(x, y)[0] * strength);
                    filterYp.put(x, y, filterYp.get(x, y)[0] * strength);
                }
            }
        }

        // extracts edges
        Mat res = Mat.zeros(image.size(), CvType.CV_8U);
        for (int x = 0; x < res.size(0); x++){
            for (int y = 0; y < res.size(1); y++){
                double ap = gXp.get(x, y)[0];
                double bp = gYp.get(x, y)[0];
                double an = gXn.get(x, y)[0];
                double bn = gYn.get(x, y)[0];
                int data;
                if (polarity == Polarity.BOTH){
                    data = (int)(Math.sqrt(ap * ap + bp * bp) + Math.sqrt(an * an + bn * bn));
                }
                else if (polarity == Polarity.POSITIVE){
                    data = (int)(Math.sqrt(ap * ap + bp * bp));
                }
                else{
                    data = (int)(Math.sqrt(an * an + bn * bn));
                }
                res.put(x, y, data);
            }
        }

        // returns images with edges extracted
        return res;
    }

    public static Mat extractEdges(Mat image){
        return extractEdges(image, 1, Polarity.BOTH);
    }

    public static Mat threshold(Mat img, float thresh){
        Mat res = new Mat();
        Imgproc.threshold(img, res, thresh * 255, 255, Imgproc.THRESH_TOZERO);
        return res;
    }

    public static Mat threshold(Mat img){
        return threshold(img, 150f/255f);
    }

    public static Mat detectCircles(Mat image, Mat orgImg, int minSize, int maxSize){

        // defines matrix used to accumulates votes
        float[][][] votes = new float[image.size(0)][image.size(1)][maxSize - minSize];

        // scales the image so that a perfect circle will have a vote of 1
        double divider = 255 * 360;
        Mat scaledImage = Mat.zeros(image.size(), CvType.CV_32F);
        for (int x = 0; x < image.size(0); x++){
            for (int y = 0; y < image.size(1); y++){
                scaledImage.put(x, y, image.get(x, y)[0] / divider);
            }
        }
        //image = scaledImage;


        // calculates the votes
        double radFrac = Math.PI / 180f;
        for (int r = 0; r < maxSize - minSize; r++){
            System.out.println(((1f / (maxSize - minSize)) * 100 * r) + "%");
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

                    if (votes[x][y][r] > 40000){
                        System.out.println(x + " " + y);
                        Circle circle = new Circle(y, x, r + minSize, votes[x][y][r]);
                        circle.drawCircle(orgImg);
                    }

                    if (votes[x][y][r] > max){

                        max = votes[x][y][r];
                        maxR = r;
                        maxX = x;
                        maxY = y;
                    }

                }
            }
        }

        Point center = new Point(maxY, maxX);
        System.out.println(maxX + " " + maxY + " " + maxR + " " + max);
        Circle bestCandidate = new Circle(maxY, maxX, maxR, max);
        bestCandidate.drawCircle(orgImg);
        return orgImg;
    }
}