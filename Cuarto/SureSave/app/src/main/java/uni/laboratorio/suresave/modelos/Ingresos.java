package uni.laboratorio.suresave.modelos;

import java.time.LocalDate;

public class Ingresos {

    private String usuarioid;
    private String ingresosid;
    private int total;
    private String categoria;
    private LocalDate date;

    public Ingresos() {
    }

    public Ingresos(String usuarioid, String ingresosid, int total, String categoria, LocalDate date) {
        this.usuarioid = usuarioid;
        this.ingresosid = ingresosid;
        this.total = total;
        this.categoria = categoria;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
