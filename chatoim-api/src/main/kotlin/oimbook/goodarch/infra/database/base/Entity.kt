package oimbook.goodarch.infra.database.base

interface Entity {

    val srl: Long

    fun create()

    fun modify()

}