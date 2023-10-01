package com.rkostiuk.cstask.test.util;

import com.rkostiuk.cstask.entity.User;

import java.util.function.Supplier;

public abstract class UserSupplierDecorator implements Supplier<User> {
    protected final Supplier<User> userSupplier;

    protected UserSupplierDecorator(Supplier<User> userSupplier) {
        this.userSupplier = userSupplier;
    }

    protected abstract void decorateCreated(User user);

    @Override
    public User get() {
        User user = userSupplier.get();
        decorateCreated(user);
        return user;
    }
}
