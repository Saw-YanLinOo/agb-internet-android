package com.agb.customer.billing.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.agb.customer.billing.R
import com.agb.customer.billing.adapter.ActivePlanSpinnerAdapter
import com.agb.customer.billing.adapter.CategorySpinnerAdapter
import com.agb.customer.billing.databinding.ActivityComplainBinding
import com.agb.customer.billing.dialog.ImageViewerDialog
import com.agb.customer.billing.modelVO.ActivePlanVO
import com.agb.customer.billing.modelVO.CategoryComplainVO
import com.agb.customer.billing.modelVO.ComplainVO
import com.agb.customer.billing.modelVO.UserVO
import com.agb.customer.billing.networks.requests.EmptyRequest
import com.agb.customer.billing.networks.requests.TicketCreateUpdateRequest
import com.agb.customer.billing.networks.responses.ComplainPreloadResponse
import com.agb.customer.billing.networks.responses.TicketResponse
import com.agb.customer.billing.utils.ImageUtils
import com.agb.customer.billing.utils.PreferenceUtils
import com.agb.customer.billing.viewmodels.ComplainViewModel
import com.agb.customer.billing.views.ComplainView
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker

class ComplainActivity : BaseActivity(), ComplainView {
    lateinit var binding: ActivityComplainBinding
    private var mImageByte: String = ""
    private var fileUri = Uri.parse("")
    lateinit var mViewModel: ComplainViewModel
    var activePlanList: MutableList<ActivePlanVO> = mutableListOf()
    var categoryList: MutableList<CategoryComplainVO> = mutableListOf()
    lateinit var mUser: UserVO

    private var activePlanVO: ActivePlanVO? = null
    private var categoryComplainVO: CategoryComplainVO? = null

