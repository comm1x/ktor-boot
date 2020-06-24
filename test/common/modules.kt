package common

import app.initDataSource
import app.services.StorageService
import common.services.TestStorageServiceImpl
import io.ktor.config.ApplicationConfig
import org.koin.core.module.Module
import org.koin.dsl.module

fun initTestAppModule(config: ApplicationConfig): Module {
    return module(createdAtStart = true) {
        single { initDataSource(config) }
        single<StorageService> { TestStorageServiceImpl() }
    }
}