package Domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test", schema = "k19", catalog = "")
public class TestEntity {
    private String nome;
    private String pass;

    @Basic
    @Column(name = "nome", nullable = true, length = 20)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "pass", nullable = true, length = 20)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestEntity that = (TestEntity) o;

        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }
}
