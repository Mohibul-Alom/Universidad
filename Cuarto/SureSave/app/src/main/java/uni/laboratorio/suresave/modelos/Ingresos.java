package uni.laboratorio.suresave.modelos;

public class Ingresos {

    private String usuarioid;
    private String ingresosid;
    private double total;
    private String categoria;
    private long date;

    public Ingresos() {
    }

    public Ingresos(String usuarioid, String ingresosid, double total, String categoria, long date) {
        this.usuarioid = usuarioid;
        this.ingresosid = ingresosid;
        this.total = total;
        this.categoria = categoria;
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(String usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getIngresosid() {
        return ingresosid;
    }

    public void setIngresosid(String ingresosid) {
        this.ingresosid = ingresosid;
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