    companion object {
        private var complainVO: ComplainVO? = null
        fun newInstance(mContext: Context, complainVO: ComplainVO? = null): Intent {
            this.complainVO = complainVO
            return Intent(mContext, ComplainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        clickEvent()
    }


    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(ComplainViewModel::class.java)
        mViewModel.setView(this)

        mUser = PreferenceUtils.getUser()

        viewLayoutVisible(false)
        preLoadApiCall()
    }


    private fun preLoadApiCall() {
        val request = EmptyRequest()
        mViewModel.getComplainPreLoad(request)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onBindEditData() {
        val indexP = activePlanList.indexOfFirst { it.id.toString() == complainVO!!.activePlanId!! }
        if (indexP >= 0)
            binding.spnActivePlan.setSelection(indexP)

        val index =
            categoryList.indexOfFirst { it.id.toString() == complainVO!!.complainCategoryId!! }
        if (index >= 0)
            binding.spnCategory.setSelection(index)

        if (!complainVO!!.complainPhoto.isNullOrEmpty()) {
            Glide.with(binding.root)
                .load(complainVO!!.complainPhoto)
                .into(binding.ivPhoto)
            binding.imgRemove.visibility = View.VISIBLE
            binding.ivPhoto.visibility = View.VISIBLE
            binding.lblUploadPhoto.visibility = View.GONE
            binding.lyPhotoButton.visibility = View.GONE
        }

        binding.apply {
            etMessage.setText(complainVO!!.complainMessage)
            tvTicketNumber.text = complainVO!!.ticketNumber
            tvTicketStatus.text = complainVO!!.ticketStatusDesc
            etReply.setText(complainVO!!.callCenterAnswer)
        }

        binding.apply {

            spnActivePlan.isEnabled = false
            spnCategory.isEnabled = false
            btnOpenGallery.visibility = View.GONE
            btnOpenCamera.visibility = View.GONE
            tvSubmit.visibility = View.GONE
            lblUploadPhoto.visibility = View.GONE
            imgRemove.visibility = View.GONE

            lblReply.visibility = View.VISIBLE
            etReply.visibility = View.VISIBLE
            lblTicketNumber.visibility = View.VISIBLE
            tvTicketNumber.visibility = View.VISIBLE
            lblTicketStatus.visibility = View.VISIBLE
            tvTicketStatus.visibility = View.VISIBLE
        }
        binding.etMessage.requestFocus()
        binding.etMessage.showSoftInputOnFocus = false

        binding.etMessage.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)

                }
            }
            false
        }

        binding.etReply.requestFocus()
        binding.etReply.showSoftInputOnFocus = false

        binding.etReply.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)

                }
            }
            false
        }

    }

    private fun clickEvent() {

        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
            tvDialogCancel.setOnClickListener {
                onBackPressed()
            }
            ivPhoto.setOnClickListener {
                showPopUp()
            }
            imgRemove.setOnClickListener {
                onBindData()
            }
            btnOpenCamera.setOnClickListener {
                openCamera()
            }
            btnOpenGallery.setOnClickListener {
                openGallery()
            }
            tvSubmit.setOnClickListener {
                checkInput()
            }
        }
    }

    private fun showPopUp() {
        val imageViewerDialog = ImageViewerDialog(
            fileUri, complainVO
        )
        imageViewerDialog.show(supportFragmentManager, "Image")
    }

    private fun checkInput() {

        if (!binding.etMessage.text.isNullOrEmpty()) {
            viewLayoutVisible(false)
            callComplainAPI()
        } else
            binding.etMessage.error = getString(R.string.require)
    }

    private fun onBindData() {
        binding.imgRemove.visibility = View.GONE
        binding.ivPhoto.visibility = View.GONE
        binding.lblUploadPhoto.visibility = View.VISIBLE
        binding.lyPhotoButton.visibility = View.VISIBLE
        mImageByte = ""
        Glide.with(binding.root)
            .load("")
            .into(binding.ivPhoto)
    }


    private fun callComplainAPI() {

        val serviceID = if (complainVO == null)
            activePlanVO!!.specialcode.toString() //first time ticket id from active plan list
        else
            complainVO?.serviceId


        binding.apply {
            val request = TicketCreateUpdateRequest(
                ticketId = complainVO?.ticketId.toString(),
                customerId = mUser.customerId.toString(),
                customerUid = mUser.uid.toString(),
                userName = mUser.username.toString(),
                serviceId = serviceID,
                activePlanId = activePlanVO?.id!!.toString(),
                activePlanText = activePlanVO?.packagename!!,
                complainCategoryId = categoryComplainVO?.id.toString(),
                complainCategoryText = categoryComplainVO?.categoryName,
                complainMessage = etMessage.text.toString(),
                complainPhoto = mImageByte,
                sessionId = mUser.sessionId.toString(),

                )

            mViewModel.uploadComplain(request)
        }

    }

    private fun openCamera() {

        ImagePicker.with(this)
            .crop()
            .cameraOnly()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { Intent: Intent? ->
                resultForProfileImageResult.launch(Intent)
            }
    }

    private fun openGallery() {
        ImagePicker.with(this)
            .galleryOnly()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                resultForProfileImageResult.launch(intent)
            }
    }

    private val resultForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    binding.imgRemove.visibility = View.VISIBLE
                    binding.ivPhoto.visibility = View.VISIBLE
                    binding.lblUploadPhoto.visibility = View.GONE
                    binding.lyPhotoButton.visibility = View.GONE
                    //Image Uri will not be null for RESULT_OK
                    fileUri = data?.data!!
                    Glide.with(binding.root)
                        .load(fileUri)
                        //.placeholder(R.drawable.ic_user_default)
                        //.error(R.drawable.ic_user_default)

                        .into(binding.ivPhoto)

                    imageToByte(fileUri)

                }

                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }

                else -> {
//                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }

        }


    private fun imageToByte(imageUri: Uri) {
        val mBitmap = ImageUtils(this).getImageFromUri(imageUri)
        mBitmap?.let {
            mImageByte = ImageUtils(this).encodeBase64String(it)
        }
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            if (b) {
                shimmerLoading.stopShimmer()
                cvSubmit.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                cvSubmit.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun setData(response: ComplainPreloadResponse) {
        viewLayoutVisible(true)

        if (response.data != null) {
            activePlanList = response.data!!.activePlanList!!
            categoryList = response.data!!.categoryComplainList!!
            bindSpinner()

        } else {
            hideLoadingError()
        }
    }

    override fun setUploadResponseData(response: TicketResponse) {
        viewLayoutVisible(true)
        if (response.responseCode == "1") {
            Toast.makeText(this, response.responseMessage, Toast.LENGTH_SHORT).show()
            // onBindData()
            //binding.etMessage.setText("")
            finish()

        } else {
            hideLoadingError()
        }
    }

    private fun bindSpinner() {
        val spnStateAdapter = ActivePlanSpinnerAdapter(this, activePlanList)
        binding.spnActivePlan.adapter = spnStateAdapter

        val adapter = CategorySpinnerAdapter(this, categoryList)
        binding.spnCategory.adapter = adapter

        binding.spnActivePlan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                pos: Int,
                p3: Long,
            ) {
                activePlanVO = activePlanList[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spnCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                pos: Int,
                p3: Long,
            ) {
                categoryComplainVO = categoryList[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        if (complainVO != null)
            onBindEditData()
    }


    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvSubmit.visibility = View.GONE
            lyError.ivError.setImageResource(R.drawable.ic_component_wifi)
            lyError.tvError.text = resources.getString(R.string.error_wifi)
            lyError.layoutError.visibility = View.VISIBLE
        }
    }

    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvSubmit.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.ivError.setImageResource(R.drawable.ic_home_complain)
            lyError.tvError.text = resources.getString(R.string.error_complain)
        }
    }

    override fun showVersionUpdate(message: String, storeUrl: String) {
        showVersionUpdateDialog(message, storeUrl)
        viewLayoutVisible(true)
    }

    override fun showError(message: String, code: String) {
        hideLoadingNetwork()
    }

    override fun showInvalidSession(message: String, code: String) {
        mInvalidSession(this, message)
    }

    override fun showNetworkFailed() {
        hideLoadingNetwork()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }


}