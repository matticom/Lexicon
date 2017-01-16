import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	repository.AllTests.class,
	businessOperations.AllTests.class
})

public class AllTestSuites {

}
