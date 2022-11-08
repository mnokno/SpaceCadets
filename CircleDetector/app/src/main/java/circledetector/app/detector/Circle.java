package circledetector.app.detector;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Circle{

    private int x;
    private int y;
    private int r;
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

    public float getScore(){
        return score;
    }

    public void drawCircle(Mat img){
        // circle centers
        Imgproc.circle(img, new Point(x,y), 1, new Scalar(255,255,255), 3, 8, 0 );
        // circle outline
        Imgproc.circle(img, new Point(x,y), r, new Scalar(50,255,50), 3, 8, 0 );
    }

    public boolean minDistance(Circle other, int minDist){
        return (Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y)) + Math.abs(this.r - other.r)) > minDist;
    }

    public void resize(double scale){
        x = (int)(x * scale);
        y = (int)(y * scale);
        r = (int)(r * scale);
    }

    public Circle copy() {
        return new Circle(x, y, r, score);
    }
}
