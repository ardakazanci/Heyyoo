package com.ardakazanci.samplesocialmediaapp.ui.main.ui.content

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.ContentAddFragmentBinding



class ContentAddFragment : Fragment() {


    private lateinit var binding: ContentAddFragmentBinding
    private lateinit var viewModel: ContentAddViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.content_add_fragment,
            container,
            false
        )

        binding.lifecycleOwner = requireActivity()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(ContentAddViewModel::class.java)

        binding.contentViewModel = viewModel

        binding.imgImagePick.visibility = View.GONE


    }


    /* @SuppressLint("WrongConstant")
 private fun checkPermissionForImage() {
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         if ((checkSelfPermission(
                 this.requireContext(),
                 Manifest.permission.READ_EXTERNAL_STORAGE
             ) == PackageManager.PERMISSION_DENIED)
             && (checkSelfPermission(
                 this.requireContext(),
                 Manifest.permission.WRITE_EXTERNAL_STORAGE
             ) == PackageManager.PERMISSION_DENIED)
         ) {
             val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
             val permissionCoarse = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

             requestPermissions(
                 permission,
                 PERMISSION_CODE_READ
             ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_READ LIKE 1001
             requestPermissions(
                 permissionCoarse,
                 PERMISSION_CODE_WRITE
             ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_WRITE LIKE 1002
         } else {
             pickImageFromGallery()
         }
     }
 }

 private fun pickImageFromGallery() {
     val intent = Intent(Intent.ACTION_PICK)
     intent.type = "image/*"
     activity!!.startActivityForResult(
         intent,
         IMAGE_PICK_CODE
     )
 }

 companion object {
     val IMAGE_PICK_CODE = 0
     private val PERMISSION_CODE_WRITE = 1
     private val PERMISSION_CODE_READ = 3
 }

 override fun onDestroy() {
     super.onDestroy()
     Log.e("Destroy", "Çalıştı")
 }

 override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

     if(ImagePicker.shouldHandle(requestCode,resultCode,data)){
         val image = ImagePicker.getFirstImageOrNull(data)
         binding.imageView2.setImageBitmap(BitmapFactory.decodeFile(image.path))
     }

     super.onActivityResult(requestCode, resultCode, data)

 }
 */
 */



}
