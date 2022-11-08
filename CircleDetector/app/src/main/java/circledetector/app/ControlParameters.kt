package circledetector.app

import circledetector.app.ControlParameters

public data class ControlParameters(
    val blurStrength: Float,
    val threshold: Float,
    val minSize: Float,
    val maxSize: Float,
    val minDistance: Float,
    val confidence: Float,
    val offScree: Float,
    val resolutions: Float
    ) {

    fun doesRequireFullReprocessing(newParameters: ControlParameters?): Boolean {
        return if (newParameters == null){
            true
        } else{
            !(blurStrength == newParameters.blurStrength
                    && threshold == newParameters.threshold
                    && minSize == newParameters.minSize
                    && maxSize == newParameters.maxSize
                    && offScree == newParameters.offScree
                    && resolutions == resolutions)
        }
    }
}