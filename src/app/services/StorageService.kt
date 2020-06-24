package app.services

interface StorageService {
    fun getFiles(): String
}

class StorageServiceImpl : StorageService {
    override fun getFiles(): String {
        return "production files"
    }
}