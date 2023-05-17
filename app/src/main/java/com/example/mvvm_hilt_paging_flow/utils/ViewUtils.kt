package com.example.mvvm_hilt_paging_flow.utils

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_hilt_paging_flow.R
import com.example.mvvm_hilt_paging_flow.domain.model.utils.getHumanReadableText
import com.example.mvvm_hilt_paging_flow.domain.model.utils.toIIError
import com.example.mvvm_hilt_paging_flow.ui.common.loadstate.ListLoadStateView
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce


@OptIn(FlowPreview::class)
suspend fun PagingDataAdapter<*, *>.listenOnLoadState(
    recyclerView: RecyclerView,
    loadStateView: ListLoadStateView,
    isEmpty: () -> Boolean,
    emptyMessage: String
) {

    loadStateView.setOnRetryListener { this.retry() }
    loadStateFlow.debounce(500).collectLatest { loadState ->

        val context = loadStateView.context
        loadStateView.hide()
        recyclerView.isVisible
            loadState.refresh is LoadState.NotLoading ||
                    loadState.source.refresh is LoadState.NotLoading

        loadStateView.isLoadingVisible = loadState.refresh is LoadState.Loading
        if(loadState.refresh is LoadState.Error){
            val sourceErrorState=loadState.source.refresh as? LoadState.Error
            val mediatorErrorState=loadState.mediator?.refresh as? LoadState.Error
            if(sourceErrorState != null){
                loadStateView.showErrorMessage(sourceErrorState.error.localizedMessage.toString())

            }else{
                recyclerView.isVisible=true

                if(mediatorErrorState !=null){
                    loadStateView.showErrorMessage(mediatorErrorState.toIIError().getHumanReadableText(context)
                    )
                }
            }

            (loadState.mediator?.refresh as LoadState.Error)?.let {
                context.showToast(
                    context.getString(R.string.api_error_prefix) + it.error.toString()
                )
            }
        }
        else{
            loadStateView.hideErrorMessage()
        }

        if(loadState.refresh is LoadState.NotLoading && isEmpty.invoke()){
            recyclerView.isVisible=false
            loadStateView.showErrorMessage(emptyMessage,false)
        }
    }


}