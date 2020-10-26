package com.csuf.cpsc411.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class Database constructor (var dbName : String = "") {

    init {
        // Create the database, create tables and keeps the db connection
        dbName = "C:\\Users\\Ernie\\Desktop\\testingDB.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        val sqlStmt = "create table if not exists claim (id text, title text, date text, isSolved text)"
        dbConn.exec(sqlStmt)
    }

    // var you can change
    // val you cannot change

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }

    // single object pattern
    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj as Database
        }
    }
}