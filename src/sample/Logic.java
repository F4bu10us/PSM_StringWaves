package sample;

class Logic{
    private static final double PI = Math.PI;
    private int size = 41;
    private final double dX = PI/20;
    double[] x = new double[size];
    double[] f = new double[size];
    private double[] v = new double[size];
    private double[] a = new double[size];

    Logic(){
        x[0] = 0;
        for(int i = 0; i<f.length; ++i){
            f[i] = Math.sin(dX*i);
            v[i] = 0;
            if(i!=0)x[i] = x[i-1] + dX;
        }
        duplicate();
    }

    void move(){
        for(int i = 0; i<v.length; ++i){
            double dt = 0.05;
            v[i]+=a[i]* dt;
            f[i]+=v[i]* dt;
        }
        duplicate();
    }

    private void duplicate() {
        for(int i = 0; i<a.length; ++i){
            if(i==0||i==size-1){
                a[i] = 0;
                continue;
            }
            a[i] = (f[i+1]-2*f[i]+f[i-1])/Math.pow(dX,2);
        }
    }
}
