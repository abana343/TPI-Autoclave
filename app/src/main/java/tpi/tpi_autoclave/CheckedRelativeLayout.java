package tpi.tpi_autoclave;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;

/**
 * Created by Javier Aros on 21-07-2015.
 */
public class CheckedRelativeLayout extends RelativeLayout implements Checkable {

    /**
     * Esta variable es la que nos sirve para almacenar el estado de este widget
     */
    private boolean _mChecked =false;

    /**
     * Este array se usa para que los drawables que se usen
     * reaccionen al cambio de estado especificado
     * En nuestro caso al "state_checked"
     * que es el que utilizamos en nuestro selector
     */
    private final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public CheckedRelativeLayout(Context context) {
        super(context);
    }

    public CheckedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Este método es el que cambia el estado de nuestro widget
     * @param checked true para activarlo y false para desactivarlo
     */
    @Override
    public void setChecked(boolean checked) {
        _mChecked = checked;
        //Cuando cambiamos el estado, debemos informar a los drawables
        //que este widget tenga vinculados
        refreshDrawableState();
        invalidate();
    }

    /**
     * Este método devuelve el estado de nuestro widget <img src="http://androcode.es/wp-includes/images/smilies/icon_smile.gif" alt=":)" class="wp-smiley">
     * @return true o false, no?
     */
    @Override
    public boolean isChecked() {
        return _mChecked;
    }

    /**
     * Este método cambia el estado de nuestro widget
     * Si estaba activo se desactiva y viceversa
     */
    @Override
    public void toggle() {
        setChecked(!_mChecked);
    }

    /**
     * Este método es un poco más complejo
     * Se encarga de combinar los diferentes "estados" de un widget
     * para informar a los drawables.
     *
     * Si nuestro widget está "checked" le añadimos el estado CHECKED_STATE_SET
     * que definimos al principio
     *
     * @return el array de estados de nuestro widget
     */
    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}