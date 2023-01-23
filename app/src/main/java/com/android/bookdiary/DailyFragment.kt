package com.android.bookdiary

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val TAG = "DailyFragment"

class DailyFragment : Fragment(), DailyClickHandler {
    var dailyChoiceList: ArrayList<DailyChoiceData> = ArrayList()
    lateinit var dailyRecycler: RecyclerView
    lateinit var dbManager : DBManager
    lateinit var sqlitedb : SQLiteDatabase

    @SuppressLint("UseRequireInsteadOfGet", "Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_daily, container, false)
        Log.d(TAG, "파일 찾는중")
        dbManager = DBManager(activity, "bookDB", null, 1)
        Log.d(TAG, "${dbManager.databaseName}")
        sqlitedb = dbManager.readableDatabase
        Log.d(TAG, "파일 찾았다2")

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM bookDB;", null)
        if (cursor != null){
            Log.d(TAG, "테이블 찾았다")
        }

        dailyRecycler = view.findViewById(R.id.dailyRecycler!!) as RecyclerView
        dailyRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        dailyRecycler.adapter = DailyChoiceAdapter(requireContext(), dailyChoiceList, this)

        while (cursor.moveToNext()){
            Log.d(TAG, "dailyFragment while문에 들어옴")
            var bookColor = cursor.getString(cursor.getColumnIndex("color"))
            var bookTitle = cursor.getString(cursor.getColumnIndex("title"))
            var data : DailyChoiceData = DailyChoiceData(bookColor, bookTitle)
            dailyChoiceList.add(data)
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()


//        dailyChoiceList.add(DailyChoiceData("RED","호랑이"))
//        dailyChoiceList.add(DailyChoiceData("ORANGE", "호시"))
//        dailyChoiceList.add(DailyChoiceData("YELLOW", "파리"))
//        dailyChoiceList.add(DailyChoiceData("GREEN","에펠탑"))
//        dailyChoiceList.add(DailyChoiceData("BLUE","여행"))
//        dailyChoiceList.add(DailyChoiceData("NAVY", "부러워"))
//        dailyChoiceList.add(DailyChoiceData("PURPLE","호랑해"))
//        dailyChoiceList.add(DailyChoiceData("PINK","햄스터"))


        return view
    }

    override fun clickedBookItem(book: DailyChoiceData) {
        Log.d(TAG, "ClickedBookItem: ${book.bookTitle}")

        var dTitle = book.bookTitle
        var bundle = Bundle()
        bundle.putString("key", dTitle)
        val ft : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

        var dailyMemoFragment = DailyMemoFragment()
        dailyMemoFragment.arguments = bundle
        ft.replace(R.id.container, dailyMemoFragment).commit()
        Toast.makeText(activity, dTitle, Toast.LENGTH_SHORT).show()
    }

}