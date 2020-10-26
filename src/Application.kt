package com.csuf.cpsc411

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import com.almworks.sqlite4java.SQLiteConnection
import com.google.gson.Gson
import com.csuf.cpsc411.Dao.Database
import com.csuf.cpsc411.Dao.claim.Claim
import com.csuf.cpsc411.Dao.claim.ClaimDao
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    // extension
    // @annotation
    // routing constructor takes only one parameter which is a lambda function
    // DSL - Domain Specific Language
    routing {
        // 'get' method
        this.get("/ClaimService/getAll"){
            println("HTTP message is using GET method with /ClaimService/getAll ")
            val cList = ClaimDao().getAll()
            println("The number of claims : ${cList.size}")
            // JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(cList)
            // println("RAH Rah test RAH")
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType= ContentType.Application.Json)
        }

        // 'post' method
        post("/ClaimService/add"){
            println("HTTP message is using POST method with /ClaimService/add ")
            val id :UUID? = UUID.randomUUID()
            val title =  call.request.queryParameters["Title"]
            val date = call.request.queryParameters["Date"]
            val isSolved = false;
            val response = String.format("ID %s and Title %s and Date %s and isSolved %s", id, title, date, isSolved)
            val cObj = Claim(id, title, date, isSolved)
            val dao = ClaimDao().addClaim(cObj)
            call.respondText(response, status = HttpStatusCode.OK, contentType = ContentType.Text.Plain)

            // In postman, select 'POST'
            // then input: http://localhost:8010/ClaimService/add?Title=RandomTitle&Date=Oct,3,2000
        }
    }
}

