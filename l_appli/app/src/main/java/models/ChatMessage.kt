package models

class ChatMessage(val id:String, val text:String, val fromId:String, val toId:String,
                  val Timestamp: Long){
    constructor() : this(id="", text="", fromId="", toId="", Timestamp=-1)
}