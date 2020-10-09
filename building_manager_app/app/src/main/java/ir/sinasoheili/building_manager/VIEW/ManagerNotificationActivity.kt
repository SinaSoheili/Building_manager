package ir.sinasoheili.building_manager.VIEW

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import ir.sinasoheili.building_manager.VIEW.FragmentRegisterNewNotification.CallBack
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.sinasoheili.building_manager.MODEL.Notification
import ir.sinasoheili.building_manager.PRESENTER.ContractManagerNotification
import ir.sinasoheili.building_manager.PRESENTER.PresenterManagerNotification
import ir.sinasoheili.building_manager.R

class ManagerNotificationActivity : AppCompatActivity() , ContractManagerNotification.ContractManagerNotificationView, View.OnClickListener
{
    private var fabAddNotification : FloatingActionButton? = null
    private var listView : ListView? = null

    private var buildingId : Int = -1
    private var presenter : PresenterManagerNotification? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_notifiaction)

        val bundle : Bundle? = intent.extras
        if(bundle != null)
        {
            buildingId = bundle.getInt("buildingId")
        }

        initObj()

        presenter!!.fetchNotificationList(buildingId)
    }

    private fun initObj()
    {
        presenter = PresenterManagerNotification(this@ManagerNotificationActivity , this)

        fabAddNotification = findViewById(R.id.fab_managerNotification_AddNotification)
        fabAddNotification!!.setOnClickListener(this)

        listView = findViewById(R.id.lv_managerNotification_notifList)
    }

    override fun onClick(view: View?)
    {
        when(view)
        {
            fabAddNotification ->
            {
                val fragment : FragmentRegisterNewNotification = FragmentRegisterNewNotification(buildingId , object:CallBack
                {
                    override fun onNotificationRegistered()
                    {
                        presenter!!.fetchNotificationList(buildingId)
                    }

                })
                supportFragmentManager.beginTransaction().add(R.id.fl_managerNotification , fragment).addToBackStack(null).commit()
            }
        }
    }

    override fun showToast(text: String)
    {
        Toast.makeText(this@ManagerNotificationActivity , text , Toast.LENGTH_SHORT).show()
    }

    override fun showList(items: List<Notification>)
    {
        val adapter : ArrayAdapter<Notification> = ArrayAdapter(this , android.R.layout.simple_list_item_1 , items)
        listView!!.adapter = adapter
    }
}