package org.hseury;

import static org.hseury.Value.JSON_TYPE.EASY_NULL;
import static org.hseury.Value.PARSE_RESULT.*;

/**
 * Created by hseury on 2018/5/11.
 */
public class Value {
    enum JSON_TYPE {EASY_NULL, EASY_FALSE, EASY_TRUE, EASY_NUMBER, EASY_STRING, EASY_ARRAY, EASY_OBJECT}


    /**
     * 若一个 JSON 只含有空白，传回 LEPT_PARSE_EXPECT_VALUE。
     * 若一个值之后，在空白之后还有其他字符，传回 LEPT_PARSE_ROOT_NOT_SINGULAR。
     * 若值不是那三种字面值，传回 LEPT_PARSE_INVALID_VALUE。
     */
    enum PARSE_RESULT {
        LEPT_PARSE_OK,
        LEPT_PARSE_EXPECT_VALUE,
        LEPT_PARSE_INVALID_VALUE,
        LEPT_PARSE_ROOT_NOT_SINGULAR
    }

    public static final int NULL_LENGTH = 4;

    public JSON_TYPE type;

    public static Value.JSON_TYPE getType(Value v) {
        assert (v != null);
        return v.type;
    }

    public static PARSE_RESULT parse(Value value, String json) {
        Context context = new Context();
        PARSE_RESULT ret;
        assert (value != null);
        context.json = json;
        value.type = EASY_NULL;
        int i = parseWhitespace(context, 0);
        context.json = context.json.substring(i);
        ret = parseValue(context, value);
        if ( ret == PARSE_RESULT.LEPT_PARSE_OK) {
            int j = parseWhitespace(context, i + NULL_LENGTH);
            if ( j < context.json.length()) {
                ret = LEPT_PARSE_ROOT_NOT_SINGULAR;
            }
        }
        return ret;
    }

    /* ws = *(%x20 / %x09 / %x0A / %x0D) */

    /**
     * 返回不为空的第一个字符的位置
     *
     * @param context
     * @return
     */
    public static int parseWhitespace(Context context, int beginIndex) {
        String json = context.json;
        int index = beginIndex;
        for (; index < json.length(); ) {
            char ch = json.charAt(index);
            if (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r') {
                index++;
            } else {
                break;
            }
        }
        return index;
    }

    /* null  = "null" */
    public static PARSE_RESULT parseNull(Context context, Value value) {
        String json = context.json;
        if (TextUtils.isEmpty(json)) {
            return LEPT_PARSE_EXPECT_VALUE;
        }
        if (json.length() > 3 && json.charAt(0) == 'n' && json.charAt(1) == 'u' && json.charAt(2) == 'l' && json.charAt(3) == 'l') {
            value.type = EASY_NULL;
            return LEPT_PARSE_OK;
        }else{
            return LEPT_PARSE_INVALID_VALUE;
        }
    }


    public static PARSE_RESULT parseValue(Context context, Value value) {
        String json = context.json;
        if (TextUtils.isEmpty(json)) {
            return LEPT_PARSE_EXPECT_VALUE;
        }
        switch (json.charAt(0)) {
            case 'n':
                return parseNull(context, value);
            case '\0':
                return LEPT_PARSE_EXPECT_VALUE;
            //todo: true /false
            default:
                return LEPT_PARSE_INVALID_VALUE;
        }
    }

}
