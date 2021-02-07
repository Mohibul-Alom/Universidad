package uni.laboratorio.suresave.modelos;

public class Movimiento implements Comparable<Movimiento> {
    //booleano para indicar si es gasto o ingreso
    private boolean tipo;
    //string categoria para detallar el movimiento
    private String categoria;
    //double para almacenar la cantidad
    private double cantidad;
    //fecha del movimiento que luego habria que pasarlo a date
    private long fecha;
    //id del gasto o ingreso en concreto para poder buscar mas informacion si se requiere
    private String idMovimiento;

    public Movimiento() {
    }

    public Movimiento(boolean tipo, String categoria, double cantidad, long fecha, String idMovimiento) {
        this.tipo = tipo;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idMovimiento = idMovimiento;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    @Override
    public int compareTo(Movimiento o) {
        return Long.compare(this.fecha,o.getFecha());
    }
}
