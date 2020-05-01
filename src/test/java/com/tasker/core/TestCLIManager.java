package com.tasker.core;

import com.tasker.core.cli.CmdLine;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author mtolstyh
 * @since 19.02.2016.
 */
@Ignore
public class TestCLIManager {

    public static final String NINE_DIGIT = "[0-9]{9}";
    public static final String NINE_CHARS = "[a-zA-Z]{9}";
    public static final String ONE_CHAR_NINE_DIGIT_PLUS_ONE = "[a-zA-Z]{1}[0-9]{9,10}";
    public static final String FIFTEEN_CHARS_ONE_DIGIT = "[a-zA-Z]{15}[0-9]{1}";
    public static final String NINE_OR_TEN_DIGITS = "[0-9]{9,10}";
    public static final String SEVEN_DIGITS_WITH_ONE_TWO_CHARS = "[0-9]{7}[a-zA-Z]{1,2}";
    public static final String SM_PLUS_FIVE_DIGITS = "SM[0-9]{5}";
    public static final String ONE_CHAR_NINE_DIGITS = "[a-zA-Z]{1}[0-9]{9}";
    public static final String TEN_DIGITS = "[0-9]{10}";
    public static final String SEVEN_OR_NINE_DIGIT = "[0-9]{7,9}";
    public static final String TWElVE_OR_THIRTEEN_CHARS = "[a-zA-Z]{12,13}";
    public static final String NINE_DIGITS_WITH_VAT = "[0-9]{9}VAT";
    public static final String ECMA_262_regex = "^[0-9]+$|^[0-9A-Z-]+$";

    @Test
    public void testCreateTaskOptions01() {
        CmdLine.handle(new String[]{});
    }

    @Test
    public void testCreateTaskOptions0() {
        String[] args = new String[] {"-h"};
        CmdLine.handle(args);
    }

    @Test
    public void testCreateTaskOptions02() {
        String[] args = new String[] {"-h", "a", "asd", "fd", "", "ar"};
        CmdLine.handle(args);
    }
    @Test
    public void testCreateTaskOptionsList() {
        String[] args = new String[] {"-f", "Tasker", "100", "N"};
        CmdLine.handle(args);
    }

    @Test
    public void testGenerateConfig() {
        String[] args = new String[] {"generate", "config"};
        new Tasker().main(args);
    }

    @Test
    public void testCreateTaskOptionsExport() {
        String[] args = new String[] {"-e", "CSV"};
        new Tasker().main(args);
    }

    @Test
    public void testCreateTaskOptionsSyncDB() {
        String[] args = new String[] {"-s", "--local"};
        new Tasker().main(args);
    }

    @Test
    public void testCreateTaskOptionsFilter() {
        String[] args = new String[] {"-f", "Maersk","10"};
        CmdLine.handle(args);
    }

    @Test
    public void testCreateTaskOptionsExportJSON() {
        String[] args = new String[] {"-e", "JSON"};
        CmdLine.handle(args);
    }

    @Test
    public void testCreateTaskOptionsResolve() {
        String[] args = new String[] {"-r", "38eadf05bfe647d983fcde9b85f59e47"};
        CmdLine.handle(args);
    }
    @Test
    public void testCreateTaskOptions1() {
        String[] args = new String[] {"-c", "Maersk \"Just call me angel\""};
        CmdLine.handle(args);
    }
    @Test
    public void testCreateTaskOptions2() {
        String[] args = new String[] {"-t", "Maersk"};
        CmdLine.handle(args);
    }
    @Test
    public void testCreateTaskOptions3() {
        String[] args = new String[] {"-t", "Maersk", "-c", "Just call me angel "};
        CmdLine manager = new CmdLine();
        CmdLine.handle(args);
    }
    @Test
    public void testDifferentRegex() {

        // 9 digits
        String inp = "123123123";
        Assert.assertTrue(inp.matches(NINE_DIGIT));

        //9 characters
        inp = "abcdefghy";
        Assert.assertTrue(inp.matches(NINE_CHARS));

        //9 characters
        inp = "abcdefghya";
        Assert.assertFalse(inp.matches(NINE_CHARS));

        //1 letter, 9 digits (includes 1 checksum)
        inp = "a123123123";
        Assert.assertTrue(inp.matches(ONE_CHAR_NINE_DIGIT_PLUS_ONE));

        inp = "a1231231231";
        Assert.assertTrue(inp.matches(ONE_CHAR_NINE_DIGIT_PLUS_ONE));

        inp = "aa123123123a";
        Assert.assertFalse(inp.matches(ONE_CHAR_NINE_DIGIT_PLUS_ONE));

        //16 characters (15 plus 1 check digit)
        inp = "abcdhdjfkbdhsjv1";//16
        Assert.assertTrue(inp.matches(FIFTEEN_CHARS_ONE_DIGIT));

        //9 or 10 digits
        inp = "123123123";//9
        Assert.assertTrue(inp.matches(NINE_OR_TEN_DIGITS));
        inp = "1231231231";//10
        Assert.assertTrue(inp.matches(NINE_OR_TEN_DIGITS));

//        7 digits plus 1 or 2 letters
        inp = "1231231a";
        Assert.assertTrue(inp.matches(SEVEN_DIGITS_WITH_ONE_TWO_CHARS));
        inp = "1231231ac";
        Assert.assertTrue(inp.matches(SEVEN_DIGITS_WITH_ONE_TWO_CHARS));

//        7 or 8 digit plus trailing check digit
        inp = "1231231";//7
        Assert.assertTrue(inp.matches(SEVEN_OR_NINE_DIGIT));
        inp = "12312311";//8
        Assert.assertTrue(inp.matches(SEVEN_OR_NINE_DIGIT));
        inp = "123123122";//9
        Assert.assertTrue(inp.matches(SEVEN_OR_NINE_DIGIT));

//        12 or 13 characters
        inp = "abcabcabcabc";
        Assert.assertTrue(inp.matches(TWElVE_OR_THIRTEEN_CHARS));
        inp = "abcabcabcabca";
        Assert.assertTrue(inp.matches(TWElVE_OR_THIRTEEN_CHARS));

//        9 digits with a control digit
        inp = "123123123";
        Assert.assertTrue(inp.matches(NINE_OR_TEN_DIGITS));
        inp = "1231231231";
        Assert.assertTrue(inp.matches(NINE_OR_TEN_DIGITS));

//        10 digits
        inp = "1231231231";
        Assert.assertTrue(inp.matches(TEN_DIGITS));

//        1 letter, 9 digits (includes 1 checksum)
        inp = "a123123123";
        Assert.assertTrue(inp.matches(ONE_CHAR_NINE_DIGITS));

        //"SM" + 5 digits
        inp = "SM12312";
        Assert.assertTrue(inp.matches(SM_PLUS_FIVE_DIGITS));
        inp = "sm12312";
        Assert.assertFalse(inp.matches(SM_PLUS_FIVE_DIGITS));

//        9 digits (Suffix if VAT)
        inp = "123123123VAT";
        Assert.assertTrue(inp.matches(NINE_DIGITS_WITH_VAT));

        inp = "S&E ORDER_FULLFILLING address";
        Assert.assertTrue(inp.matches(ECMA_262_regex));

    }
}
