package uni.laboratorio.suresave.modelos;

import java.time.LocalDate;

public class Gastos {

    private String usuarioid;
    private String gastosid;
    //funcionaba con string total
    private double total;
    private String categoria;
    private long fecha;

    public Gastos() {
    }

    public Gastos(String usuarioid, String gastosid, double total, String categoria, long fecha) {
        this.usuarioid = usuarioid;
        this.gastosid = gastosid;
        this.total = total;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(String usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getGastosid() {
        return gastosid;
    }

    public void setGastosid(String gastosid) {
        this.gastosid = gastosid;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}
