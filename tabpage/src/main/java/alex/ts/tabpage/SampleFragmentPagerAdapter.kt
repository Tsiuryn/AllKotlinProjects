package alex.ts.tabpage

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class SampleFragmentPagerAdapter(private val context: Context, fm: FragmentManager):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val PAGE = 3

    private val tabTitles = arrayOf(R.drawable.ic_baseline_looks_one, R.drawable.ic_baseline_looks_two_24, R.drawable.ic_baseline_looks_3_24)
    private val tabTitles_2 = arrayOf("One", "Two", "Three")



    override fun getCount(): Int {
        return PAGE
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = Frag1()
            }
            1 -> {
                fragment = Frag2()
            }
            2 -> {
                fragment = Frag3()
            }
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val resId =  tabTitles[position]
        val image = context.resources.getDrawable(resId)
        image.setBounds(0, 0, image.intrinsicWidth, image.intrinsicHeight)
        val sb = SpannableString(" \n" + tabTitles_2[position] )
        val imgsp = ImageSpan(image, ImageSpan.ALIGN_BOTTOM)
        sb.setSpan(imgsp, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return sb
    }
}