package alex.ts.tabpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Frag2.MyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pages.adapter = SampleFragmentPagerAdapter( this, supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(view_pages)
        val id = tabLayout.selectedTabPosition
        view_pages.setCurrentItem(1, true)



    }

    override fun sendToActivity() {
        Log.d("TAG", "MainActivity sendToActivity")
        view_pages.setCurrentItem(2, true)
    }
}
