package ir.sinasoheili.building_manager.VIEW

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.sinasoheili.building_manager.MODEL.Building
import ir.sinasoheili.building_manager.PRESENTER.ContractManagerBuildingList
import ir.sinasoheili.building_manager.PRESENTER.PresenterManagerBuildingList
import ir.sinasoheili.building_manager.R
import kotlinx.android.synthetic.main.activity_building_list.*

class ManagerBuildingListActivity : AppCompatActivity() , ContractManagerBuildingList.ContractManagerBuildingListView , View.OnClickListener, Toolbar.OnMenuItemClickListener
{
    private var listView : ListView? = null
    private var iv_reload : ImageView? = null
    private var tv_emptyList : TextView? = null
    private var floatBtnAdd : FloatingActionButton? = null
    private var bottomAppBar : BottomAppBar? = null
    private var frameLayout : FrameLayout? = null

    private var presenter : PresenterManagerBuildingList? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_list)

        initObj()

        presenter!!.getBuildingList()
    }

    private fun initObj()
    {
        listView = findViewById(R.id.lv_buildingList)

        iv_reload = findViewById(R.id.iv_buildingList_reload)
        iv_reload!!.setOnClickListener(this)

        tv_emptyList = findViewById(R.id.tv_buildingList_emptyList)
        tv_emptyList!!.setOnClickListener(this)

        presenter = PresenterManagerBuildingList(this@ManagerBuildingListActivity , this)

        bottomAppBar = findViewById(R.id.bab_buildingList)
        bottomAppBar!!.setOnMenuItemClickListener(this)

        floatBtnAdd = findViewById(R.id.fab_buildingList)
        floatBtnAdd!!.setOnClickListener(this)

        frameLayout = findViewById(R.id.fl_buildingList)
    }

    override fun showReloadButton()
    {
        invisibleList()
        invisibleTVEmptyAlert()
        visibleReloadImage()
    }

    override fun showEmptyListAlert()
    {
        invisibleList()
        invisibleReloadImage()
        visibleTVEmptyAlert()
    }

    override fun showBuildingList(buildingList: List<Building>)
    {
        invisibleReloadImage()
        invisibleTVEmptyAlert()
        visibleList()

        val adapterManager : ManagerBuildingListAdapter = ManagerBuildingListAdapter(this@ManagerBuildingListActivity , buildingList)
        lv_buildingList.adapter = adapterManager

        listView!!.setOnItemClickListener(object : AdapterView.OnItemClickListener
        {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
            {
                val intent : Intent = Intent(this@ManagerBuildingListActivity , ManagerManagerUnitListActivity::class.java)
                intent.putExtra("building_id" , buildingList.get(p2).id)
                startActivity(intent)
            }

        })
    }

    override fun onClick(view : View?)
    {
        when(view)
        {
            iv_reload ->
            {
                invisibleList()

                presenter!!.getBuildingList()
            }

            floatBtnAdd ->
            {
                val fragment : FragmentManagerRegisterNewBuilding = FragmentManagerRegisterNewBuilding(object:FragmentManagerRegisterNewBuilding.CallBack
                {
                    override fun onBuildingRegistred(building: Building)
                    {
                        presenter!!.getBuildingList()
                    }

                })
                supportFragmentManager.beginTransaction().replace(R.id.fl_buildingList , fragment).addToBackStack(null).commit()
            }
        }
    }

    private fun invisibleList()
    {
        listView!!.visibility  = View.GONE
    }

    private fun visibleList()
    {
        listView!!.visibility  = View.VISIBLE
    }

    private fun visibleReloadImage()
    {
        iv_reload!!.visibility = View.VISIBLE
    }

    private fun invisibleReloadImage()
    {
        iv_reload!!.visibility = View.GONE
    }

    private fun visibleTVEmptyAlert()
    {
        tv_emptyList!!.visibility = View.VISIBLE
    }

    private fun invisibleTVEmptyAlert()
    {
        tv_emptyList!!.visibility = View.GONE
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean
    {
        when(item!!.itemId)
        {
            R.id.menu_bab_buildingList_setting ->
            {
                val fragment : FragmentManagerSetting = FragmentManagerSetting()
                supportFragmentManager.beginTransaction().replace(R.id.fl_buildingList , fragment).addToBackStack(null).commit()

                return true
            }
        }

        return false
    }
}