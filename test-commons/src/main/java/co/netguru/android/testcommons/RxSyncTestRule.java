package co.netguru.android.testcommons;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * <p> Use this JUnit test rule to enforce synchronous RxJava behavior for testing purposes.
 * Including this rule in your test class will override the following methods:
 * <ul>
 *  <li>{@link Schedulers#io()}</li>
 *  <li>{@link Schedulers#computation()}</li>
 *  <li>{@link Schedulers#newThread()}</li>
 *  <li>{@link AndroidSchedulers#mainThread()}</li>
 * </ul>
 * to return the {@link Schedulers#immediate()} scheduler that works synchronously and provides
 * additional debugging functionalities. </p>
 *
 * <p> Usage - put the following code at the top of your JUnit test class:</p>
 *
 * <pre> {@code
 * @literal @Rule
 *   public TestRule rxRule = new RxSyncTestRule();
 * } </pre>
 *
 */
public class RxSyncTestRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                // Override RxJava schedulers
                RxJavaHooks.reset();
                RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
                    @Override
                    public Scheduler call(Scheduler scheduler) {
                        return Schedulers.immediate();
                    }
                });
                RxJavaHooks.setOnComputationScheduler(new Func1<Scheduler, Scheduler>() {
                    @Override
                    public Scheduler call(Scheduler scheduler) {
                        return Schedulers.immediate();
                    }
                });
                RxJavaHooks.setOnNewThreadScheduler(new Func1<Scheduler, Scheduler>() {
                    @Override
                    public Scheduler call(Scheduler scheduler) {
                        return Schedulers.immediate();
                    }
                });

                // Override RxAndroid schedulers
                final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
                rxAndroidPlugins.reset();
                rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
                    @Override
                    public Scheduler getMainThreadScheduler() {
                        return Schedulers.immediate();
                    }
                });

                base.evaluate();

                RxJavaHooks.reset();
                rxAndroidPlugins.reset();
            }
        };
    }
}
