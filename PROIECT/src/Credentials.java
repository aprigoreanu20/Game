public class Credentials {
    private String email, password;

    public Credentials(String email, String password) {
        this.password = password;
        this.email = email;
    }

    // getters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // setters
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // metoda equals - folosita pentru validarea inputului dat de user la log in
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Credentials c)){
            return false;
        }
        return email.equals(c.getEmail()) && password.equals(c.getPassword());
    }
}
