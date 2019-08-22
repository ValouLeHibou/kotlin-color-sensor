package com.example.color_sensor

import android.content.Context
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt



class MainActivity : AppCompatActivity(), SensorEventListener {

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun roundValue(axisValue: Float): Int {
        var result = axisValue.roundToInt()
        if (result < 0) {
            result *= -1
        }

        return result
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event !== null) {
            var colorId : Int? = null

            // je récupère la valeur des axes que me donne le sensor
            var x = roundValue(event.values[0])
            var y = roundValue(event.values[1])
            var z = roundValue(event.values[2])

            // je change le texte à afficher en fonction de la valeur des axes
            axeX.text = x.toString()
            axeY.text = y.toString()
            axeZ.text = z.toString()

            // lorsque le score de l'axe est de 10, on attribut la couleur indiquée dans color.xml
            when {
                x == 10 -> colorId = R.color.blue
                y == 10 -> colorId = R.color.green
                z == 10 -> colorId = R.color.purple
            }

            if (colorId !== null) {
                mainlayout.setBackgroundResource(colorId)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}
