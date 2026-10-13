package mathexpression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignificationTest {

    @Test
    void signifyNumber() throws Exception {
        Expression e = new Number(0);
        assertEquals(0, e.eval("x = 10"));
    }

    @Test
    void signifyVariable() throws Exception {
        Expression e = new Variable("x");
        assertEquals(10, e.eval("x = 10"));
    }

    @Test
    void signifyExpr0() throws Exception {
        // e = (1/x)
        Expression e = new Div(new Number(1), new Variable("x"));
        assertThrows(WrongSignificationException.class, () -> {
            e.eval("x = 0");
        });
    }

    @Test
    void signifyExpr00() throws Exception {
        // e = (1/x)
        Expression e = new Div(new Number(1), new Variable("x"));
        assertThrows(WrongSignificationException.class, () -> {
            e.eval("y = 1");
        });
    }

    @Test
    void signifyExpr000() throws Exception {
        // e = (1/x)
        Expression e = new Div(new Number(1), new Variable("x"));
        assertThrows(WrongSignificationException.class, () -> {
            e.eval("x=1");
        });
    }

    @Test
    void signifyExpr1() throws Exception {
        // e = (3+(2*x))
        Expression e = new Add(new Number(3), new Mul(new Number(2),
            new Variable("x")));

        assertEquals(17, e.eval("x = 7"));
    }

    @Test
    void signifyExpr2() throws Exception {
        // e = ((190*666)-(2*xy))
        Expression e = new Sub(
            new Mul(new Number(190),
                new Number(666)),
            new Mul(new Number(2),
                new Variable("xy")));

        assertEquals(126536, e.eval("xy = 2"));
    }

    @Test
    void signifyExpr3() throws Exception {
        // e = ((y-x)/3)
        Expression e = new Div(
            new Sub(new Variable("y"),
                new Variable("x")),
            new Number(3));

        assertEquals(0, e.eval("x = 2; y = 2"));
    }

    @Test
    void signifyExpr4() throws Exception {
        // e = (qwerty+(2*x))
        Expression e = new Add(new Variable("qwerty"), new Mul(new Number(2),
            new Variable("x")));

        assertEquals(15, e.eval("x = 7; qwerty = 1"));
    }

    @Test
    void signifyExpr5() throws Exception {
        // e = ((y-x)/z)
        Expression e = new Div(
            new Sub(new Variable("y"),
                new Variable("x")),
            new Variable("z"));

        assertEquals(1, e.eval("x = 1; y = 2; z = 1"));
    }
}
