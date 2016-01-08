Paste.ee 4 Scala
===
Pastee4scala is a Paste.ee API wrapper written in Scala. It supports both uploading and downloading pastes.

<br>

Uploading a Paste
-------
Uploading a paste is simple. Construct a new ```PasteeUploadRequest``` class with your desired parameters and then invoke `sendAndWait` to execute an upload attempt. ```sendAndWait``` will return a ```Try[PasteeUploadResponse]``` instance containing the result of the upload request. An example is displayed below

```scala
val result = new PasteeUploadRequest(description = "A test upload description.",
    paste = "Hello, World :)",
    expireTime = 1).sendAndWait

result.foreach(it => println(it.link))
```

To handle failed requests, pattern matching can be used

```scala
val result = new PasteeUploadRequest(description = "A test upload description.",
    paste = "Hello, World :)",
    expireTime = 1).sendAndWait

result match {
  case Success(it) => println(it.link)

  case Failure(it) => it.printStackTrace()
}
```

<br>

Downloading a Paste
-------
Downloading a paste is almost identical to uploading, but a plain String containing the paste contents is returned instead of a ```PasteeUploadResponse```.

```scala
val result = new PasteeDownloadRequest(id = "AbCDe").sendAndWait

result.foreach(it => println(it))
```

