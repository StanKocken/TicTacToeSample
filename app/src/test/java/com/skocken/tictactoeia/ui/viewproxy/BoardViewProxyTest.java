package com.skocken.tictactoeia.ui.viewproxy;

import com.skocken.tictactoeia.R;
import com.skocken.tictactoeia.ui.definition.BoardDef;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardViewProxyTest extends TestCase {

    private View mContentView;

    private BoardDef.IPresenter mPresenter;

    private BoardViewProxy mViewProxy;

    @Before
    public void setUp() throws Exception {
        mPresenter = Mockito.mock(BoardDef.IPresenter.class);

        mContentView = Mockito.mock(View.class);
        Activity mockActivity = Mockito.mock(Activity.class);
        when(mockActivity.findViewById(android.R.id.content))
                .thenReturn(mContentView);

        mViewProxy = new BoardViewProxy(mockActivity);
        mViewProxy.setPresenter(mPresenter);
    }

    @Test
    public void testBoxClickListener() throws Exception {
        int xExpected = 2;
        int yExpected = 1;

        BoardDef.IPresenter presenter = Mockito.mock(BoardDef.IPresenter.class);
        mViewProxy.setPresenter(presenter);

        View view = Mockito.mock(View.class);
        when(view.getTag(R.id.tag_x)).thenReturn(xExpected);
        when(view.getTag(R.id.tag_y)).thenReturn(yExpected);
        mViewProxy.onClick(view);

        ArgumentCaptor<Integer> xArg = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yArg = ArgumentCaptor.forClass(Integer.class);
        verify(presenter).onSelectBox(xArg.capture(), yArg.capture());
        assertEquals(xExpected, (int) xArg.getValue());
        assertEquals(yExpected, (int) yArg.getValue());
    }

    @Test
    public void testSetBoxValue() throws Exception {
        ViewGroup boxesViewGroup = Mockito.mock(ViewGroup.class);
        when(mContentView.findViewById(R.id.boxes_layout)).thenReturn(boxesViewGroup);

        ViewGroup firstRowViewGroup = Mockito.mock(ViewGroup.class);
        ViewGroup secondRowViewGroup = Mockito.mock(ViewGroup.class);
        ViewGroup thirdRowViewGroup = Mockito.mock(ViewGroup.class);
        when(boxesViewGroup.getChildAt(0)).thenReturn(firstRowViewGroup);
        when(boxesViewGroup.getChildAt(1)).thenReturn(secondRowViewGroup);
        when(boxesViewGroup.getChildAt(2)).thenReturn(thirdRowViewGroup);

        View firstRowFirstColView = Mockito.mock(View.class);
        View secondRowSecondColView = Mockito.mock(View.class);
        View thirdRowSecondColView = Mockito.mock(View.class);
        when(firstRowViewGroup.getChildAt(0)).thenReturn(firstRowFirstColView);
        when(secondRowViewGroup.getChildAt(1)).thenReturn(secondRowSecondColView);
        when(thirdRowViewGroup.getChildAt(1)).thenReturn(thirdRowSecondColView);

        mViewProxy.setBoxValue(0, 0, BoardDef.BOX_CROSS);
        mViewProxy.setBoxValue(1, 1, BoardDef.BOX_ROUND);
        mViewProxy.setBoxValue(1, 2, BoardDef.BOX_EMPTY);

        assertBackgroundColor(Color.RED, firstRowFirstColView);
        assertBackgroundColor(Color.BLUE, secondRowSecondColView);
        assertBackgroundColor(Color.GRAY, thirdRowSecondColView);
    }

    private void assertBackgroundColor(int expectedColor, View view) {
        ArgumentCaptor<Integer> colorArg = ArgumentCaptor.forClass(Integer.class);
        verify(view).setBackgroundColor(colorArg.capture());
        assertEquals("The background expectedColor is not correct",
                expectedColor, (int) colorArg.getValue());
    }
}