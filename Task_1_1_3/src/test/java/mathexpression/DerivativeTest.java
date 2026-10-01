package mathexpression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DerivativeTest {

    @Test
    void diffVariable1() {
        Expression var = new Variable("x");
        assertEquals("1", var.derivative("x").toString());
    }

    @Test
    void diffVariable2() {
        Expression var = new Variable("y");
        assertEquals("0", var.derivative("x").toString());
    }

    @Test
    void diffNumber() {
        Expression number = new Number(100);
        assertEquals("0", number.derivative("x").toString());
    }

    @Test
    void diffExpr1() {
        // e = (3+(2*x))
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));

        assertEquals("(0+((0*x)+(2*1)))", e.derivative("x").toString());
    }

    @Test
    void diffExpr2() {
        // e = ((190*666)-(2*xy))
        Expression e = new Sub(
                new Mul(new Number(190),
                        new Number(666)),
                new Mul(new Number(2),
                        new Variable("xy")));

        assertEquals("(((0*666)+(190*0))-((0*xy)+(2*1)))", e.derivative("xy").toString());
    }

    @Test
    void diffExpr3() {
        // e = ((y-4)/3)
        Expression e = new Div(
                    new Sub(new Variable("y"),
                            new Number(4)),
                    new Number(3));

        assertEquals("((((1-0)*3)-((y-4)*0))/(3*3))", e.derivative("y").toString());
    }
}
