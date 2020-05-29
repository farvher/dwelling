package com.dwelling.app.elasticsearch

import com.dwelling.app.domain.CV

/**
 *
 * @author fsanmiguel
 * */
interface ICVIndexService {

    /**
     * retrieve cv by user id
     * */
    fun getCVById(id: Long): CV

    /**
     * save the first cv
     * */
    fun saveCV(cv: CV): Unit


    /**
     * update the cv
     * */
    fun updateCV(cv: CV): Unit


    /**
     * delete the selected cv
     * */
    fun deleteCV(id: Long, cvId: Long): Unit

}
