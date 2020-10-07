package ir.sinasoheili.building_manager.VIEW

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import ir.sinasoheili.building_manager.MODEL.Building
import ir.sinasoheili.building_manager.PRESENTER.API_BuildingRegisterHandler
import ir.sinasoheili.building_manager.PRESENTER.ContractRegisterNewBuilding
import ir.sinasoheili.building_manager.PRESENTER.PresenterRegisterNewBuilding
import ir.sinasoheili.building_manager.R

class FragmentRegisterNewBuilding constructor(callBack:CallBack) : Fragment(R.layout.fragment_register_new_building) , View.OnClickListener , ContractRegisterNewBuilding.ContractRegisterNewBuildingView
{
    private var tilName : TextInputLayout? = null
    private var tilCash : TextInputLayout? = null
    private var tilAddress : TextInputLayout? = null
    private var tilUnitCount : TextInputLayout? = null
    private var etName : EditText? = null
    private var etAddress : EditText? = null
    private var etCash : EditText? = null
    private var etUnitCount : EditText? = null
    private var btnSubmit : Button? = null

    private val presenter : PresenterRegisterNewBuilding = PresenterRegisterNewBuilding(this)
    private val callBack : CallBack = callBack

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        initObj(view)
    }

    private fun initObj(view : View)
    {
        tilName = view.findViewById(R.id.til_registerNewBuilding_name)
        tilCash = view.findViewById(R.id.til_registerNewBuilding_cash)
        tilAddress = view.findViewById(R.id.til_registerNewBuilding_address)
        tilUnitCount = view.findViewById(R.id.til_registerNewBuilding_unitCount)

        etName = view.findViewById(R.id.et_registerNewBuilding_name)
        etAddress = view.findViewById(R.id.et_registerNewBuilding_address)
        etCash = view.findViewById(R.id.et_registerNewBuilding_cash)
        etUnitCount = view.findViewById(R.id.et_registerNewBuilding_unitCount)

        btnSubmit = view.findViewById(R.id.btn_registerNewBuilding_submit)
        btnSubmit!!.setOnClickListener(this)
    }

    override fun onClick(view : View?)
    {
        when(view)
        {
            btnSubmit ->
            {
                if(checkName() && checkAddress() && checkCash() && checkUnitCount())
                {
                    val name : String = etName?.text.toString()
                    val cash : Double = etCash?.text.toString().toDouble()
                    val address : String = etAddress?.text.toString()
                    val unit_count : Int = etUnitCount?.text.toString().toInt()

                    val building : Building = Building(name , cash , address , unit_count)
                    presenter.registerBuilding(context!! , building)
                }
            }
        }
    }

    private fun checkName():Boolean
    {
        if(etName!!.text.isEmpty())
        {
            tilName!!.error = context?.getString(R.string.fill_field)
            etName?.requestFocus()
            return false
        }
        tilName?.isErrorEnabled = false
        return true
    }

    private fun checkAddress():Boolean
    {
        if(etAddress!!.text.isEmpty())
        {
            tilAddress!!.error = context?.getString(R.string.fill_field)
            etAddress?.requestFocus()
            return false
        }

        tilAddress?.isErrorEnabled = false
        return true
    }

    private fun checkCash():Boolean
    {
        if(etCash!!.text.isEmpty())
        {
            tilCash!!.error = context?.getString(R.string.fill_field)
            etCash?.requestFocus()
            return false
        }

        tilCash?.isErrorEnabled = false
        return true
    }

    private fun checkUnitCount():Boolean
    {
        if(etUnitCount!!.text.isEmpty())
        {
            tilUnitCount!!.error = context?.getString(R.string.fill_field)
            etUnitCount?.requestFocus()
            return false
        }

        tilUnitCount?.isErrorEnabled = false
        return true
    }

    override fun showToast(text: String)
    {
        Toast.makeText(context , text , Toast.LENGTH_SHORT).show()
    }

    override fun buildingRegistered(building: Building)
    {
        callBack.onBuildingRegistred(building)
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    //callback interface for when new building registered
    interface CallBack
    {
        fun onBuildingRegistred(building:Building)
    }
}