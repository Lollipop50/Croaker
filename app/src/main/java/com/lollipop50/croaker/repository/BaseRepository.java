package com.lollipop50.croaker.repository;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseRepository implements Repository {

    private final Set<Listener> listeners = new HashSet<>();

    @Override
    public final void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public final void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public final void notifyListeners() {
        for (Listener listener : listeners) {
            listener.onDataChanged();
        }
    }
}
