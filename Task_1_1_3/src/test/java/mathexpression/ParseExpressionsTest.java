package mathexpression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParseExpressionsTest {

    @Test
    void parseNumber() throws Exception {
        Expression expected = new Number(10);
        String string = "10";
        Expression e = Expression.makeExpression(string);
        assertEquals(expected, e);
    }

    @Test
    void parseVariable() throws Exception {
        Expression expected = new Variable("x");
        String string = "x";
        Expression e = Expression.makeExpression(string);
        assertEquals(expected, e);
    }

    @Test
    void parseExpr00() throws Exception {
        String string = "";
        assertThrows(IncorrectStringExpressionException.class, () -> {
            Expression.makeExpression(string);
        });
    }

    @Test
    void parseExpr01() throws Exception {
        String string = "(x5)";
        assertThrows(IncorrectStringExpressionException.class, () -> {
            Expression.makeExpression(string);
        });
    }

    @Test
    void parseExpr02() throws Exception {
        String string = "x+5";
        assertThrows(IncorrectStringExpressionException.class, () -> {
            Expression.makeExpression(string);
        });
    }

    @Test
    void parseExpr1() throws Exception {
        Expression expected = new Add(new Number(3),
            new Mul(new Number(2), new Variable("x")));
        String string = "(3+(2*x))";
        Expression e = Expression.makeExpression(string);
        assertEquals(expected, e);
    }

    @Test
    void parseExpr2() throws Exception {
        Expression expected = new Sub(
            new Mul(new Number(190), new Number(666)),
            new Mul(new Number(2), new Variable("xy")));
        String string = "((190*666)-(2*xy))";
        Expression e = Expression.makeExpression(string);
        assertEquals(expected, e);
    }

    @Test
    void parseExpr3() throws Exception {
        Expression expected = new Add(
            new Mul(
                new Div(
                    new Sub(new Variable("y"),
                        new Number(4)),
                    new Number(3)),
                new Number(2)),
            new Number(1));
        String string = "((((y-4)/3)*2)+1)";
        Expression e = Expression.makeExpression(string);
        assertEquals(expected, e);
    }

    @Test
    void parseExpr4() throws Exception {
        Expression expected = new Div(
            new Sub(new Variable("y"), new Number(4)),
            new Number(3));
        String string = "((y-4)/3)";
        Expression e = Expression.makeExpression(string);
        assertEquals(expected, e);
    }
}
