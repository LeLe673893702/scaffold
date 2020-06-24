package com.newler.scaffold.common.config.modlue

import android.app.Application
import com.newler.scaffold.common.config.bus.BusStrategy
import com.newler.scaffold.common.config.modlue.cache.Cache
import com.newler.state.StateManager
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *
 * @what 全局配置管理器
 * @author 17173
 * @date 2020/1/8
 *
 */
class GlobalConfigModule private constructor(builder: Builder) {
    private var busStrategy : BusStrategy?= null
    private var retrofitConfiguration : NetWorkModule.RetrofitConfiguration?= null
    private var okHttpConfiguration: NetWorkModule.OkHttpClientConfiguration?= null
    private var gsonConfiguration: NetWorkModule.GsonConfiguration?= null
    private var stateAdapter: StateManager.Adapter ?= null
    private var application: Application ?= null
    private var baseUrl: HttpUrl ?= null
    private var footerCreator: DefaultRefreshFooterCreator ?= null
    private var headerCreator: DefaultRefreshHeaderCreator ?= null
    private var cacheFactory: Cache.Factory? = null
    companion object{
        fun newBuilder()  = Builder()
    }
    init {
        this.busStrategy =
            builder.busStrategy
        this.retrofitConfiguration =
            builder.retrofitConfiguration
        this.okHttpConfiguration =
            builder.okHttpConfiguration
        this.gsonConfiguration =
            builder.gsonConfiguration
        this.stateAdapter =
            builder.stateViewAdapter
        this.baseUrl =
            builder.baseUrl
        this.application =
            builder.application
        this.footerCreator =
            builder.footerCreator
        this.headerCreator =
            builder.headerCreator
        this.cacheFactory =
            builder.cacheFactory
    }

    fun init() {
        application?.let {
            initApp(it)
            buildNetWorkModule(it)
            buildCache()
        }
    }

    private fun initApp(application: Application) {
        val appModule = AppInitialization()
        appModule.initStateManager(stateAdapter)
        appModule.initAppManger(application)
        busStrategy?.let { appModule.initBus(it) }
        footerCreator?.let { appModule.initFooterCreator(it) }
        headerCreator?.let { appModule.initHeaderCreator(it) }
    }

    /**
     * 构建网络相关组件
     */
    private fun buildNetWorkModule(it:Application) {
        val netWorkModule = NetWorkModule()
        val gson = netWorkModule.provideGson(it, gsonConfiguration)
        val okHttpClient = netWorkModule.provideOkHttpClient(it, okHttpConfiguration, OkHttpClient.Builder())
        val retrofit = netWorkModule.provideRetrofit(it, retrofitConfiguration, Retrofit.Builder(), okHttpClient, baseUrl, gson)
        RetrofitProvider.instance.inject(okHttpClient, retrofit, gson)
    }

    private fun buildCache() {
        cacheFactory?.let {
            CacheProvider.instance.inject(it)
        }
    }

    class Builder {
        internal var busStrategy : BusStrategy?= null
        internal var retrofitConfiguration : NetWorkModule.RetrofitConfiguration?= null
        internal var okHttpConfiguration: NetWorkModule.OkHttpClientConfiguration?= null
        internal var gsonConfiguration: NetWorkModule.GsonConfiguration?= null
        internal var stateViewAdapter: StateManager.Adapter ?= null
        internal var baseUrl: HttpUrl ?= null
        internal var application: Application ?= null
        internal var footerCreator: DefaultRefreshFooterCreator ?= null
        internal var headerCreator: DefaultRefreshHeaderCreator ?= null
        internal var cacheFactory: Cache.Factory? = null

        fun bus(busStrategy: BusStrategy) : Builder {
            this.busStrategy = busStrategy
            return this
        }

        fun retrofit(retrofitConfiguration : NetWorkModule.RetrofitConfiguration) : Builder {
            this.retrofitConfiguration = retrofitConfiguration
            return this
        }

        fun okHttpClient(okHttpConfiguration: NetWorkModule.OkHttpClientConfiguration) : Builder {
            this.okHttpConfiguration = okHttpConfiguration
            return this
        }

        fun gson(gsonConfiguration: NetWorkModule.GsonConfiguration) : Builder {
            this.gsonConfiguration = gsonConfiguration
            return this
        }

        fun stateViewAdapter(stateViewAdapter: StateManager.Adapter) : Builder {
            this.stateViewAdapter = stateViewAdapter
            return this
        }

        fun baseUrl(baseUrl: String) : Builder {
            this.baseUrl = HttpUrl.parse(baseUrl)
            return this
        }

        fun application(application: Application) : Builder {
            this.application = application
            return this
        }

        fun footerCreator(footerCreator: DefaultRefreshFooterCreator) : Builder {
            this.footerCreator = footerCreator
            return this
        }

        fun headerCreator(headerCreator: DefaultRefreshHeaderCreator) : Builder {
            this.headerCreator = headerCreator
            return this
        }

        fun cacheFactory(cacheFactory: Cache.Factory): Builder {
            this.cacheFactory = cacheFactory
            return this
        }


        fun builder(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }
}