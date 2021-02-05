package uni.laboratorio.suresave.modelos;

public class Usuario {

    private String id;
    private String nombre;
    private String email;
    private String imagenUrl;
    private double gastos;
    private double ingresos;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String email, String imagenUrl, double gastos, double ingresos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.imagenUrl = imagenUrl;
        this.gastos = gastos;
        this.ingresos = ingresos;
    }

    public double getGastos() {
        return gastos;
    }

    public void setGastos(double gastos) {
        this.gastos = gastos;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }


}
