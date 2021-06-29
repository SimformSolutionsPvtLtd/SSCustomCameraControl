package com.customcamerafilters.app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.customcamerafilters.app.model.FilterModel
import com.uvstudio.him.photofilterlibrary.PhotoFilter

class FilterAdapter(
    var context: Context,
    private var filterList: ArrayList<FilterModel>,
    private var capturedImageUri: String,
    var filterClickListener: FilterClickListener?
) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    var searchStockLiveData = MutableLiveData<Int>()
    val photoFilter = PhotoFilter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.filterName?.text =
            (filterList[position].filterName)
        holder.filteredImage?.let {
            val exif = ExifInterface(capturedImageUri)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

//            when(position){
//                0 -> Glide.with(context).load(capturedImageUri).into(it)
//                1 -> Glide.with(context).load(rotateBitmap(photoFilter.one(context, BitmapFactory.decodeFile(capturedImageUri)), orientation )).into(it)
//                2 -> Glide.with(context).load(rotateBitmap(photoFilter.two(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                3 -> Glide.with(context).load(rotateBitmap(photoFilter.three(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                4 -> Glide.with(context).load(rotateBitmap(photoFilter.four(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                5 -> Glide.with(context).load(rotateBitmap(photoFilter.five(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                6 -> Glide.with(context).load(rotateBitmap(photoFilter.six(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                7 -> Glide.with(context).load(rotateBitmap(photoFilter.seven(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                8 -> Glide.with(context).load(rotateBitmap(photoFilter.eight(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                9 -> Glide.with(context).load(rotateBitmap(photoFilter.nine(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                10 -> Glide.with(context).load(rotateBitmap(photoFilter.ten(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                11 -> Glide.with(context).load(rotateBitmap(photoFilter.eleven(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                12 -> Glide.with(context).load(rotateBitmap(photoFilter.twelve(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                13 -> Glide.with(context).load(rotateBitmap(photoFilter.thirteen(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                14 -> Glide.with(context).load(rotateBitmap(photoFilter.fourteen(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                15 -> Glide.with(context).load(rotateBitmap(photoFilter.fifteen(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                16 -> Glide.with(context).load(rotateBitmap(photoFilter.sixteen(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//                else -> Glide.with(context).load(rotateBitmap(photoFilter.one(context, BitmapFactory.decodeFile(capturedImageUri)),orientation)).into(it)
//            }
//            Glide.with(context).load(capturedImageUri).into(it)
            it.setOnClickListener { filterClickListener?.onFilterClick(position) }
        }


//        holder.ll?.setOnClickListener {
//            stockClickListner?.stockOnClick(position)
//        }
//        if (isBlackBackground) {
//            holder.ll?.setOnClickListener {
//                searchStockLiveData.value = position
//            }
//            holder.ll?.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
//        }
    }

    override fun getItemCount(): Int {
        return filterList.size
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var filterName: AppCompatTextView? = null
        var filteredImage: ImageView? = null
//        var ll: LinearLayout? = null

        init {
            filterName = view.findViewById(R.id.tv_filer_name)
            filteredImage = view.findViewById(R.id.img_filter)
        }
    }
}

