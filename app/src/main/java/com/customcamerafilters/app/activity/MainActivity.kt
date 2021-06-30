package com.customcamerafilters.app.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.customcamerafilters.app.R.layout
import com.customcamerafilters.app.R.string
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.android.synthetic.main.activity_main.camera_capture_button
import kotlinx.android.synthetic.main.activity_main.img_switch_camera
import kotlinx.android.synthetic.main.activity_main.progress_bar_image_process
import kotlinx.android.synthetic.main.activity_main.viewFinder
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

class MainActivity : AppCompatActivity() {
	private lateinit var outputDirectory: File
	private lateinit var cameraExecutor: ExecutorService
	var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
	var lensFacing = CameraSelector.DEFAULT_FRONT_CAMERA

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(layout.activity_main)

		// Request camera permissions
		if (allPermissionsGranted()) {
			startCamera()
		} else {
			ActivityCompat.requestPermissions(
				this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
		}

		// Set up the listener for take photo button
		camera_capture_button.setOnClickListener { takePhoto() }

		outputDirectory = getOutputDirectory()

		cameraExecutor = Executors.newSingleThreadExecutor()

		img_switch_camera.setOnClickListener {
			flipCamera()
		}
	}

	private fun flipCamera() {
		if (lensFacing === CameraSelector.DEFAULT_FRONT_CAMERA) lensFacing = CameraSelector.DEFAULT_BACK_CAMERA else if (lensFacing === CameraSelector.DEFAULT_BACK_CAMERA) lensFacing = CameraSelector.DEFAULT_FRONT_CAMERA
		startCamera()
	}

	private fun takePhoto() {
		progress_bar_image_process.visibility = View.VISIBLE
		// Get a stable reference of the modifiable image capture use case
		val imageCapture = ImageCapture.Builder().setTargetRotation(viewFinder.display.rotation).build()

		// Create time-stamped output file to hold the image
		val photoFile = File(
			outputDirectory,
			SimpleDateFormat(FILENAME_FORMAT, Locale.US
			).format(System.currentTimeMillis()) + getString(string.txt_jpg))

		// Create output options object which contains file + metadata
		val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

		// Set up image capture listener, which is triggered after photo has
		// been taken
		cameraProviderFuture?.get()?.bindToLifecycle(
			this, lensFacing, imageCapture)
		imageCapture?.takePicture(
			outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
			override fun onError(exc: ImageCaptureException) {
				Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
			}

			override fun onImageSaved(output: ImageCapture.OutputFileResults) {
				val savedUri = Uri.fromFile(photoFile)
				val filterIntent = Intent(this@MainActivity, FilterActivity::class.java)
				filterIntent.putExtra(CAPTURED_IMAGE, photoFile.path)
				progress_bar_image_process.visibility = View.INVISIBLE
				startActivity(filterIntent)
				val msg = "Photo capture succeeded: $savedUri"
				Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
				Log.d(TAG, msg)
			}
		})
	}

	private fun startCamera() {
		cameraProviderFuture = ProcessCameraProvider.getInstance(this)

		cameraProviderFuture?.addListener(Runnable {
			// Used to bind the lifecycle of cameras to the lifecycle owner
			cameraProviderFuture?.get()?.let {
				val cameraProvider: ProcessCameraProvider = it


				// Preview
				val preview = Preview.Builder()
					.build()
					.also {
						it.setSurfaceProvider(viewFinder.surfaceProvider)
					}

				// Select back camera as a default
				val cameraSelector = lensFacing

				try {
					// Unbind use cases before rebinding
					cameraProvider.unbindAll()

					// Bind use cases to camera
					cameraProvider.bindToLifecycle(
						this, cameraSelector, preview)

				} catch (exc: Exception) {
					Log.e(TAG, "Use case binding failed", exc)
				}
			}
		}, ContextCompat.getMainExecutor(this))
	}

	private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
		ContextCompat.checkSelfPermission(
			baseContext, it) == PackageManager.PERMISSION_GRANTED
	}

	private fun getOutputDirectory(): File {
		val mediaDir = externalMediaDirs.firstOrNull()?.let {
			File(it, resources.getString(string.app_name)).apply { mkdirs() }
		}
		return if (mediaDir != null && mediaDir.exists())
			mediaDir else filesDir
	}

	override fun onDestroy() {
		super.onDestroy()
		cameraExecutor.shutdown()
	}

	companion object {
		private const val TAG = "CameraXBasic"
		private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
		private const val REQUEST_CODE_PERMISSIONS = 10
		private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
		const val CAPTURED_IMAGE = "CAPTURED_IMAGE"

	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		if (requestCode == REQUEST_CODE_PERMISSIONS) {
			if (allPermissionsGranted()) {
				startCamera()
			} else {
				Toast.makeText(this,
					getString(string.txt_permission_not_granted),
					Toast.LENGTH_SHORT).show()
				finish()
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

	}

}