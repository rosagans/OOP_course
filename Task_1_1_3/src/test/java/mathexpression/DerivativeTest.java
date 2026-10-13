package mathexpression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DerivativeTest {

    @Test
    void diffVariable1() {
        Expression var = new Variable("x");
        assertEquals(new Number(1), var.derivative("x"));
    }

    @Test
    void diffVariable2() {
        Expression var = new Variable("y");
        assertEquals(new Number(0), var.derivative("x"));
    }

    @Test
    void diffNumber() {
        Expression number = new Number(100);
        assertEquals(new Number(0), number.derivative("x"));
    }

    @Test
    void diffExpr1() {
        // e.toString() = (3+(2*x))
        Expression e = new Add(new Number(3), new Mul(new Number(2),
            new Variable("x")));

        // result.toString() = "(0+((0*x)+(2*1)))"
        Expression result = new Add(new Number(0),
            new Add(new Mul(new Number(0), new Variable("x")),
                new Mul(new Number(2), new Number(1))));

        assertEquals(result, e.derivative("x"));
    }

    @Test
    void diffExpr2() {
        // e.toString() = (666-(2*xy)
        Expression e = new Sub(new Number(666),
            new Mul(new Number(2),
                new Variable("xy")));

        // result.toString() = (0-((0*xy)+(2*1)))
        Expression result = new Sub(new Number(0),
            new Add(new Mul(new Number(0), new Variable("xy")),
                new Mul(new Number(2), new Number(1))));

        assertEquals(result, e.derivative("xy"));
    }

    @Test
    void diffExpr3() {
        // e.toString() = (y/3)
        Expression e = new Div(new Variable("y"), new Number(3));

        // result.toString() = (((1*3)-(y*0))/(3*3))
        Expression result = new Div(
            new Sub(new Mul(new Number(1), new Number(3)),
                new Mul(new Variable("y"), new Number(0))),
            new Mul(new Number(3), new Number(3)));

        assertEquals(result, e.derivative("y"));
    }
}
