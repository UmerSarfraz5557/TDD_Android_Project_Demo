package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    private lateinit var database : ShoppingItemDatabase
    private lateinit var dao : ShoppingDao


    @Before
    fun setup(){

        /*
         - In memory database as for test we don't require persisted DB
         - Run on main thread as for test we can ignore async
         */
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java)
            .allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }



    @After
    fun terminate(){
        database.close()
    }



    










}