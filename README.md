Paste.ee 4 Scala
===
Pastee4scala is a Paste.ee API wrapper written in Scala. It supports both uploading and downloading pastes.

<br>

Uploading a Paste
-------
Uploading a paste is simple. Construct a new ```PasteeUploadRequest``` class with your desired parameters and then invoke `sendAndWait` to execute an upload attempt. ```sendAndWait``` will return a ```PasteeUploadResponse``` instance containing the result of the upload request. An example is displayed below

```scala
val response = new PasteeUploadRequest(description = "A simple description.",
  paste = "A simple paste.",
  expireTime = 1).sendAndWait

if (response.successful) {
  println(response.toSuccessResponse.link)
} else {
  val error = response.toErrorResponse
  println(s"Paste upload unsuccessful [opcode=${error.id} msg=${error.msg}]")
}
```

Or alternatively, as a more idiomatic solution you can use pattern matching

```scala
val response = new PasteeUploadRequest(description = "A simple description.",
    paste = "A simple paste.",
    expireTime = 1).sendAndWait

response match {
  case success: PasteeSuccessUploadResponse => println(success.link)
   
  case error: PasteeErrorUploadResponse => 
    println(s"Paste upload unsuccessful [opcode=${error.id}, msg=${error.msg}]")
      
  case _ => throw new IllegalStateException("unexpected")
}
```

<br>

Downloading a Paste
-------
Downloading a paste is almost identical to uploading, but a plain String containing the paste is returned instead. If there are any problems an ```Exception``` implementation will be thrown. An example is displayed below

```scala
try {
  val pasteContent = new PasteeDownloadRequest(id = "AbCDe").sendAndWait

  println(pasteContent)
} catch {
  ...
}
```

