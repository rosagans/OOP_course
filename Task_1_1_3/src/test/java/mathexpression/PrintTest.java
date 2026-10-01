package mathexpression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintTest {

    @Test
    void printNumber() {
        Expression e = new Number(0);
        assertEquals("0", e.toString());
    }

    @Test
    void printVariable() {
        Expression e = new Variable("qwerty");
        assertEquals("qwerty", e.toString());
    }

    @Test
    void printExpr1() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        assertEquals("(3+(2*x))", e.toString());
    }

    @Test
    void printExpr2() {
        Expression e = new Sub(
                new Mul(new Number(190),
                        new Number(666)),
                new Mul(new Number(2),
                        new Variable("xy")));
        assertEquals("((190*666)-(2*xy))", e.toString());
    }

    @Test
    void printExpr3() {
        Expression e = new Add(
                new Mul(
                        new Div(
                                new Sub(new Variable("y"),
                                        new Number(4)),
                                new Number(3)),
                        new Number(2)),
                new Number(1));
        assertEquals("((((y-4)/3)*2)+1)", e.toString());
    }
}
