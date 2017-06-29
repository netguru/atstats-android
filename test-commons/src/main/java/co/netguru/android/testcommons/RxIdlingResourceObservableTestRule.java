package co.netguru.android.testcommons;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.plugins.RxJavaHooks;
import rx.plugins.RxJavaPlugins;

/**
 * /**
 * <p> Use this JUnit test rule with {@link Espresso} instrumentation tests. </p>
 * <p> Including this rule will create an {@link rx.plugins.RxJavaObservableExecutionHook}</p>
 *
 * <p> Usage - put the following code at the top of your Espresso test class:</p>
 *
 * <pre> {@code
 * @literal @Rule
 *   public TestRule rxRule = new RxIdlingResourceObservableTestRule();
 * } </pre>
 *
 */
public class RxIdlingResourceObservableTestRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                // setup espresso rx idling
                final CountingIdlingResource idlingResource =
                        new CountingIdlingResource("RxJava", true);

                RxIdlingResourceObservableHook observableHook = new RxIdlingResourceObservableHook(idlingResource);

                RxJavaHooks.setOnObservableStart(observableHook.onStart());
                RxJavaHooks.setOnObservableReturn(observableHook.onReturn());
                RxJavaHooks.setOnObservableSubscribeError(observableHook.onSubscribeError());

                Espresso.registerIdlingResources(idlingResource);

                // execute statement
                base.evaluate();

                Espresso.unregisterIdlingResources(idlingResource);
                RxJavaHooks.reset();
            }
        };
    }
}
