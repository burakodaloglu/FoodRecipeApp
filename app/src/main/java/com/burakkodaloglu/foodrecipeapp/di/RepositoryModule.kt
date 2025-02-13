package com.burakkodaloglu.foodrecipeapp.di

import com.burakkodaloglu.foodrecipeapp.data.repository.AuthRepository
import com.burakkodaloglu.foodrecipeapp.data.repository.RecipeRepositoryImpl
import com.burakkodaloglu.foodrecipeapp.data.source.remote.RecipeService
import com.burakkodaloglu.foodrecipeapp.domain.repository.RecipeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindAuthRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository =
        AuthRepository(auth, firestore)

    @Provides
    fun provideCategoryRepo(
        recipeService: RecipeService,
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService)
    }
}