package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.functional.FlowResult
import com.example.data.core.NetworkFailure
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.local.UserLocalDataSourceImpl
import com.example.data.datasource.local.query.QueryLocalDataSource
import com.example.data.datasource.local.query.QueryLocalDataSourceImpl
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import com.example.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject internal constructor(
    queryDataSourceImpl: QueryLocalDataSourceImpl,
    userDataSourceImpl: UserLocalDataSourceImpl,
    remoteDataSourceImpl: UserRemoteDataSourceImpl
) : UserRepository {

    private val queryDataSource: QueryLocalDataSource = queryDataSourceImpl

    private val userDataSource: UserLocalDataSource = userDataSourceImpl

    private val remoteDataSource: UserRemoteDataSource = remoteDataSourceImpl

    override suspend fun loadUsers(query: String): Flow<PagingData<UserEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                prefetchDistance = 20
            ),

            remoteMediator = UserRemoteMediator(
                queryDataSource = queryDataSource,
                userDataSource = userDataSource,
                remoteDataSource = remoteDataSource,
                query = query
            ),

            pagingSourceFactory = {
                userDataSource.loadUsers(query)
            }
        ).flow
}