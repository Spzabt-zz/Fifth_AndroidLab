package com.example.lab_4_stoida

interface AdapterListener {
    fun OnUpdate(users: Users?)
    fun OnDelete(id: Int, pos: Int)
}