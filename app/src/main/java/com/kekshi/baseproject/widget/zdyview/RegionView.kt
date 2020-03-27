package com.kekshi.baseproject.widget.zdyview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * 区域（Range）
 *
 *  public Region()  //创建一个空的区域  
 public Region(Region region) //拷贝一个region的范围  
 public Region(Rect r)  //创建一个矩形的区域  
 public Region(int left, int top, int right, int bottom) //创建一个矩形的区域  

public void setEmpty()  从某种意义上讲置空也是一个构造函数，即将原来的一个区域变量变成了一个空变量，要再利用其它的Set方法重新构造区域。
 public boolean set(Region region)   利用新的区域值来替换原来的区域
 public boolean set(Rect r)   利用矩形所代表的区域替换原来的区域
 public boolean set(int left, int top, int right, int bottom)   同样，根据矩形的两个点构造出矩形区域来替换原来的区域值
 public boolean setPath(Path path, Region clip)    根据路径的区域与某区域的交集，构造出新区域
 */
class RegionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val paint = Paint()
    //    val rgn = Region(10, 10, 100, 100)
    val rgn = Region()

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //用set的区域，代替原始区域
//        rgn.set(100, 100, 200, 200)

        //构造一个椭圆路径
        val ovalPath = Path()
        val rect = RectF(50f, 50f, 200f, 500f)
        ovalPath.addOval(rect, Path.Direction.CCW)// CCW  逆时针; CW  顺时针

        rgn.setPath(ovalPath, Region(50, 50, 200, 500))

//        drawRegion(canvas, rgn, paint)

        drawRegion2(canvas)
    }

    /**
     * RegionIterator类，实现了获取组成区域的矩形集的功能，其实RegionIterator类非常简单，总共就两个函数，一个构造函数和一个获取下一个矩形的函数；
    RegionIterator(Region region) //根据区域构建对应的矩形集
    boolean next(Rect r) //获取下一个矩形，结果保存在参数Rect r 中
    由于在Canvas中没有直接绘制Region的函数，我们想要绘制一个区域，就只能通过利用RegionIterator构造矩形集来逼近的显示区域。

    首先，根据区域构建一个矩形集，然后利用next(Rect r)来逐个获取所有矩形，绘制出来，最终得到的就是整个区域，
    如果我们将上面的画笔Style从FILL改为STROKE，重新绘制椭圆路径，会看得更清楚。
     */
    private fun drawRegion(canvas: Canvas?, region: Region, paint: Paint) {
        val iter = RegionIterator(region)
        val r = Rect()
        while (iter.next(r)) {
            canvas?.drawRect(r, paint)
        }
    }

    /**
     * 区域的合并、交叉等操作
     * public final boolean union(Rect r)   
    public boolean op(Rect r, Op op) {  
    public boolean op(int left, int top, int right, int bottom, Op op)   
    public boolean op(Region region, Op op)   
    public boolean op(Rect rect, Region region, Op op)   

    除了Union(Rect r)是指定合并操作以外，其它四个op（）构造函数，都是指定与另一个区域的操作。其中最重要的指定Op的参数，Op的参数有下面四个：

    假设用region1  去组合region2
    public enum Op {
    DIFFERENCE(0), //最终区域为region1 与 region2不同的区域
    INTERSECT(1), // 最终区域为region1 与 region2相交的区域
    UNION(2),      //最终区域为region1 与 region2组合一起的区域
    XOR(3),        //最终区域为region1 与 region2相交之外的区域
    REVERSE_DIFFERENCE(4), //最终区域为region2 与 region1不同的区域
    REPLACE(5); //最终区域为为region2的区域
    }
     */
    private fun drawRegion2(canvas: Canvas?) {
        //构造两个矩形
        val rect1 = Rect(100, 100, 400, 200)
        val rect2 = Rect(200, 0, 300, 300)

        //构造一个画笔，画出矩形轮廓
        canvas?.drawRect(rect1, paint)
        canvas?.drawRect(rect2, paint)

        //构造两个Region
        val region = Region(rect1)
        val region2 = Region(rect2)

        //取两个区域的交集
        region.op(region2, Region.Op.INTERSECT)

        //再构造一个画笔,填充Region操作结果
        val paint_fill = Paint()
        paint_fill.color = Color.GREEN
        paint_fill.style = Paint.Style.FILL
        drawRegion(canvas, region, paint_fill)
    }
}