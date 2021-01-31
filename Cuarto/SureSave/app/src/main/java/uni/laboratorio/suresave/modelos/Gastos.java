package uni.laboratorio.suresave.modelos;

import java.time.LocalDate;

public class Gastos {

    private String usuarioid;
    private String gastosid;
    private int total;
    private String categoria;
    private LocalDate date;

    public Gastos() {
    }

    public Gastos(String usuarioid, String gastosid, int total, String categoria, LocalDate date) {
        this.usuarioid = usuarioid;
        this.gastosid = gastosid;
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

    public String getGastosid() {
        return gastosid;
    }

    public void setGastosid(String gastosid) {
        this.gastosid = gastosid;
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
