package com.example.saladdetector.src.fragments.orders_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saladdetector.src.bd.order.Order
import com.example.saladdetector.src.domain_entyties.DetectedProduct
import com.example.saladdetector.src.domain_entyties.OrderInfo
import com.example.saladdetector.src.repos.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    orderRepository: OrderRepository
) : ViewModel() {

    private val _orders = MutableLiveData<List<OrderInfo>>()
    val orders: LiveData<List<OrderInfo>> = _orders

    init {
        viewModelScope.launch {
            _orders.postValue(orderRepository.getOrdersInfo())
        }
    }



}