import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({ "sprint5_test","software_masters.planner_networking", "software_masters.gui_test"})
class ApplicationTest {

}