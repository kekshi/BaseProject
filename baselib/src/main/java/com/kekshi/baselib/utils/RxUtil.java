package com.kekshi.baselib.utils;

import com.elvishew.xlog.XLog;
import com.kekshi.baselib.base.BasePresenter;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public final class RxUtil {

    /**
     * Applies standard Schedulers to an {@link Observable}, ie IO for subscription, Main Thread for
     * onNext/onComplete/onError
     */
    public static <T> ObservableTransformer<T, T> applySchedulersToObservable() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(XLog::e);
    }

    /**
     * Applies standard Schedulers to an {@link Observable}, ie IO for subscription, Main Thread for
     * onNext/onComplete/onError
     */
    public static <T> ObservableTransformer<T, T> applySchedulersToConsumerObservable() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Applies standard Schedulers to a {@link Single}, ie IO for subscription, Main Thread for
     * onNext/onComplete/onError
     */
    public static <T> SingleTransformer<T, T> applySchedulersToSingle() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(XLog::e);
    }

    /**
     * Applies standard Schedulers to a {@link Completable}, ie IO for subscription,
     * Main Thread for onNext/onComplete/onError
     */
    public static CompletableTransformer applySchedulersToCompletable() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(XLog::e);
    }

    /**
     * Allows you to call two different {@link Observable} objects based on result of a predicate.
     */
    public static <T, R> Function<? super T, ? extends Observable<? extends R>> ternary(
            Function<T, Boolean> predicate,
            Function<? super T, ? extends Observable<? extends R>> ifTrue,
            Function<? super T, ? extends Observable<? extends R>> ifFalse) {
        return (item) -> predicate.apply(item)
                ? ifTrue.apply(item)
                : ifFalse.apply(item);
    }

    /**
     * Adds the subscription to the upstream {@link Observable} to the {@link CompositeDisposable}
     * supplied by a class extending {@link BasePresenter}. This allows the subscription to be
     * cancelled automatically by the Presenter on Android lifecycle events.
     *
     * @param presenter A class extending {@link BasePresenter}
     * @param <T>       The type of the upstream {@link Observable}
     */
    public static <T> ObservableTransformer<T, T> addObservableToCompositeDisposable(BasePresenter presenter) {
        return upstream -> upstream.doOnSubscribe(disposable ->
                presenter.getCompositeDisposable().add(disposable));
    }

    /**
     * Adds the subscription to the upstream {@link Completable} to the {@link
     * CompositeDisposable} supplied by a class extending {@link BasePresenter}. This allows the
     * subscription to be cancelled automatically by the Presenter on Android lifecycle events.
     *
     * @param presenter A class extending {@link BasePresenter}
     */
    public static CompletableTransformer addCompletableToCompositeDisposable(BasePresenter presenter) {
        return upstream -> upstream.doOnSubscribe(disposable ->
                presenter.getCompositeDisposable().add(disposable));
    }

    /**
     * Adds the subscription to the upstream {@link Single} to the {@link CompositeDisposable}
     * supplied by a class extending {@link BasePresenter}. This allows the subscription to be
     * cancelled automatically by the Presenter on Android lifecycle events.
     *
     * @param presenter A class extending {@link BasePresenter}
     * @param <T>       The type of the upstream {@link Single}
     */
    public static <T> SingleTransformer<T, T> addSingleToCompositeDisposable(BasePresenter presenter) {
        return upstream -> upstream.doOnSubscribe(disposable ->
                presenter.getCompositeDisposable().add(disposable));
    }

}

