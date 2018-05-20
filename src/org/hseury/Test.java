package org.hseury;

import static org.hseury.Value.PARSE_RESULT.LEPT_PARSE_EXPECT_VALUE;
import static org.hseury.Value.PARSE_RESULT.LEPT_PARSE_INVALID_VALUE;
import static org.hseury.Value.PARSE_RESULT.LEPT_PARSE_ROOT_NOT_SINGULAR;

/**
 * Created by hseury on 2018/5/11.
 */
public class Test {

    static int test_count = 0;
    static int test_pass = 0;


    public static void testParse() {
        testParseNull();
        testParseTrue();
        testParseFalse();
        testParseExpectValue();
        testParseInvalidValue();
        testParseRootNotSingular();
    }
    private static void testParseNull() {
        Value  v = new Value();
        v.type = Value.JSON_TYPE.EASY_FALSE;
        expectEqualInt(Value.PARSE_RESULT.LEPT_PARSE_OK,Value.parse(v,"null"));
        expectEqualInt(Value.JSON_TYPE.EASY_NULL,Value.getType(v));
    }

    private static void testParseTrue() {
        Value  v = new Value();
        v.type = Value.JSON_TYPE.EASY_FALSE;
        expectEqualInt(Value.PARSE_RESULT.LEPT_PARSE_OK,Value.parse(v,"true"));
        expectEqualInt(Value.JSON_TYPE.EASY_TRUE,Value.getType(v));
    }

    private static void testParseFalse() {
        Value  v = new Value();
        v.type = Value.JSON_TYPE.EASY_TRUE;
        expectEqualInt(Value.PARSE_RESULT.LEPT_PARSE_OK,Value.parse(v,"false"));
        expectEqualInt(Value.JSON_TYPE.EASY_FALSE,Value.getType(v));
    }


    private static void testParseExpectValue() {
        testError(LEPT_PARSE_EXPECT_VALUE,"");
        testError(LEPT_PARSE_EXPECT_VALUE," ");
    }

    private static void testParseInvalidValue() {
        testError(LEPT_PARSE_INVALID_VALUE,"nul");
        testError(LEPT_PARSE_INVALID_VALUE,"?");
    }

    private static void testParseRootNotSingular() {
        testError(LEPT_PARSE_ROOT_NOT_SINGULAR,"null   x");
    }




    public static <T> void expectEqualInt(T expect,T actual){
        expectEqualBase((expect == actual),expect,actual);
    }


    public static <T> void expectEqualBase(Boolean equality, T expect, T actual) {
        test_count++;
        if (equality)
            test_pass++;
        else {
            System.out.println("stderr expect : " + expect + " actual : " + actual);
        }
    }

    public static void testError(Value.PARSE_RESULT error, String json){
        Value  v = new Value();

        v.type = Value.JSON_TYPE.EASY_FALSE;
        expectEqualInt(error,Value.parse(v,json));
        expectEqualInt(Value.JSON_TYPE.EASY_NULL,Value.getType(v));
    }
}
