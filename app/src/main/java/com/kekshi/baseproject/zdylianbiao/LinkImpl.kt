package com.kekshi.baseproject.zdylianbiao

class LinkImpl<E> : ILink<E> {

    //根节点的引用
    private var root: Node? = null
    private var count = 0 //数据个数
    private var foot = 0 //下标
    private var returnData: Array<Any>? = null//返回的所有数据

    /**
     * 添加数据，每次添加一个新的Node，将数据封装进去
     */
    override fun add(e: E) {
        if (e == null) {
            return
        }
        //数据不为空创建新的节点
        val newNode = Node(e)
        //如果根节点为空，则第一条数据作为根节点
        if (this.root == null) {
            this.root = newNode
        } else {
            //如果根节点不为空，则赋值给下一条数据。这样的话只能添加2条数据，因此需要更改设计
//            this.root?.next = newNode
            this.root?.addNode(newNode)
        }
        this.count++
    }

    /**
     * 获取数据数量
     */
    override fun size(): Int {
        return count
    }

    /**
     * 获取指定位置的数据
     */
    override fun get(index: Int): E? {
        if (index >= count || index < 0) {
            return null
        }
        resetFoot()
        return root?.getNode(index)
    }

    /**
     * 修改指定位置的数据
     */
    override fun set(index: Int, e: E) {
        if (index >= count || index < 0) {
            return
        }
        resetFoot()
        root?.setNode(index, e)
    }

    /**
     * 删除指定的数据
     */
    override fun remove(e: E) {
        if (count == 0 || e == null) {
            return
        }
        if (contains(e)) {
            //如果是删除根节点，则将下一条数据作为根节点
            if (root?.data == e) {
                root = root?.next
            } else {
                //如果不是删除根节点，则从根节点的下一个节点开始判断，并传入上一个Noede和数据
                root?.next?.removeNode(root, e)
            }
        }
        count--
    }

    /**
     * 删除指定位置的数据
     */
    override fun remove(index: Int) {
        if (count == 0 || index >= count || index < 0) {
            return
        }
        //如果是删除根节点，则将下一条数据作为根节点
        if (index == 0) {
            root = root?.next
        } else {
            //如果不是删除根节点，则从根节点的下一个节点开始判断，并传入上一个Noede和数据
            resetFoot()
            root?.next?.removeNode(root, index)
        }
        count--
    }

    /**
     * 清空所有数据
     */
    override fun clear() {
        root = null
        count = 0
    }

    private fun resetFoot() {
        foot = 0
    }

    /**
     * 数据是否为空
     */
    override fun isEmpty(): Boolean {
        return count == 0
    }

    /**
     * 是否包含指定数据
     */
    override fun contains(e: E): Boolean {
        if (e == null) {
            return false
        }
        if (count == 0) {
            return false
        }
        return root!!.containsNode(e)
    }

    /**
     * 将所有数据转为数组并返回
     */
    override fun toArray(): Array<Any> {
        returnData = Array(count) {}
        resetFoot()

        root?.toArrayNode() //利用Node类递归取数据

        return returnData!!
    }

    /**
     * 保存数据的包装类
     * @param data 保存的数据
     */
    private inner class Node(var data: E) {
        //下一个节点的引用
        var next: Node? = null

        /**
         * 保存新的节点数据
         * 第一次调用由root 调用
         * 第二次调用由root.next 调用
         * 第三次调用由root.next.next 调用
         */
        fun addNode(newNode: Node) {
            //当前节点的下一个节点为空
            if (next == null) {
                next = newNode
            } else {
                next?.addNode(newNode)
            }
        }

        fun getNode(index: Int): E? {
            // foot++ 先用foot进行比较然后再自增，这样就不用写两行了。
            return if (foot++ == index) {
                data
            } else
                next?.getNode(index)
        }

        fun setNode(index: Int, e: E) {
            if (foot++ == index) {
                data = e
            } else
                next?.setNode(index, e)
        }

        //根节点之后的删除逻辑
        fun removeNode(previous: Node?, e: E) {
            if (data == e) {
                previous?.next = next
            } else {
                next?.removeNode(this, e)
            }
        }

        //根节点之后的删除逻辑，因此index从1开始
        fun removeNode(previous: Node?, index: Int) {
            if (index == (++foot)) {
                previous?.next = next
            } else {
                next?.removeNode(this, index)
            }
        }

        fun containsNode(e: E): Boolean {
            return if (e == data) {
                return true
            } else {
                if (next == null) {
                    false
                } else {
                    next!!.containsNode(e)
                }
            }

        }

        /**
         * 获取所有节点数据，并保存到数组中的对应位置
         * 第一次获取的是root.data
         * 第二次获取的是root.next.data
         * 第三次获取的是root.next.next.data
         */
        fun toArrayNode() {
            returnData!![foot++] = data!!
            next?.toArrayNode()
        }
    }

}