package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var insTaskExecutorRule = InstantTaskExecutorRule()
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

    @Test
    fun insertShoppingItemTest() = runTest {
        val shoppingItem = ShoppingItem("n/a",1,10f,"n/a",id = 1)
        dao.insertShoppingItem(shoppingItem)
        val items = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(items).contains(shoppingItem)
    }


    @Test
    fun deleteItemTest() = runTest {
        val shoppingItem = ShoppingItem("n/a",1,10f,"n/a",id = 1)
        dao.deleteShoppingItem(shoppingItem)
        val items = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(items).doesNotContain(shoppingItem)
    }



    @Test
    fun sumOfAllPriceTest() = runTest {
        val shoppingItem = ShoppingItem("n/a",1,10f,"n/a",id = 1)
        val shoppingItem1 = ShoppingItem("n/a",2,10f,"n/a",id = 2)
        val shoppingItem2 = ShoppingItem("n/a",3,10f,"n/a",id = 3)
        dao.insertShoppingItem(shoppingItem)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)

        val items = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(items).isEqualTo(1*10f + 2*10f + 3 * 10f )
    }












}