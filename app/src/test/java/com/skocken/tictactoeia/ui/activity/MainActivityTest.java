package com.skocken.tictactoeia.ui.activity;

import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.tictactoeia.R;
import com.skocken.tictactoeia.ui.definition.BoardDef;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import android.view.View;

public class MainActivityTest extends TestCase {

    @Test
    public void testGetContentView() throws Exception {
        MainActivity activity = new MainActivity();
        assertEquals(R.layout.activity_main, activity.getContentView());
    }

    @Test
    public void testNewPresenter() throws Exception {
        final View view = Mockito.mock(View.class);
        MainActivity activity = new MainActivity() {
            @Override
            public View findViewById(int id) {
                return view;
            }
        };
//        MainActivity activity = Mockito.mock(MainActivity.class);
//        when(activity.findViewById(android.R.id.content)).thenReturn(view);
        BasePresenter presenter = activity.newPresenter();
        assertTrue(presenter instanceof BoardDef.IPresenter);
    }
}