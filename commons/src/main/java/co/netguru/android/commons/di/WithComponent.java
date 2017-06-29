package co.netguru.android.commons.di;

public interface WithComponent<T extends BaseComponent> {

    T getComponent();
}
