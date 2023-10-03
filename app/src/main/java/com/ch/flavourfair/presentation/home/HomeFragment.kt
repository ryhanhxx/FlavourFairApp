package com.ch.flavourfair.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ch.flavourfair.data.dummy.CategoryDataSource
import com.ch.flavourfair.data.dummy.CategoryDataSourceImpl
import com.ch.flavourfair.data.dummy.ProductDataSource
import com.ch.flavourfair.data.dummy.ProductDataSourceImpl
import com.ch.flavourfair.databinding.FragmentHomeBinding
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.presentation.detail.DetailProductActivity
import com.ch.flavourfair.presentation.home.adapter.AdapterLayout
import com.ch.flavourfair.presentation.home.adapter.CategoryAdapter
import com.ch.flavourfair.presentation.home.adapter.ProductAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val datasourceProduct: ProductDataSource by lazy {
        ProductDataSourceImpl()
    }

    private val datasourceCategory: CategoryDataSource by lazy {
        CategoryDataSourceImpl()
    }

    private val adapter: ProductAdapter by lazy {
        ProductAdapter(AdapterLayout.LINEAR) { product: Product ->
            navigateToDetailFragment(product)
        }
    }

    private val adapterCategory: CategoryAdapter by lazy{
        CategoryAdapter {

        }
    }

    private fun navigateToDetailFragment(product: Product) {
        DetailProductActivity.startActivity(requireContext(), product)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryRecyclerView()
        setupRecyclerview()
        setupSwitch()
    }

    private fun setupCategoryRecyclerView() {
        binding.rvCategory.adapter = adapterCategory
        adapterCategory.setData(CategoryDataSourceImpl().getCategoryData())
    }

    private fun setupRecyclerview() {
        val span = if (adapter.adapterLayout == AdapterLayout.LINEAR) 1 else 2
        binding.rvFoods.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = this@HomeFragment.adapter
        }
        adapter.setData(datasourceProduct.getProductData())
    }

    private fun setupSwitch() {
        val layoutManager = binding.rvFoods.layoutManager as GridLayoutManager
        binding.ivBtnchangeview.setOnClickListener {
            val newSpanCount = if (layoutManager.spanCount == 1) 2 else 1
            layoutManager.spanCount = newSpanCount
            adapter.adapterLayout =
                if (newSpanCount == 2) AdapterLayout.GRID else AdapterLayout.LINEAR
            adapter.refreshList()
        }
    }




}