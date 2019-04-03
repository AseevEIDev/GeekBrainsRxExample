package com.speakapp.rules;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RxImmediateSchedulerRule implements TestRule {

  private static final TestScheduler TEST_SCHEDULER = new TestScheduler();
  private static final Scheduler IMMEDIATE_SCHEDULER = new Scheduler() {
    @Override
    public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
      return super.scheduleDirect(run, 0, unit);
    }

    @Override
    public Worker createWorker() {
      return new ExecutorScheduler.ExecutorWorker(Runnable::run, false);
    }
  };

  private List<Throwable> list;

  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        list = Collections.synchronizedList(new ArrayList<>());
        RxJavaPlugins.setErrorHandler(list::add);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> IMMEDIATE_SCHEDULER);
        RxJavaPlugins.setComputationSchedulerHandler(
            scheduler -> IMMEDIATE_SCHEDULER);
        RxJavaPlugins.setNewThreadSchedulerHandler(
            scheduler -> IMMEDIATE_SCHEDULER);
        try {
          base.evaluate();
        } finally {
          RxJavaPlugins.reset();
        }
      }
    };
  }

  public boolean hasErrors() {
    return !list.isEmpty();
  }

  public TestScheduler getTestScheduler() {
    return TEST_SCHEDULER;
  }
}