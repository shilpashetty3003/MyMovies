package com.example.mvvm_hilt_paging_flow.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigator

fun NavController.navigateSafe(
    directions: NavDirections,extras:Navigator.Extras?=null
){

    if(currentDestination?.getAction(directions.actionId) !=null){
        if (extras !=null){
            navigate(directions,extras)
        }else{
            navigate(directions)
        }
    }

}