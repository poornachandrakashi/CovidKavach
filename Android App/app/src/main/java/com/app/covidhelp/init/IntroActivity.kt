package com.app.covidhelp.init


import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.covidhelp.R
import com.app.covidhelp.databinding.IntroActivityBinding


class IntroActivity : AppCompatActivity() {

    lateinit var bind: IntroActivityBinding
    lateinit var layouts: IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = IntroActivityBinding.inflate(layoutInflater)
        setContentView(bind.root)

        layouts = intArrayOf(
            R.layout.intro_slide_1,
            R.layout.intro_slide_2,
            R.layout.intro_slide_3,
            R.layout.intro_slide_4,
        )

        addBottomDots(0)

        bind.viewPager.adapter = ViewPagerAdapter()

        bind.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {


            }

            override fun onPageSelected(position: Int) {

                addBottomDots(position);
                if (position == layouts.size - 1) {
                    bind.btnNext.text = getString(R.string.start);
                    bind.btnSkip.visibility = View.GONE;
                } else {
                    // still pages are left
                    bind.btnNext.text = getString(R.string.next);
                    bind.btnSkip.visibility = View.VISIBLE;
                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        bind.btnSkip.setOnClickListener {
            launchRoutingActivity();
        }

        bind.btnNext.setOnClickListener {
            val current: Int = bind.viewPager.currentItem + 1
            if (current < layouts.size) {
                // move to next screen
                bind.viewPager.setCurrentItem(current)
            } else {
                launchRoutingActivity()
            }
        }

    }

    private fun launchRoutingActivity() {
        val intent: Intent = Intent(this, AppRoutingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun addBottomDots(currentPage: Int) {
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        val tvlist = ArrayList<TextView>()
        bind.layoutDots.removeAllViews()
        for (i in 0..layouts.size - 1) {

            val tv = TextView(this)
            tv.setText(Html.fromHtml("&#8226;"))
            tv.textSize = 25f
            tv.setTextColor(colorsInactive[currentPage]);
            tvlist.add(tv)
            bind.layoutDots.addView(tv)
        }

        if (tvlist.size > 0)
            tvlist.get(currentPage).setTextColor(colorsActive[currentPage])


    }


    inner class ViewPagerAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val view: View = layoutInflater.inflate(layouts[position], container, false)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

            val view = `object` as View
            container.removeView(view)
        }

        override fun getCount(): Int {
            return layouts.size

        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

    }


}