package circledetector.app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import circledetector.app.databinding.ActivityMainBinding
import circledetector.app.detector.Circle
import circledetector.app.detector.Utilities
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import java.lang.Exception
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var originalImage: Bitmap
    private var controlParameters: ControlParameters? = null
    private lateinit var votes:Array<Array<FloatArray>>
    private lateinit var circles:ArrayList<Circle>
    private lateinit var coachLImage: Mat

    companion object{
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(OpenCVLoader.initDebug()) Log.d("LOADED", "successful")
        else Log.d("LOADED", "fail")

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
            else{
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE)
            }
        }
        binding.fab2.setOnClickListener {
            val newControlParameters: ControlParameters =  readInputs()
            if (newControlParameters.doesRequireFullReprocessing(controlParameters)){
                controlParameters = newControlParameters
                val imageWithDetectedCircles: Bitmap = hardImageProcessing(originalImage)
                showImage(imageWithDetectedCircles)
                Log.d("Calc Mode", "Hard")
            }
            else{
                controlParameters = newControlParameters
                val imageWithDetectedCircles: Bitmap = softImageProcessing(originalImage)
                showImage(imageWithDetectedCircles)
                Log.d("Calc Mode", "Soft")
            }
        }
    }

    private fun readInputs(): ControlParameters{
        return ControlParameters(
            binding.blur.value,
            binding.threshold.value,
            binding.minSize.value,
            binding.maxSize.value,
            binding.minDistance.value,
            binding.confidence.value,
            binding.offScreenScore.value,
            binding.resolution.value
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
            else{
                Toast.makeText(
                    this,
                    "Oops you just denied permission for the camera, we cant capture an image without this permission.",
                    Toast.LENGTH_LONG
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA_REQUEST_CODE){
                originalImage = data!!.extras!!.get("data") as Bitmap
                showImage(originalImage)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showImage(image: Bitmap){
        binding.imageView.setImageBitmap(image)
    }

    private fun hardImageProcessing(bitmap: Bitmap): Bitmap{
        // converts bitmap to mat
        val original:Mat = Mat()
        Utils.bitmapToMat(bitmap, original);

        // coverts the image to grayscale
        var img:Mat = Utilities.toGrayScale(original)

        // resizes the image to decrease precessing time
        val resolution: Int = (10000 + 40000 * controlParameters!!.resolutions).toInt()
        img = Utilities.resize(img, resolution)

        // blurs the image
        val blurStrength:Int = (10 * controlParameters!!.blurStrength).toInt();
        if (blurStrength > 0){
            img = Utilities.blur(img, blurStrength * 3)
        }

        // extracts edges from the image
        img = Utilities.extractEdges(img)

        // applies threshold
        img = Utilities.threshold(img, controlParameters!!.threshold)

        // calculates votes
        votes = Utilities.calculateVotes(img,
            controlParameters!!.minSize / 2f,
            controlParameters!!.maxSize / 2f,
            200 * controlParameters!!.offScree)

        // extracts circles
        circles = Utilities.extractCircles(img, votes,
            controlParameters!!.minSize / 2f,
            controlParameters!!.maxSize / 2f,
            controlParameters!!.confidence)

        // applies minDistance between circles
        coachLImage = img
        val localCircle: ArrayList<Circle> = circles.map{ it.copy() } as ArrayList<Circle>
        Utilities.applyMinCircleDistance(localCircle, img, controlParameters!!.minDistance)

        // draws the circles on the original image
        Utilities.drawCirclesOnImage(original, localCircle.toArray(arrayOf<Circle>()),
            Utilities.getResizeFactor(original, resolution))

        // converts back from mat to bitmap
        val resBitmap: Bitmap = bitmap.copy(bitmap.config, true)
        Utils.matToBitmap(original, resBitmap)

        // returns a bitmap with detected circles drawn on it
        return resBitmap
    }

    private fun softImageProcessing(bitmap: Bitmap): Bitmap{
        // converts bitmap to mat
        val original:Mat = Mat()
        Utils.bitmapToMat(bitmap, original);

        // applies minDistance between circles
        val localCircle: ArrayList<Circle> = circles.map{ it.copy() } as ArrayList<Circle>
        Utilities.applyMinCircleDistance(localCircle, coachLImage, controlParameters!!.minDistance)

        // extracts circles
        circles = Utilities.extractCircles(coachLImage, votes,
            controlParameters!!.minSize / 2f,
            controlParameters!!.maxSize / 2f,
            controlParameters!!.confidence)

        // draws the circles on the original image
        val resolution: Int = (10000 + 40000 * controlParameters!!.resolutions).toInt()
        Utilities.drawCirclesOnImage(original, localCircle.toArray(arrayOf<Circle>()),
            Utilities.getResizeFactor(original, resolution))

        // converts back from mat to bitmap
        val resBitmap: Bitmap = bitmap.copy(bitmap.config, true)
        Utils.matToBitmap(original, resBitmap)

        // returns a bitmap with detected circles drawn on it
        return resBitmap
    }
}