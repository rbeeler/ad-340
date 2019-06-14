package com.ruthbeeler.possibleapplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class Validate {

    private MainActivity mainActivity = new MainActivity();

    @Test
    public void trueIfValid() {
        assertThat(mainActivity.validateForm(), is(true));
    }

    @Test
    public void falseIfEmailEmpty() {
        assertThat(mainActivity.validateForm(), is(false));
    }

    @Test
    public void falseIfPasswordEmpty() {
        assertThat(mainActivity.validateForm(), is(false));
    }

    @Test
    public void falseIfNameEmpty() {
        assertThat(mainActivity.validateForm(), is(false));
    }


}
