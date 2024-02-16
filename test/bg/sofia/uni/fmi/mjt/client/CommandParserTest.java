package bg.sofia.uni.fmi.mjt.client;

import bg.sofia.uni.fmi.mjt.exceptions.UnsupportedCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandParserTest {
    @Test
    void testParseCommandWithNullText() {
        CommandType actual = CommandParser.parse(null);

        assertEquals(CommandType.UNKNOWN, actual,
                "Expected commandType.UNKNOWN but it was : " + actual);
    }

    @Test
    void testParseCommandWithEmptyText() {
        CommandType actual1 = CommandParser.parse("");
        CommandType actual2 = CommandParser.parse("     ");

        assertEquals(CommandType.UNKNOWN, actual1,
                "Expected commandType.UNKNOWN but it was : " + actual1);
        assertEquals(CommandType.UNKNOWN, actual2,
                "Expected commandType.UNKNOWN but it was : " + actual2);
    }

    @Test
    void testParseCommandGetFoodWithIncorrectPrefixFormat() {
        CommandType actual1 = CommandParser.parse("get-food1");
        CommandType actual2 = CommandParser.parse("get-foodN");
        CommandType actual3 = CommandParser.parse("get-food@");
        CommandType actual4 = CommandParser.parse("get_food ");

        assertEquals(CommandType.UNKNOWN, actual1,
                "Expected commandType.UNKNOWN but it was : " + actual1);
        assertEquals(CommandType.UNKNOWN, actual2,
                "Expected commandType.UNKNOWN but it was : " + actual2);
        assertEquals(CommandType.UNKNOWN, actual3,
                "Expected commandType.UNKNOWN but it was : " + actual3);
        assertEquals(CommandType.UNKNOWN, actual4,
                "Expected commandType.UNKNOWN but it was : " + actual4);
    }

    @Test
    void testParseCommandGetFoodPrefixIsCaseSensitive() {
        CommandType actual1 = CommandParser.parse("Get-food");
        CommandType actual2 = CommandParser.parse("get-Food");
        CommandType actual3 = CommandParser.parse("Get-Food");
        CommandType actual4 = CommandParser.parse("GET-FOOD");

        assertEquals(CommandType.UNKNOWN, actual1,
                "Expected commandType.UNKNOWN but it was : " + actual1);
        assertEquals(CommandType.UNKNOWN, actual2,
                "Expected commandType.UNKNOWN but it was : " + actual2);
        assertEquals(CommandType.UNKNOWN, actual3,
                "Expected commandType.UNKNOWN but it was : " + actual3);
        assertEquals(CommandType.UNKNOWN, actual4,
                "Expected commandType.UNKNOWN but it was : " + actual4);
    }

    @Test
    void testParseCommandGetFoodWithDigitsInName() {
        String case1 = "get-food tomatoes12";
        String case2 = "get-food tomatoes 12";
        String case3 = "get-food t3oma1to7e2";

        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case1),
                "Expected UnsupportedCommandException but wasn't thrown");
        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case2),
                "Expected UnsupportedCommandException but wasn't thrown");
        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case3),
                "Expected UnsupportedCommandException but wasn't thrown");
    }

    @Test
    void testParseCommandGetFoodWithSpecialSymbolsInName() {
        String case1 = "get-food pasta#cherry*tomatoes\\peperoni+cheese"; // # * \ +
        String case2 = "get-food pasta_cherry-tomatoes!peperoni?cheese"; // _ - ! ?
        String case3 = "get-food pasta$cherry&tomatoes|peperoni%cheese"; // $ & | %

        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case1),
                "Expected UnsupportedCommandException but wasn't thrown");
        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case2),
                "Expected UnsupportedCommandException but wasn't thrown");
        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case3),
                "Expected UnsupportedCommandException but wasn't thrown");
    }

    @Test
    void testParseCommandGetFoodIsCorrect() {
        CommandType actual1 = CommandParser.parse("get-food tomatoes");
        CommandType actual2 = CommandParser.parse("get-food beef noodle soup");

        assertEquals(CommandType.GET_FOOD_BY_NAME, actual1,
                "Expected commandType.GET_FOOD_BY_NAME but it was : " + actual1);
        assertEquals(CommandType.GET_FOOD_BY_NAME, actual2,
                "Expected commandType.GET_FOOD_BY_NAME but it was : " + actual2);
    }

    @Test
    void testParseCommandGetFoodReportWithIncorrectPrefixFormat() {
        CommandType actual1 = CommandParser.parse("get-food1");
        CommandType actual2 = CommandParser.parse("get-foodN");
        CommandType actual3 = CommandParser.parse("get-food@");
        CommandType actual4 = CommandParser.parse("get_food ");

        assertEquals(CommandType.UNKNOWN, actual1,
                "Expected commandType.UNKNOWN but it was : " + actual1);
        assertEquals(CommandType.UNKNOWN, actual2,
                "Expected commandType.UNKNOWN but it was : " + actual2);
        assertEquals(CommandType.UNKNOWN, actual3,
                "Expected commandType.UNKNOWN but it was : " + actual3);
        assertEquals(CommandType.UNKNOWN, actual4,
                "Expected commandType.UNKNOWN but it was : " + actual4);
    }

    @Test
    void testParseCommandGetFoodReportPrefixIsCaseSensitive() {
        CommandType actual1 = CommandParser.parse("Get-food-report");
        CommandType actual2 = CommandParser.parse("get-food-Report");
        CommandType actual3 = CommandParser.parse("Get-Food-Report");
        CommandType actual4 = CommandParser.parse("GET-FOOD-REPORT");

        assertEquals(CommandType.UNKNOWN, actual1,
                "Expected commandType.UNKNOWN but it was : " + actual1);
        assertEquals(CommandType.UNKNOWN, actual2,
                "Expected commandType.UNKNOWN but it was : " + actual2);
        assertEquals(CommandType.UNKNOWN, actual3,
                "Expected commandType.UNKNOWN but it was : " + actual3);
        assertEquals(CommandType.UNKNOWN, actual4,
                "Expected commandType.UNKNOWN but it was : " + actual4);
    }

    @Test
    void testParseCommandGetFoodReportWithDigitsInName() {
        String case1 = "get-food-report 415269tomatoes";
        String case2 = "get-food-report tomatoes 415269";
        String case3 = "get-food-report to2mat1oe1s";

        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case1),
                "Expected UnsupportedCommandException but wasn't thrown");
        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case2),
                "Expected UnsupportedCommandException but wasn't thrown");
        assertThrows(UnsupportedCommandException.class,
                () -> CommandParser.parse(case3),
                "Expected UnsupportedCommandException but wasn't thrown");
    }

    @Test
    void testParseCommandGetFoodReportWithSpecialSymbolsInName() { // to-do throw exceptions
        CommandType actual1 = CommandParser.parse("get-food cherry*tomatoes");
        CommandType actual2 = CommandParser.parse("get-food cherry@tomatoes");
        CommandType actual3 = CommandParser.parse("get-food cherry_tomatoes");
        CommandType actual4 = CommandParser.parse("get-food cherry-tomatoes");
        CommandType actual5 = CommandParser.parse("get-food cherry!tomatoes");
        CommandType actual6 = CommandParser.parse("get-food cherry#tomatoes");
        CommandType actual7 = CommandParser.parse("get-food cherry?tomatoes");

        assertEquals(CommandType.UNKNOWN, actual1,
                "Expected commandType.UNKNOWN but it was : " + actual1);
        assertEquals(CommandType.UNKNOWN, actual2,
                "Expected commandType.UNKNOWN but it was : " + actual2);
        assertEquals(CommandType.UNKNOWN, actual3,
                "Expected commandType.UNKNOWN but it was : " + actual3);
        assertEquals(CommandType.UNKNOWN, actual4,
                "Expected commandType.UNKNOWN but it was : " + actual4);
        assertEquals(CommandType.UNKNOWN, actual5,
                "Expected commandType.UNKNOWN but it was : " + actual5);
        assertEquals(CommandType.UNKNOWN, actual6,
                "Expected commandType.UNKNOWN but it was : " + actual6);
        assertEquals(CommandType.UNKNOWN, actual7,
                "Expected commandType.UNKNOWN but it was : " + actual7);
    }

    @Test
    void testParseCommandGetFoodReportIsCorrect() {
        CommandType actual1 = CommandParser.parse("get-food-report 415269");
        CommandType actual2 = CommandParser.parse("get-food-report 415234");

        assertEquals(CommandType.GET_FOOD_REPORT_BY_FCD_ID, actual1,
                "Expected commandType.GET_FOOD_BY_NAME but it was : " + actual1);
        assertEquals(CommandType.GET_FOOD_REPORT_BY_FCD_ID, actual2,
                "Expected commandType.GET_FOOD_BY_NAME but it was : " + actual2);
    }
}
