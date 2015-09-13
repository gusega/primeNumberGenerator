package com.mytech.controller;

import java.util.Collections;

import com.mytech.controller.inputModel.ParsedInput;
import com.mytech.controller.inputParser.ParametersParser;
import com.mytech.generators.Generator;
import com.mytech.generatorservice.PrimeNumberGenerator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MainControllerTest {

    private PrimeNumberGenerator generator = Mockito.mock(PrimeNumberGenerator.class);

    private MainController controller = new MainController(
        generator,
        new ParametersParser());

    @Before
    public void setUp() {
        Mockito.when(generator.generate(
            Mockito.any(PrimeNumberGenerator.Strategies.class),
            Mockito.anyInt(), Mockito.anyInt())).thenReturn(PrimeNumberGenerator.GenerationResult.EMPTY);
    }

    @Test
    public void testExecuteGarbleInput() throws Exception {
        assertEquals(ParsedInput.Status.WRONG_FORMAT,
            controller.execute(new String[]{"asdf"}).getStatus());

    }
    @Test
    public void testExecuteEmptyInput() throws Exception {
        assertEquals(ParsedInput.Status.WRONG_FORMAT,
            controller.execute(new String[]{}).getStatus());

    }

    @Test
    public void testExecuteWrongStrategy() throws Exception {
        assertEquals(ParsedInput.Status.WRONG_STRATEGY,
            controller.execute(new String[]{"d3", "3", "4"}).getStatus());

    }

    @Test
    public void testExecuteEmpty() throws Exception {
        assertEquals(ParsedInput.Status.OK,
            controller.execute(new String[]{"n", "99", "4"}).getStatus());

    }

    @Test
    public void testExecuteNotNumber() throws Exception {
        assertEquals(ParsedInput.Status.WRONG_FORMAT,
            controller.execute(new String[]{"n", "a3", "4"}).getStatus());

    }
    @Test
    public void testExecuteTest() throws Exception {
        assertEquals(ParsedInput.Status.TEST,
            controller.execute(new String[]{"test"}).getStatus());

    }
    @Test
    public void testExecuteHELP() throws Exception {
        assertEquals(ParsedInput.Status.HELP,
            controller.execute(new String[]{"--help"}).getStatus());

    }
}
