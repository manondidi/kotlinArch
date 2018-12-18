package com.czq.kotlinarch.example.component

import android.content.Context
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.czq.kotlinarch.BuildConfig

import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE


class GalleryLayoutManager(orientation: Int) : RecyclerView.LayoutManager(),
    RecyclerView.SmoothScroller.ScrollVectorProvider {

    private var mFirstVisiblePosition = 0
    private var mLastVisiblePos = 0
    private var mInitialSelectedPosition = 0

    var curSelectedPosition = -1
        internal set

    internal var mCurSelectedView: View? = null
    /**
     * Scroll state
     */
    private var mState: State? = null

    private val mSnapHelper = LinearSnapHelper()

    private val mInnerScrollListener = InnerScrollListener()

    private var mCallbackInFling = false

    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL]
     */
    val orientation = HORIZONTAL

    private var mHorizontalHelper: OrientationHelper? = null
    private var mVerticalHelper: OrientationHelper? = null

    private val horizontalSpace: Int
        get() = width - paddingRight - paddingLeft

    private val verticalSpace: Int
        get() = height - paddingBottom - paddingTop

    val state: State
        get() {
            if (mState == null) {
                mState = State()
            }
            return mState
        }

    val orientationHelper: OrientationHelper
        get() {
            if (orientation == HORIZONTAL) {
                if (mHorizontalHelper == null) {
                    mHorizontalHelper = OrientationHelper.createHorizontalHelper(this)
                }
                return mHorizontalHelper
            } else {
                if (mVerticalHelper == null) {
                    mVerticalHelper = OrientationHelper.createVerticalHelper(this)
                }
                return mVerticalHelper
            }
        }


    private var mItemTransformer: ItemTransformer? = null

    private var mOnItemSelectedListener: OnItemSelectedListener? = null

    internal var mRecyclerView: RecyclerView

    init {
        this.orientation = orientation
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return if (orientation == VERTICAL) {
            GalleryLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            GalleryLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun generateLayoutParams(c: Context, attrs: AttributeSet): RecyclerView.LayoutParams {
        return LayoutParams(c, attrs)
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): RecyclerView.LayoutParams {
        return if (lp is ViewGroup.MarginLayoutParams) {
            LayoutParams(lp)
        } else {
            LayoutParams(lp)
        }
    }

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        return lp is LayoutParams
    }


    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onLayoutChildren() called with: state = [$state]")
        }
        if (itemCount == 0) {
            reset()
            detachAndScrapAttachedViews(recycler!!)
            return
        }
        if (state!!.isPreLayout) {
            return
        }
        if (state.itemCount != 0 && !state.didStructureChange()) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onLayoutChildren: ignore extra layout step")
            }
            return
        }
        if (childCount == 0 || state.didStructureChange()) {
            reset()
        }
        mInitialSelectedPosition = Math.min(Math.max(0, mInitialSelectedPosition), itemCount - 1)
        detachAndScrapAttachedViews(recycler!!)
        firstFillCover(recycler, state, 0)
    }


    private fun reset() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "reset: ")
        }
        if (mState != null) {
            mState!!.mItemsFrames.clear()
        }
        //when data set update keep the last selected position
        if (curSelectedPosition != -1) {
            mInitialSelectedPosition = curSelectedPosition
        }
        mInitialSelectedPosition = Math.min(Math.max(0, mInitialSelectedPosition), itemCount - 1)
        mFirstVisiblePosition = mInitialSelectedPosition
        mLastVisiblePos = mInitialSelectedPosition
        curSelectedPosition = -1
        if (mCurSelectedView != null) {
            mCurSelectedView!!.isSelected = false
            mCurSelectedView = null
        }
    }


    private fun firstFillCover(recycler: RecyclerView.Recycler, state: RecyclerView.State, scrollDelta: Int) {
        if (orientation == HORIZONTAL) {
            firstFillWithHorizontal(recycler, state)
        } else {
            firstFillWithVertical(recycler, state)
        }

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "firstFillCover finish:first: $mFirstVisiblePosition,last:$mLastVisiblePos")
        }

        if (mItemTransformer != null) {
            var child: View?
            for (i in 0 until childCount) {
                child = getChildAt(i)
                mItemTransformer!!.transformItem(this, child, calculateToCenterFraction(child, scrollDelta.toFloat()))
            }
        }
        mInnerScrollListener.onScrolled(mRecyclerView, 0, 0)
    }

    /**
     * Layout the item view witch position specified by [GalleryLayoutManager.mInitialSelectedPosition] first and then layout the other
     *
     * @param recycler
     * @param state
     */
    private fun firstFillWithHorizontal(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val leftEdge = orientationHelper.startAfterPadding
        val rightEdge = orientationHelper.endAfterPadding
        val startPosition = mInitialSelectedPosition
        val scrapWidth: Int
        val scrapHeight: Int
        val scrapRect = Rect()
        val height = verticalSpace
        val topOffset: Int
        //layout the init position view
        val scrap = recycler.getViewForPosition(mInitialSelectedPosition)
        addView(scrap, 0)
        measureChildWithMargins(scrap, 0, 0)
        scrapWidth = getDecoratedMeasuredWidth(scrap)
        scrapHeight = getDecoratedMeasuredHeight(scrap)
        topOffset = (paddingTop + (height - scrapHeight) / 2.0f).toInt()
        val left = (paddingLeft + (horizontalSpace - scrapWidth) / 2f).toInt()
        scrapRect.set(left, topOffset, left + scrapWidth, topOffset + scrapHeight)
        layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
        if (state.mItemsFrames.get(startPosition) == null) {
            state.mItemsFrames.put(startPosition, scrapRect)
        } else {
            state.mItemsFrames.get(startPosition).set(scrapRect)
        }
        mLastVisiblePos = startPosition
        mFirstVisiblePosition = mLastVisiblePos
        val leftStartOffset = getDecoratedLeft(scrap)
        val rightStartOffset = getDecoratedRight(scrap)
        //fill left of center
        fillLeft(recycler, mInitialSelectedPosition - 1, leftStartOffset, leftEdge)
        //fill right of center
        fillRight(recycler, mInitialSelectedPosition + 1, rightStartOffset, rightEdge)
    }

    override fun onItemsRemoved(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
        super.onItemsRemoved(recyclerView, positionStart, itemCount)
    }

    /**
     * Layout the item view witch position special by [GalleryLayoutManager.mInitialSelectedPosition] first and then layout the other
     *
     * @param recycler
     * @param state
     */
    private fun firstFillWithVertical(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val topEdge = orientationHelper.startAfterPadding
        val bottomEdge = orientationHelper.endAfterPadding
        val startPosition = mInitialSelectedPosition
        val scrapWidth: Int
        val scrapHeight: Int
        val scrapRect = Rect()
        val width = horizontalSpace
        val leftOffset: Int
        //layout the init position view
        val scrap = recycler.getViewForPosition(mInitialSelectedPosition)
        addView(scrap, 0)
        measureChildWithMargins(scrap, 0, 0)
        scrapWidth = getDecoratedMeasuredWidth(scrap)
        scrapHeight = getDecoratedMeasuredHeight(scrap)
        leftOffset = (paddingLeft + (width - scrapWidth) / 2.0f).toInt()
        val top = (paddingTop + (verticalSpace - scrapHeight) / 2f).toInt()
        scrapRect.set(leftOffset, top, leftOffset + scrapWidth, top + scrapHeight)
        layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
        if (state.mItemsFrames.get(startPosition) == null) {
            state.mItemsFrames.put(startPosition, scrapRect)
        } else {
            state.mItemsFrames.get(startPosition).set(scrapRect)
        }
        mLastVisiblePos = startPosition
        mFirstVisiblePosition = mLastVisiblePos
        val topStartOffset = getDecoratedTop(scrap)
        val bottomStartOffset = getDecoratedBottom(scrap)
        //fill left of center
        fillTop(recycler, mInitialSelectedPosition - 1, topStartOffset, topEdge)
        //fill right of center
        fillBottom(recycler, mInitialSelectedPosition + 1, bottomStartOffset, bottomEdge)
    }

    /**
     * Fill left of the center view
     *
     * @param recycler
     * @param startPosition start position to fill left
     * @param startOffset   layout start offset
     * @param leftEdge
     */
    private fun fillLeft(recycler: RecyclerView.Recycler, startPosition: Int, startOffset: Int, leftEdge: Int) {
        var startOffset = startOffset
        var scrap: View
        var topOffset: Int
        var scrapWidth: Int
        var scrapHeight: Int
        val scrapRect = Rect()
        val height = verticalSpace
        var i = startPosition
        while (i >= 0 && startOffset > leftEdge) {
            scrap = recycler.getViewForPosition(i)
            addView(scrap, 0)
            measureChildWithMargins(scrap, 0, 0)
            scrapWidth = getDecoratedMeasuredWidth(scrap)
            scrapHeight = getDecoratedMeasuredHeight(scrap)
            topOffset = (paddingTop + (height - scrapHeight) / 2.0f).toInt()
            scrapRect.set(startOffset - scrapWidth, topOffset, startOffset, topOffset + scrapHeight)
            layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
            startOffset = scrapRect.left
            mFirstVisiblePosition = i
            if (state.mItemsFrames.get(i) == null) {
                state.mItemsFrames.put(i, scrapRect)
            } else {
                state.mItemsFrames.get(i).set(scrapRect)
            }
            i--
        }
    }

    /**
     * Fill right of the center view
     *
     * @param recycler
     * @param startPosition start position to fill right
     * @param startOffset   layout start offset
     * @param rightEdge
     */
    private fun fillRight(recycler: RecyclerView.Recycler, startPosition: Int, startOffset: Int, rightEdge: Int) {
        var startOffset = startOffset
        var scrap: View
        var topOffset: Int
        var scrapWidth: Int
        var scrapHeight: Int
        val scrapRect = Rect()
        val height = verticalSpace
        var i = startPosition
        while (i < itemCount && startOffset < rightEdge) {
            scrap = recycler.getViewForPosition(i)
            addView(scrap)
            measureChildWithMargins(scrap, 0, 0)
            scrapWidth = getDecoratedMeasuredWidth(scrap)
            scrapHeight = getDecoratedMeasuredHeight(scrap)
            topOffset = (paddingTop + (height - scrapHeight) / 2.0f).toInt()
            scrapRect.set(startOffset, topOffset, startOffset + scrapWidth, topOffset + scrapHeight)
            layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
            startOffset = scrapRect.right
            mLastVisiblePos = i
            if (state.mItemsFrames.get(i) == null) {
                state.mItemsFrames.put(i, scrapRect)
            } else {
                state.mItemsFrames.get(i).set(scrapRect)
            }
            i++
        }
    }

    /**
     * Fill top of the center view
     *
     * @param recycler
     * @param startPosition start position to fill top
     * @param startOffset   layout start offset
     * @param topEdge       top edge of the RecycleView
     */
    private fun fillTop(recycler: RecyclerView.Recycler, startPosition: Int, startOffset: Int, topEdge: Int) {
        var startOffset = startOffset
        var scrap: View
        var leftOffset: Int
        var scrapWidth: Int
        var scrapHeight: Int
        val scrapRect = Rect()
        val width = horizontalSpace
        var i = startPosition
        while (i >= 0 && startOffset > topEdge) {
            scrap = recycler.getViewForPosition(i)
            addView(scrap, 0)
            measureChildWithMargins(scrap, 0, 0)
            scrapWidth = getDecoratedMeasuredWidth(scrap)
            scrapHeight = getDecoratedMeasuredHeight(scrap)
            leftOffset = (paddingLeft + (width - scrapWidth) / 2.0f).toInt()
            scrapRect.set(leftOffset, startOffset - scrapHeight, leftOffset + scrapWidth, startOffset)
            layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
            startOffset = scrapRect.top
            mFirstVisiblePosition = i
            if (state.mItemsFrames.get(i) == null) {
                state.mItemsFrames.put(i, scrapRect)
            } else {
                state.mItemsFrames.get(i).set(scrapRect)
            }
            i--
        }
    }

    /**
     * Fill bottom of the center view
     *
     * @param recycler
     * @param startPosition start position to fill bottom
     * @param startOffset   layout start offset
     * @param bottomEdge    bottom edge of the RecycleView
     */
    private fun fillBottom(recycler: RecyclerView.Recycler, startPosition: Int, startOffset: Int, bottomEdge: Int) {
        var startOffset = startOffset
        var scrap: View
        var leftOffset: Int
        var scrapWidth: Int
        var scrapHeight: Int
        val scrapRect = Rect()
        val width = horizontalSpace
        var i = startPosition
        while (i < itemCount && startOffset < bottomEdge) {
            scrap = recycler.getViewForPosition(i)
            addView(scrap)
            measureChildWithMargins(scrap, 0, 0)
            scrapWidth = getDecoratedMeasuredWidth(scrap)
            scrapHeight = getDecoratedMeasuredHeight(scrap)
            leftOffset = (paddingLeft + (width - scrapWidth) / 2.0f).toInt()
            scrapRect.set(leftOffset, startOffset, leftOffset + scrapWidth, startOffset + scrapHeight)
            layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
            startOffset = scrapRect.bottom
            mLastVisiblePos = i
            if (state.mItemsFrames.get(i) == null) {
                state.mItemsFrames.put(i, scrapRect)
            } else {
                state.mItemsFrames.get(i).set(scrapRect)
            }
            i++
        }
    }


    private fun fillCover(recycler: RecyclerView.Recycler?, state: RecyclerView.State?, scrollDelta: Int) {
        if (itemCount == 0) {
            return
        }

        if (orientation == HORIZONTAL) {
            fillWithHorizontal(recycler, state, scrollDelta)
        } else {
            fillWithVertical(recycler, state, scrollDelta)
        }


        if (mItemTransformer != null) {
            var child: View?
            for (i in 0 until childCount) {
                child = getChildAt(i)
                mItemTransformer!!.transformItem(this, child, calculateToCenterFraction(child, scrollDelta.toFloat()))
            }
        }
    }

    private fun calculateToCenterFraction(child: View?, pendingOffset: Float): Float {
        val distance = calculateDistanceCenter(child, pendingOffset)
        val childLength = if (orientation == GalleryLayoutManager.HORIZONTAL) child!!.width else child!!.height

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "calculateToCenterFraction: distance:$distance,childLength:$childLength")
        }
        return Math.max(-1f, Math.min(1f, distance * 1f / childLength))
    }

    /**
     * @param child
     * @param pendingOffset child view will scroll by
     * @return
     */
    private fun calculateDistanceCenter(child: View?, pendingOffset: Float): Int {
        val orientationHelper = orientationHelper
        val parentCenter =
            (orientationHelper.endAfterPadding - orientationHelper.startAfterPadding) / 2 + orientationHelper.startAfterPadding
        return if (orientation == GalleryLayoutManager.HORIZONTAL) {
            (child!!.width / 2 - pendingOffset + child.left - parentCenter).toInt()
        } else {
            (child!!.height / 2 - pendingOffset + child.top - parentCenter).toInt()
        }

    }

    /**
     * @param recycler
     * @param state
     * @param dy
     */
    private fun fillWithVertical(recycler: RecyclerView.Recycler?, state: RecyclerView.State?, dy: Int) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "fillWithVertical: dy:$dy")
        }
        val topEdge = orientationHelper.startAfterPadding
        val bottomEdge = orientationHelper.endAfterPadding

        //1.remove and recycle the view that disappear in screen
        var child: View?
        if (childCount > 0) {
            if (dy >= 0) {
                //remove and recycle the top off screen view
                var fixIndex = 0
                for (i in 0 until childCount) {
                    child = getChildAt(i + fixIndex)
                    if (getDecoratedBottom(child!!) - dy < topEdge) {
                        if (BuildConfig.DEBUG) {
                            Log.v(
                                TAG,
                                "fillWithVertical: removeAndRecycleView:" + getPosition(child) + ",bottom:" + getDecoratedBottom(
                                    child
                                )
                            )
                        }
                        removeAndRecycleView(child, recycler!!)
                        mFirstVisiblePosition++
                        fixIndex--
                    } else {
                        if (BuildConfig.DEBUG) {
                            Log.d(
                                TAG,
                                "fillWithVertical: break:" + getPosition(child) + ",bottom:" + getDecoratedBottom(child)
                            )
                        }
                        break
                    }
                }
            } else { //dy<0
                //remove and recycle the bottom off screen view
                for (i in childCount - 1 downTo 0) {
                    child = getChildAt(i)
                    if (getDecoratedTop(child!!) - dy > bottomEdge) {
                        if (BuildConfig.DEBUG) {
                            Log.v(TAG, "fillWithVertical: removeAndRecycleView:" + getPosition(child))
                        }
                        removeAndRecycleView(child, recycler!!)
                        mLastVisiblePos--
                    } else {
                        break
                    }
                }
            }

        }
        var startPosition = mFirstVisiblePosition
        var startOffset = -1
        var scrapWidth: Int
        var scrapHeight: Int
        var scrapRect: Rect?
        val width = horizontalSpace
        var leftOffset: Int
        var scrap: View
        //2.Add or reattach item view to fill screen
        if (dy >= 0) {
            if (childCount != 0) {
                val lastView = getChildAt(childCount - 1)
                startPosition = getPosition(lastView!!) + 1
                startOffset = getDecoratedBottom(lastView)
            }
            var i = startPosition
            while (i < itemCount && startOffset < bottomEdge + dy) {
                scrapRect = state.mItemsFrames.get(i)
                scrap = recycler!!.getViewForPosition(i)
                addView(scrap)
                if (scrapRect == null) {
                    scrapRect = Rect()
                    state.mItemsFrames.put(i, scrapRect)
                }
                measureChildWithMargins(scrap, 0, 0)
                scrapWidth = getDecoratedMeasuredWidth(scrap)
                scrapHeight = getDecoratedMeasuredHeight(scrap)
                leftOffset = (paddingLeft + (width - scrapWidth) / 2.0f).toInt()
                if (startOffset == -1 && startPosition == 0) {
                    //layout the first position item in center
                    val top = (paddingTop + (verticalSpace - scrapHeight) / 2f).toInt()
                    scrapRect.set(leftOffset, top, leftOffset + scrapWidth, top + scrapHeight)
                } else {
                    scrapRect.set(leftOffset, startOffset, leftOffset + scrapWidth, startOffset + scrapHeight)
                }
                layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
                startOffset = scrapRect.bottom
                mLastVisiblePos = i
                if (BuildConfig.DEBUG) {
                    Log.d(
                        TAG,
                        "fillWithVertical: add view:$i,startOffset:$startOffset,mLastVisiblePos:$mLastVisiblePos,bottomEdge$bottomEdge"
                    )
                }
                i++
            }
        } else {
            //dy<0
            if (childCount > 0) {
                val firstView = getChildAt(0)
                startPosition = getPosition(firstView!!) - 1 //前一个View的position
                startOffset = getDecoratedTop(firstView)
            }
            var i = startPosition
            while (i >= 0 && startOffset > topEdge + dy) {
                scrapRect = state.mItemsFrames.get(i)
                scrap = recycler!!.getViewForPosition(i)
                addView(scrap, 0)
                if (scrapRect == null) {
                    scrapRect = Rect()
                    state.mItemsFrames.put(i, scrapRect)
                }
                measureChildWithMargins(scrap, 0, 0)
                scrapWidth = getDecoratedMeasuredWidth(scrap)
                scrapHeight = getDecoratedMeasuredHeight(scrap)
                leftOffset = (paddingLeft + (width - scrapWidth) / 2.0f).toInt()
                scrapRect.set(leftOffset, startOffset - scrapHeight, leftOffset + scrapWidth, startOffset)
                layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
                startOffset = scrapRect.top
                mFirstVisiblePosition = i
                i--
            }
        }
    }


    /**
     * @param recycler
     * @param state
     */
    private fun fillWithHorizontal(recycler: RecyclerView.Recycler?, state: RecyclerView.State?, dx: Int) {
        val leftEdge = orientationHelper.startAfterPadding
        val rightEdge = orientationHelper.endAfterPadding
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "fillWithHorizontal() called with: dx = [$dx],leftEdge:$leftEdge,rightEdge:$rightEdge")
        }
        //1.remove and recycle the view that disappear in screen
        var child: View?
        if (childCount > 0) {
            if (dx >= 0) {
                //remove and recycle the left off screen view
                var fixIndex = 0
                for (i in 0 until childCount) {
                    child = getChildAt(i + fixIndex)
                    if (getDecoratedRight(child!!) - dx < leftEdge) {
                        removeAndRecycleView(child, recycler!!)
                        mFirstVisiblePosition++
                        fixIndex--
                        if (BuildConfig.DEBUG) {
                            Log.v(
                                TAG,
                                "fillWithHorizontal:removeAndRecycleView:" + getPosition(child) + " mFirstVisiblePosition change to:" + mFirstVisiblePosition
                            )
                        }
                    } else {
                        break
                    }
                }
            } else { //dx<0
                //remove and recycle the right off screen view
                for (i in childCount - 1 downTo 0) {
                    child = getChildAt(i)
                    if (getDecoratedLeft(child!!) - dx > rightEdge) {
                        removeAndRecycleView(child, recycler!!)
                        mLastVisiblePos--
                        if (BuildConfig.DEBUG) {
                            Log.v(
                                TAG,
                                "fillWithHorizontal:removeAndRecycleView:" + getPosition(child) + "mLastVisiblePos change to:" + mLastVisiblePos
                            )
                        }
                    }
                }
            }

        }
        //2.Add or reattach item view to fill screen
        var startPosition = mFirstVisiblePosition
        var startOffset = -1
        var scrapWidth: Int
        var scrapHeight: Int
        var scrapRect: Rect?
        val height = verticalSpace
        var topOffset: Int
        var scrap: View
        if (dx >= 0) {
            if (childCount != 0) {
                val lastView = getChildAt(childCount - 1)
                startPosition = getPosition(lastView!!) + 1 //start layout from next position item
                startOffset = getDecoratedRight(lastView)
                if (BuildConfig.DEBUG) {
                    Log.d(
                        TAG,
                        "fillWithHorizontal:to right startPosition:$startPosition,startOffset:$startOffset,rightEdge:$rightEdge"
                    )
                }
            }
            var i = startPosition
            while (i < itemCount && startOffset < rightEdge + dx) {
                scrapRect = state.mItemsFrames.get(i)
                scrap = recycler!!.getViewForPosition(i)
                addView(scrap)
                if (scrapRect == null) {
                    scrapRect = Rect()
                    state.mItemsFrames.put(i, scrapRect)
                }
                measureChildWithMargins(scrap, 0, 0)
                scrapWidth = getDecoratedMeasuredWidth(scrap)
                scrapHeight = getDecoratedMeasuredHeight(scrap)
                topOffset = (paddingTop + (height - scrapHeight) / 2.0f).toInt()
                if (startOffset == -1 && startPosition == 0) {
                    // layout the first position item in center
                    val left = (paddingLeft + (horizontalSpace - scrapWidth) / 2f).toInt()
                    scrapRect.set(left, topOffset, left + scrapWidth, topOffset + scrapHeight)
                } else {
                    scrapRect.set(startOffset, topOffset, startOffset + scrapWidth, topOffset + scrapHeight)
                }
                layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
                startOffset = scrapRect.right
                mLastVisiblePos = i
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "fillWithHorizontal,layout:mLastVisiblePos: $mLastVisiblePos")
                }
                i++
            }
        } else {
            //dx<0
            if (childCount > 0) {
                val firstView = getChildAt(0)
                startPosition = getPosition(firstView!!) - 1 //start layout from previous position item
                startOffset = getDecoratedLeft(firstView)
                if (BuildConfig.DEBUG) {
                    Log.d(
                        TAG,
                        "fillWithHorizontal:to left startPosition:$startPosition,startOffset:$startOffset,leftEdge:$leftEdge,child count:$childCount"
                    )
                }
            }
            var i = startPosition
            while (i >= 0 && startOffset > leftEdge + dx) {
                scrapRect = state.mItemsFrames.get(i)
                scrap = recycler!!.getViewForPosition(i)
                addView(scrap, 0)
                if (scrapRect == null) {
                    scrapRect = Rect()
                    state.mItemsFrames.put(i, scrapRect)
                }
                measureChildWithMargins(scrap, 0, 0)
                scrapWidth = getDecoratedMeasuredWidth(scrap)
                scrapHeight = getDecoratedMeasuredHeight(scrap)
                topOffset = (paddingTop + (height - scrapHeight) / 2.0f).toInt()
                scrapRect.set(startOffset - scrapWidth, topOffset, startOffset, topOffset + scrapHeight)
                layoutDecorated(scrap, scrapRect.left, scrapRect.top, scrapRect.right, scrapRect.bottom)
                startOffset = scrapRect.left
                mFirstVisiblePosition = i
                i--
            }
        }
    }

    private fun calculateScrollDirectionForPosition(position: Int): Int {
        if (childCount == 0) {
            return LAYOUT_START
        }
        val firstChildPos = mFirstVisiblePosition
        return if (position < firstChildPos) LAYOUT_START else LAYOUT_END
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        val direction = calculateScrollDirectionForPosition(targetPosition)
        val outVector = PointF()
        if (direction == 0) {
            return null
        }
        if (orientation == HORIZONTAL) {
            outVector.x = direction.toFloat()
            outVector.y = 0f
        } else {
            outVector.x = 0f
            outVector.y = direction.toFloat()
        }
        return outVector
    }

    /**
     * @author chensuilun
     */
    internal inner class State {
        /**
         * Record all item view 's last position after last layout
         */
        var mItemsFrames: SparseArray<Rect>

        /**
         * RecycleView 's current scroll distance since first layout
         */
        var mScrollDelta: Int = 0

        init {
            mItemsFrames = SparseArray()
            mScrollDelta = 0
        }
    }


    override fun canScrollHorizontally(): Boolean {
        return orientation == HORIZONTAL
    }


    override fun canScrollVertically(): Boolean {
        return orientation == VERTICAL
    }


    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        // When dx is positive，finger fling from right to left(←)，scrollX+
        if (childCount == 0 || dx == 0) {
            return 0
        }
        var delta = -dx
        val parentCenter =
            (orientationHelper.endAfterPadding - orientationHelper.startAfterPadding) / 2 + orientationHelper.startAfterPadding
        val child: View?
        if (dx > 0) {
            //If we've reached the last item, enforce limits
            if (getPosition(getChildAt(childCount - 1)!!) == itemCount - 1) {
                child = getChildAt(childCount - 1)
                delta = -Math.max(0, Math.min(dx, (child!!.right - child.left) / 2 + child.left - parentCenter))
            }
        } else {
            //If we've reached the first item, enforce limits
            if (mFirstVisiblePosition == 0) {
                child = getChildAt(0)
                delta = -Math.min(0, Math.max(dx, (child!!.right - child.left) / 2 + child.left - parentCenter))
            }
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "scrollHorizontallyBy: dx:$dx,fixed:$delta")
        }
        state.mScrollDelta = -delta
        fillCover(recycler, state, -delta)
        offsetChildrenHorizontal(delta)
        return -delta
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        if (childCount == 0 || dy == 0) {
            return 0
        }
        var delta = -dy
        val parentCenter =
            (orientationHelper.endAfterPadding - orientationHelper.startAfterPadding) / 2 + orientationHelper.startAfterPadding
        val child: View?
        if (dy > 0) {
            //If we've reached the last item, enforce limits
            if (getPosition(getChildAt(childCount - 1)!!) == itemCount - 1) {
                child = getChildAt(childCount - 1)
                delta = -Math.max(
                    0,
                    Math.min(
                        dy,
                        (getDecoratedBottom(child!!) - getDecoratedTop(child)) / 2 + getDecoratedTop(child) - parentCenter
                    )
                )
            }
        } else {
            //If we've reached the first item, enforce limits
            if (mFirstVisiblePosition == 0) {
                child = getChildAt(0)
                delta = -Math.min(
                    0,
                    Math.max(
                        dy,
                        (getDecoratedBottom(child!!) - getDecoratedTop(child)) / 2 + getDecoratedTop(child) - parentCenter
                    )
                )
            }
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "scrollVerticallyBy: dy:$dy,fixed:$delta")
        }
        state.mScrollDelta = -delta
        fillCover(recycler, state, -delta)
        offsetChildrenVertical(delta)
        return -delta
    }

    /**
     * @author chensuilun
     */
    class LayoutParams : RecyclerView.LayoutParams {

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {}

        constructor(width: Int, height: Int) : super(width, height) {}

        constructor(source: ViewGroup.MarginLayoutParams) : super(source) {}

        constructor(source: ViewGroup.LayoutParams) : super(source) {}

        constructor(source: RecyclerView.LayoutParams) : super(source) {}
    }


    fun setItemTransformer(itemTransformer: ItemTransformer) {
        mItemTransformer = itemTransformer
    }

    /**
     * A ItemTransformer is invoked whenever a attached item is scrolled.
     * This offers an opportunity for the application to apply a custom transformation
     * to the item views using animation properties.
     */
    interface ItemTransformer {

        /**
         * Apply a property transformation to the given item.
         *
         * @param layoutManager Current LayoutManager
         * @param item          Apply the transformation to this item
         * @param fraction      of page relative to the current front-and-center position of the pager.
         * 0 is front and center. 1 is one full
         * page position to the right, and -1 is one page position to the left.
         */
        fun transformItem(layoutManager: GalleryLayoutManager, item: View?, fraction: Float)
    }

    /**
     * Listen for changes to the selected item
     *
     * @author chensuilun
     */
    interface OnItemSelectedListener {
        /**
         * @param recyclerView The RecyclerView which item view belong to.
         * @param item         The current selected view
         * @param position     The current selected view's position
         */
        fun onItemSelected(recyclerView: RecyclerView, item: View, position: Int)
    }

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener
    }

    fun attach(recyclerView: RecyclerView) {
        this.attach(recyclerView, -1)
    }

    /**
     * @param recyclerView
     * @param selectedPosition
     */
    fun attach(recyclerView: RecyclerView?, selectedPosition: Int) {
        if (recyclerView == null) {
            throw IllegalArgumentException("The attach RecycleView must not null!!")
        }
        mRecyclerView = recyclerView
        mInitialSelectedPosition = Math.max(0, selectedPosition)
        recyclerView.layoutManager = this
        mSnapHelper.attachToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(mInnerScrollListener)
    }


    fun setCallbackInFling(callbackInFling: Boolean) {
        mCallbackInFling = callbackInFling
    }

    /**
     * Inner Listener to listen for changes to the selected item
     *
     * @author chensuilun
     */
    private inner class InnerScrollListener : RecyclerView.OnScrollListener() {
        internal var mState: Int = 0
        internal var mCallbackOnIdle: Boolean = false

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val snap = mSnapHelper.findSnapView(recyclerView.layoutManager!!)
            if (snap != null) {
                val selectedPosition = recyclerView.layoutManager!!.getPosition(snap)
                if (selectedPosition != curSelectedPosition) {
                    if (mCurSelectedView != null) {
                        mCurSelectedView!!.isSelected = false
                    }
                    mCurSelectedView = snap
                    mCurSelectedView!!.isSelected = true
                    curSelectedPosition = selectedPosition
                    if (!mCallbackInFling && mState != SCROLL_STATE_IDLE) {
                        if (BuildConfig.DEBUG) {
                            Log.v(TAG, "ignore selection change callback when fling ")
                        }
                        mCallbackOnIdle = true
                        return
                    }
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener!!.onItemSelected(recyclerView, snap, curSelectedPosition)
                    }
                }
            }
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "onScrolled: dx:$dx,dy:$dy")
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            mState = newState
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "onScrollStateChanged: $newState")
            }
            if (mState == SCROLL_STATE_IDLE) {
                val snap = mSnapHelper.findSnapView(recyclerView.layoutManager!!)
                if (snap != null) {
                    val selectedPosition = recyclerView.layoutManager!!.getPosition(snap)
                    if (selectedPosition != curSelectedPosition) {
                        if (mCurSelectedView != null) {
                            mCurSelectedView!!.isSelected = false
                        }
                        mCurSelectedView = snap
                        mCurSelectedView!!.isSelected = true
                        curSelectedPosition = selectedPosition
                        if (mOnItemSelectedListener != null) {
                            mOnItemSelectedListener!!.onItemSelected(recyclerView, snap, curSelectedPosition)
                        }
                    } else if (!mCallbackInFling && mOnItemSelectedListener != null && mCallbackOnIdle) {
                        mCallbackOnIdle = false
                        mOnItemSelectedListener!!.onItemSelected(recyclerView, snap, curSelectedPosition)
                    }
                } else {
                    Log.e(TAG, "onScrollStateChanged: snap null")
                }
            }
        }
    }


    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller = GallerySmoothScroller(recyclerView!!.context)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    /**
     * Implement to support [GalleryLayoutManager.smoothScrollToPosition]
     */
    private inner class GallerySmoothScroller(context: Context) : LinearSmoothScroller(context) {

        /**
         * Calculates the horizontal scroll amount necessary to make the given view in center of the RecycleView
         *
         * @param view The view which we want to make in center of the RecycleView
         * @return The horizontal scroll amount necessary to make the view in center of the RecycleView
         */
        fun calculateDxToMakeCentral(view: View): Int {
            val layoutManager = layoutManager
            if (layoutManager == null || !layoutManager.canScrollHorizontally()) {
                return 0
            }
            val params = view.layoutParams as RecyclerView.LayoutParams
            val left = layoutManager.getDecoratedLeft(view) - params.leftMargin
            val right = layoutManager.getDecoratedRight(view) + params.rightMargin
            val start = layoutManager.paddingLeft
            val end = layoutManager.width - layoutManager.paddingRight
            val childCenter = left + ((right - left) / 2.0f).toInt()
            val containerCenter = ((end - start) / 2f).toInt()
            return containerCenter - childCenter
        }

        /**
         * Calculates the vertical scroll amount necessary to make the given view in center of the RecycleView
         *
         * @param view The view which we want to make in center of the RecycleView
         * @return The vertical scroll amount necessary to make the view in center of the RecycleView
         */
        fun calculateDyToMakeCentral(view: View): Int {
            val layoutManager = layoutManager
            if (layoutManager == null || !layoutManager.canScrollVertically()) {
                return 0
            }
            val params = view.layoutParams as RecyclerView.LayoutParams
            val top = layoutManager.getDecoratedTop(view) - params.topMargin
            val bottom = layoutManager.getDecoratedBottom(view) + params.bottomMargin
            val start = layoutManager.paddingTop
            val end = layoutManager.height - layoutManager.paddingBottom
            val childCenter = top + ((bottom - top) / 2.0f).toInt()
            val containerCenter = ((end - start) / 2f).toInt()
            return containerCenter - childCenter
        }


        protected override fun onTargetFound(
            targetView: View,
            state: RecyclerView.State?,
            action: RecyclerView.SmoothScroller.Action
        ) {
            val dx = calculateDxToMakeCentral(targetView)
            val dy = calculateDyToMakeCentral(targetView)
            val distance = Math.sqrt((dx * dx + dy * dy).toDouble()).toInt()
            val time = calculateTimeForDeceleration(distance)
            if (time > 0) {
                action.update(-dx, -dy, time, mDecelerateInterpolator)
            }
        }
    }

    companion object {
        private val TAG = "GalleryLayoutManager"
        internal val LAYOUT_START = -1

        internal val LAYOUT_END = 1

        val HORIZONTAL = OrientationHelper.HORIZONTAL

        val VERTICAL = OrientationHelper.VERTICAL
    }
}