package com.sonder.peeker.presentation.document_list;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\b\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u000eH\u0002J\b\u0010\u0013\u001a\u00020\u000eH\u0002J\u0006\u0010\u0014\u001a\u00020\u000eJ\u0006\u0010\u0015\u001a\u00020\u000eJ\u0006\u0010\u0016\u001a\u00020\u000eJ\u000e\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0011J\u0006\u0010\u0019\u001a\u00020\fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u001a"}, d2 = {"Lcom/sonder/peeker/presentation/document_list/DocumentListViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_state", "Landroidx/compose/runtime/MutableState;", "Lcom/sonder/peeker/presentation/document_list/DocumentListState;", "state", "Landroidx/compose/runtime/State;", "getState", "()Landroidx/compose/runtime/State;", "documents", "", "", "getAllDocuments", "", "getDocumentsByTag", "tagIndex", "", "getExpiredDocuments", "getFavoriteDocuments", "selectAll", "selectExpired", "selectFavorites", "selectTag", "index", "selectedTitle", "app_debug"})
public final class DocumentListViewModel extends androidx.lifecycle.ViewModel {
    private final androidx.compose.runtime.MutableState<com.sonder.peeker.presentation.document_list.DocumentListState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.State<com.sonder.peeker.presentation.document_list.DocumentListState> state = null;
    
    @javax.inject.Inject()
    public DocumentListViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.State<com.sonder.peeker.presentation.document_list.DocumentListState> getState() {
        return null;
    }
    
    private final void getFavoriteDocuments() {
    }
    
    private final void getExpiredDocuments() {
    }
    
    private final void getAllDocuments() {
    }
    
    private final void getDocumentsByTag(int tagIndex) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> documents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String selectedTitle() {
        return null;
    }
    
    public final void selectFavorites() {
    }
    
    public final void selectExpired() {
    }
    
    public final void selectAll() {
    }
    
    public final void selectTag(int index) {
    }
}