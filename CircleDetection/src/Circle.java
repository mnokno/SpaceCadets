import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Circle{

    private final int x;
    private final int y;
    private final int r;
    private final float score;

    public Circle(int x, int y, int r, float score){
        this.x = x;
        this.y = y;
        this.r = r;
        this.score = score;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getR(){
        return r;
    }

    public void drawCircle(Mat img){
        // circle centers
        Imgproc.circle(img, new Point(x,y), 1, new Scalar(255,255,255), 3, 8, 0 );
        // circle outline
        Imgproc.circle(img, new Point(x,y), r, new Scalar(255,0,255), 3, 8, 0 );
    }

    public boolean minDistance(Circle other, int minDist){
        return ((Math.sqrt(this.x + this.y) + this.r) - (Math.sqrt(other.x + other.y) + other.r)) > minDist;
    }
}
