package Modelos;

public class Cliente {

    private String dpi;
    private String nombre;
    private String telefono;
    private String email;
    private String nacionalidad;
    private String fecha_nacimiento;

    public Cliente(String dpi, String nombre, String fecha_nacimiento, String telefono, String email, String nacionalidad) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.email = email;
        this.nacionalidad = nacionalidad;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFechaNacimiento() {
        return fecha_nacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fecha_nacimiento = fechaNacimiento;
    }
}
