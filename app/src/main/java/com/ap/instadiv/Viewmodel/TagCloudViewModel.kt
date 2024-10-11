package com.ap.instadiv.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TagCloudViewModel : ViewModel() {

    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> = _tags

    private val _selectedTag = MutableStateFlow<String?>(null)
    val selectedTag: StateFlow<String?> = _selectedTag

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val firestore = FirebaseFirestore.getInstance()

    init {
        fetchTags()
    }

    private fun fetchTags() {
        _isLoading.value = true
        viewModelScope.launch {
            firestore.collection("tags")
                .get()
                .addOnSuccessListener { result ->
                    _tags.value = result.documents.map { it.getString("tag") ?: "" }
                    _isLoading.value = false
                }
                .addOnFailureListener { exception ->
                    _errorMessage.value = exception.message
                    _isLoading.value = false
                }
        }
    }

    fun selectTag(tag: String) {
        _selectedTag.value = tag
    }

    fun clearSelection() {
        _selectedTag.value = null
    }
}
