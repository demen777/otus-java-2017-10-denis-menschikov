package dm.otus.l5_annotation;

@SuppressWarnings("unused")
public class Movement {
    private double velocity;
    private double curTimestamp;
    private double x;

    public void move(double duration, double acceleration){
        x += velocity*duration + acceleration*duration*duration/2;
        velocity += acceleration*duration;
        curTimestamp += duration;
    }

    public boolean stop(double acceleration){
        if(MathUtil.doubleEquality(velocity, 0.0)) return true;
        if(MathUtil.doubleEquality(acceleration, 0.0)) return false;
        if((acceleration > 0.0 && velocity > 0.0) || (acceleration < 0.0 && velocity < 0.0)) {
            return false;
        }
        final double duration = Math.abs(velocity/acceleration);
        move(duration, acceleration);
        return true;
    }

    //<editor-fold desc="Getter">
    public double getVelocity() {
        return velocity;
    }

    public double getCurTimestamp() {
        return curTimestamp;
    }

    public double getX() {
        return x;
    }
    //</editor-fold>
}
