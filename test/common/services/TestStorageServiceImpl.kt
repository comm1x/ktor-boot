package common.services

import app.services.StorageService

class TestStorageServiceImpl : StorageService {
    override fun getFiles(): String {
        return "test files"
    }
}