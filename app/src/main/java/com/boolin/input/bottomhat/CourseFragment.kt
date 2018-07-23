package com.boolin.input.bottomhat
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by farme on 7/23/2018.
 */
class CourseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_course, container, false)

    companion object {
        fun newInstance(): CourseFragment = CourseFragment()
    }
}