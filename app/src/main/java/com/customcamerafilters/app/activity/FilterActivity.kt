package com.customcamerafilters.app.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.customcamerafilters.app.R.layout
import com.customcamerafilters.app.R.string
import com.customcamerafilters.app.adapter.FilterAdapter
import com.customcamerafilters.app.interfaces.FilterClickListener
import com.customcamerafilters.app.model.FilterModel
import com.uvstudio.him.photofilterlibrary.PhotoFilter
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class FilterActivity : AppCompatActivity(), FilterClickListener {
	private var capturedImagePath = ""
	private val photoFilter = PhotoFilter()
	private var filterAdapter: FilterAdapter? = null
	private var capturedImageBitmap: Bitmap? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layout.activity_filter)

		intent.extras?.let {
			if (it.containsKey(MainActivity.CAPTURED_IMAGE)) {
				(it.get(MainActivity.CAPTURED_IMAGE) as? String)?.let {
					capturedImagePath = it
					capturedImageBitmap = BitmapFactory.decodeFile(capturedImagePath)
				}
			}
		}
		val filterList = ArrayList<FilterModel>()
		filterList.add(FilterModel(filterName = getString(string.txt_none)))
		for (i in 1 until 16) {
			filterList.add(FilterModel(filterName = getString(string.txt_filter) + i))
		}

		filterAdapter = FilterAdapter(this, filterList, capturedImagePath, this)
		recycler_view_filter_list.adapter = filterAdapter
		Glide.with(this).load(capturedImagePath).into(img_filtered_image)
		img_save.setOnClickListener {
			createBitmapFromView(this, constraint_layout_filtered_image)
		}
	}

	fun createBitmapFromView(ctx: Context, view: View) {
		progress_bar_add_filter.visibility = View.VISIBLE
		view.layoutParams = ConstraintLayout.LayoutParams(
				ConstraintLayout.LayoutParams.MATCH_PARENT,
				ConstraintLayout.LayoutParams.MATCH_PARENT
		)
		val dm = ctx.resources.displayMetrics
		view.measure(
				View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
				View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY)
		)
		view.layout(0, 0, view.measuredWidth, view.measuredHeight)
		val bitmap = Bitmap.createBitmap(
				view.measuredWidth,
				view.measuredHeight,
				Bitmap.Config.ARGB_8888
		)
		val canvas = Canvas(bitmap)
		view.layout(view.left, view.top, view.right, view.bottom)
		view.draw(canvas)
		val path = getOutputDirectory().toString() + (System.currentTimeMillis()) + getString(string.txt_jpg)
		kotlin.runCatching {
			val fout = FileOutputStream(path)
			val bos = BufferedOutputStream(fout)
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
			bos.flush()
			bos.close()
		}.onSuccess {
			progress_bar_add_filter.visibility = View.INVISIBLE
			val image = File(path)
			Glide.with(this@FilterActivity).load(path).into(img_filtered_image)
			Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show()
			MediaScannerConnection.scanFile(
					this, arrayOf(path.toString()),
					null, null)
		}.onFailure {
			it.printStackTrace()
			Toast.makeText(this, "please try again", Toast.LENGTH_LONG).show()
		}
	}

	private fun getOutputDirectory(): File {
		val mediaDir = externalMediaDirs.firstOrNull()?.let {
			File(it, resources.getString(string.app_name)).apply { mkdirs() }
		}
		return if (mediaDir != null && mediaDir.exists())
			mediaDir else filesDir
	}

	override fun onFilterClick(position: Int) {
		val exif = ExifInterface(capturedImagePath)
		val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
		when (position) {
			0 -> Glide.with(this).load(capturedImagePath).into(img_filtered_image)
			1 -> Glide.with(this).load(rotateBitmap(photoFilter.one(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			2 -> Glide.with(this).load(rotateBitmap(photoFilter.two(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			3 -> Glide.with(this).load(rotateBitmap(photoFilter.three(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			4 -> Glide.with(this).load(rotateBitmap(photoFilter.four(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			5 -> Glide.with(this).load(rotateBitmap(photoFilter.five(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			6 -> Glide.with(this).load(rotateBitmap(photoFilter.six(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			7 -> Glide.with(this).load(rotateBitmap(photoFilter.seven(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			8 -> Glide.with(this).load(rotateBitmap(photoFilter.eight(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			9 -> Glide.with(this).load(rotateBitmap(photoFilter.nine(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			10 -> Glide.with(this).load(rotateBitmap(photoFilter.ten(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			11 -> Glide.with(this).load(rotateBitmap(photoFilter.eleven(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			12 -> Glide.with(this).load(rotateBitmap(photoFilter.twelve(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			13 -> Glide.with(this).load(rotateBitmap(photoFilter.thirteen(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			14 -> Glide.with(this).load(rotateBitmap(photoFilter.fourteen(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			15 -> Glide.with(this).load(rotateBitmap(photoFilter.fifteen(this, capturedImageBitmap), orientation))
					.into(img_filtered_image)
			16 -> Glide.with(this).load(rotateBitmap(photoFilter.sixteen(this, capturedImageBitmap), orientation)).into(img_filtered_image)
			else -> Glide.with(this).load(rotateBitmap(photoFilter.one(this, capturedImageBitmap), orientation)).into(img_filtered_image)
		}

	}

	fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
		val matrix = Matrix()
		when (orientation) {
			ExifInterface.ORIENTATION_NORMAL -> return bitmap
			ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
			ExifInterface.ORIENTATION_ROTATE_180 ->
				// matrix.setRotate(180);
				matrix.postRotate(180f)
			ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
				// matrix.setRotate(180);
				matrix.postRotate(180f)
				matrix.postScale(-1f, 1f)
			}
			ExifInterface.ORIENTATION_TRANSPOSE -> {
				// matrix.setRotate(90);
				matrix.postRotate(90f)
				matrix.postScale(-1f, 1f)
			}
			ExifInterface.ORIENTATION_ROTATE_90 ->
				// matrix.setRotate(90);
				matrix.postRotate(90f)
			ExifInterface.ORIENTATION_TRANSVERSE -> {
				// matrix.setRotate(-90);
				matrix.postRotate(-90f)
				matrix.postScale(-1f, 1f)
			}
			ExifInterface.ORIENTATION_ROTATE_270 ->
				// matrix.setRotate(-90);
				matrix.postRotate(-90f)
			else -> return bitmap
		}
		try {
			val bmRotated =
				Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
			bitmap.recycle()

			return bmRotated
		} catch (e: OutOfMemoryError) {
			e.printStackTrace()
			return null
		}

	}
}

