package nl.devpieter.healthtags.Screens.Widgets;

import org.jetbrains.annotations.Nullable;

public interface ICallback<T> {

    /***
     * Called when the callback is triggered.
     * @param value The value of the callback.
     */
    void onCallback(@Nullable T value);
}
