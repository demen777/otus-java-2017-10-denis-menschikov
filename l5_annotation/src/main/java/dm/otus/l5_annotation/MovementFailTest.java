package dm.otus.l5_annotation;

import dm.otus.l5_annotation.annotations.After;
import dm.otus.l5_annotation.annotations.Before;
import dm.otus.l5_annotation.annotations.Test;

public class MovementFailTest {

    Movement movement;

    @Before
    public void initialState(){
        movement = new Movement();
    }

    @Test
    public void stop_fail(){
        movement.move(10.0, 6.0);
        final boolean isStopped = movement.stop(3.0);
        assert isStopped;
    }

    @After
    public void endState(){
        System.out.printf("X=%f velocity=%f time=%f\n", movement.getX(), movement.getVelocity(),
                movement.getCurTimestamp());
    }

}
