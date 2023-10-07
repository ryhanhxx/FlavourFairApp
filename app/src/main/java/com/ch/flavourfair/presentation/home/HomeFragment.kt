package com.ch.flavourfair.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ch.flavourfair.R
import com.ch.flavourfair.data.dummy.DummyCategoryDataSource
import com.ch.flavourfair.data.dummy.DummyCategoryDataSourceImpl
import com.ch.flavourfair.data.dummy.DummyProductDataSource
import com.ch.flavourfair.data.dummy.DummyProductDataSourceImpl
import com.ch.flavourfair.data.local.database.AppDatabase
import com.ch.flavourfair.data.local.database.datasource.ProductDatabaseDataSource
import com.ch.flavourfair.data.local.datastore.UserPreferenceDataSourceImpl
import com.ch.flavourfair.data.local.datastore.appDataStore
import com.ch.flavourfair.data.repository.ProductRepository
import com.ch.flavourfair.data.repository.ProductRepositoryImpl
import com.ch.flavourfair.databinding.FragmentHomeBinding
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.presentation.detail.DetailProductActivity
import com.ch.flavourfair.presentation.home.adapter.subadapter.AdapterLayout
import com.ch.flavourfair.presentation.home.adapter.subadapter.CategoryAdapter
import com.ch.flavourfair.presentation.home.adapter.subadapter.ProductAdapter
import com.ch.flavourfair.presentation.main.MainViewModel
import com.ch.flavourfair.utils.GenericViewModelFactory
import com.ch.flavourfair.utils.PreferenceDataStoreHelperImpl
import com.ch.flavourfair.utils.proceedWhen
import com.ch.flavourfair.utils.toCurrencyFormat

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val datasourceProduct: DummyProductDataSource by lazy {
        DummyProductDataSourceImpl()
    }

    private val datasourceCategory: DummyCategoryDataSource by lazy {
        DummyCategoryDataSourceImpl()
    }

    private val adapter: ProductAdapter by lazy {
        ProductAdapter(AdapterLayout.LINEAR) { product: Product ->
            navigateToDetailFragment(product)
        }
    }

    private val adapterCategory: CategoryAdapter by lazy {
        CategoryAdapter {

        }
    }

    private fun navigateToDetailFragment(product: Product) {
        DetailProductActivity.startActivity(requireContext(), product)
    }

    private val viewModel: HomeViewModel by viewModels {
        val cds: DummyCategoryDataSource = DummyCategoryDataSourceImpl()
        val database = AppDatabase.getInstance(requireContext())
        val productDao = database.productDao()
        val productDataSource = ProductDatabaseDataSource(productDao)
        val repo: ProductRepository = ProductRepositoryImpl(productDataSource, cds)
        GenericViewModelFactory.create(HomeViewModel(repo))
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
        fetchData()

    }

    private fun setObserveData() {
        viewModel.productData.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvHome.isVisible = true
                    result.payload?.let {
                        adapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvHome.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvHome.isVisible = false
                }, doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = "Empty"
                    binding.rvHome.isVisible = false
                }
            )
        }
    }

    private fun setupCategoryRecyclerView() {
        binding.rvCategory.adapter = adapterCategory
        adapterCategory.setItems(DummyCategoryDataSourceImpl().getCategoryData())
    }

    private fun setupRecyclerview() {
        val span = if (adapter.adapterLayout == AdapterLayout.LINEAR) 1 else 2
        binding.rvHome.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = this@HomeFragment.adapter
        }/*
        adapter.setData(datasourceProduct.getProductData())*/
        setObserveData()
    }

    private fun setupSwitch() {
        viewModel.productData.observe(viewLifecycleOwner){
            val layoutManager = binding.rvHome.layoutManager as GridLayoutManager
            binding.ivBtnchangeview.setOnClickListener {
                val newSpanCount = if (layoutManager.spanCount == 1) 2 else 1
                layoutManager.spanCount = newSpanCount
                adapter.adapterLayout =
                    if (newSpanCount == 2) AdapterLayout.GRID else AdapterLayout.LINEAR
                adapter.refreshList()
            }
        }
    }

    private fun fetchData() {
        viewModel.productData.observe(viewLifecycleOwner) {
            adapter.setData(datasourceProduct.getProductData())
        }
    }

    /*private fun setupRecyclerview() {
        viewModelDataStore.userGridModeLiveData.observe(viewLifecycleOwner) { isUsingGridMode ->
            val span = if (isUsingGridMode) 2 else 1
            binding.rvHome.apply {
                layoutManager = GridLayoutManager(requireContext(), span)
                adapter = this@HomeFragment.adapter
            }
            viewModel.homeData.observe(viewLifecycleOwner) {
                adapter.setData(it)
            }
        }
    }

    private fun setupSwitch() {
        val layoutManager = binding.rvHome.layoutManager as GridLayoutManager
        binding.ivBtnchangeview.setOnClickListener {
            val newGridMode = layoutManager.spanCount == 1
            viewModelDataStore.setUserGridModePref(newGridMode)
        }
    }*/

}