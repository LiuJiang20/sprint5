

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import sprint5_test.AutoSaveTest;
import sprint5_test.MakeCommentTest;
import sprint5_test.MarkPlanTest;


@RunWith(JUnitPlatform.class)
@SelectClasses({MarkPlanTest.class,MakeCommentTest.class,AutoSaveTest.class})
public class AllTests {

}
