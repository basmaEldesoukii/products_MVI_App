package com.example.products_list.presentation

import android.content.res.Resources
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.base.BaseViewHolder
import com.example.products_list.data.ProductsListDataModel
import com.example.products_list.databinding.SingleProductCardBinding


class ProductItemViewHolder(private val binding: SingleProductCardBinding,
                            private val viewModel: ProductsListViewModel? = null
): BaseViewHolder<ProductsListDataModel, SingleProductCardBinding>(binding) {


    init {
        binding.root.setOnClickListener {
            getRowItem()?.let { productItem ->
                viewModel?.setAction(
                    ProductsListContract.Action.onProductClicked(
                        productItem
                    )
                )
            }
        }
    }

    override fun bind() {
        getRowItem()?.let {
            binding.productTitle.text = it.title
           /* val imageBytes: ByteArray = Base64.decode(it.image, Base64.DEFAULT)
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.productImg.setImageBitmap(bitmap)*/
            //binding.productImg.setImageResource(it.image.toInt())
            //binding.productImg.setImageResource(getResources().getIdentifier("ImageName","drawable",getPackageName()));
        }
    }


}