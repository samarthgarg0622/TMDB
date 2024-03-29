package com.example.findingfalcone.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            logApiResponseError(response.code(), response.message())
            if (response.isSuccessful) {
                return Resource.success(response.body())
            }
            var jObjError: JSONObject
            var errorMessage = EMPTY_STRING
            try {
                response.errorBody()?.let {
                    coroutineScope {
                        launch(Dispatchers.IO) {
                            jObjError = JSONObject(it.string())
                            errorMessage = jObjError.getString(ERROR)
                        }
                    }
                }
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
            } finally {
                if (errorMessage.isEmpty()) errorMessage = OOPS_SOMETHING_WENT_WRONG
            }
            return error(errorMessage.replace(VALIDATION_EXCEPTION_ERROR, EMPTY_STRING).trim())
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun logApiResponseError(code: Int, message: String) {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCustomKey("errorCode", code)
        crashlytics.setCustomKey("errorMessage", message)
        crashlytics.recordException(java.lang.Exception("ja kr logs m dekh"))
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(message)
    }

    companion object {
        const val ERROR = "error"
        const val EMPTY_STRING = ""
        const val OOPS_SOMETHING_WENT_WRONG = "Oops! Something Went Wrong"
        const val VALIDATION_EXCEPTION_ERROR = "Validation Exception:"
    }
}