package nl.devpieter.healthtags.Screens.Widgets;

public interface ICallback<T> {

    /***
     * Called when the callback is triggered.
     * @param value The value of the callback.
     */
    void onCallback(T value);
}
