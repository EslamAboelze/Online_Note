package com.example.noteonline.model

class Note {
    var id: String? = null
    var title: String? = null
    var content: String? = null
    var timestamp: String? = null

    constructor(){
        // empty default constructor, necessary for Firebase to be able to deserialize users
    }

    constructor(id: String, title: String, content: String, timestamp: String)  {
        this.id = id
        this.title = title
        this.content = content
        this.timestamp = timestamp
    }

}