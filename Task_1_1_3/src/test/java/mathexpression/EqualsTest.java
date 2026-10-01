package mathexpression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EqualsTest {

    @Test
    void numbers() {
        Expression e1 = new Number(0);
        Expression e2 = new Number(0);
        Expression e3 = new Number(1);
        assertTrue(e1.equals(e2));
        assertFalse(e1.equals(e3));
    }

    @Test
    void variables() {
        Expression e1 = new Variable("x");
        Expression e2 = new Variable("x");
        Expression e3 = new Variable("y");
        assertTrue(e1.equals(e2));
        assertFalse(e1.equals(e3));
    }

    @Test
    void expressions1() {
        Expression exprBase = new Add(new Number(5), new Number(6));
        Expression exprSame = new Add(new Number(5), new Number(6));
        Expression exprSwpd = new Add(new Number(6), new Number(5));
        assertTrue(exprBase.equals(exprSame));
        assertFalse(exprBase.equals(exprSwpd));
    }

    @Test
    void expressions2() {
        Expression exprBase = new Add(new Number(5), new Number(6));
        Expression exprSame = new Add(new Number(5), new Number(6));
        Expression exprSwpd = new Add(new Number(6), new Number(5));
        assertTrue(exprBase.equals(exprSame));
        assertFalse(exprBase.equals(exprSwpd));
    }

    @Test
    void expressions3() {
        // exprBase = ((190*666)-(2*xy))
        Expression exprBase = new Sub(
                new Mul(new Number(190),
                        new Number(666)),
                new Mul(new Number(2),
                        new Variable("xy")));
        // exprSame = ((190*666)-(2*xy))
        Expression exprSame = new Sub(
                new Mul(new Number(190),
                        new Number(666)),
                new Mul(new Number(2),
                        new Variable("xy")));
        // exprDifferent = ((190*900)-(2*q))
        Expression exprDifferent = new Sub(
                new Mul(new Number(190),
                        new Number(900)),
                new Mul(new Number(2),
                        new Variable("q")));
        assertTrue(exprBase.equals(exprSame));
        assertFalse(exprBase.equals(exprDifferent));
    }

    @Test
    void expressions4() {
        Expression exprBase = new Div(new Number(6), new Number(3));
        Expression exprSame = new Div(new Number(6), new Number(3));
        Expression exprDifferent = new Div(new Number(3), new Number(6));
        assertTrue(exprBase.equals(exprSame));
        assertFalse(exprBase.equals(exprDifferent));
    }

    @Test
    void expressions5() {
        // exprBase = ((((y-4)/3)*2)+1)
        Expression exprBase = new Add(
                new Mul(
                        new Div(
                                new Sub(new Variable("y"),
                                        new Number(4)),
                                new Number(3)),
                        new Number(2)),
                new Number(1));
        // exprSame = ((((y-4)/3)*2)+1)
        Expression exprSame = new Add(
                new Mul(
                        new Div(
                                new Sub(new Variable("y"),
                                        new Number(4)),
                                new Number(3)),
                        new Number(2)),
                new Number(1));
        // exprDifferent = ((((x-4)/2)*2)+2)
        Expression exprDifferent = new Add(
                new Mul(
                        new Div(
                                new Sub(new Variable("x"),
                                        new Number(4)),
                                new Number(2)),
                        new Number(2)),
                new Number(2));
        assertTrue(exprBase.equals(exprSame));
        assertFalse(exprBase.equals(exprDifferent));
    }
}
