package co.netguru.android.socialslack

import io.reactivex.Scheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import io.reactivex.android.plugins.RxAndroidPlugins

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.disposables.Disposable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class RxImmediateSchedulerRule : TestRule {

    private val immediate = object : Scheduler() {

        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { immediate }
                RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
                RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
