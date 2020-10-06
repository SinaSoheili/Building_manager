package ir.sinasoheili.building_manager.PRESENTER

import android.content.Context
import android.view.View
import ir.sinasoheili.building_manager.MODEL.Unit
import ir.sinasoheili.building_manager.MODEL.UnitAddResponse
import ir.sinasoheili.building_manager.R

class PresenterRegisterNewUnit constructor(view:ContractRegisterNewUnit.ContractRegisterNewUnitView) : ContractRegisterNewUnit.ContractRegisterNewUnitPresenter
{
    val view : ContractRegisterNewUnit.ContractRegisterNewUnitView = view

    override fun registerUnit(context: Context, unit: Unit)
    {
        val api : API_UnitAdd =  API_UnitAdd(context)
        api.start(unit , object:API_UnitAdd.CallBack
        {
            override fun onFailure()
            {
                view.showToast(context.getString(R.string.toast_fail_connect_to_server))
            }

            override fun onResponse(response: UnitAddResponse)
            {
                if(response.status)
                {
                    view.unitRegistered()
                }
            }

        })
    }

}