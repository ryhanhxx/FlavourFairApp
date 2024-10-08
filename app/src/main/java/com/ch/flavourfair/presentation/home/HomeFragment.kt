package com.ch.flavourfair.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ch.flavourfair.R
import com.ch.flavourfair.data.dummy.DummyCategoryDataSourceImpl
import com.ch.flavourfair.data.local.datastore.UserPreferenceDataSourceImpl
import com.ch.flavourfair.data.local.datastore.appDataStore
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
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val adapter: ProductAdapter by lazy {
        ProductAdapter(AdapterLayout.LINEAR) { product: Product ->
            navigateToDetailFragment(product)
        }
    }

    private val adapterCategory: CategoryAdapter by lazy {
        CategoryAdapter {
            viewModel.getProducts(it.slug)
        }
    }

    private fun navigateToDetailFragment(product: Product) {
        DetailProductActivity.startActivity(requireContext(), product)
    }

    private val viewModel: HomeViewModel by viewModel()

    private val mainViewModel: MainViewModel by viewModels {
        val dataStore = this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(MainViewModel(userPreferenceDataSource))
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
        observeData()
        getData()
    }

    private fun getData() {
        viewModel.getCategories()
        viewModel.getProducts()
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateCategory.root.isVisible = false
                binding.layoutStateCategory.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = false
                binding.rvCategory.apply {
                    isVisible = true
                    adapter = adapterCategory
                }
                it.payload?.let { data -> adapterCategory.setItems(data) }
            }, doOnLoading = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = true
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvCategory.isVisible = false
                }, doOnError = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategory.isVisible = false
                })
        }
        viewModel.products.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateProduct.root.isVisible = false
                binding.layoutStateProduct.pbLoading.isVisible = false
                binding.layoutStateProduct.tvError.isVisible = false
                binding.rvHome.apply {
                    isVisible = true
                    adapter = adapter
                }
                it.payload?.let { data -> adapter.setData(data) }
            }, doOnLoading = {
                    binding.layoutStateProduct.root.isVisible = true
                    binding.layoutStateProduct.pbLoading.isVisible = true
                    binding.layoutStateProduct.tvError.isVisible = false
                    binding.rvHome.isVisible = false
                }, doOnError = {
                    binding.layoutStateProduct.root.isVisible = true
                    binding.layoutStateProduct.pbLoading.isVisible = false
                    binding.layoutStateProduct.tvError.isVisible = true
                    binding.layoutStateProduct.tvError.text = it.exception?.message.orEmpty()
                    binding.rvHome.isVisible = false
                }, doOnEmpty = {
                    binding.layoutStateProduct.root.isVisible = true
                    binding.layoutStateProduct.pbLoading.isVisible = false
                    binding.layoutStateProduct.tvError.isVisible = true
                    binding.layoutStateProduct.tvError.setText(R.string.product_not_found)
                    binding.rvHome.isVisible = false
                })
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
        }
    }
}
