package tfs.converter.base;

/**
 * Created by sinopsys on 9/12/18.
 */

/**
 * A base class for all presentors. In this case, there exists only 1 singleton presentor.
 *
 * @param <T> View to which the presentor is binded.
 */
public abstract class BasePresenter<T extends BaseView> {

    private T view;

    public void attachView(T view) {
        if (this.view == null) {
            this.view = view;
        }
    }

    public void detatchView() {
        if (this.view != null) {
            this.view = null;
        }
    }

    public T getView() {
        return view;
    }
}


// EOF
