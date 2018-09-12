package tfs.converter.base;

/**
 * Created by sinopsys on 9/12/18.
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

    public boolean isViewAttached() {
        return view != null;
    }
}


// EOF
