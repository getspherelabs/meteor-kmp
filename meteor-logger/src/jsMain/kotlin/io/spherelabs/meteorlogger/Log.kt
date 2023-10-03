package io.spherelabs.meteorlogger

import java.util.StringJoiner

public actual fun info(message: String, tag: String){
    console.log("[$tag] INFO: $message")
}
public actual fun debug(message: String, tag: String){
    console.log("[$tag] DEBUG: $message")
}

public actual fun failure(message: String, throwable: Throwable?, tag: String){
    console.log("[$tag] FAILURE: $message", throwable)
}

public actual fun logNetworkSuccess(url: String, statusCode: Int, responseData){
    console.log("Network response: $statusCode $url")
    console.log("Response Data: $responseData")
}
// integer formatter logger function
public actual fun formatInteger(i:Int): String {
    return if (i < 100) ("0" + if (i < 10) "0" else "") + i else i.toString()
}
