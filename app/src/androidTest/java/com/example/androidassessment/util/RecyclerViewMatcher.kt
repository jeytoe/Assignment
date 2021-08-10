package com.example.androidassessment.util

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.*

class RecyclerViewMatcher(private val recyclerViewId: Int) {
    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                if (resources != null) {
                    idDescription = try {
                        resources!!.getResourceName(recyclerViewId)
                    } catch (var4: NotFoundException) {
                        String.format("%s (resource name not found)", recyclerViewId)
                    }
                }
                description.appendText("with id: $idDescription")
            }

            public override fun matchesSafely(view: View): Boolean {
                resources = view.resources
                val recyclerView: RecyclerView = view.rootView.findViewById(recyclerViewId)
                if (recyclerView == null || recyclerView.id != recyclerViewId || null == recyclerView.findViewHolderForAdapterPosition(
                        position
                    )
                ) {
                    return false
                }
                val childView = recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }

    fun atPositionOnView(position: Int, contentDescription: String?): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                if (resources != null) {
                    idDescription = try {
                        resources!!.getResourceName(recyclerViewId)
                    } catch (var4: NotFoundException) {
                        String.format("%s (resource name not found)", recyclerViewId)
                    }
                }
                description.appendText("with id: $idDescription")
            }

            public override fun matchesSafely(view: View): Boolean {
                resources = view.resources
                val recyclerView: RecyclerView = view.rootView.findViewById(recyclerViewId)
                if (recyclerView == null || recyclerView.id != recyclerViewId || null == recyclerView.findViewHolderForAdapterPosition(
                        position
                    )
                ) {
                    return false
                }
                val childView = recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                val matchedViews = ArrayList<View>()
                childView.findViewsWithText(
                    matchedViews,
                    contentDescription,
                    View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION
                )
                return view === matchedViews[0]
            }
        }
    }
}
