package com.deloitte.pokedex.application

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.deloitte.pokedex.database.Converters
import com.deloitte.pokedex.database.PokedexDatabase
import com.deloitte.pokedex.dataflow.PokedexDataFlow
import com.deloitte.pokedex.service.PokedexRepository
import com.deloitte.pokedex.service.PokedexRepositoryImpl
import com.deloitte.pokedex.service.PokedexServices
import com.deloitte.pokedex.util.BASE_URL_POKEMON_API
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PokedexApplication : Application() {

    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()


        val appModule = module {
            /*database*/
            single { Moshi.Builder().build() }
            single { Converters(get()) }
            factory { provideDatabase(get(), get()) }
            /*viewmodel*/
            viewModel { PokedexDataFlow(get(), get()) }
            /*modules*/
            factory { provideRetrofit() }
            single { providePokedexApi(get()) }
            /*repository*/
            single<PokedexRepository> { PokedexRepositoryImpl(get()) }
        }

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PokedexApplication)
            androidFileProperties()
            modules(appModule)
        }
    }

    private fun providePokedexApi(retrofit: Retrofit): PokedexServices =
        retrofit.create(PokedexServices::class.java)

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL_POKEMON_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun provideDatabase(
        context: Context,
        converters: Converters
    ): PokedexDatabase {
        return PokedexDatabase.create(context, converters)
    }

}