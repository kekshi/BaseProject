package com.kekshi.baseproject.zdylianbiao

interface ILink<E> {
    fun add(e: E)

    fun size(): Int

    fun isEmpty(): Boolean

    fun toArray(): Array<Any>

    fun get(index: Int): E?

    fun set(index: Int, e: E)

    fun contains(e: E): Boolean

    fun remove(e: E)

    fun remove(index: Int)

    fun clear()
}