package dm.otus.l5_annotation;

import dm.otus.l5_annotation.annotations.After;
import dm.otus.l5_annotation.annotations.Before;
import dm.otus.l5_annotation.annotations.Test;

import static dm.otus.l5_annotation.MathUtil.doubleEquality;

@SuppressWarnings("ALL")
public class MovementTest {
    Movement movement;

    @Before
    public void initialState(){
        movement = new Movement();
    }

    @Test
    public void move(){
        movement.move(10.0, 5.0);
        assert doubleEquality(movement.getVelocity(), 50.0);
        assert doubleEquality(movement.getCurTimestamp(), 10.0);
        assert doubleEquality(movement.getX(), 250.0);
        movement.move(1.0, 2.0);
        assert doubleEquality(movement.getVelocity(), 52.0);
        assert doubleEquality(movement.getCurTimestamp(), 11.0);
        assert doubleEquality(movement.getX(), 301.0);
    }

    @Test
    public void stop(){
        movement.move(10.0, 6.0);
        final boolean isStopped = movement.stop(-3.0);
        assert isStopped;
        assert doubleEquality(movement.getCurTimestamp(), 30.0);
    }

    @Test
    public void stop_fail(){
        movement.move(10.0, 6.0);
        final boolean isStopped = movement.stop(3.0);
        assert !isStopped;
    }

    @After
    public void endState(){
        System.out.printf("X=%f velocity=%f time=%f\n", movement.getX(), movement.getVelocity(),
                movement.getCurTimestamp());
    }

}
