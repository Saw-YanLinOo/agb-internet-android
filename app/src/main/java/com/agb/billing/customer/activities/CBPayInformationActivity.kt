package com.agb.billing.customer.activities

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ActivityCbPayDetailBinding
import com.agb.billing.customer.modelVO.CBPaymentVO
import com.agb.billing.customer.modelVO.InvoiceVO
import com.agb.billing.customer.networks.requests.CBPaymentQRDownloadRequest
import com.agb.billing.customer.networks.responses.CBPaymentQRDownloadQRResponse
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.utils.DialogUtil
import com.agb.billing.customer.viewmodels.CBPayInformationViewModel
import com.agb.billing.customer.views.CBPayInformationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class CBPayInformationActivity : BaseActivity(), CBPayInformationView {
    lateinit var binding: ActivityCbPayDetailBinding
    lateinit var mViewModel: CBPayInformationViewModel

    private var mBitmap: Bitmap? = null
    private lateinit var permissions: Array<String>
    private var allPermissions = 1

    companion object {
        private var invoiceVO: InvoiceVO? = null
        private var isMainScreen: Boolean = false
        fun newInstance(
            mContext: Context,
            data: InvoiceVO?,
            isMainScreen: Boolean = false,
        ): Intent {
            invoiceVO = data
            this.isMainScreen = isMainScreen
            return Intent(mContext, CBPayInformationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCbPayDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        clickEvent()

    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(CBPayInformationViewModel::class.java)
        mViewModel.setView(this)

        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            tvTransactionId.text = invoiceVO?.invnumber.toString()
            tvProductId.text = invoiceVO?.specialcode.toString()
            tvAmount.text = invoiceVO?.totalcost.toString()
            tvReferenceNo.text = invoiceVO?.referenceNo.toString()

            try {
                Glide.with(this@CBPayInformationActivity)
                    .asBitmap()
                    .load(invoiceVO?.cbPayQRUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(object : CustomTarget<Bitmap?>() {
                        override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap?>?,
                        ) {
                            //mBitmap = resource
                            imgQr.setImageBitmap(resource)

                        }
                    })


            } catch (ex: Exception) {

            }
        }
    }


    private fun clickEvent() {

        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }
            btnSave.setOnClickListener {
                checkPermission()
            }
            btnCheckPayment.setOnClickListener {
                if (isMainScreen)
                    goToInvoiceList()
                else
                    onBackPressed()
            }
        }
    }

    private fun getCBPaymentQRDownload() {
        showProgress()
        val request = CBPaymentQRDownloadRequest()
        request.invnumber = invoiceVO!!.invnumber
        request.serviceID = invoiceVO!!.specialcode
        mViewModel.getCBPaymentQRDownload(request)

    }

    private fun goToInvoiceList() {
        startActivity(InvoiceActivity.newInstance(this))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }


    /*    private fun storeGallery(): String? {
            val folderName = "AGB Internet"
            val imgUrl = invoiceVO!!.cbPayQRUrl!!
            val fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1);
            var fos: FileOutputStream? = null
            val status = Environment.getExternalStorageState()
            if (status == Environment.MEDIA_MOUNTED) {
                val root = File(
                    Environment.getExternalStorageDirectory(),
                    folderName
                )

                if (!root.exists()) {
                    root.mkdirs()
                }

                val file = File(root, fileName)

                try {
                    fos = FileOutputStream(file)
                    mBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()
                    Toast.makeText(this, "Image is saved successfully!", Toast.LENGTH_SHORT).show()

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                val muri = Uri.fromFile(file)
                val path = muri.path
                MediaScannerConnection.scanFile(
                    this,
                    arrayOf(file.absolutePath),
                    null
                ) { path, _ -> Log.d("Scan", "scanned : $path") }
                return path
            }
            return null
        }*/

    private fun saveMediaToStorage(cbPaymentVO: CBPaymentVO) {
        val folderName = "AGB Internet"
        val imgUrl = cbPaymentVO.pdfUrl!!
        val fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1)

        var fos: OutputStream?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            this.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {

                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        "${Environment.DIRECTORY_PICTURES}/$folderName"
                    )

                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
                fos?.use {
                    mBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    Toast.makeText(this, "QR saved to photo gallery.", Toast.LENGTH_SHORT).show()

                }
            }
        } else {
            val root = File(
                Environment.getExternalStorageDirectory(),
                folderName
            )
            if (!root.exists()) {
                root.mkdirs()
            }

            val file = File(root, fileName)
            fos = FileOutputStream(file)

            fos?.use {
                mBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, it)
                Toast.makeText(this, "QR saved to photo gallery.", Toast.LENGTH_SHORT).show()
            }

        }
        dismissProgress()

    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

    private fun checkPermission() {

        permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        } else
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val agree = hasPermissions(this, *permissions)
        if (!agree) {
            ActivityCompat.requestPermissions(this, permissions, allPermissions)
        } else {

            getCBPaymentQRDownload()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == allPermissions) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getCBPaymentQRDownload()
                    break
                }

            }

        }

    }

    private fun hasPermissions(
        context: Context?,
        vararg permissions: String?,
    ): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }

            }

        }
        return true
    }

    override fun setPaymentQRResponse(response: CBPaymentQRDownloadQRResponse) {


        if (response.data != null) {

            Glide.with(this@CBPayInformationActivity)
                .asBitmap()
                .load(response.data!!.pdfUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?,
                    ) {
                        mBitmap = resource
                        saveMediaToStorage(response.data!!)

                    }
                })
        }
    }

    private fun dismissProgress() {
        try {
            showProgressDialog?.dismiss()
        } catch (ex: Exception) {

        }
    }


    override fun showError(message: String, code: String) {
        dismissProgress()
        DialogUtil(this).showErrorDialog(getString(R.string.errorTitle), message)

    }

    override fun showInvalidSession(message: String, code: String) {
        dismissProgress()
        mInvalidSession(this, message)

    }

    override fun showNetworkFailed() {
        dismissProgress()
        DialogUtil(this).showErrorDialog(
            getString(R.string.errorTitle),
            Constants.CONNECTION_FAIL
        )

    }

    private fun showProgress() {
        try {
            showProgressDialog?.show()
        } catch (ex: Exception) {

        }

    }

}