package org.hseury;

/**
 * Created by hseury on 2018/5/11.
 */
public class Test {

    static int test_count = 0;
    static int test_pass = 0;


    public static void testParse() {
        testParseNull();
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


    private static void testParseExpectValue() {
        Value  v = new Value();

        v.type = Value.JSON_TYPE.EASY_FALSE;
        expectEqualInt(Value.PARSE_RESULT.LEPT_PARSE_EXPECT_VALUE,Value.parse(v,""));
        expectEqualInt(Value.JSON_TYPE.EASY_NULL,Value.getType(v));

        v.type = Value.JSON_TYPE.EASY_FALSE;
        expectEqualInt(Value.PARSE_RESULT.LEPT_PARSE_EXPECT_VALUE,Value.parse(v," "));
        expectEqualInt(Value.JSON_TYPE.EASY_NULL,Value.getType(v));
    }

    private static void testParseInvalidValue() {
        Value  v = new Value();

        v.type = Value.JSON_TYPE.EASY_FALSE;
        expectEqualInt(Value.PARSE_RESULT.LEPT_PARSE_INVALID_VALUE,Value.parse(v,"nul"));
        expectEqualInt(Value.JSON_TYPE.EASY_NULL,Value.getType(v));

        v.type = Value.JSON_TYPE.EASY_FALSE;
        expectEqualInt(Value.PARSE_RESULT.LEPT_PARSE_INVALID_VALUE,Value.parse(v,"?"));
        expectEqualInt(Value.JSON_TYPE.EASY_NULL,Value.getType(v));
    }

    private static void testParseRootNotSingular() {
        Value  v = new Value();

        v.type = Value.JSON_TYPE.EASY_FALSE;
        expectEqualInt(Value.PARSE_RESULT.LEPT_PARSE_ROOT_NOT_SINGULAR,Value.parse(v,"null   x"));
        expectEqualInt(Value.JSON_TYPE.EASY_NULL,Value.getType(v));
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
}
