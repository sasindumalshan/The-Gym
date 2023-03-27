package lk.ijse.theGym.util.Alert;

import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;

public class AlertToolkit implements EventTarget {
    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return null;
    }
    public final void show(){}
}
