package mathexpression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParseExpressionsTest {

    @Test
    void parseNumber() throws Exception {
        String number = "10";
        Expression e = Expression.makeExpression(number);
        assertEquals(number, e.toString());
    }

    @Test
    void parseVariable() throws Exception {
        String variable = "x";
        Expression e = Expression.makeExpression(variable);
        assertEquals(variable, e.toString());
    }

    @Test
    void parseExpr00() throws Exception {
        String string = "";
        try {
            Expression e = Expression.makeExpression(string);
        } catch (Exception exception) {
            assertTrue(true);
        }
    }

    @Test
    void parseExpr01() throws Exception {
        String string = "(x5)";
        try {
            Expression e = Expression.makeExpression(string);
        } catch (Exception exception) {
            assertTrue(true);
        }
    }

    @Test
    void parseExpr02() throws Exception {
        String string = "x+5";
        try {
            Expression e = Expression.makeExpression(string);
        } catch (Exception exception) {
            assertTrue(true);
        }
    }

    @Test
    void parseExpr1() throws Exception {
        String string = "(3+(2*x))";
        Expression e = Expression.makeExpression(string);
        assertEquals(string, e.toString());
    }

    @Test
    void parseExpr2() throws Exception {
        String string = "((190*666)-(2*xy))";
        Expression e = Expression.makeExpression(string);
        assertEquals(string, e.toString());
    }

    @Test
    void parseExpr3() throws Exception {
        String string = "((((y-4)/3)*2)+1)";
        Expression e = Expression.makeExpression(string);
        assertEquals(string, e.toString());
    }

    @Test
    void parseExpr4() throws Exception {
        String string = "((y-4)/3)";
        Expression e = Expression.makeExpression(string);
        assertEquals(string, e.toString());
    }
}
